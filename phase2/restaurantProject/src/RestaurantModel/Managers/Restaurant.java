package RestaurantModel.Managers;

import RestaurantModel.Interfaces.InventorySystem;
import RestaurantModel.Interfaces.OrderSystem;
import RestaurantModel.Interfaces.RestaurantModel;
import RestaurantModel.RestaurantObjects.Food;
import RestaurantModel.RestaurantObjects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

/**
 * The Restaurant class
 * stimulates the operations of the restaurant, holds the models and facilitates backend operations
 * */
public class Restaurant implements RestaurantModel {
    private OrderSystem orderManager;
    private InventorySystem inventoryManager;
    private ObservableMap<String, Food> menu;

    private static final String MENU_FILE = "configs/menu.txt";
    private Logger logger;
    FileHandler fh;

    public Restaurant() throws IOException {
        this.orderManager = new OrderManager();
        this.inventoryManager = new ComplexInventory();
        this.menu = FXCollections.observableHashMap();

        constructMenu();
        createLog();
    }

    /**
     * creates the log file which records
     */
    private void createLog() throws IOException {
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


    /**
     * creates the menu from the MENU_FILE
     */
    private void constructMenu() {
        try {
            //Create the menu file if it does not exist
            if (!(new File(MENU_FILE).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(MENU_FILE)));
            }

            BufferedReader fileReader = new BufferedReader(new FileReader(MENU_FILE));
            Set<String> ingredientTypes = new HashSet<>();

            // Iterate through the lines from the file starting at 1.
            String line = fileReader.readLine();
            while (line != null) {
                Map<String, Integer> allIngredients = new HashMap<>();

                //First item is name, rest is ingredients
                String[] barSplit = line.split("\\|");
                Float price = Float.valueOf(barSplit[0].trim());
                String foodName = barSplit[1].trim();

                //Each ingredient is separated by a comma
                String[] ingredients = barSplit[2].split(",");
                for (String s : ingredients) {
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

    // getters and setters
    @Override
    public ObservableMap<String, Food> getMenu() {
        return menu;
    }

    @Override
    public ObservableMap<String, Integer> getInventory() {
        return inventoryManager.getInventory();
    }

    /**
     * returns all the orders at the given stage of progress
     * @param stage the desired stage of completion
     * @return a list of all the orders at the given stage of completion
     * */
    @Override
    public ObservableList getOrdersAtStage(String stage) {
        switch (stage) {
            case "InProgress":
                return orderManager.getOrdersInProgress();
            case "Completed":
                return orderManager.getCompletedOrders();
            case "Cooked":
                return orderManager.getCookedOrders();
            case "Pending":
                return orderManager.getPendingOrders();
        }
        return null;
    }

    /**
     * places an order in the Restaurant. this method also informs the orderManager of the order and reserves the
     * required ingredients to complete the order from the inventory using the inventoryManager
     * @param order the order that was placed
     * */
    @Override
    public void placeOrder(Order order) {
        orderManager.placeOrder(order);
        inventoryManager.useIngredients(order.getAllIngredients());
        logger.log(Level.INFO, "Placed Order for " + order.toString());
    }

    /**
     * confirms that a cook has seen and begun working on an order
     * @param order the order that was picked up by a cook
     * */
    @Override
    public void confirmOrder(Order order) {
        orderManager.acceptOrder(order);
    }

    /**
     * confirms that a cook has completed the given order
     * @param order the order that has finished cooking
     * */
    @Override
    public void cookOrder(Order order) {
        orderManager.orderIsCooked(order);
        logger.log(Level.INFO, "Prepared Order for " + order.toString());
        logger.log(Level.INFO, "Used ingredients" + order.getAllIngredients().toString());
    }

    /**
     * confirms that a server has picked up and delivered the given order. method is called when the customer accepts
     * said order
     * @param order the order that was delivered successfully
     * */
    @Override
    public void receiveOrder(Order order) {
        orderManager.retrieveOrder(order);
        orderManager.confirmCompleted(order);
        logger.log(Level.INFO, "Received Order for " + order.toString());
    }

    /**
     * sends an order back to the cook after it was delivered to and rejected by a customer
     * @param order the order that was rejected
     * */
    @Override
    public void rejectOrder(Order order) {
        orderManager.retrieveOrder(order);
        logger.log(Level.INFO, "Rejected Order for " + order.toString());
    }

    /**
     * generates a bill from the given order
     * @param order the order to be billed
     * @return the bill of the customer, in the form of a string
     * */
    @Override
    public String requestBill(Order order) {
        orderManager.getCompletedOrders().remove(order);

        StringBuilder bill = new StringBuilder();
        for (Map.Entry<String, Float> item : order.getPrices().entrySet()) {
            String word = item.getKey() + ": " + item.getValue() + "$\n";
            bill.append(word);
        }
        String finalBill = bill.toString();
        logger.log(Level.INFO, finalBill);
        return finalBill;
    }

    /**
     * registers a new shipment of ingredients with the inventoryManager
     * @param shipment a map of the ingredients (keys) and quantities (values) received
     * */
    @Override
    public void receiveShipment(Map<String, Integer> shipment) {
        inventoryManager.receiveShipment(shipment);
    }

    /**
     * returns whether there is enough food in the inventory to cook the given food
     * @param food the food which needs to be cooked
     * @return whether or not the food can be cooked with the current stock levels of the inventory
     * */
    @Override
    public Boolean hasEnough(Food food) {
        return inventoryManager.hasEnough(food);
    }

    /**
     * returns the total amount of calories a food will provide
     * @param food the food
     * @return the calorie count of the food
     * */
    @Override
    public int getCalories(Food food) {
        return inventoryManager.getCalories(food);
    }
}
