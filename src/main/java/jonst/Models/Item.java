package jonst.Models;


import java.util.List;

public class Item extends GenericObject {

    List<String> qualities;     //Tracks what sort of item it is. Wearable, weapon, light sourse food, etc.

    public Item(String name, String shortName, String description, String locationName) {
        super(name, shortName, description, locationName);
    }
}
