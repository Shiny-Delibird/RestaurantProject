import java.io.*;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The main class of the project that controls all other classes
public class Restaurant {
    private OrderManager orderManager;
    private Kitchen kitchen;
    private List<Server> servers;
    private Map<String, Food> menu;

    private static final String MENUFILE = "phase1/restaurantProject/src/menu.txt";
    private static final String EVENTFILE = "phase1/restaurantProject/src/events.txt";

    public Restaurant() {
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);
        this.menu = new HashMap<>();

        constructMenu(MENUFILE);
    }

    public Restaurant(List<Server> servers, List<Cook> cooks){
        this.servers = servers;
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);
        this.menu = new HashMap<>();

        constructMenu(MENUFILE);
    }

    //Will make the Menu from the text file
    private void constructMenu(String file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            // Iterate through the lines from the file starting at 1.
            String line = fileReader.readLine();
            while (line != null){
                Map<String, Integer> allIngredients = new HashMap<>();

                //First item is name, rest is ingredients
                String[] barSplit = line.split("\\|");
                Float price = Float.valueOf(barSplit[0].trim());
                String foodName = barSplit[1].trim();

                //Each ingredient is separated by a comma
                String[] ingredients = barSplit[2].split(",");
                for (String s : ingredients){
                    //There is an "x" between the amount and the ingredient name
                    String[] ingredient = s.split("x");
                    String ingredientName = ingredient[1].trim();
                    Integer ingredientAmount = Integer.valueOf(ingredient[0].trim());

                    allIngredients.put(ingredientName, ingredientAmount);
                }

                menu.put(foodName, new Food(foodName, price, allIngredients));
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processEvents(String file){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            String line = fileReader.readLine();
            while (line != null){

                processEvent(line);
                line = fileReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processEvent(String event) {
        String[] split = event.split("\\|");

        String workerName = split[0].trim();
        String eventType = split[1].trim();
        String orderId = split[2].trim();
        String notes = split[3].trim();

        switch (eventType){
            case "takeOrder":
                placeOrder(workerName, notes);
                break;
            case "cookConfirmOrder":
                confirmOrder(workerName, orderId);
                break;
            case "cookFinishedOrder":
                orderFilled(workerName, orderId);
                break;
            case "tableReceivedOrder":
                orderReceived(workerName, orderId);
                break;
            case "tableRejectedOrder":
                orderRejected(workerName, orderId, notes);
                break;
        }
    }

    private void orderRejected(String workerName, String orderId, String notes) {
    }

    private void orderReceived(String server, String orderId) {
    }

    private void orderFilled(String cook, String orderId) {
    }

    private void confirmOrder(String cook, String orderId) {
    }

    private void placeOrder(String server, String foods) {
    }

    //Main loop that will read the events and do them
    public static void main(String[] args) {
        Restaurant mainRestaurant = new Restaurant();
    }
}
