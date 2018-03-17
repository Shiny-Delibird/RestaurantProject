package Interface;

import RestaurantModel.EventManager;
import RestaurantModel.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController extends Application {
    private EventManager eventManager = new EventManager();
    private Restaurant restaurant;

    @Override
    public void start(Stage primaryStage) throws IOException {
        restaurant = eventManager.restaurant;

        FXMLLoader testLoader = new FXMLLoader(getClass().getResource("/Interface/testList.fxml"));
        Parent root = testLoader.load();
        testList testList = testLoader.getController();
        testList.init(restaurant.menu);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
