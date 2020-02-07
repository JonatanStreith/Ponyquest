package jonst.Models.Objects;

import jonst.App;
import jonst.Models.Exit;

import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {

//
//    private List<String> legitimateExits = new ArrayList<>();

    private String defaultEnterId;
    private String defaultExitId;

    private Location defaultEnter;
    private Location defaultExit;

    private List<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private List<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();


    public Location(String name, String id, String locationName, List<String> alias, List<String> attributes, String defaultEnterId, String defaultExitId) {
        super(name, id, locationName, alias, attributes);

//        this.legitimateExits = legitimateExits;

        setDefaultEnterId(defaultEnterId);
        setDefaultExitId(defaultExitId);

        setLocation(this);
    }


    public void setDefaultEnterId(String defaultEnterId) {
        this.defaultEnterId = defaultEnterId;
    }

    public void setDefaultExitId(String defaultExitId) {
        this.defaultExitId = defaultExitId;
    }

    public String getDefaultEnterId() {
        return defaultEnterId;
    }

    public String getDefaultExitId() {
        return defaultExitId;
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

//    public List<String> getExits() {
//        return legitimateExits;
//    }

    public Location getDefaultEnter() {
        return defaultEnter;
    }

    public void setDefaultEnter(Location defaultEnter) {
        this.defaultEnter = defaultEnter;
    }

    public Location getDefaultExit() {
        return defaultExit;
    }

    public void setDefaultExit(Location defaultExit) {
            this.defaultExit = defaultExit;
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

    public List<Exit> getExits(){
        List<Exit> exits = new ArrayList<>();

        List<Exit> allExits = App.getWorld().getExitList();

        for (Exit ex : allExits) {
            if(ex.containsLocation(this)){
                exits.add(ex);
            }
        }
        return exits;
    }

    public List<String> getExitNames(){
        List<String> exitNames = new ArrayList<>();

        List<Exit> allExits = getExits();

        for (Exit ex: allExits) {
            exitNames.add(ex.getConnectingLocation(this).getName());
        }
        return exitNames;
    }


}
