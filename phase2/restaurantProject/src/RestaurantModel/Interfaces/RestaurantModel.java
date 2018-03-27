package RestaurantModel.Interfaces;

import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Map;

public interface RestaurantModel {

    ObservableMap<String, Food> getMenu();

    ObservableMap<String, Integer> getInventory();

    ObservableList getOrdersAtStage(String stage);

    // a method to submit the order to the OrderManager
    void placeOrder(Order order);

    // a method for the cooks to confirm that an order as been cooked
    void confirmOrder(Order order);

    // a method to for the cooks to cook the order
    void cookOrder(Order order);

    // a method to indicate that the customers have received the order
    void receiveOrder(Order order);

    // a method to indicate that the customers have rejected the order
    void rejectOrder(Order order);

    String requestBill(Order order);

    void receiveShipment(Map<String, Integer> shipment);

    Boolean hasEnough(Food food);

    int getCalories(Food food);

}
