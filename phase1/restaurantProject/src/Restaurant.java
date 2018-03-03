import java.io.*;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The main class of the project that controls all other classes
public class Restaurant {
    private OrderManager orderManager;
    private Kitchen kitchen;
    private List<Server> servers;
    private Map<String, Food> menu;

    private static final String MENUFILE = "phase1/restaurantProject/src/menu.txt";

    public Restaurant() {
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);

        constructMenu(MENUFILE);
    }

    //Will make the Menu from the text file
    private void constructMenu(String file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            // Iterate through the lines from the file starting at 1.
            String line = fileReader.readLine();
            while (line != null){
                Map<String, Integer> allIngredients = new HashMap<>();

                //First item is name, rest is ingredients
                String[] barSplit = line.split("\\|");
                String foodName = barSplit[0].trim();

                //Each ingredient is separated by a comma
                String[] ingredients = barSplit[1].split(",");
                for (String s : ingredients){
                    //There is an "x" between the amount and the ingredient name
                    String[] ingredient = s.split("x");
                    String ingredientName = ingredient[1].trim();
                    Integer ingredientAmount = Integer.valueOf(ingredient[0].trim());

                    allIngredients.put(ingredientName, ingredientAmount);
                }

                //Now I have foodName and a Map of ingredients, up to Leon to make the food constructor so I can
                //actually make the food object now.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Main loop that will read the events and do them
    public static void main(String[] args) {
        Restaurant mainRestaurant = new Restaurant();

    }
}
