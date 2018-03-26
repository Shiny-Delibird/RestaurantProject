package RestaurantModel.Interfaces;

import RestaurantModel.RestaurantObjects.Food;
import javafx.collections.ObservableMap;

import java.util.Map;
import java.util.Set;

public interface InventorySystem {

    void checkIntegrity(Set<String> ingredients);

    void useIngredients(Map<String, Integer> used);

    void receiveShipment(Map<String, Integer> shipment);

    ObservableMap<String, Integer> getInventory();

    String toString();

    boolean hasEnough(Food food);
}
