package Interface.Controllers;

import RestaurantModel.RestaurantObjects.Order;
import RestaurantModel.Interfaces.RestaurantModel;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/*
* @startuml
* class ServerController{
* -receiveShipment: Button
* -prevLabel: Label
* -postLabel: Label
* -prevOrderButton: Button
* -postOrderButton: Button
* -takeOrderButton: Button
* prevOrderList: ListView
* -postOrderList: ListView
* -restaurant: RestaurantModel
* +init(restaurant: RestaurantModel): void
* +initialize(): void
* +receiveShipment(event: ActionEvent):void
* +cancelOrder():void
* }
* @enduml
 */


public class ServerController implements WorkerController {

    @FXML
    private Label prevLabel;
    @FXML
    private Label postLabel;

    @FXML
    private Button receiveShipment;
    @FXML
    private Button prevOrderButton;
    @FXML
    private Button postOrderButton;
    @FXML
    private Button selectOrdersButton;
    private Button takeOrderButton;

    @FXML
    private ListView prevOrderList;
    @FXML
    private ListView postOrderList;

    @FXML
    private TextField tableForOrders;

    private RestaurantModel restaurant;

    private int serverID;
    private static int totalServers = 1;

    public void init(RestaurantModel restaurant){
        this.restaurant = restaurant;
        prevOrderList.setItems(restaurant.getOrdersAtStage("Cooked"));

        postOrderList.setItems(restaurant.getOrdersAtStage("Completed"));

        restaurant.getOrdersAtStage("Cooked").addListener((ListChangeListener) c -> {
            if (prevOrderList.getItems().isEmpty()){
                takeOrderButton.setDisable(false);
            }else{
                for (Object order : restaurant.getOrdersAtStage("Cooked")){
                    if (((Order) order).getServerNumber() == serverID ){
                        takeOrderButton.setDisable(true);
                        return;
                    }
                }
            }
        });
    }

    public void initialize(){
        serverID = totalServers;
        totalServers += 1;

        takeOrderButton = new Button();
        ((GridPane) receiveShipment.getParent()).add(takeOrderButton, 0, 1);
        takeOrderButton.setText("Take Order");
        GridPane.setHalignment(takeOrderButton, HPos.CENTER);

        prevOrderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        postOrderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        prevLabel.setText("To Deliver");
        postLabel.setText("To be Billed");
        takeOrderButton.setVisible(true);
        prevOrderButton.setText("Deliver Order");
        postOrderButton.setText("Give Bill");

        prevOrderButton.setOnAction(event -> deliverOrder());
        postOrderButton.setOnAction(event -> giveBill());
        takeOrderButton.setOnAction(this::takeOrder);
        selectOrdersButton.setOnAction(event -> selectOrdersAtTable());

        tableForOrders.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tableForOrders.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void deliverOrder(){
        if (!prevOrderList.getSelectionModel().isEmpty()){
            ObservableList<Order> orders = prevOrderList.getSelectionModel().getSelectedItems();

            for (Object order : orders){
                restaurant.receiveOrder((Order) order);
            }
        }
    }

    private void giveBill(){
        if (!postOrderList.getSelectionModel().isEmpty() && takeOrderButton.isVisible()){
            try {
                ObservableList<Order> orders = postOrderList.getSelectionModel().getSelectedItems();

                FXMLLoader billFragmentLoader = new FXMLLoader(getClass().getResource("/Interface/Views/BillFragment.fxml"));
                Parent billRoot = billFragmentLoader.load();
                BillController billController = billFragmentLoader.getController();
                billController.init(orders, restaurant, takeOrderButton);

                takeOrderButton.setVisible(false);
                ((GridPane) prevOrderList.getParent()).add(billRoot, 0, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void takeOrder(ActionEvent event){
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sourceScene = ((Node) event.getSource()).getScene();

        try {
            FXMLLoader takeOrderLoader = new FXMLLoader(getClass().getResource("/Interface/Views/TakeOrder.fxml"));
            Parent root = takeOrderLoader.load();

            TakeOrderController takeOrderController = takeOrderLoader.getController();
            takeOrderController.previousScene = sourceScene;
            takeOrderController.init(restaurant);
            takeOrderController.serverNumberPlacingOrder = serverID;

            sourceStage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveShipment(ActionEvent event){
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sourceScene = ((Node) event.getSource()).getScene();

        try {
            FXMLLoader takeOrderLoader = new FXMLLoader(getClass().getResource("/Interface/Views/ReceiveShipment.fxml"));
            Parent root = takeOrderLoader.load();

            ReceiveShipmentsController receiveShipmentsController = takeOrderLoader.getController();
            receiveShipmentsController.previousScene = sourceScene;
            receiveShipmentsController.init(restaurant);

            sourceStage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(){
        if (!prevOrderList.getSelectionModel().isEmpty()){
            Order order = (Order) prevOrderList.getSelectionModel().getSelectedItem();
            restaurant.rejectOrder(order);
        }
    }

    public void selectOrdersAtTable(){
        if (!tableForOrders.getText().isEmpty()){
            postOrderList.getSelectionModel().clearSelection();
            for (Object order : postOrderList.getItems()){
                if (((Order) order).getTableNumber() == Integer.parseInt(tableForOrders.getText())){
                    postOrderList.getSelectionModel().select(order);
                }
            }
        }
    }
}
