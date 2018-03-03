import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class InventoryManager {
    private Map<String, Integer> inventory;

    InventoryManager (String file){
        inventory = new HashMap<>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            String line = fileReader.readLine();
            while (line != null){
                String[] split = line.split("\\s\\|\\s");
                inventory.put(split[0], Integer.parseInt(split[1]));

                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useIngredients(Map<String, Integer> used){
        for (String key : used.keySet()){
            if (inventory.containsKey(key)) {
                Integer old = inventory.get(key);
                inventory.replace(key, old, old - used.get(key));
            } else{
                throw new IllegalArgumentException(key + " is not a valid ingredient!");
            }

        }
    }

    //Adds more ingredients to the inventory
    public void addIngredient(String ingredient, int amount){}

    //Removes ingredients from the inventory
    public void removeIngredient(String ingredient, int amount){}

    //Removes all ingredients in the food from the inventory
    public void removeIngredient(Food food){}

    //Removes all ingredients in the order from the inventory
    public void removeIngredient(Order order){}
}
