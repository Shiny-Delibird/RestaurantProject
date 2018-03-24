package Interface.Views;

import RestaurantModel.Order;
import RestaurantModel.RestaurantModel;
import javafx.collections.ObservableList;

public interface EmployeeController {
    void init(ObservableList<Order> prevList, ObservableList<Order> postList, RestaurantModel restaurant);
}
