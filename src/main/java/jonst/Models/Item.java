package jonst.Models;


import java.util.List;

public class Item extends GenericObject {


    private GenericObject owner;

    List<String> qualities;     //Tracks what sort of item it is. Wearable, weapon, light sourse food, etc.

    public Item(String name, String id, String description, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, description, locationName, alias, attributes);
    }

    public boolean setOwner(GenericObject owner){
        this.owner = owner;
        return true;
    }

    public GenericObject getOwner() {
        return owner;
    }
}
