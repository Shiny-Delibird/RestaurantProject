package RestaurantModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * The RestaurantModel.OrderManager class. Keeps track of Orders in their various stages of completion and updates their statuses
 * when necessary
 * */

public class OrderManager {
    private ObservableList<Order> pendingOrders; // server placed order, cooking not started
    private ObservableList<Order> ordersInProgress; // cook confirmed order, cooking in progress
    private ObservableList<Order> cookedOrders; // order has been cooked, waiting for server pick up
    private ObservableList<Order> completedOrders; // order has been delivered and accepted by customer

    OrderManager(){
        pendingOrders = FXCollections.observableList(new LinkedList<Order>());
        ordersInProgress = FXCollections.observableList(new LinkedList<Order>());
        cookedOrders = FXCollections.observableList(new LinkedList<Order>());
        completedOrders = FXCollections.observableList(new LinkedList<Order>());
    }

    // getter for list of pending Orders
    public ObservableList<Order> getPendingOrders(){
        return pendingOrders;
    }

    /**
     * Submits an RestaurantModel.Order to the kitchen through the RestaurantModel.OrderManager
     * @param order the RestaurantModel.Order being placed
     * */
    public void placeOrder(Order order){
        if (!pendingOrders.contains(order))
            pendingOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been placed!");
    }

    /**
     * Updates the status of an RestaurantModel.Order that has been accepted by a cook from pending to in progress
     * @param order the RestaurantModel.Order that is now being cooked
     * */
    public void acceptOrder(Order order){
        if (pendingOrders.contains(order)){
            pendingOrders.remove(order);
            ordersInProgress.add(order);
        } else{
            throw new IllegalArgumentException("This order isn't in the list of orders waiting to be cooked!");
        }
    }

    /**
     * Updates the status of an RestaurantModel.Order from being cooked to cooked
     * @param order the RestaurantModel.Order that is now cooked and ready to be served
     * */
    public void orderIsCooked(Order order){
        if (ordersInProgress.contains(order)){
            ordersInProgress.remove(order);
            cookedOrders.add(order);
        } else {
            throw new IllegalArgumentException("This order wasn't being cooked!");
        }
    }

    /**
     * Used when an RestaurantModel.Order is retrieved from the RestaurantModel.Kitchen and brought out to the customer
     * @param order the RestaurantModel.Order that has been retrieved to be served
     * */
    public void retrieveOrder(Order order){
        if (cookedOrders.contains(order))
            cookedOrders.remove(order);
        else
            throw new IllegalArgumentException("This order isn't ready to be picked up!");
    }

    /**
     * Updates the status of an RestaurantModel.Order to completed. This should only be called when the customer has accepted the food
     * brought to them. Otherwise the RestaurantModel.Order is retrieved but not completed.
     * @param order the RestaurantModel.Order that was accepted by the customer
     * */
    public void confirmCompleted(Order order){
        if (!completedOrders.contains(order))
            completedOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been completed!");
    }

    /**
     * returns the RestaurantModel.Order with the given id from the given list
     * @param id the id of the RestaurantModel.Order to be returned
     * @param list the list that the desired RestaurantModel.Order is in
     * */
    public Order getOrder(int id, String list){
        ObservableList<Order> search;
        switch (list){
            case "pending":
                search = pendingOrders;
                break;
            case "in progress":
                search = ordersInProgress;
                break;
            case "cooked":
                search = cookedOrders;
                break;
            case "completed":
                search = completedOrders;
                break;
            default:
                throw new IllegalArgumentException("That's not a list I can search!");
        }
        for (Order o : search){
            if (o.orderNumber == id)
                return o;
        }
        throw new IllegalArgumentException("That list doesn't contain the specified order!");
    }
}
