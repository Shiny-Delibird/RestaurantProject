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

public class CalorieEntry implements Entry {
    private int quantity;
    private int minimum;
    private int calorieCount;

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
    public int getCalorieCount(){
        return calorieCount;
    }

    public void setCalorieCount(int c){
        calorieCount = c;
    }

    public int getQuantity(){return quantity;}

    public void addQuantity(int q){
        quantity += q;
    }

    public void useQuantity(int q){
        quantity -= q;
    }

    public void setMinimum(int m){
        minimum = m;
    }

    public boolean hasEnough(){
        return (quantity >= minimum);
    }
}
