package RestaurantModel.Managers;

import RestaurantModel.Interfaces.InventorySystem;
import RestaurantModel.RestaurantObjects.CalorieEntry;
import RestaurantModel.RestaurantObjects.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* @startuml
* class ComplexInventory{
* -inventory: Map<String, CalorieEntry>
* -requests: RequestManager
* - {static} INVENTORY_FILE: String
* - {static} MINIMUM_FILE: String
* - {static} CALORIE_TABLE: String
* +ComplexInventory()
* +checkIntegrity(ingredients: Set<String>): void
* +useIngredients(used: Map<String, Integer>): void
* +receiveShipment(shipment: Map<String, Integer>): void
* +getInventory(): ObservableMap<String, Integer>
* +hasEnough(food: Food): boolean
* +getCalories(food: Food): int
* +toString(): String
* }
* @enduml
 */
/**
 * The ComplexInventory class
 * Used by the RestaurantModel to keep track of it's inventory of ingredients. This specific implementation of
 * InventorySystem supports the storage of calorie information along with standard ingredient information
 * */
public class ComplexInventory implements InventorySystem {
    private Map<String, CalorieEntry> inventory;
    private RequestManager requests;

    private static final String INVENTORY_FILE = "configs/inventory.txt";
    private static final String MINIMUM_FILE = "configs/minimums.txt";
    private static final String CALORIE_TABLE = "configs/calories.txt";

