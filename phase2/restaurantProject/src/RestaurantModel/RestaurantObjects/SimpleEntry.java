package RestaurantModel.RestaurantObjects;

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

    public SimpleEntry(int q){
        quantity = q;
        minimum = 10;
    }

    public SimpleEntry(int q, int m){
        quantity = q;
        minimum = m;
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
