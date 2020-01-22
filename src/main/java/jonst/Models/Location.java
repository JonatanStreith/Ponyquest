package jonst.Models;

import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {


    private List<String> legitimateExits;


    //IMPORTANT! Locations should store a list/array/Enumerator of legitimate exits: direction, and which location it leads to. FIGURE OUT!

    //FACT: All location needs unique names (their "true full name"), but different locations can have the same shortname(s).
    //For instance, "The Everfree Forest" may be "forest", and "Cottontail woods" may be "forest".
    //As long as we resolve with returnLocalFullName, this should not be an issue.


    //Todo: figure out way to use hashmaps instead of arraylists to store this; easier to search through.

    private List<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private List<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();
 //   private List<Item> itemsAtLocation = new ArrayList<Item>();

    public Location(String name, String id, String description, String locationName, List<String> alias, List<String> attributes, ArrayList<String> legitimateExits) {
        super(name, id, description, locationName, alias, attributes);

        this.legitimateExits = legitimateExits;

        setLocation(this);
    }


    public List<Creature> getCreaturesAtLocation() {
        return creaturesAtLocation;
    }

    public List<StationaryObject> getObjectsAtLocation() {
        return objectsAtLocation;
    }

//    public List<Item> getItemsAtLocation() {
//        return itemsAtLocation;
//    }


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

//    public void addItem(Item name) {
//        if (!itemIsAtLocation(name)) {
//            itemsAtLocation.add(name);
//        }
//    }
//
//    public void removeItem(Item name) {
//        if (itemIsAtLocation(name)) {
//            itemsAtLocation.remove(name);
//        }
//    }

    public List<String> getExits() {
        return legitimateExits;
    }

//    public Item getItemByName(String name) {
//
//        for (Item item : getItemList()) {
//            if (item.getName().equalsIgnoreCase(name))
//                return item;
//        }
//        return null;
//    }

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

//    public boolean itemIsAtLocation(Item item) {
//        return getItemsAtLocation().contains(item);
//    }

    public boolean stationaryObjectIsAtLocation(StationaryObject object) {
        return getObjectsAtLocation().contains(object);
    }

    public boolean creatureIsAtLocation(Creature creature) {
        return getCreaturesAtLocation().contains(creature);
    }




    public List<GenericObject> getAllAtLocation(){
        List<GenericObject> genList = new ArrayList<>();    //Contains all things at the location, including itself
        genList.addAll(creaturesAtLocation);
        genList.addAll(getItemList());
        genList.addAll(objectsAtLocation);

        List<Item> containedItemsList = new ArrayList<>();  //Add every contained item to a list, and add that to the genList
        for (GenericObject gen: genList) {
            containedItemsList.addAll(gen.getItemList());
        }
        genList.addAll(containedItemsList);

        genList.add(this);   

        return genList;
    }


}
