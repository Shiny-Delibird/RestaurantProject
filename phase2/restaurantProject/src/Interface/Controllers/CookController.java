package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;
import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CookController implements EmployeeController{

    @FXML
    private Label prevLabel;
    @FXML
    private Label postLabel;
    @FXML
    private Label infoLabel;

    @FXML
    private Button receiveShipment;
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
        prevOrderList.setItems(restaurant.getOrdersAtStage("Pending"));
        postOrderList.setItems(restaurant.getOrdersAtStage("InProgress"));
        this.restaurant = restaurant;
    }

    public void initialize() {
        Label infoTitle = new Label();
        ((GridPane) receiveShipment.getParent()).add(infoTitle, 0, 0);
        infoTitle.setText("Order Info");
        ((GridPane) receiveShipment.getParent()).setHalignment(infoTitle, HPos.CENTER);

        prevLabel.setText("To Confirm");
        postLabel.setText("To Cook");
        prevOrderButton.setText("Confirm Order");
        postOrderButton.setText("Cook Order");

        prevOrderButton.setOnAction(event -> confirmOrder());
        postOrderButton.setOnAction(event -> cookOrder());

        prevOrderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayOrderInfo((Order) newValue); });
    }

    private void displayOrderInfo(Order order) {
        if (order != null){
            StringBuilder instructions = new StringBuilder();

            if (!order.getInstructions().isEmpty()){
                instructions.append(order.getInstructions());
                instructions.append("\n\n");
            }

            for (Food food : order.getFoods()){
                if (food.getInstructions() != null || !food.getChangedIngredients().isEmpty()){
                    instructions.append(food.getName() + ": ");
                    if (food.getInstructions() != null){
                        instructions.append(food.getInstructions());
                        instructions.append("\n");
                    }
                    for (String ingredient : food.getChangedIngredients().keySet()){
                        int changedAmount = food.getChangedIngredients().get(ingredient);
                        if (changedAmount > 0){
                            instructions.append("Add " + ingredient + " x " + Integer.toString(changedAmount) + "\n");
                        } else if (changedAmount < 0){
                            instructions.append("Remove " + ingredient + " x " + Integer.toString(Math.abs(changedAmount)) + "\n");
                        }
                    }
                }
            }

            infoLabel.setText(instructions.toString());
        }
    }

    private void cookOrder() {
        if (!postOrderList.getSelectionModel().isEmpty()){
            ObservableList<Order> orders = postOrderList.getSelectionModel().getSelectedItems();

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
}