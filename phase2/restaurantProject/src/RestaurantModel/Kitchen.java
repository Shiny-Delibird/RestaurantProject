package RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class Kitchen {
    private OrderManager orderManager;
    InventoryManager inventoryManager;

    //Constructor will take the orderManager from restaurant

    /**
     * The constructor will take an orderManager from the restaurant and initialize new ArrayList of Cook and
     * inventoryManager class
     *
     * @param orderManager The general orderManager used by the restaurant
     */
    Kitchen(OrderManager orderManager) {
        this.orderManager = orderManager;
        this.inventoryManager = new InventoryManager();
    }

    //Will take an order and the given cook will "cook" it, thereby moving it to next stage

    /**
     * Cooks the given order
     *
     * @param order is the order to be prepared
     */
    public void cook(Order order) {
        orderManager.orderIsCooked(order);
        inventoryManager.useIngredients(order.getAllIngredients());
    }

    public void acceptOrder(Order order) {
        orderManager.acceptOrder(order);
    }

}


