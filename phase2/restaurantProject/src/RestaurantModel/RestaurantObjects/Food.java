package RestaurantModel.RestaurantObjects;

import java.util.HashMap;
import java.util.Map;

/*
* @startuml
* class Food{
* -ingredients: Map<String, Integer>
* -price: float
* name: String
* -instructions: String
* -changedIngredients: Map<String, Integer>
* +Food(name: String, price: float, ingredients: Map<String, Integer>)
* +Food(name: String, price: float)
* +Food()
* +Food(another: Food)
* +getChangedIngredients(): Map<String, Integer>
* +getName(): String
* +getInstructions(): String
* +setInstructions(instructions: String): void
* +addIngredient(ingredientName: String, ingredientQuantity: int): void
* +removeIngredient(ingredientName: String, ingredientQuantity: int): void
* +setPrice(price: float): void
* +getPrice(): float
* +getIngredients(): Map<String, Integer>
* +toString(): String
* }
* @enduml
 */

/**
 * The food class
 * represents and stores the associated data of an item served by the restaurant
 * */
public class Food{
    private Map<String, Integer> ingredients;   // the ingredients required to make the food
    private float price;    // the price of the food
    private String name;    // the name of the food
    private String instructions;    // optional special instructions for making the food
    private Map<String, Integer> changedIngredients;    // list of ingredients changed from the default recipe

    /**
     * Creates a item on the menu with a name, price, and ingredients
     * @param name The name of the dish
     * @param price The base price of the dish
     * @param ingredients Ingredients used to prepare this dish
     */
    public Food(String name, float price, Map<String, Integer> ingredients){
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.changedIngredients = new HashMap<>();
    }

    public Food(String name, float price){
        this.name = name;
        this.price = price;
        this.ingredients = new HashMap<>();
        this.changedIngredients = new HashMap<>();
    }

    public Food(){
        this.ingredients = new HashMap<>();
        this.changedIngredients = new HashMap<>();
    }

    /**
     * This constructor is used STRICTLY for making a copy of the given food
     * @param another The food instance that must be copied
     */
    public Food(Food another){
        Map<String, Integer> ingredientsCopy = new HashMap<>(another.ingredients);
        this.name = another.name;
        this.price = another.price;
        this.ingredients = ingredientsCopy;
        this.changedIngredients = new HashMap<>();
    }

    /**
     * adds the given quantity of the given ingredient to the food
     * @param ingredientName the ingredient to add to the food
     * @param ingredientQuantity the amount of ingredient to add to the food
     * */
    public void addIngredient(String ingredientName, int ingredientQuantity){
        if (ingredients.containsKey(ingredientName)){
            int originalQuantity = ingredients.get(ingredientName);
            ingredients.put(ingredientName, originalQuantity + ingredientQuantity);
        }else{
            ingredients.put(ingredientName, ingredientQuantity);
        }

        if (changedIngredients.containsKey(ingredientName)){
            int originalQuantity = changedIngredients.get(ingredientName);
            changedIngredients.put(ingredientName, originalQuantity + ingredientQuantity);
        }else{
            changedIngredients.put(ingredientName, ingredientQuantity);
        }
    }

    /**
     * removes the given quantity of the given ingredient from the food
     * if the quantity becomes 0, the ingredient is removed from the food altogether
     * @param ingredientName the ingredient to be removed
     * @param ingredientQuantity the amount in units of the ingredient to remove
     * */
    public void removeIngredient(String ingredientName, int ingredientQuantity){
        if (ingredients.containsKey(ingredientName)) {
            int originalQuantity = ingredients.get(ingredientName);
            if (ingredientQuantity >= originalQuantity) {
                ingredients.remove(ingredientName);
            } else {
                ingredients.put(ingredientName, originalQuantity - ingredientQuantity);
            }
        }
        if (changedIngredients.containsKey(ingredientName)) {
            int originalQuantity = changedIngredients.get(ingredientName);
            if (originalQuantity - ingredientQuantity == 0){
                changedIngredients.remove(ingredientName);
            } else {
                changedIngredients.put(ingredientName, originalQuantity - ingredientQuantity);
            }
        } else  {
            changedIngredients.put(ingredientName, -1);
        }
    }

    // getters and setters
    float getPrice() { return price; }

    public Map<String, Integer> getIngredients(){
        return ingredients;
    }

    public Map<String, Integer> getChangedIngredients() {
        return changedIngredients;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return name;
    }
}
