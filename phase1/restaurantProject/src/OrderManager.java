import java.util.LinkedList;
import java.util.Queue;

public class OrderManager {
    private LinkedList<Order> pendingOrders; // server placed order, cooking not started
    private LinkedList<Order> ordersInProgress; // cook accepted order, cooking in progress
    private LinkedList<Order> cookedOrders; // order has been cooked, waiting for server pick up
    private LinkedList<Order> completedOrders; // order has been delivered and accepted by customer

    OrderManager(){
        pendingOrders = new LinkedList<>();
        ordersInProgress = new LinkedList<>();
        cookedOrders = new LinkedList<>();
        completedOrders = new LinkedList<>();
    }

    public LinkedList<Order> getPendingOrders(){
        return pendingOrders;
    }

    public LinkedList<Order> getOrdersInProgress(){
        return ordersInProgress;
    }

    public LinkedList<Order> getCookedOrders(){
        return cookedOrders;
    }

    public LinkedList<Order> getCompletedOrders(){
        return completedOrders;
    }

    public void placeOrder(Order order){
        if (!pendingOrders.contains(order))
            pendingOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been placed!");
    }

    public void acceptOrder(Order order){
        if (pendingOrders.contains(order)){
            pendingOrders.remove(order);
            ordersInProgress.add(order);
        } else{
            throw new IllegalArgumentException("This order isn't in the list of orders waiting to be cooked!");
        }
    }

    public void orderIsCooked(Order order){
        if (ordersInProgress.contains(order)){
            ordersInProgress.remove(order);
            cookedOrders.add(order);
        } else {
            throw new IllegalArgumentException("This order wasn't being cooked!");
        }
    }

    public void retrieveOrder(Order order){
        if (cookedOrders.contains(order))
            cookedOrders.remove(order);
        else
            throw new IllegalArgumentException("This order isn't ready to be picked up!");
    }

    public void confirmCompleted(Order order){
        if (!completedOrders.contains(order))
            completedOrders.add(order);
        else
            throw new IllegalArgumentException("This order has already been completed!");
    }
}
