package RestaurantModel.RestaurantObjects;

import RestaurantModel.Interfaces.Entry;

/*
* @startuml
* class CalorieEntry{
* -quantity: int
* -minimum: int
* -calorieCount: int
* +CalorieEntry(q; int)
* +CalorieEntry(q: int, m: int)
* +getCalorieCount(): int
* +setCalorieCount(c: int): void
* +getQuantity(): int
* +addQuantity(q: int): void
* +useQuantity(q: int): void
* +setMinimum(m: int): void
* +hasEnough(): boolean
* }
* @enduml
 */

/**
 * The Entry of an ingredient in the InventorySystem
 * this implementation of Entry supports the recording of calorie information
 * */
public class CalorieEntry implements Entry {
    private int quantity;   // the amount of this ingredient in the inventory
    private int minimum;    // the minimum accepted amount of this ingredient in the inventory
    private int calorieCount;   // the caloric count of one unit of this ingredient

    public CalorieEntry(int q){
        quantity = q;
        minimum = 10;
        calorieCount = 0;
    }

    public CalorieEntry(int q, int m){
        quantity = q;
        minimum = m;
        calorieCount = 0;
    }

    // getters and setters
    public int getCalorieCount(){
        return calorieCount;
    }

    public void setCalorieCount(int c){
        calorieCount = c;
    }

    public int getQuantity(){return quantity;}

    public void setMinimum(int m){
        minimum = m;
    }

    /**
     * increments the quantity of this ingredient by the given amount
     * @param q the amount of this ingredient added to the inventory
     * */
    public void addQuantity(int q){
        quantity += q;
    }

    /**
     * decrements the quantity of this ingredient by the given amount
     * @param q the amount of this ingredient taken from the inventory and used
     * */
    public void useQuantity(int q){
        quantity -= q;
    }

    /**
     * returns whether the quantity of this ingredient is above the minimum allowed amount
     * */
    public boolean hasEnough(){
        return (quantity >= minimum);
    }
}
