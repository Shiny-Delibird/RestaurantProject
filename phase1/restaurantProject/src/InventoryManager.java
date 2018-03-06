import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * The InventoryManager class.
 * Represents the inventory of a Restaurant and manages the stock of ingredients for cooking
 * */
class InventoryManager {
    private Map<String, Integer> inventory;
    private Map<String, Integer> minimums;

    private static final String INVENTORY_FILE = "phase1/restaurantProject/src/inventory.txt";
    private static final String MINIMUM_FILE = "phase1/restaurantProject/src/minimums.txt";
    private static final String REORDER_FILE = "phase1/restaurantProject/src/requests.txt";

    /**
     * Read a file with the inventory quantity and instantiate the inventory as a HashMap. Also reads another file with
     * the minimum quantities for each item in the inventory and instantiates as a HashMap. Orders more inventory if
     * necessary.
     */
    InventoryManager (){
        inventory = new HashMap<>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(INVENTORY_FILE));

            String line = fileReader.readLine();
            while (line != null){
                String[] split = line.split("\\s\\|\\s");
                addIngredient(split[0], Integer.parseInt(split[1]));

                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        minimums = new HashMap<>();
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(MINIMUM_FILE));

            String line = fileReader.readLine();
            while (line != null){
                String[] split = line.split("\\s\\|\\s");
                minimums.put(split[0], Integer.parseInt(split[1]));

                line = fileReader.readLine();
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        checkAndReorder(inventory.keySet());
    }

    /**
     * Subtracts <inventory> hashmap with a hashmap of the ingredients used
     * @param used a HashMap that contains ingredients to be subtracted
     */
    public void useIngredients(Map<String, Integer> used){
        for (String key : used.keySet()){
            if (inventory.containsKey(key)) {
                Integer old = inventory.get(key);
                inventory.replace(key, old, old - used.get(key));
            } else{
                throw new IllegalArgumentException(key + " is not a valid ingredient!");
            }

        }
        checkAndReorder(used.keySet());
    }

    /**
     * Incorporates a new shipment of ingredients into the inventory
     * @param shipment A map of each ingredient name and the amount received
     * */
    public void receiveShipment(Map<String, Integer> shipment){
        for (String key : shipment.keySet()){
            addIngredient(key, shipment.get(key));
        }
    }

    /**
     * Adds a given amount of a single ingredient into the inventory
     * @param food the ingredient being added
     * @param amount the amount of the ingredient being added
     * */
    public void addIngredient(String food, Integer amount){
        if (inventory.containsKey(food)){
            Integer old = inventory.get(food);
            inventory.replace(food, old, old + amount);
        } else{
            inventory.put(food, amount);
        }
    }

    /**
     * checks if there is enough of each ingredient in the inventory for a given set of ingredients and writes to the
     * reorder file if necessary
     * @param keys the set of ingredients to check
     * */
    private void checkAndReorder(Set<String> keys){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));

            for (String key : keys){
                if (inventory.get(key) < minimums.get(key)){
                    out.println(key + " x 20");
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Generates a list of all ingredients and the amount in stock of each for a manager to see
     * */
    @Override
    public String toString(){
        StringBuilder full = new StringBuilder();
        for (String key : inventory.keySet()){
            full.append(key);
            full.append(" x ");
            full.append(inventory.get(key));
            full.append(System.lineSeparator());
        }
        return full.toString();
    }
}
