/*
Represents a food
 */
import java.util.HashMap;
import java.util.Map;

public class Food{
    //Make list of types of ingredients
    Map<String, Integer> ingredients;
    private float price;
    private String name;

    public Food(String name, float price, Map<String, Integer> ingredients){
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredientName, int ingredientQuantity){
        if (ingredients.containsKey(ingredientName)){
            int originalQuantity = ingredients.get(ingredientName);
            ingredients.put(ingredientName, originalQuantity + ingredientQuantity);
        }else{
            ingredients.put(ingredientName, ingredientQuantity);
        }
    }

    public void removeIngredient(String ingredientName, int ingredientQuantity){
        if (ingredients.containsKey(ingredientName)) {
            int originalQuantity = ingredients.get(ingredientName);
            if (ingredientQuantity >= originalQuantity) {
                ingredients.remove(ingredientName);
            } else {
                ingredients.put(ingredientName, originalQuantity - ingredientQuantity);
            }
        }
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() { return price; }

    //This is here so when we make an order we can give a copy of the Food
    public Food(Food another){
        Map<String, Integer> ingredientsCopy = new HashMap<String, Integer>(another.ingredients);
        Food newFood = new Food(another.name, another.price, ingredientsCopy);
    }

    public Map<String, Integer> getIngredients(){
        return ingredients;
    }
}
