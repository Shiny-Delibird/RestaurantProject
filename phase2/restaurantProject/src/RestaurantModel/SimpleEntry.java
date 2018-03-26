package RestaurantModel;

public class SimpleEntry implements Entry{
    private int quantity;
    private int minimum;

    SimpleEntry(int q){
        quantity = q;
        minimum = 10;
    }

    SimpleEntry(int q, int m){
        quantity = q;
        minimum = m;
    }

    public int getQuantity(){return quantity;}

    public void addQuantity(int q){
        quantity += q;
    }

    public void useQuantity(int q){
        quantity -= q;
    }

    public void setMinimum(int m){
        minimum = m;
    }

    public boolean hasEnough(){
        return (quantity >= minimum);
    }

}
