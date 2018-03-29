package RestaurantModel.UNUSED;

import RestaurantModel.Interfaces.Entry;

/*
* @startuml
* class SimpleEntry{
* -quantity: int
* -minimum: int
* +SimpleEntry(q; int)
* +SimpleEntry(q: int, m: int)
* +getQuantity(): int
* +addQuantity(q: int): void
* +useQuantity(q: int): void
* +setMinimum(m: int): void
* +hasEnough(): boolean
* }
* @enduml
 */

public class SimpleEntry implements Entry {
    private int quantity;
    private int minimum;

    SimpleEntry(int q){
        quantity = q;
        minimum = 10;
    }

    SimpleEntry(int q, int m){
        quantity = q;
        minimum = m;
    }

    // getters and setters
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
     * checks whether the quantity of this ingredient is above the minimum allowed amount
     * @return true or false depending on whether the quantity is greater than or equal to the minimum
     * */
    public boolean hasEnough(){
        return (quantity >= minimum);
    }

}
