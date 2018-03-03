import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private List<Food> foods;
    private int tableNumber;
    private static int classOrderNumber = 1;
    private int orderNumber;
    public Order(int tableNumber){
        this.tableNumber = tableNumber;
        this.orderNumber = classOrderNumber;
        classOrderNumber += 1;
        foods = new ArrayList<Food>(foods);
    }

    //Returns a Map with the name and amount of each ingredient
    public Map<String, Integer> getAllIngredients(){
        Map<String, Integer> allIngredients = new HashMap<String, Integer>();

        for (Food food : foods){
            Map<String, Integer> currentIngredients = food.getIngredients();
            for (Map.Entry<String, Integer> entry : currentIngredients.entrySet()) {
                String name = entry.getKey();
                Integer quantity = entry.getValue();
                if (allIngredients.containsKey(name)){
                    int originalQuantity = allIngredients.get(name);
                    allIngredients.put(name, originalQuantity + quantity);
                }else{
                    allIngredients.put(name, quantity);
                }
            }
        }
        return null;
    }

    //Adds the food to the order
    public void addFood(Food food){
        foods.add(food);
    }

    //Returns the total price of all the foods
    public float getPrice(){
        float total = 0;
        for (Food food : foods){
            total += food.getPrice();
        }
        return total;
    }
}
