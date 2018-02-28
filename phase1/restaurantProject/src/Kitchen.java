import java.util.List;

public class Kitchen {
    private OrderManager orderManager;
    private InventoryManager inventoryManager;
    private List<Cook> cooks;

    //Constructor will take the orderManager from restaurant
    public Kitchen(OrderManager orderManager){}

    //Will take an order and the given cook will "cook" it, thereby moving it to next stage
    public void cook(Order order, Cook cook){}

    //Will take an order and a random cook will "cook" it, thereby moving it to next stage
    //Idk, the random thing might be temporary
    public void cook(Order order){}
}
