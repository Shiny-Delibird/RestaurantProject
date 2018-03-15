import java.io.*;
import java.util.*;

// The main class of the project that controls all other classes
public class Restaurant {
    public OrderManager orderManager;
    public Kitchen kitchen;
    public Map<String, Food> menu;
    private List<Server> servers;

    private static final String MENU_FILE = "src/menu.txt";

    Restaurant() {
        this.servers = new ArrayList<>();
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager);
        this.menu = new HashMap<>();

        constructMenu(MENU_FILE);
    }

    public Restaurant(List<Server> servers, List<Cook> cooks){
        this.servers = servers;
        this.orderManager = new OrderManager();
        this.kitchen = new Kitchen(orderManager, cooks);
        this.menu = new HashMap<>();

        constructMenu(MENU_FILE);
    }

    public void addServer(Server server){
        this.servers.add(server);
    }

    public void addCook(Cook cook){
        this.kitchen.cooks.add(cook);
    }

    //Generates the Menu from the menu.txt file
    private void constructMenu(String file) {
        try {
            //Create the menu file if it does not exist
            if (!(new File(file).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(file)));}

            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            Set<String> ingredientTypes = new HashSet<>();

            // Iterate through the lines from the file starting at 1.
            String line = fileReader.readLine();
            while (line != null){
                Map<String, Integer> allIngredients = new HashMap<>();

                //First item is name, rest is ingredients
                String[] barSplit = line.split("\\|");
                Float price = Float.valueOf(barSplit[0].trim());
                String foodName = barSplit[1].trim();

                //Each ingredient is separated by a comma
                String[] ingredients = barSplit[2].split(",");
                for (String s : ingredients){
                    //There is an "x" between the amount and the ingredient name
                    String[] ingredient = s.split("x");
                    String ingredientName = ingredient[1].trim();
                    Integer ingredientAmount = Integer.valueOf(ingredient[0].trim());

                    allIngredients.put(ingredientName, ingredientAmount);
                    ingredientTypes.add(ingredientName);
                }

                menu.put(foodName, new Food(foodName, price, allIngredients));
                line = fileReader.readLine();
            }
            kitchen.inventoryManager.checkIntegrity(ingredientTypes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Gets the Server object from the list based on the serverID
    public Server getServer(String serverID){
        for (Server server : servers){
            if (serverID.equals(server.getID())){
                return server;
            }
        }
        throw new IllegalArgumentException("Server not found");
    }


}
