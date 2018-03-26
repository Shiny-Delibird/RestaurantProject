package Interface.Views;

import RestaurantModel.Food;
import RestaurantModel.Order;
import RestaurantModel.RestaurantModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.soap.Text;


public class TakeOrderController implements EmployeeController{

    public Scene previousScene;

    @FXML
    private ListView menuList;
    @FXML
    private ListView orderList;
    @FXML
    private ListView ingredientList;

    @FXML
    private TextField tableNumberInput;
    @FXML
    private TextField ingredientBox;

    private Order order;
    private RestaurantModel restaurant;

    @Override
    public void init(RestaurantModel restaurant) {
        order = new Order();
        this.restaurant = restaurant;

        menuList.getItems().setAll(restaurant.getMenu().keySet());
        restaurant.getMenu().addListener((MapChangeListener<String, Food>) change -> {
            if (change.wasAdded()){
                menuList.getItems().setAll(restaurant.getMenu().keySet());
            }
        });

        orderList.setItems(order.getFoods());

        tableNumberInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tableNumberInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        orderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                ingredientList.setItems(TakeOrderController.this.getFixedListFromFood((Food) newValue));
            }
            }
        );


    }

    @FXML
    private void addFood(){
        Food selectedFood = restaurant.getMenu().get(menuList.getSelectionModel().getSelectedItem());
        order.addFood(new Food(selectedFood));
    }

    private Boolean checkIfIngredientValid(String ingredient){
        return restaurant.getInventory().containsKey(ingredient);
    }

    public void addIngredient() {
        if (!orderList.getSelectionModel().isEmpty() && checkIfIngredientValid(ingredientBox.getText())){
            Food selectedFood = (Food) orderList.getSelectionModel().getSelectedItem();
            selectedFood.addIngredient(ingredientBox.getText(), 1);

            ingredientList.setItems(getFixedListFromFood(selectedFood));
        }
    }

    public void removeIngredient() {
        if (!orderList.getSelectionModel().isEmpty() && checkIfIngredientValid(ingredientBox.getText())){
            Food selectedFood = (Food) orderList.getSelectionModel().getSelectedItem();
            selectedFood.removeIngredient(ingredientBox.getText(), 1);

            ingredientList.setItems(getFixedListFromFood(selectedFood));
        }
    }

    private ObservableList getFixedListFromFood(Food food){
        ObservableList fixedIngredients = FXCollections.observableArrayList();
        for (String ingredient : food.getIngredients().keySet()){
            fixedIngredients.add(ingredient + " x " + food.getIngredients().get(ingredient));
        }
        ingredientList.getItems().setAll(fixedIngredients);

        return fixedIngredients;
    }


    public void submitOrder(ActionEvent event) {
        if (!tableNumberInput.getText().isEmpty()){
            order.setTableNumber(Integer.parseInt(tableNumberInput.getText()));
            restaurant.placeOrder(order);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).setScene(previousScene);
        }
    }

    public void removeFood() {
        Food food = (Food) orderList.getSelectionModel().getSelectedItem();
        order.removeFood(food);
        ingredientList.getItems().clear();
    }
}
