package Interface.Controllers;

import RestaurantModel.RestaurantObjects.Order;
import RestaurantModel.Interfaces.RestaurantModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
    private Button takeOrderButton;

    @FXML
    private ListView prevOrderList;
    @FXML
    private ListView postOrderList;

    private RestaurantModel restaurant;

    public void init(RestaurantModel restaurant){
        this.restaurant = restaurant;
        prevOrderList.setItems(restaurant.getOrdersAtStage("Cooked"));

        postOrderList.setItems(restaurant.getOrdersAtStage("Completed"));

        restaurant.getOrdersAtStage("Cooked").addListener((ListChangeListener) c -> {
            if (prevOrderList.getItems().isEmpty()){
                takeOrderButton.setDisable(false);
            }else{
                takeOrderButton.setDisable(true);
            }
        });
    }

    public void initialize(){
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
        if (!postOrderList.getSelectionModel().isEmpty()){
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
}
