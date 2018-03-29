package RestaurantModel.Managers;

import RestaurantModel.Interfaces.OrderSystem;
import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/*
* @startuml
* class OrderManager{
* -pendingOrders: ObservableList<Order>
* -ordersInProgress: ObservableList<Order>
* -cookedOrders: ObservableList<Order>
* -completedOrders: ObservableList<Order>
* +OrderManager()
* +getPendingOrders(): ObservableList<Order>
* +getOrdersInProgress: ObservableList<Order>
* +getCookedOrders(): ObservableList<Order>
* +getCompletedOrders(): ObservableList<Order>
* +placeOrder(order: Order): void
* +acceptOrder(order: Order): void
* +orderIsCooked(order: Order): void
* +retreiveOrder(order: Order): void
* +confirmCompleted(order: Order): void
* }
* @enduml
 */

/**
 * The OrderManager class. Keeps track of Orders in their various stages of completion and updates their statuses
 * when necessary
 * */

class OrderManager implements OrderSystem {
    private ObservableList<Order> pendingOrders; // server placed order, cooking not started
    private ObservableList<Order> ordersInProgress; // cook confirmed order, cooking in progress
    private ObservableList<Order> cookedOrders; // order has been cooked, waiting for server pick up
    private ObservableList<Order> completedOrders; // order has been delivered and accepted by customer

    OrderManager(){
        pendingOrders = FXCollections.observableList(new LinkedList<>());
        ordersInProgress = FXCollections.observableList(new LinkedList<>());
        cookedOrders = FXCollections.observableList(new LinkedList<>());
        completedOrders = FXCollections.observableList(new LinkedList<>());
    }

    // getter for list of pending Orders
    public ObservableList<Order> getPendingOrders(){
        return pendingOrders;
    }

    // getter for list of Orders in progress
    public ObservableList<Order> getOrdersInProgress(){
        return ordersInProgress;
    }

    // getter for list of cooked Orders
    public ObservableList<Order> getCookedOrders(){
        return cookedOrders;
    }

    // getter for list of completed Orders
    public ObservableList<Order> getCompletedOrders(){
        return completedOrders;
    }

    /**
     * Submits an Order to the kitchen through the OrderManager
     * @param order the Order being placed
     * */
    public void placeOrder(Order order){
        if (!pendingOrders.contains(order))
            pendingOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been placed!");
    }

    /**
     * Updates the status of an Order that has been accepted by a cook from pending to in progress
     * @param order the Order that is now being cooked
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
     * Updates the status of an Order from being cooked to cooked
     * @param order the Order that is now cooked and ready to be served
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
     * Used when an Order is retrieved from the Kitchen and brought out to the customer
     * @param order the Order that has been retrieved to be served
     * */
    public void retrieveOrder(Order order){
        if (cookedOrders.contains(order))
            cookedOrders.remove(order);
        else
            throw new IllegalArgumentException("This order isn't ready to be picked up!");
    }

    /**
     * Updates the status of an Order to completed. This should only be called when the customer has accepted the food
     * brought to them. Otherwise the Order is retrieved but not completed.
     * @param order the Order that was accepted by the customer
     * */
    public void confirmCompleted(Order order){
        if (!completedOrders.contains(order))
            completedOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been completed!");
    }


}
