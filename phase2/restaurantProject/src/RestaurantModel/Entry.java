package RestaurantModel;

abstract class Entry {
    private String name;
    private int quantity;

    Entry(String n, int q){
        name = n;
        quantity = q;
    }

    String getName(){
        return name;
    }
    int getQuantity(){
        return quantity;
    }

    void setQuantity(int q){
        quantity = q;
    }

    abstract boolean equals(Entry other);
}
