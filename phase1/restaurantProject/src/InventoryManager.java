import java.util.Map;

public class InventoryManager {
    private Map<String, Integer> inventory;

    //Adds more ingredients to the inventory
    public void addIngredient(String ingredient, int amount){}

    //Removes ingredients from the inventory
    public void removeIngredient(String ingredient, int amount){}

    //Removes all ingredients in the food from the inventory
    public void removeIngredient(Food food){}

    //Removes all ingredients in the order from the inventory
    public void removeIngredient(Order order){}
}
