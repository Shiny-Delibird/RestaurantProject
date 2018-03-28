package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;

/*
* @startuml
* Interface WorkerController{
* +init(restaurant: RestaurantModel): void
* }
* @enduml
 */

public interface WorkerController {
    void init(RestaurantModel restaurant);
}
