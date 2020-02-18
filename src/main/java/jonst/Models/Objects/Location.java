package jonst.Models.Objects;

import jonst.App;
import jonst.Models.Exit;
import jonst.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Location extends GenericObject {

//
//    private List<String> legitimateExits = new ArrayList<>();

    private String defaultEnterId;
    private String defaultExitId;

    private Location defaultEnter;
    private Location defaultExit;

    private List<Creature> creaturesAtLocation;
    private List<StationaryObject> objectsAtLocation;


    public Location(String name, String id, String locationId, List<String> alias, List<String> attributes, String defaultEnterId, String defaultExitId, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName) {
        super(name, id, locationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerName);


        setDefaultEnterId(defaultEnterId);
        setDefaultExitId(defaultExitId);

        setLocation(this);

        creaturesAtLocation = new ArrayList<Creature>();
        objectsAtLocation = new ArrayList<StationaryObject>();
    }

    //--------- Getters ------------

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

    public Location getDefaultEnter() {
        return defaultEnter;
    }

    public Location getDefaultExit() {
        return defaultExit;
    }
    //--------- Setters ------------

    private void setDefaultEnterId(String defaultEnterId) {
        this.defaultEnterId = defaultEnterId;
    }

    private void setDefaultExitId(String defaultExitId) {
        this.defaultExitId = defaultExitId;
    }

    public void setDefaultEnter(Location defaultEnter) {
        this.defaultEnter = defaultEnter;
    }

    public void setDefaultExit(Location defaultExit) {
        this.defaultExit = defaultExit;
    }

    //--------- Other ------------

    public void add(GenericObject obj) {
        if (!isAtLocation(obj)) {
            if (obj instanceof Creature) {
                creaturesAtLocation.add((Creature) obj);
            } else if (obj instanceof StationaryObject) {
                objectsAtLocation.add((StationaryObject) obj);
            }
        }
    }

    public void remove(GenericObject obj) {
        if (isAtLocation(obj)) {
            if (obj instanceof Creature) {
                creaturesAtLocation.remove((Creature) obj);
            } else if (obj instanceof StationaryObject) {
                objectsAtLocation.remove((StationaryObject) obj);
            }
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

    public boolean isAtLocation(GenericObject object) {
        if (object instanceof StationaryObject) {
            return getObjectsAtLocation().contains(object);
        } else if (object instanceof Creature) {
            return getCreaturesAtLocation().contains(object);
        } else
            return false;
    }

    public List<GenericObject> getAllGroundOnly() {
        List<GenericObject> genList = new ArrayList<>();    //Contains all things at the location, including itself
        genList.addAll(creaturesAtLocation);
        genList.addAll(getItemList());
        genList.addAll(objectsAtLocation);

        genList.add(this);

        return genList;
    }


    public List<GenericObject> getAllAtLocation() {
        List<GenericObject> genList = new ArrayList<>();    //Contains all things at the location, including itself
        genList.addAll(creaturesAtLocation);
        genList.addAll(getItemList());
        genList.addAll(objectsAtLocation);

        List<Item> containedItemsList = new ArrayList<>();  //Add every contained item to a list, and add that to the genList
        for (GenericObject gen : genList) {
            if (!(gen.hasAttribute("closed")))       //Ignore containers that are closed
                containedItemsList.addAll(gen.getItemList());
        }
        genList.addAll(containedItemsList);

        genList.add(this);

        return genList;
    }

    public List<Exit> getExits() {
        List<Exit> exits = new ArrayList<>();

        List<Exit> allExits = App.getWorld().getExitList();

        for (Exit ex : allExits) {
            if (ex.containsLocation(this)) {
                exits.add(ex);
            }
        }
        return exits;
    }

    public List<String> getExitNames() {
        List<String> exitNames = new ArrayList<>();

        List<Exit> allExits = getExits();

        for (Exit ex : allExits) {
            exitNames.add(ex.getConnectingLocation(this).getName());
        }
        return exitNames;
    }

    public boolean connectsTo(Location otherLocation, World world){
        List<Exit> exits = world.getExitList();

        for (Exit exit : exits) {
            if(exit.connectionExists(this, otherLocation)){
                return true;
            }
        }
        return false;
    }



}
