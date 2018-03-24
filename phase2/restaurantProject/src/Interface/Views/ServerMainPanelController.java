package Interface.Views;

import RestaurantModel.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ServerMainPanelController {

    @FXML
    private Button takeOrderButton;
    @FXML
    private Button deliverOrderButton;
    @FXML
    private Button generateBillButton;

    @FXML
    private ListView deliverList;
    @FXML
    private ListView billList;

    public  void initLists(ObservableList<Order> toReceive, ObservableList<Order> toBill){
        deliverList.setItems(toReceive);
        billList.setItems(toBill);
    }

}
