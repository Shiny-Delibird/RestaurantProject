package Interface;

import Interface.Views.MainPanelController;
import RestaurantModel.Order;
import RestaurantModel.Restaurant;
import RestaurantModel.RestaurantModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class UIController extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        RestaurantModel mine = new Restaurant();
        ArrayList<Order> test1 = new ArrayList<>();
        test1.add(new Order(5));
        test1.add(new Order(6));
        ObservableList<Order> test = FXCollections.observableList(test1);

        FXMLLoader serverMainLoader = new FXMLLoader(getClass().getResource("/Interface/Views/MainPanel.fxml"));
        Parent root = serverMainLoader.load();
        Scene serverScene = new Scene(root, 600, 400);
        MainPanelController serverController = serverMainLoader.getController();

        serverController.initLists(test, test);
        serverController.initButtons(new Callable() {
            @Override
            public Object call() throws Exception {
                mine.confirmOrder(new Order(2));
                return null;
            }
        }, new Callable() {
            @Override
            public Object call() throws Exception {
                mine.cookOrder(new Order(3));
                return null;
            }
        });

        Stage serverWindow = new Stage();
        serverWindow.setScene(serverScene);
        serverWindow.show();
        serverWindow.setTitle("Server Interface");

        test.add(new Order(7));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
