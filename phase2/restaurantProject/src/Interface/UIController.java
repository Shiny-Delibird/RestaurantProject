package Interface;

import Interface.Views.CookController;
import Interface.Views.EmployeeController;
import Interface.Views.ServerController;
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
    private RestaurantModel restaurant;

    @Override
    public void start(Stage primaryStage) throws IOException {
        restaurant = new Restaurant();

        EmployeeController serverController = new ServerController();
        EmployeeController cookController = new CookController();

        makeStage(serverController, "Server Interface");
        makeStage(cookController, "Cook Interface");
    }

    private void makeStage(EmployeeController controller, String name) throws IOException {
        FXMLLoader MainLoader = new FXMLLoader(getClass().getResource("/Interface/Views/MainPanel.fxml"));
        MainLoader.setController(controller);
        Parent root = MainLoader.load();
        Scene serverScene = new Scene(root, 600, 400);
        controller.init(restaurant);

        Stage serverWindow = new Stage();
        serverWindow.setScene(serverScene);
        serverWindow.show();
        serverWindow.setTitle(name);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
