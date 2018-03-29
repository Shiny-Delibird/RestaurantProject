package RestaurantModel.Interfaces;

import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.ObservableList;

public interface OrderSystem {
    ObservableList<Order> getPendingOrders();

    ObservableList<Order> getOrdersInProgress();

    ObservableList<Order> getCookedOrders();

    ObservableList<Order> getCompletedOrders();

    void placeOrder(Order order);

    void acceptOrder(Order order);

    void orderIsCooked(Order order);

    void retrieveOrder(Order order);

    void confirmCompleted(Order order);
}
