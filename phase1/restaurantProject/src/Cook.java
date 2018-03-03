public class Cook {

    public void acceptOrder(OrderManager manager, Order order){
        manager.acceptOrder(order);
    }

    public void submitOrder(OrderManager manager, Order order){
        manager.orderIsCooked(order);
    }
}
