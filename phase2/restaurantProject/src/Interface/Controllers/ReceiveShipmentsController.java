package Interface.Controllers;

import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import RestaurantModel.Interfaces.RestaurantModel;
import javafx.beans.value.ObservableMapValue;
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
import java.util.regex.Pattern;
import java.util.HashMap;


public class ReceiveShipmentsController implements EmployeeController{
	public Scene previousScene;
	@FXML
	private ListView<String> ingredientsList;
	@FXML
	private ListView<String> shipmentsList;
	@FXML
	private TextField quantityBox;

	private ObservableMap<String, Integer> shipment;
	private RestaurantModel restaurant;

	@Override
	public void init(RestaurantModel restaurant) {
		this.restaurant = restaurant;
		ingredientsList.getItems().setAll(getFixedInventory(restaurant.getInventory()));
		shipment = FXCollections.observableHashMap();
	}

	@FXML
	private void addIngredient(){
		if (ingredientsList.getSelectionModel().isEmpty()){
			return;
		}
		String selectedIngredient = ingredientsList.getSelectionModel().getSelectedItem();
		if (!quantityBox.getText().isEmpty()){
			String text = quantityBox.getText();
			if (Pattern.matches("[0-9]*$", text)) {
				int quantity = Integer.parseInt(quantityBox.getText());
				shipment.put(selectedIngredient, quantity);
				shipmentsList.getItems().add(selectedIngredient + " x " + String.valueOf(quantity));
			}
		}
	}

	public void removeIngredient(){
		if (shipmentsList.getSelectionModel().isEmpty()){
			return;
		}

		String selectedIngredient = shipmentsList.getSelectionModel().getSelectedItem();
		shipmentsList.getItems().remove(selectedIngredient);

	}
	private ObservableList<String> getFixedInventory(ObservableMap<String, Integer> inventory){
		ObservableList<String> fixedInventory= FXCollections.observableArrayList();
		fixedInventory.addAll(inventory.keySet());
		return fixedInventory;
	}

	public void confirmShipment(ActionEvent event){
		if (!shipmentsList.getItems().isEmpty()){
			restaurant.receiveShipment(shipment);
			((Stage) ((Node) event.getSource()).getScene().getWindow()).setScene(previousScene);
		}
	}
}
