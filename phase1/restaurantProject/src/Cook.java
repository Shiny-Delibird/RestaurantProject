
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Queue;

public class Cook {
    private static int classIdNumber = 1;
    private int idNumber;
    private Order currentOrder;

    public Cook(Kitchen kitchen){
        this.idNumber = classIdNumber;
        classIdNumber += 1;

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
}
