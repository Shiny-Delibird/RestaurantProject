package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;


public class ReceiveShipmentsController implements WorkerController {
	public Scene previousScene;
	@FXML
	private ListView<String> ingredientsList;
	@FXML
	private ListView<String> shipmentsList;
	@FXML
	private TextField quantityBox;

	@FXML
    private TextField searchBar;

	private FilteredList<String> ingredientsFiltered;

	private ObservableMap<String, Integer> shipment;
	private RestaurantModel restaurant;

	@Override
	public void init(RestaurantModel restaurant) {
		this.restaurant = restaurant;
        ObservableList<String> ingredientNames = FXCollections.observableArrayList(restaurant.getInventory().keySet()).sorted();
		ingredientsFiltered = new FilteredList<String>(ingredientNames);
		ingredientsList.setItems(ingredientsFiltered);
		shipment = FXCollections.observableHashMap();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> ingredientsFiltered.setPredicate(s -> {
            String lowerCaseSearch = newValue.toLowerCase();
            return s.contains(lowerCaseSearch);
        }));
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

	public void confirmShipment(ActionEvent event){
        restaurant.receiveShipment(shipment);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setScene(previousScene);
	}}