    /**
     * Initializes the inventory and minimums maps using their respective files. If the files are not present, they are
     * auto-generated with pre-determined values. Also creates the requests.txt file which holds all the items which
     * a manager must order from the supplier
     */
    ComplexInventory (){
        try{
            inventory = new HashMap<>();
            // read and organize data from files
            parseFile(INVENTORY_FILE);
            Set<String> minimumKeys = parseFile(MINIMUM_FILE);
            parseFile(CALORIE_TABLE);
            // initialize the RequestSystem
            requests = new RequestManager();
            // ensure consistency of files and the inventory hashmap
            updateInventoryFile();
            updateMinimumsFile(minimumKeys);
            // check for insufficient stock and reorder
            checkAndReorder(inventory.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the given config file and creates/updates the associated information (ingredient, minimum or calorie
     * information) in the instance. This method also creates the given file if it does not exist.
     * @param fileName the file to be parsed. must either be the INVENTORY_FILE, MINIMUM_FILE or CALORIE_TABLE
     * */
    private Set<String> parseFile(String fileName) throws IOException{
        if (!(new File(fileName).exists())) {
            // create file if it does not exist
            new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            return null;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            Set<String> found = new HashSet<>();

            while (line != null) {
                String[] split = line.split("\\s\\|\\s");
                String key = split[0];
                int num = Integer.parseInt(split[1]);

                if (fileName.equals(INVENTORY_FILE)){
                    inventory.put(key, new CalorieEntry(num));
                }
                else if (fileName.equals(MINIMUM_FILE)){
                    found.add(key);

                    if (inventory.keySet().contains(key)){
                        inventory.get(key).setMinimum(num);
                    }
                    else{
                        inventory.put(key, new CalorieEntry(0, num));
                    }
                }
                else if (fileName.equals(CALORIE_TABLE)){
                    if (inventory.keySet().contains(key)){
                        inventory.get(key).setCalorieCount(num);
                    }
                }

                line = reader.readLine();
            }
            return found;
        }
    }

    /**
     * updates the inventory.txt file to match the inventory map
     * */
    private void updateInventoryFile(){
        try{
            FileWriter clear = new FileWriter(INVENTORY_FILE,false);
            clear.write("");
        } catch(IOException e){
            e.printStackTrace();
        }
        try(PrintWriter fresh = new PrintWriter(new BufferedWriter(new FileWriter(INVENTORY_FILE, true)))){
            for (String key : inventory.keySet()){
                fresh.println(key + " | " + inventory.get(key).getQuantity());
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Analyses for missing entries in the  the minimums.txt file and generates default minimums for each
     * By default, the default minimum for all ingredients is 10 units. This can be adjusted afterwards in the
     * minimums.txt file
     * */
    private void updateMinimumsFile(Set<String> currentInFile) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(MINIMUM_FILE, true)))) {
            for (String key : inventory.keySet()){
                if (!currentInFile.contains(key)){
                    out.println(key + " | " + 10);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * checks if each ingredient in a given set of ingredients is recorded in the inventory. if it is not, it is added
     * to the inventory with a stock-value of 0. any insufficient ingredients are then reordered. entries missing in the
     * minimums file are also added
     * @param ingredients the set of ingredients to check
     * */
    public void checkIntegrity(Set<String> ingredients){
        Set<String> oldKeys = inventory.keySet();

        for (String key : ingredients){
            if (!inventory.containsKey(key)){
                inventory.put(key, new CalorieEntry(0, 10));
            }
        }
        updateInventoryFile();
        updateMinimumsFile(oldKeys);

        checkAndReorder(inventory.keySet());
    }

    /**
     * subtracts the given map of ingredients from the inventory map
     * @param used the map of ingredients and quantities used
     * */
    public void useIngredients(Map<String, Integer> used){
        for (String key : used.keySet()){
            if (inventory.containsKey(key)) {
                inventory.get(key).useQuantity(used.get(key));
            } else{
                throw new IllegalArgumentException(key + " is not a valid ingredient!");
            }
        }
        checkAndReorder(used.keySet());
        updateInventoryFile();
    }

    /**
     * adds the given map of ingredients to the inventory map
     * @param shipment the map of ingredients and quantities to be addede
     * */
    public void receiveShipment(Map<String, Integer> shipment){
        for (String key : shipment.keySet()){
            inventory.get(key).addQuantity(shipment.get(key));
        }
        requests.clear();
        checkAndReorder(shipment.keySet());

        updateInventoryFile();
    }

    /**
     * checks each ingredient in the given set to see if they have sufficient stock. if the stock level is below
     * the minimum, a request for more is placed by the RequestSystem
     * @param keys the set of ingredients to be checked
     * */
    private void checkAndReorder(Set<String> keys){
        for (String key : keys){
            if (!inventory.get(key).hasEnough()){
                requests.placeRequest(key);
            }
        }
    }

    /**
     * returns a map with keys being ingredient names and values being their individual quantities.
     * */
    public ObservableMap<String, Integer> getInventory(){
        ObservableMap<String,Integer> quantities = FXCollections.observableHashMap();
        for (String key : inventory.keySet()){
            quantities.put(key, inventory.get(key).getQuantity());
        }
        return quantities;
    }

    /**
     * checks the inventory to see if there is enough ingredients in stock to make a given food
     * @param food the food that needs to be made
     * */
    public boolean hasEnough(Food food){
        boolean enough = true;
        Map<String, Integer> ingredients = food.getIngredients();
        for (String key : ingredients.keySet()){
            if (inventory.get(key).getQuantity() < ingredients.get(key)){
                enough = false;
            }
        }
        return enough;
    }

    /**
     * returns the calorie count of a given food, which is the sum of the calories of each ingredient in the food
     * @param food the food in question
     * */
    public int getCalories(Food food){
        int sum = 0;
        Map<String, Integer> ingredients = food.getIngredients();
        for (String key : ingredients.keySet()){
            sum += ingredients.get(key) * inventory.get(key).getCalorieCount();
        }
        return sum;
    }

    @Override
    public String toString(){
        StringBuilder full = new StringBuilder();
        for (String key : inventory.keySet()){
            full.append(key);
            full.append(" x ");
            full.append(inventory.get(key).getQuantity());
            full.append(System.lineSeparator());
        }
        return full.toString();
    }
}
