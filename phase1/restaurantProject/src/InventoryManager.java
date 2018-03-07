import java.io.*;
import java.util.*;

/**
 * The InventoryManager class.
 * Represents the inventory of a Restaurant and manages the stock of ingredients for cooking
 * */
class InventoryManager {
    private Map<String, Integer> inventory;
    private Map<String, Integer> minimums;
    private Set<String> requested;

    private static final String INVENTORY_FILE = "phase1/restaurantProject/src/inventory.txt";
    private static final String MINIMUM_FILE = "phase1/restaurantProject/src/minimums.txt";
    private static final String REORDER_FILE = "phase1/restaurantProject/src/requests.txt";

    /**
     * Read a file with the inventory quantity and instantiate the inventory as a HashMap. Also reads another file with
     * the minimum quantities for each item in the inventory and instantiates as a HashMap. Orders more inventory if
     * necessary.
     */
    InventoryManager (){
        try{
            inventory = new HashMap<>();

            //Creates the inventory file if it doesn't exist
            if (!(new File(INVENTORY_FILE).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(INVENTORY_FILE)));}

            //Adds items from the inventory file to the Map
            BufferedReader inventoryReader = new BufferedReader(new FileReader(INVENTORY_FILE));
            String inventoryLine = inventoryReader.readLine();
            while (inventoryLine != null){
                String[] split = inventoryLine.split("\\s\\|\\s");
                addIngredient(split[0], Integer.parseInt(split[1]));

                inventoryLine = inventoryReader.readLine();
            }

            minimums = new HashMap<>();

            //Creates the minimums file if it doesn't exist
            if (!(new File(MINIMUM_FILE).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(MINIMUM_FILE)));}

            //Adds items from the minimum file to the Map
            BufferedReader minimumReader = new BufferedReader(new FileReader(MINIMUM_FILE));
            String minimumLine = minimumReader.readLine();
            while (minimumLine != null){
                String[] split = minimumLine.split("\\s\\|\\s");
                minimums.put(split[0], Integer.parseInt(split[1]));

                minimumLine = minimumReader.readLine();
            }

            // detects and fills in missing entries from the minimums file
            fillMinimums();

            requested = new HashSet<>();

            new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));
            checkAndReorder(inventory.keySet());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyses for missing entries in the minimums Map or the minimums.txt file and generates default minimums for each
     * By default, the default minimum for all ingredients is 10 units. This can be adjusted afterwards in the
     * minimums.txt file
     * */
    private void fillMinimums() throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(MINIMUM_FILE, true)))) {
            for (String key : inventory.keySet()){
                if (!minimums.containsKey(key)){
                    out.println(key + " | " + 10);
                    minimums.put(key, 10);
                }
            }
        }
    }

    /**
     * Subtracts <inventory> hashmap with a hashmap of the ingredients used.
     * if any items in the inventory go below the minimum threshold for its stock, it is noted in requests.txt
     * changes to the inventory are also reflected in inventory.txt
     * @param used a HashMap that contains ingredients to be subtracted
     */
    public void useIngredients(Map<String, Integer> used){
        for (String key : used.keySet()){
            if (inventory.containsKey(key)) {
                Integer old = inventory.get(key);
                if (old - used.get(key) >= 0){
                    inventory.replace(key, old, old - used.get(key));}
                else{
                    throw new IllegalArgumentException("We don't have enough " + key + " for that order!");}
            } else{
                throw new IllegalArgumentException(key + " is not a valid ingredient!");
            }

        }
        checkAndReorder(used.keySet());
        updateInventory();
    }

    /**
     * Incorporates a new shipment of ingredients into the inventory. requests.txt is then cleared and ingredients still
     * under threshold are reordered. also updates inventory.txt
     * @param shipment A map of each ingredient name and the amount received
     * */
    public void receiveShipment(Map<String, Integer> shipment){
        for (String key : shipment.keySet()){
            addIngredient(key, shipment.get(key));
        }
        try{
            // clears the requests.txt file
            FileWriter clear = new FileWriter(REORDER_FILE, false);
            clear.write("");
            // clears the requested set
            requested.clear();
            // rechecks the inventory for insufficient stock
            checkAndReorder(inventory.keySet());
        } catch(IOException e){
            e.printStackTrace();
        }
        updateInventory();
    }

    /**
     * Adds a given amount of a single ingredient into the inventory
     * @param food the ingredient being added
     * @param amount the amount of the ingredient being added
     * */
    private void addIngredient(String food, Integer amount){
        if (inventory.containsKey(food)){
            Integer old = inventory.get(food);
            inventory.replace(food, old, old + amount);
        } else{
            inventory.put(food, amount);
        }
    }

    /**
     * checks if there is enough of each ingredient in the inventory for a given set of ingredients and writes to the
     * reorder file if necessary. if an ingredient has already been noted in requests.txt then it is ignored
     * @param keys the set of ingredients to check
     * */
    private void checkAndReorder(Set<String> keys){
        // generates the reorder file if it isn't present
        try {
            if (!(new File(REORDER_FILE).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));}
        } catch(IOException e){
            e.printStackTrace();
        }
            // fills the reorder file with the required ingredients and keeps note of ordered items in the requested set
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE, true)))) {
            for (String key : keys) {
                if (inventory.get(key) < minimums.get(key) && !requested.contains(key)) {
                    out.println(key + " x 20");
                    requested.add(key);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * updates the inventory.txt file to match the inventory map
     * */
    private void updateInventory(){
        try{
            FileWriter clear = new FileWriter(INVENTORY_FILE,false);
            clear.write("");
        } catch(IOException e){
            e.printStackTrace();
        }
        try(PrintWriter fresh = new PrintWriter(new BufferedWriter(new FileWriter(INVENTORY_FILE, true)))){
            for (String key : inventory.keySet()){
                fresh.println(key + " | " + inventory.get(key));
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
