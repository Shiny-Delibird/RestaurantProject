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

class RequestManager implements RequestSystem {
    private Set<String> requested;

    private static final String REORDER_FILE = "configs/requests.txt";

    RequestManager(){
        requested = new HashSet<>();
        try {
            new PrintWriter(new BufferedWriter(new FileWriter(REORDER_FILE)));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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
