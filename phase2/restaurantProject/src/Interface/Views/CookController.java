package Interface.Views;

import RestaurantModel.Order;
import RestaurantModel.RestaurantModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CookController implements EmployeeController{

    @FXML
    private Label prevLabel;
    @FXML
    private Label postLabel;

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

    public  void init(RestaurantModel restaurant){
        prevOrderList.setItems(restaurant.getOrdersAtStage("pending"));
        postOrderList.setItems(restaurant.getOrdersAtStage("in progress"));
        this.restaurant = restaurant;
    }

    public void initialize() {
        prevLabel.setText("To Confirm");
        postLabel.setText("To Cook");
        prevOrderButton.setText("Confirm Order");
        postOrderButton.setText("Cook Order");

        prevOrderButton.setOnAction(event -> confirmOrder());
        postOrderButton.setOnAction(event -> cookOrder());
    }

    private void cookOrder() {
        if (!prevOrderList.getSelectionModel().isEmpty()){
            ObservableList<Order> orders = prevOrderList.getSelectionModel().getSelectedItems();

            for (Order order : orders){
                restaurant.cookOrder(order);
            }
        }
    }

    private void confirmOrder() {
        if (!prevOrderList.getSelectionModel().isEmpty()){
            ObservableList<Order> orders = prevOrderList.getSelectionModel().getSelectedItems();

            for (Order order : orders){
                restaurant.confirmOrder(order);
            }
        }
    }
}