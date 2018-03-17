package Interface;

import RestaurantModel.Food;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class testList {

    @FXML
    private ListView foods;

    @FXML
    private ListView ingredients;

    public void init(ObservableMap<String, Food> menu){
        foods.getItems().setAll(menu.keySet());

        menu.addListener(new MapChangeListener() {
            @Override
            public void onChanged(Change change) {
                foods.getItems().removeAll(change);
                if (change.wasAdded()){
                    foods.getItems().add(change.getKey());
                }
            }
        });

        foods.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Food chosenFood = menu.get(newValue);
                ingredients.getItems().setAll(chosenFood.getIngredients().keySet());
            }
        });
    }

}
