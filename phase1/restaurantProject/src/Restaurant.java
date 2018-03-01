import java.util.List;
import java.util.Map;

// The main class of the project that controls all other classes
public class Restaurant {
    private OrderManager orderManager;
    private Kitchen kitchen;
    private List<Server> servers;
    private Map<String, Food> menu;

    public Restaurant() {
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);

        constructMenu();
    }

    //Will make the Menu from the text file
    private void constructMenu() {
    }

    //Main loop that will read the events and do them
    public static void main(String[] args) {
        Restaurant mainRestaurant = new Restaurant();

    }
}
