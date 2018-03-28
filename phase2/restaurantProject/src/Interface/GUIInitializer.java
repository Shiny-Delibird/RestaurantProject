package Interface;

import Interface.Controllers.CookController;
import Interface.Controllers.ManagerController;
import Interface.Controllers.WorkerController;
import Interface.Controllers.ServerController;
import RestaurantModel.Managers.Restaurant;
import RestaurantModel.Interfaces.RestaurantModel;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIInitializer extends Application {
    private RestaurantModel restaurant;

    @Override
    public void start(Stage primaryStage) throws IOException {
        restaurant = new Restaurant();

        FXMLLoader managerLoader = new FXMLLoader(getClass().getResource("/Interface/Views/ManagerPanel.fxml"));
        Parent root = managerLoader.load();
        WorkerController managerController = managerLoader.getController();
        Scene serverScene = new Scene(root, 600, 400);
        managerController.init(restaurant);

        Stage serverWindow = new Stage();
        serverWindow.setScene(serverScene);
        serverWindow.show();
        serverWindow.setTitle("Manager Interface");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
