package Interface.Controllers;

import RestaurantModel.Interfaces.RestaurantModel;
import RestaurantModel.Managers.Restaurant;
import RestaurantModel.RestaurantObjects.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
* @startuml
* class BillController{
* +parentGrid: GridPane
* -finalPrice: Label
* -fullBill: Label
* -tipPrice: Label
* -subTotal: Label
* -tipInput: TextField
* -prevButton: Button
* -tipAmount: double
* -totalPrice: double
* +init(orders: ObservableList<Order>, restaurant: RestaurantModel, button: Button):void
* -setLabels(taxAmount: double, tipAmount: double, totalPrice: double): void
* +closeBill(): void
* }
* @enduml
 */

public class BillController {

    @FXML
    public GridPane parentGrid;

    @FXML
    private Label finalPrice;
    @FXML
    private Label fullBill;
    @FXML
    private Label tipPrice;
    @FXML
    private Label subTotal;

    @FXML
    private TextField tipInput;

    private Button prevButton;
    private double tipAmount;
    private double totalPrice;

    public void init(ObservableList<Order> orders, RestaurantModel restaurant, Button button){
        double taxAmount = 0.15;
        prevButton = button;
        tipAmount = 0.15;
        totalPrice = 0;
        StringBuilder totalBill = new StringBuilder();

        List<Order> fixedOrders = new ArrayList<>(orders);

        for (Order order : fixedOrders){
            totalBill.append(order.toString() + ":\n");
            totalBill.append(restaurant.requestBill(order));
            totalBill.append("Order total: " + order.getTotalPrice() + "\n\n");

            totalPrice = totalPrice + order.getTotalPrice();
        }

        fullBill.setText(totalBill.toString());

        setLabels(taxAmount, tipAmount, totalPrice);

        tipInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*$")) {
                tipInput.setText(newValue.replaceAll("[^(\\d*.?\\d*$)]", ""));
            }
            try {
                tipAmount = Double.parseDouble(newValue)/100;
                if (tipAmount < 2){
                    setLabels(taxAmount, tipAmount, totalPrice);
                } else {
                    tipInput.setText(oldValue);
                }
            } catch (Exception ignored){}
        });
    }

    private void setLabels(double taxAmount, double tipAmount, double totalPrice){
        DecimalFormat price = new DecimalFormat("#,###.00");

        tipPrice.setText("Tip of $" + price.format(tipAmount * totalPrice) + " at " + tipAmount*100 + "%");
        subTotal.setText("Subtotal: $" + price.format(totalPrice) + " plus " + taxAmount*100 + "% tax of $" + price.format(totalPrice));
        finalPrice.setText("Total Price: $" + price.format(totalPrice + totalPrice * taxAmount + tipAmount * totalPrice));
    }

    public void closeBill(){
        parentGrid.setVisible(false);
        prevButton.setVisible(true);
    }
}
