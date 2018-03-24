package RestaurantModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.*;
import java.util.*;

// The main class of the project that controls all other classes
public class Restaurant implements RestaurantModel{
    private OrderManager orderManager;
    private InventoryManager inventoryManager;
    private ObservableMap<String, Food> menu;

    private static final String MENU_FILE = "configs/menu.txt";

    public Restaurant() {
        this.orderManager = new OrderManager();
        this.inventoryManager = new InventoryManager();
        this.menu = FXCollections.observableHashMap();

        constructMenu(MENU_FILE);
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

    //TODO FILL ALL METHODS TO FINISH RESTAURANT

    @Override
    public Map<String, Food> getMenu() {
        return null;
    }

    @Override
    public Map<String, Integer> getInventory() {
        return null;
    }

    @Override
    public ObservableList getOrdersAtStage(String stage) {
        return null;
    }

    @Override
    public void placeOrder(Order order) {

    }

    @Override
    public void confirmOrder(Order order) {
        System.out.println("a");
    }

    @Override
    public void cookOrder(Order order) {
        System.out.println("b");
        //For testing
    }

    @Override
    public void receiveOrder(Order order) {

    }

    @Override
    public String rejectOrder(Order order) {
        return null;
    }

    @Override
    public String requestBill(Order order) {
        return null;
    }

    @Override
    public void receiveShipment(Map<String, Integer> shipment) {

    }
}
