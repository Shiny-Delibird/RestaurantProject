package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;
import RestaurantModel.RestaurantObjects.Food;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController implements WorkerController {

    @FXML
    private ListView confirmList;
    @FXML
    private ListView cookList;
    @FXML
    private ListView deliverList;
    @FXML
    private ListView billList;
    @FXML
    private ListView inventoryList;

    private RestaurantModel restaurant;

    @Override
    public void init(RestaurantModel restaurant) {
        this.restaurant = restaurant;

        confirmList.setItems(restaurant.getOrdersAtStage("Pending"));
        cookList.setItems(restaurant.getOrdersAtStage("InProgress"));
        deliverList.setItems(restaurant.getOrdersAtStage("Cooked"));
        billList.setItems(restaurant.getOrdersAtStage("Completed"));

        inventoryList.setItems(getFixedInventoryList(restaurant.getInventory()));
        restaurant.getInventory().addListener((MapChangeListener<String, Integer>) change ->
                inventoryList.setItems(getFixedInventoryList(restaurant.getInventory())));
    }

    public void receiveShipment(ActionEvent event){
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sourceScene = ((Node) event.getSource()).getScene();

        try {
            FXMLLoader takeOrderLoader = new FXMLLoader(getClass().getResource("/Interface/Views/ReceiveShipment.fxml"));
            Parent root = takeOrderLoader.load();

            ReceiveShipmentsController receiveShipmentsController = takeOrderLoader.getController();
            receiveShipmentsController.previousScene = sourceScene;
            receiveShipmentsController.init(restaurant);

            sourceStage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<String> getFixedInventoryList(ObservableMap<String, Integer> inventory){
        ObservableList<String> fixedIngredients = FXCollections.observableArrayList();
        for (String ingredient : inventory.keySet()){
            fixedIngredients.add(ingredient + " x " + inventory.get(ingredient));
        }

        return fixedIngredients.sorted();
    }

    public void addServer() throws IOException {
        WorkerController serverController = new ServerController();
        makeWorker(serverController, "Server Interface");
    }

    public void addCook() throws IOException {
        WorkerController cookController = new CookController();
        makeWorker(cookController, "Cook Interface");
    }


    private void makeWorker(WorkerController controller, String name) throws IOException {
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
}
