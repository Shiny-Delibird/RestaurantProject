package RestaurantModel;

import javafx.collections.ObservableList;

import java.util.Map;

public interface RestaurantInterface {

    Map<String, Food> getMenu();

    Map<String, Integer> getInventory();

    ObservableList getOrdersAtStage(String stage);

    void placeOrder(Order order);

    void confirmOrder(Order order);

    void cookOrder(Order order);

    void receiveOrder(Order order);

    String rejectOrder(Order order);

    String requestBill(Order order);

    void receiveShipment(Map<String, Integer> shipment);
}
