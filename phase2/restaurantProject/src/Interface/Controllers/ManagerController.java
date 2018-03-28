package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ManagerController implements WorkerController {

    @FXML
    private ListView confirmList;
    @FXML
    private ListView cookList;
    @FXML
    private ListView deliverList;
    @FXML
    private ListView billList;

    @Override
    public void init(RestaurantModel restaurant) {
        confirmList.setItems(restaurant.getOrdersAtStage("Pending"));
        cookList.setItems(restaurant.getOrdersAtStage("InProgress"));
        deliverList.setItems(restaurant.getOrdersAtStage("Cooked"));
        billList.setItems(restaurant.getOrdersAtStage("Completed"));
    }
}
