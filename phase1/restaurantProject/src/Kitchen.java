import java.util.ArrayList;
import java.util.List;

public class Kitchen {
    private OrderManager orderManager;
    private InventoryManager inventoryManager;
    private List<Cook> cooks;

    //Constructor will take the orderManager from restaurant

    /**
     * The constructor will take an orderManager from the restaurant and initialize new ArrayList of Cook and
     * inventoryManager class
     * @param orderManager The general orderManager used by the restaurant
     */
    public Kitchen(OrderManager orderManager){
        this.orderManager = orderManager;
        this.inventoryManager = new InventoryManager("inventory.txt");
        this.cooks = new ArrayList<Cook>();
    }

    //Will take an order and the given cook will "cook" it, thereby moving it to next stage

    /**
     * Prompts the given Cook to prepare the given Order
     * @param order
     * @param cook
     */
    public void cook(Order order, Cook cook){}


    //Will take an order and a random cook will "cook" it, thereby moving it to next stage
    //Idk, the random thing might be temporary

    /**
     * Prompts a random Cook to prepare the given Order
     * @param order The order that needs to be cooked
     */
    public void cook(Order order){}
}
