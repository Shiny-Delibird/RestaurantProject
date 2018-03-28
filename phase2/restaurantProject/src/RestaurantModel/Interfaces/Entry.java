package RestaurantModel.Interfaces;

/*
* @startuml
* Interface Entry{
* +getQuantity(): int
* +addQuantity(q: int): void
* +useQuantity(q: int): void
* +setMinimum(m: int): void
* +hasEnough(): boolean
* }
* @enduml
 */

public interface Entry{
    int getQuantity();

    void addQuantity(int q);

    void useQuantity(int q);

    void setMinimum(int m);

    boolean hasEnough();
}
