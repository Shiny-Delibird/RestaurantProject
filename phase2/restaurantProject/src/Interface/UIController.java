package Interface;

import Interface.Views.ServerMainPanelController;
import RestaurantModel.Order;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UIController extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        ArrayList<Order> test1 = new ArrayList<>();
        test1.add(new Order(5));
        test1.add(new Order(6));
        ObservableList<Order> test = FXCollections.observableList(test1);

        FXMLLoader serverMainLoader = new FXMLLoader(getClass().getResource("/Interface/Views/ServerMainPanel.fxml"));
        Parent root = serverMainLoader.load();
        Scene serverScene = new Scene(root, 600, 400);
        ServerMainPanelController serverController = serverMainLoader.getController();
        serverController.initLists(test, test);

        Stage serverWindow = new Stage();
        serverWindow.setScene(serverScene);
        serverWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
