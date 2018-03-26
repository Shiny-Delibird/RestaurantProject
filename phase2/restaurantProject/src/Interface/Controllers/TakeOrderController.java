package Interface.Controllers;

import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import RestaurantModel.Interfaces.RestaurantModel;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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

        menuList.getItems().setAll(getFixedMenu(restaurant.getMenu()));
        restaurant.getMenu().addListener((MapChangeListener<String, Food>) change -> {
            if (change.wasAdded()){
                menuList.getItems().setAll(getFixedMenu(restaurant.getMenu()));
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
        if (orderList.getSelectionModel().isEmpty()){
            return;
        }

        Food selectedFood = (Food) orderList.getSelectionModel().getSelectedItem();
        String selectedIngredient = "";
        Integer selectedItemIndex = null;

        if (checkIfIngredientValid(ingredientBox.getText())){
            selectedIngredient = ingredientBox.getText();
        } else if (!ingredientList.getSelectionModel().isEmpty()){
            selectedIngredient = ((String) ingredientList.getSelectionModel().getSelectedItem()).split("x")[0].trim();
            selectedItemIndex = (Integer) ingredientList.getSelectionModel().getSelectedIndices().get(0);
        } else{
            return;
        }


        int restaurantAmount = restaurant.getInventory().get(selectedIngredient);

        if (restaurantAmount <= 0){
            return;
        }
        if ((restaurantAmount > 0 && !selectedFood.getIngredients().containsKey(selectedIngredient)) ||
                restaurantAmount > selectedFood.getIngredients().get(selectedIngredient)){
            selectedFood.addIngredient(selectedIngredient, 1);
            ingredientList.setItems(getFixedListFromFood(selectedFood));
        }

        if (selectedItemIndex != null){
            ingredientList.getSelectionModel().selectIndices(selectedItemIndex);
        }
    }

    public void removeIngredient() {
        if (orderList.getSelectionModel().isEmpty()){
            return;
        }

        Food selectedFood = (Food) orderList.getSelectionModel().getSelectedItem();
        String selectedIngredient = "";
        Integer selectedItemIndex = null;

        if (checkIfIngredientValid(ingredientBox.getText())){
            selectedIngredient = ingredientBox.getText();
        } else if (!ingredientList.getSelectionModel().isEmpty()){
            selectedIngredient = ((String) ingredientList.getSelectionModel().getSelectedItem()).split("x")[0].trim();
            selectedItemIndex = (Integer) ingredientList.getSelectionModel().getSelectedIndices().get(0);
        }

        selectedFood = (Food) orderList.getSelectionModel().getSelectedItem();
        selectedFood.removeIngredient(selectedIngredient, 1);

        ingredientList.setItems(getFixedListFromFood(selectedFood));

        if (selectedItemIndex != null){
            ingredientList.getSelectionModel().selectIndices(selectedItemIndex);
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

    private ObservableList getFixedMenu(ObservableMap<String, Food> menu){
        ObservableList fixedMenu = FXCollections.observableArrayList();
        for (String item : menu.keySet()){
            if (restaurant.hasEnough(menu.get(item))){
                fixedMenu.add(item);
            }
        }
        return fixedMenu;
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
