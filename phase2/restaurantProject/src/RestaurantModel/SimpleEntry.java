package RestaurantModel;

public class SimpleEntry extends Entry{
    public SimpleEntry(String name, int quantity){
        super(name, quantity);
    }

    @Override
    boolean equals(Entry other){
        return (other instanceof SimpleEntry) && (this.getName().equals(other.getName()));
    }
}
