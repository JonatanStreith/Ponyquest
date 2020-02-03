package jonst.Models.Objects;

import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {


    private List<String> legitimateExits = new ArrayList<>();

    private String defaultEnter;
    private String defaultExit;

    private List<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private List<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();


    public Location(String name, String id, String locationName, List<String> alias, List<String> attributes, ArrayList<String> legitimateExits, String defaultEnter, String defaultExit) {
        super(name, id, locationName, alias, attributes);

        this.legitimateExits = legitimateExits;

        setDefaultEnter(defaultEnter);
        setDefaultExit(defaultExit);

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

    public List<String> getExits() {
        return legitimateExits;
    }

    public String getDefaultEnter() {
        return defaultEnter;
    }

    public void setDefaultEnter(String defaultEnter) {
        if(legitimateExits.contains(defaultEnter)){        //Only set a defaultEnter if it's on the list!
        this.defaultEnter = defaultEnter;                  //Otherwise it'll be null. This method should only be called during worldbuilding.
        }


    }

    public String getDefaultExit() {
        return defaultExit;
    }

    public void setDefaultExit(String defaultExit) {
        if(legitimateExits.contains(defaultExit)){        //Only set a defaultExit if it's on the list!
            this.defaultExit = defaultExit;               //Otherwise it'll be null. This method should only be called during worldbuilding.
        }
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

//    public boolean itemIsAtLocation(Item item) {
//        return getItemsAtLocation().contains(item);
//    }

    public boolean stationaryObjectIsAtLocation(StationaryObject object) {
        return getObjectsAtLocation().contains(object);
    }

    public boolean creatureIsAtLocation(Creature creature) {
        return getCreaturesAtLocation().contains(creature);
    }

    public List<GenericObject> getAllGroundOnly(){
        List<GenericObject> genList = new ArrayList<>();    //Contains all things at the location, including itself
        genList.addAll(creaturesAtLocation);
        genList.addAll(getItemList());
        genList.addAll(objectsAtLocation);

        genList.add(this);

        return genList;
    }


    public List<GenericObject> getAllAtLocation(){
        List<GenericObject> genList = new ArrayList<>();    //Contains all things at the location, including itself
        genList.addAll(creaturesAtLocation);
        genList.addAll(getItemList());
        genList.addAll(objectsAtLocation);

        List<Item> containedItemsList = new ArrayList<>();  //Add every contained item to a list, and add that to the genList
        for (GenericObject gen: genList) {
            if(!(gen.hasAttribute("closed")))       //Ignore containers that are closed
            containedItemsList.addAll(gen.getItemList());
        }
        genList.addAll(containedItemsList);

        genList.add(this);   

        return genList;
    }


}
