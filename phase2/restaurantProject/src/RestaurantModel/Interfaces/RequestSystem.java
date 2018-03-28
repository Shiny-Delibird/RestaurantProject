package RestaurantModel.Interfaces;

/*
* @startuml
* Interface RequestSystem{
* +clear(): void
* +placeRequest(item: String): void
* }
* @enduml
 */

public interface RequestSystem {
    void clear();

    void placeRequest(String item);
}
