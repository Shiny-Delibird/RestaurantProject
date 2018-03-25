package RestaurantModel;

import java.util.Map;
import java.util.Set;

public interface InventorySystem {

    void checkIntegrity(Set<String> ingredients);

    void useIngredients(Map<String, Integer> used);

    void receiveShipment(Map<String, Integer> shipment);

    Map<String, Integer> getInventory();

    String toString();
}
