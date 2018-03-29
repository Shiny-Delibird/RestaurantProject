package RestaurantModel.RestaurantObjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/*
 * @startuml
 * class Order{
 * -foods: ObservableList<Food>
 * -tableNumber: int
 * -{static} classOrderNumber: int
 * -nickname: String
 * -instructions: String
 * -orderNumber: int
 * +setNickname(nickname: String): void
 * +getInstructions(): String
 * +setInstructions(instructions: String): void
 * +getOrderNumber(): int
 * +Order()
 * +getAllIngredients(): Map<String, Integer>
 * +addFood(food: Food): void
 * +removeFood(food: Food): void
 * +getPrices(): Map<String, float>
 * +getTotalPrice(): double
 * +toString(): String
 *  }
 * @enduml
 */

/**
 * The Order class
 * represents and contains the associated information of an order placed in the restaurant
 * */
public class Order {

    private ObservableList<Food> foods;     // the foods in this order
    private int tableNumber;    // the table this order was placed at
    private static int classOrderNumber = 1;    // the global order counter
    private String nickname;    // the nickname for this order (typically customer name)
    private String instructions;    // the instructions for the cook
    private int orderNumber;    // the order number of this order
    private int serverNumber;   // the number of the server who placed this order

    public Order(){
        this.orderNumber = classOrderNumber;
        classOrderNumber += 1;
        foods = FXCollections.observableArrayList();
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

    // getters and setters
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(int serverNumber) {
        this.serverNumber = serverNumber;
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

    public ObservableList<Food> getFoods() {
        return foods;
    }

    /**
     * returns all the ingredients needed to cook all of the foods in this order
     * @return a map with the ingredients (keys) and their individual quantities (values) required
     * */
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

    /**
     * adds the given food to the order
     * @param food the new food
     * */
    public void addFood(Food food){
        foods.add(food);
    }

    /**
     * removes the given food from the order
     * @param food the unwanted food
     * */
    public void removeFood(Food food){foods.remove(food);}

    /**
     * returns the foods on this order and their individual prices
     * @return a map consisting of the foods in this order as keys and the price of each food as values
     * */
    public Map<String, Float> getPrices(){
        Map<String, Float> prices = new HashMap<>();
        for (Food food : foods){
            prices.put(food.toString(),food.getPrice());
        }
        return prices;
    }

    /**
     * calculates the total cumulative price of all items in this order
     * @return the total price of the order
     * */
    public double getTotalPrice(){
        float price = 0;
        for (Food food : foods){
            price = price + food.getPrice();
        }
        return price;
    }

    @Override
    public String toString(){
        if (nickname.isEmpty()){
            return "Order " + orderNumber + " at Table " + getTableNumber();
        }else{
            return nickname + " order " + " at Table " + getTableNumber();
        }

    }
}
