package jonst.Models;

import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {


    private List<String> legitimateExits;


    //IMPORTANT! Locations should store a list/array/Enumerator of legitimate exits: direction, and which location it leads to. FIGURE OUT!


    //Todo: figure out way to use hashmaps instead of arraylists to store this; easier to search through.

    private List<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private List<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();
    private List<Item> itemsAtLocation = new ArrayList<Item>();

    public Location(String name, String shortName, String description, String locationName, ArrayList<String> legitimateExits) {
        super(name, shortName, description, locationName);

        this.legitimateExits = legitimateExits;

        setLocation(this);
    }


    public List<Creature> getCreaturesAtLocation() {
        return creaturesAtLocation;
    }

    public List<StationaryObject> getObjectsAtLocation() {
        return objectsAtLocation;
    }

    public List<Item> getItemsAtLocation() {
        return itemsAtLocation;
    }


    public void addCreature(Creature name) {
        if (!creatureIsAtLocation(name)) {
            creaturesAtLocation.add(name);
        }
    }

    public void removeCreature(Creature name) {
        if (creatureIsAtLocation(name)) {
            creaturesAtLocation.remove(name);
        }
    }

    public void addObject(StationaryObject name) {
        if (!stationaryObjectIsAtLocation(name)) {
            objectsAtLocation.add(name);
        }
    }

    public void removeObject(StationaryObject name) {
        if (stationaryObjectIsAtLocation(name)) {
            objectsAtLocation.remove(name);
        }
    }

    public void addItem(Item name) {
        if (!itemIsAtLocation(name)) {
            itemsAtLocation.add(name);
        }
    }

    public void removeItem(Item name) {
        if (itemIsAtLocation(name)) {
            itemsAtLocation.remove(name);
        }
    }

    public List<String> getExits() {
        return legitimateExits;
    }

    public Item getItemByName(String name) {

        for (Item item : getItemsAtLocation()) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
        }
        return null;
    }

    public Creature getCreatureByName(String name) {

        for (Creature creature : getCreaturesAtLocation()) {
            if (creature.getName().equalsIgnoreCase(name))
                return creature;
        }
        return null;
    }

    public StationaryObject getStationaryObjectByName(String name) {

        for (StationaryObject object : getObjectsAtLocation()) {
            if (object.getName().equalsIgnoreCase(name))
                return object;
        }
        return null;
    }

    public boolean itemIsAtLocation(Item item) {
        return getItemsAtLocation().contains(item);
    }

    public boolean stationaryObjectIsAtLocation(StationaryObject object) {
        return getObjectsAtLocation().contains(object);
    }

    public boolean creatureIsAtLocation(Creature creature) {
        return getCreaturesAtLocation().contains(creature);
    }


}
