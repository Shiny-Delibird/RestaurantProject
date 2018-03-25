package RestaurantModel;

public interface Entry{
    int getQuantity();

    void addQuantity(int q);

    void useQuantity(int q);

    void setMinimum(int m);

    boolean hasEnough();
}
