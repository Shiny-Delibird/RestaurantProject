package RestaurantModel.Interfaces;

import RestaurantModel.RestaurantObjects.Food;
import javafx.collections.ObservableMap;

import java.util.Map;
import java.util.Set;

/*
* @startuml
* Interface InventorySystem{
* +checkIntegrity(ingredients: Set<String>): void
* +useIngredients(used: Map<String, Integer>): void
* +receiveShipment(shipment: Map<String, Integer>): void
* +getInventory(): ObservableMap<String, Integer>
* +toString(): String
* +hasEnough(food: Food): boolean
* +getCalories(food: Food): int
* }
* @enduml
 */

public interface InventorySystem {

    void checkIntegrity(Set<String> ingredients);

    void useIngredients(Map<String, Integer> used);

    void receiveShipment(Map<String, Integer> shipment);

    ObservableMap<String, Integer> getInventory();

    String toString();

    boolean hasEnough(Food food);
}
