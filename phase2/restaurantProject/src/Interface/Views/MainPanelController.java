package Interface.Views;

import RestaurantModel.Order;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.Callable;

public class MainPanelController {

    @FXML
    private Button takeOrderButton;
    @FXML
    private Button prevOrderButton;
    @FXML
    private Button postOrderButton;

    @FXML
    private ListView prevOrderList;
    @FXML
    private ListView postOrderList;

    private Callable f1;
    private Callable f2;


    public  void initLists(ObservableList<Order> prevList, ObservableList<Order> postList){
        prevOrderList.setItems(prevList);
        postOrderList.setItems(postList);

    }

    public void initButtons(Callable func1, Callable func2){
        f1 = func1;
        f2 = func2;
    }

    public void test1() throws Exception {
        f1.call();
    }

    public void test2() throws Exception{
        f2.call();
    }
}
