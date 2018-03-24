package Interface.Views;

import RestaurantModel.Order;
import RestaurantModel.RestaurantModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ServerController implements EmployeeController{

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

    public void init(ObservableList<Order> prevList, ObservableList<Order> postList, RestaurantModel restaurant){
        prevOrderList.setItems(prevList);
        postOrderList.setItems(postList);
        this.restaurant = restaurant;
    }

    public void initialize(){
        prevOrderButton.setOnAction(event -> deliverOrder());
        postOrderButton.setOnAction(event -> giveBill());
        takeOrderButton.setOnAction(event -> takeOrder());
    }

    private void deliverOrder(){

    }

    private void giveBill(){

    }

    private void takeOrder(){

    }
}
