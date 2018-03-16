package RestaurantModel;

import java.util.LinkedList;
/**
 * The RestaurantModel.Cook class takes an RestaurantModel.Order from RestaurantModel.OrderManager to prepare and pass it on
 */
public class Cook {

    private static int classIdNumber = 1;
    private String ID;
    private Order currentOrder;

    /**
     * Creates an instance of cook with an id number and updates the counter for cooks
     */
    public Cook(String ID){
        this.ID = ID;
    }

    public void acceptOrder(OrderManager manager){
        manager.acceptOrder(currentOrder);
    }

    public void submitOrder(OrderManager manager){
        manager.orderIsCooked(currentOrder);
    }

    public void chooseOrder(OrderManager manager){
        LinkedList<Order> pendingOrders = manager.getPendingOrders();
        currentOrder = pendingOrders.getFirst();
    }

    public String getID() {
        return ID;
    }
}
