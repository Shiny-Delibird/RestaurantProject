package Interface.Controllers;

import RestaurantModel.RestaurantObjects.Order;
import RestaurantModel.Interfaces.RestaurantModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController implements EmployeeController{

    @FXML
    private Label prevLabel;
    @FXML
    private Label postLabel;

    @FXML
    private Button receiveShipment;
    @FXML
    private Button takeOrderButton;
    @FXML
    private Button prevOrderButton;
    @FXML
    private Button postOrderButton;

    @FXML
    private ListView prevOrderList;
    @FXML
    private ListView postOrderList;

    private RestaurantModel restaurant;

    public void init(RestaurantModel restaurant){
        prevOrderList.setItems(restaurant.getOrdersAtStage("Cooked"));

        postOrderList.setItems(restaurant.getOrdersAtStage("Completed"));
        this.restaurant = restaurant;
    }

    public void initialize(){
        prevOrderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        if (!prevOrderList.getSelectionModel().isEmpty()){
            Order order = (Order) postOrderList.getSelectionModel().getSelectedItem();

            //TODO ADD SOME WAY TO DISPLAY BILL
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
}
