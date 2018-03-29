package RestaurantModel.Managers;

import RestaurantModel.Interfaces.InventorySystem;
import RestaurantModel.Interfaces.RestaurantModel;
import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
* @startuml
* class Restaurant{
* -orderManager: OrderManager
* -inventoryManager: InventorySystem
* -menu: ObservableMap<String, Food>
* -{static} MENU_FILE: String
* -logger: Logger
* +fh: FileHandler
* +Restaurant()
* +createLog(): void
* +getMenu(): ObservableMap<String, Foodr>
* +getInventory(): ObservableMap<String, Integer>
* +getOrdersAtStage(stage: String): ObservableList
* +placeOrder(order: Order): void
* +confirmOrder(order: Order): void
* +cookOrder(order: Order): void
* +receiveOrder(order: Order): void
* +rejectOrder(order: Order): void
* +requestBill(order: Order): String
* +receiveShipment(shipment: Map<String, Integer>): void
* +hasEnough(food: Food): Boolean
* +getCalories(food: Food) int
* }
* @enduml
 */

// The main class of the project that controls all other classes
public class Restaurant implements RestaurantModel {
    private OrderManager orderManager;
    private InventorySystem inventoryManager;
    private ObservableMap<String, Food> menu;

    private static final String MENU_FILE = "configs/menu.txt";
    private Logger logger;
    FileHandler fh;
    public Restaurant() throws IOException{
        this.orderManager = new OrderManager();
        this.inventoryManager = new ComplexInventory();
        this.menu = FXCollections.observableHashMap();

        constructMenu(MENU_FILE);
        createLog();
    }

    public void createLog() throws IOException{
        File f = new File("configs/log.txt");
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        fh = new FileHandler("configs/log.txt", true);
        logger = Logger.getLogger("test");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setUseParentHandlers(false);
    }


    //Generates the Menu from the menu.txt file
    private void constructMenu(String file) {
        try {
            //Create the menu file if it does not exist
            if (!(new File(file).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(file)));}

            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            Set<String> ingredientTypes = new HashSet<>();

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
                    ingredientTypes.add(ingredientName);
                }

                menu.put(foodName, new Food(foodName, price, allIngredients));
                line = fileReader.readLine();
            }
            inventoryManager.checkIntegrity(ingredientTypes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ObservableMap<String, Food> getMenu() {
        return menu;
    }

    @Override
    public ObservableMap<String, Integer> getInventory() {
        return inventoryManager.getInventory();
    }

    @Override
    public ObservableList getOrdersAtStage(String stage) {
        switch (stage){
            case "InProgress": return orderManager.getOrdersInProgress();
            case "Completed": return orderManager.getCompletedOrders();
            case "Cooked": return orderManager.getCookedOrders();
            case "Pending": return orderManager.getPendingOrders();
        }
        return null;
    }

    @Override
    public void placeOrder(Order order) {
        orderManager.placeOrder(order);
        inventoryManager.useIngredients(order.getAllIngredients());
        logger.log(Level.INFO, "Placed Order for " + order.toString());

    }

    @Override
    public void confirmOrder(Order order) {
        orderManager.acceptOrder(order);
    }

    @Override
    public void cookOrder(Order order) {
        orderManager.orderIsCooked(order);
        logger.log(Level.INFO, "Prepared Order for " + order.toString());
        logger.log(Level.INFO, "Used ingredients" + order.getAllIngredients().toString());
    }

    @Override
    public void receiveOrder(Order order) {
        orderManager.retrieveOrder(order);
        orderManager.confirmCompleted(order);
        logger.log(Level.INFO, "Received Order for " + order.toString());
    }

    @Override
    public void rejectOrder(Order order) {
        orderManager.getOrdersInProgress().add(order);
        orderManager.retrieveOrder(order);
        logger.log(Level.INFO, "Rejected Order for " + order.toString());
    }

    @Override
    public String requestBill(Order order) {
        orderManager.getCompletedOrders().remove(order);

        StringBuilder bill = new StringBuilder();
        for (Map.Entry<String, Float> item: order.getPrices().entrySet()){
            String word = item.getKey() + ": " + item.getValue() + "$\n";
            bill.append(word);
        }
        String finalBill = bill.toString();
        logger.log(Level.INFO, finalBill);
        return finalBill;
    }

    @Override
    public void receiveShipment(Map<String, Integer> shipment) {
        inventoryManager.receiveShipment(shipment);
    }

    @Override
    public Boolean hasEnough(Food food) {
        return inventoryManager.hasEnough(food);
    }

    @Override
    public int getCalories(Food food) {
        return inventoryManager.getCalories(food);
    }
}
