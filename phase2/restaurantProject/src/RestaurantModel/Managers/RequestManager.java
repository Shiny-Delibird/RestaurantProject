package RestaurantModel.Managers;

import RestaurantModel.Interfaces.RequestSystem;

import java.io.*;
import java.util.*;

/*
* @startuml
* class RequestManager{
* -requested: Set<String>
* -{static} REORDER_FILE: String
* +RequestManager()
* +clear(): void
* +placeRequest(item: String): void
* }
* @enduml
 */

/**
 * The RequestManager class
 * Used by the InventorySystem in the event of an ingredient being low on stock to generate a request for more
 * */
class RequestManager implements RequestSystem {
    private Set<String> requested;  // the set of items that a request has been placed for

    private static final String REORDER_FILE = "configs/requests.txt";

    RequestManager(){
        requested = new HashSet<>();
        try {
            new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * clears the requests.txt file and the set of requested items
     * */
    public void clear(){
        try{
            // clears the requests.txt file
            FileWriter clear = new FileWriter(REORDER_FILE, false);
            clear.write("");
            // clears the requested set
            requested.clear();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * writes a request for an item to the requests.txt file
     * @param item the ingredient that needs to be reordered
     * */
    public void placeRequest(String item){
        // generates the reorder file if it isn't present
        try {
            if (!(new File(REORDER_FILE).exists())) {
                new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));}
        } catch(IOException e){
            e.printStackTrace();
        }
        // fills the reorder file with the required ingredients and keeps note of ordered items in the requested set
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE, true)))) {
            if (!requested.contains(item)) {
                out.println(item + " x 20");
                requested.add(item);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
