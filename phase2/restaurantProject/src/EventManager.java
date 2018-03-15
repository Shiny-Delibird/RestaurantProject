import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    public Restaurant restaurant;

    private static final String EVENT_FILE = "configs/events.txt";

    public EventManager() {
        this.restaurant = new Restaurant();
    }

    //Iterates through every line in the events.txt file
    public void processEvents(String file){
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
        String notes = "";

        String[] split = event.split("\\|");

        String workerName = split[0].trim();
        String eventType = split[1].trim();
        String orderId = split[2].trim();

        if (split.length == 4){
            notes = split[3].trim();
        }

        switch (eventType){
            case "takeOrder":
                Order myOrder = parseOrder(notes);
                Server orderServer = restaurant.getServer(workerName);

                restaurant.orderManager.placeOrder(myOrder);
                System.out.println("Order " + myOrder.orderNumber + " placed with foods: " + myOrder.foods + " by " + orderServer.getID());
                break;
            case "cookConfirmOrder":
                Order toConfirm = restaurant.orderManager.getOrder(Integer.valueOf(orderId), "pending");
                Cook confirmingCook = restaurant.kitchen.getCook(workerName);

                restaurant.kitchen.acceptOrder(toConfirm, confirmingCook);
                System.out.println(confirmingCook.getID() + " confirmed order" + toConfirm.orderNumber);
                break;
            case "cookFinishedOrder":
                Order toFill = restaurant.orderManager.getOrder(Integer.valueOf(orderId), "in progress");
                Cook cookingCook = restaurant.kitchen.getCook(workerName);

                restaurant.kitchen.cook(toFill, cookingCook);
                System.out.println(cookingCook.getID() + " cooked order" + toFill.orderNumber);
                break;
            case "tableReceivedOrder":
                Order toReceive = restaurant.orderManager.getOrder(Integer.valueOf(orderId), "cooked");
                Server receivingServer = restaurant.getServer(workerName);

                restaurant.orderManager.retrieveOrder(toReceive);
                restaurant.orderManager.confirmCompleted(toReceive);
                System.out.println(receivingServer.getID() + " gave order " + toReceive.orderNumber + " to table " + toReceive.getTableNumber());
                break;
            case "tableRejectedOrder":
                Order toReject = restaurant.orderManager.getOrder(Integer.valueOf(orderId), "cooked");
                Server rejectingServer = restaurant.getServer(workerName);

                restaurant.orderManager.retrieveOrder(toReject);
                System.out.println(rejectingServer.getID() + " rejected order " + toReject.orderNumber + " from table " + toReject.getTableNumber() + " for reason " + notes);
                break;
            case "tableRequestedBill":
                Order toPay = restaurant.orderManager.getOrder(Integer.valueOf(orderId), "completed");
                Server billServer = restaurant.getServer(workerName);

                System.out.println(billServer.getID() + " gave bill of " + toPay.getPrice() + " to table " + toPay.getTableNumber());
                break;
            case "receiveShipment":
                Map<String, Integer> inventoryShipment = parseShipment(notes);
                restaurant.kitchen.inventoryManager.receiveShipment(inventoryShipment);

                System.out.println("Received shipment of " + inventoryShipment);
                break;
            default:
                System.out.println("Event " + eventType + "not recognized");
        }
    }

    //Parses the string to a shipment
    private Map<String,Integer> parseShipment(String shipment) {
        Map<String, Integer> allItems = new HashMap<>();
        String[] items = shipment.split(",");

        for (String s : items){
            String itemName = s.split("x")[0].trim();
            Integer amount = Integer.valueOf(s.split("x")[1].trim());

            allItems.put(itemName, amount);
        }

        return allItems;
    }

    //Parses the string to a valid Order object
    private Order parseOrder(String event){
        Integer tableNumber = Integer.valueOf(event.split(";")[0].trim());
        String[] items = event.split(";")[1].split(",");
        Order myOrder = new Order(tableNumber);

        for (String s : items){
            String foodItem = s.split("x")[0].trim();
            Integer amount = Character.getNumericValue(s.split("x")[1].trim().charAt(0));

            if (restaurant.menu.containsKey(foodItem)){
                for (int i = 0; i < amount; i++){
                    Food toAdd = new Food(restaurant.menu.get(foodItem));

                    if (s.contains("+")){
                        List<String> additions = parseChanges(s, '+');
                        for (String addition : additions){
                            toAdd.addIngredient(addition, 1);
                        }
                    } else if (s.contains("-")){
                        List<String> removals = parseChanges(s, '-');
                        for (String removal : removals){
                            toAdd.removeIngredient(removal, 1);
                        }
                    }

                    myOrder.addFood(toAdd);
                }
            }
        }

        return myOrder;
    }

    private List<String> parseChanges(String fullText, char change) {
        String[] allThings = fullText.split("\\s");
        ArrayList<String> items = new ArrayList<>();

        for (String s : allThings){
            if (!s.isEmpty() && s.charAt(0) == change){
                items.add(s.substring(1));
            }
        }

        return items;
    }

    //Main loop that will read the events and do them
    public static void main(String[] args) {
        EventManager mainManager = new EventManager();

        mainManager.restaurant.addServer(new Server("server1"));
        mainManager.restaurant.addServer(new Server("server2"));
        mainManager.restaurant.addServer(new Server("server3"));

        mainManager.restaurant.addCook(new Cook("cook1"));
        mainManager.restaurant.addCook(new Cook("cook2"));
        mainManager.restaurant.addCook(new Cook("cook3"));

        mainManager.processEvents(EventManager.EVENT_FILE);
    }
}
