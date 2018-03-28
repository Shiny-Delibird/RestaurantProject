package RestaurantModel.RestaurantObjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/**
 * The RestaurantModel.RestaurantObjects.Order class. Orders contain a list of foods to be cooked and are passed through RestaurantModel.OrderManager and are
 * given a tableNumber
 */

public class Order {

    private ObservableList<Food> foods;
    private int tableNumber;
    private static int classOrderNumber = 1;
    private String nickname;
    private String instructions;
    private int orderNumber;
    private int serverNumber;

    public int getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(int serverNumber) {
        this.serverNumber = serverNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getOrderNumber() {
        return orderNumber;
    }


    /**
     * Creates an RestaurantModel.RestaurantObjects.Order with a tableNumber, OrderNumber, and list of foods.
     * OrderNumber is set based on the number of Orders taken already
     * @param tableNumber The number of the table
     */
    public Order(int tableNumber){
        this.tableNumber = tableNumber;
        this.orderNumber = classOrderNumber;
        classOrderNumber += 1;
        foods = FXCollections.observableArrayList();
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ObservableList<Food> getFoods() {
        return foods;
    }

    public Order(){
        this.orderNumber = classOrderNumber;
        classOrderNumber += 1;
        foods = FXCollections.observableArrayList();
    }

    //Returns a Map with the name and amount of each ingredient
    public Map<String, Integer> getAllIngredients(){
        Map<String, Integer> allIngredients = new HashMap<>();

        for (Food food : foods){
            Map<String, Integer> currentIngredients = food.getIngredients();
            for (Map.Entry<String, Integer> entry : currentIngredients.entrySet()) {
                String name = entry.getKey();
                Integer quantity = entry.getValue();
                if (allIngredients.containsKey(name)){
                    int originalQuantity = allIngredients.get(name);
                    allIngredients.replace(name, originalQuantity, originalQuantity + quantity);
                }else{
                    allIngredients.put(name, quantity);
                }
            }
        }
        return allIngredients;
    }

    //Adds the food to the order
    public void addFood(Food food){
        foods.add(food);
    }

    public void removeFood(Food food){foods.remove(food);}

    //Returns the total price of all the foods
    public Map<String, Float> getPrices(){
        Map<String, Float> prices = new HashMap<>();
        for (Food food : foods){
            prices.put(food.toString(),food.getPrice());
        }
        return prices;
    }

    public double getTotalPrice(){
        float price = 0;
        for (Food food : foods){
            price = price + food.getPrice();
        }
        return price;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    @Override
    public String toString(){
        if (nickname.isEmpty()){
            return "Order " + getOrderNumber() + " at Table " + getTableNumber();
        }else{
            return nickname + " order " + " at Table " + getTableNumber();
        }

    }
}
