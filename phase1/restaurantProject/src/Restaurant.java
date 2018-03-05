import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The main class of the project that controls all other classes
public class Restaurant {
    private OrderManager orderManager;
    private Kitchen kitchen;
    private List<Server> servers;
    private Map<String, Food> menu;

    private static final String MENU_FILE = "phase1/restaurantProject/src/menu.txt";
    private static final String EVENT_FILE = "phase1/restaurantProject/src/events.txt";

    public Restaurant() {
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);
        this.menu = new HashMap<>();

        constructMenu(MENU_FILE);
    }

    public Restaurant(List<Server> servers, List<Cook> cooks){
        this.servers = servers;
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);
        this.menu = new HashMap<>();

        constructMenu(MENU_FILE);
    }

    //Generates the Menu from the menu.txt file
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

    //Iterates through every line in the events.txt file
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

    //Takes one event line from the file and processes it
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
            case "tableRequestedBill":
                requestBill(workerName, orderId);
        }
    }

    //Table has requested the Bill for the Order
    private void requestBill(String server, String orderId) {
        Order toPay = orderManager.getOrder(Integer.valueOf(orderId), "completed");
        Server myServer = getServer(server);

        System.out.println(toPay.getPrice());
    }

    //Order has been taken to the table and they have rejected it
    private void orderRejected(String server, String orderId, String notes) {
        Order toReject = orderManager.getOrder(Integer.valueOf(orderId), "cooked");
        Server myServer = getServer(server);

        orderManager.retrieveOrder(toReject);
    }

    //Order has been taken to the table and they have accepted it
    private void orderReceived(String server, String orderId) {
        Order toReceive = orderManager.getOrder(Integer.valueOf(orderId), "cooked");
        Server myServer = getServer(server);

        orderManager.retrieveOrder(toReceive);
        orderManager.confirmCompleted(toReceive);
    }

    //Order is finished cooking and ready to be taken out by the Server
    private void orderFilled(String cook, String orderId) {
        Order toFill = orderManager.getOrder(Integer.valueOf(orderId), "in progress");
        Cook myCook = kitchen.getCook(cook);

        kitchen.cook(toFill, myCook);
    }

    //Cook confirms the Order is received
    private void confirmOrder(String cook, String orderId) {
        Order toConfirm = orderManager.getOrder(Integer.valueOf(orderId), "pending");
        Cook myCook = kitchen.getCook(cook);

        kitchen.cook(toConfirm, myCook);
    }

    //Constructs the Order object and then places the Order
    private void placeOrder(String server, String notes) {
        Integer tableNumber = Integer.valueOf(notes.split("-")[0].trim());
        String[] items = notes.split("-")[1].split(",");
        Order myOrder = new Order(tableNumber);

        for (String s : items){
            Integer amount = Integer.valueOf(s.split("x")[0].trim());
            String foodItem = s.split("x")[1].trim();

            if (menu.containsKey(s)){
                for (int i = 0; i < amount; i++){
                    myOrder.addFood(new Food(menu.get(foodItem)));
                }
            }
        }
        Server myServer = getServer(server);

        orderManager.placeOrder(myOrder);
    }

    //Gets the Server object from the list based on the serverID
    private Server getServer(String serverID){
        for (Server server : servers){
            if (serverID.equals(server.getID())){
                return server;
            }
        }
        return null;
    }

    //Main loop that will read the events and do them
    public static void main(String[] args) {
        Restaurant mainRestaurant = new Restaurant();
        //mainRestaurant.processEvents(Restaurant.EVENT_FILE);
    }
}
