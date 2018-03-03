public class Server {
    private Order currentOrder;
    private static int classIdNumber = 1;
    private int idNumber;
    private Restaurant restaurant;

    public Server(Restaurant restaurant){
        this.idNumber = classIdNumber;
        classIdNumber += 1;
        this.restaurant = restaurant;
    }


    public void submitOrder(OrderManager manager){
        manager.placeOrder(currentOrder);
    }

    public void retrieveOrder(OrderManager manager, Order order){
        manager.retrieveOrder(order);
    }

    public void confirmOrder(OrderManager manager, Order order){
        manager.confirmCompleted(order);
    }

    public void addFoodtoOrder(Food food){
        currentOrder.addFood(food);
    }

    public void createNewOrder(int tableNumber){
        currentOrder = new Order(tableNumber);
    }

}
