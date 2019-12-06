package jonst;

import jonst.Data.*;
import jonst.Models.*;

import java.io.IOException;
import java.util.*;


public class World {

    //public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!

    private List<Location> locationList;      //Main lists that store all game objects
    private List<Creature> creatureList;
    private List<StationaryObject> stationaryObjectList;
    private List<Item> itemList;
    private List<GenericObject> genericList;

    private List<Item> playerInventory;

    private Creature playerCharacter = null;

    private Parser parser;


    public World(String loadFilePath) {

        playerInventory = new ArrayList<>();        //Todo: Find way to generate inventory

        loadListsFromFile(loadFilePath);        //Build lists

        populateLocationLists();

        parser = new Parser(genericList);       //The parser holds lists of words, and parses input


        setMainCharacter(getCreature("Trixie"));             //Establish protagonist



    }  //End of World constructor


    public void runGame() {

        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        Commands.LookAround(this);

        while (true)                //Continuously running play loop that parses instructions

        {
            try {
                parser.runCommand(SystemData.getReply("Please input command: "), this);
            } catch (IOException e) {
                System.out.println("For some reason, the input failed.");
                e.printStackTrace();    //Todo: Remove this in final release
            }
        }


    }


//-------- Add/Remove stuff -----------------------------------

    public void transferCreatureToLocation(String creature, String oldLocation, String newLocation) {

        if (doesObjectExist(creature) && doesObjectExist(oldLocation) && doesObjectExist(newLocation)) {
            removeCreatureFromLocation(creature, oldLocation);
            addCreatureToLocation(creature, newLocation);
        } else
            System.out.println("Illegal operation: transferCreatureToLocation, " + creature + " from " + oldLocation + " to " + newLocation + ".");
    }

    public void transferItemToLocation(String item, String oldLocation, String newLocation) {

        if (doesObjectExist(item) && doesObjectExist(oldLocation) && doesObjectExist(newLocation)) {
            removeItemFromLocation(item, oldLocation);
            addItemToLocation(item, newLocation);
        } else
            System.out.println("Illegal operation: transferItemToLocation, " + item + " from " + oldLocation + " to " + newLocation + ".");

    }

    public void transferObjectToLocation(String object, String oldLocation, String newLocation) {

        if (doesObjectExist(object) && doesObjectExist(oldLocation) && doesObjectExist(newLocation)) {
            removeObjectFromLocation(object, oldLocation);
            addObjectToLocation(object, newLocation);
        } else
            System.out.println("Illegal operation: transferObjectToLocation, " + object + " from " + oldLocation + " to " + newLocation + ".");
    }


    public void addCreatureToLocation(String creature, String location) {
        //Adds "creature" to "location"

        Location loc = getLocation(location);
        Creature cre = getCreature(creature);

        loc.addCreature(cre);
        cre.setLocation(loc);
    }

    public void removeCreatureFromLocation(String creature, String location) {

        Location loc = getLocation(location);
        Creature cre = getCreature(creature);

        loc.removeCreature(cre);
    }

    public void addItemToLocation(String item, String location) {
        //Adds "item" to "location"
        Location loc = getLocation(location);
        Item it = getItem(item);

        loc.addItem(it);
        it.setLocation(loc);
    }

    public void removeItemFromLocation(String item, String location) {

        Location loc = getLocation(location);
        Item it = getItem(item);

        getLocation(location).removeItem(getItem(item));
    }

    public void addObjectToLocation(String stationaryObject, String location) {
        //Adds "stationary" to "location"

        Location loc = getLocation(location);
        StationaryObject obj = getStationaryObject(stationaryObject);

        loc.addObject(obj);
        obj.setLocation(loc);
    }

    public void removeObjectFromLocation(String stationaryObject, String location) {

        Location loc = getLocation(location);
        StationaryObject obj = getStationaryObject(stationaryObject);

        loc.removeObject(obj);
    }

    public void addToInventory(Item item) {
        if (!isInInventory(item)) {
            playerInventory.add(item);
            item.setLocation(getLocation("inventory"));
        }
    }

    public void removeFromInventory(Item item) {
        if (isInInventory(item)) {
            playerInventory.remove(item);
            item.setLocation(getPlayerLocation());
        }
    }

    public boolean isInInventory(Item item) {
        boolean isIn = playerInventory.contains(item);
        return isIn;
    }

    //-------- List handling -----------------------

    public void populateLocationLists() {

        for (Location location : locationList) {

            for (Creature creature : creatureList) {
                if (creature.getLocationName().equalsIgnoreCase(location.getLocationName())) {
                    location.addCreature(creature); //Adds creature to the location's list of creatures
                    creature.setLocation(location); //Sets creature's location reference
                }
            }

            for (Item item : itemList) {
                if (item.getLocationName().equalsIgnoreCase("inventory")) {
                    addToInventory(item);
                } else if (item.getLocationName().equalsIgnoreCase(location.getLocationName())) {
                    location.addItem(item);         //Adds item to the location's list of items
                    item.setLocation(location);     //Sets item's location reference
                }
            }

            for (StationaryObject object : stationaryObjectList) {
                if (object.getLocationName().equalsIgnoreCase(location.getLocationName())) {
                    location.addObject(object);     //Adds object to the location's list of objects
                    object.setLocation(location);   //Sets object's location reference
                }
            }
        }
    }


    // --------------- Setters & Getters ------------------------

    public boolean setMainCharacter(Creature name) {

                playerCharacter = name;

                return !(playerCharacter == null);

    }

    public Creature getPlayer() {
        return playerCharacter;
    }

    public Location getPlayerLocation() {
        return getLocation(getPlayer().getLocationName());
    }

    public List<Item> getPlayerInventory() {
        return playerInventory;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public List<Creature> getCreatureList() {
        return creatureList;
    }

    public List<StationaryObject> getStationaryObjectList() {
        return stationaryObjectList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<GenericObject> getGenericList() {
        return genericList;
    }

    public Parser getParser() {
        return parser;
    }

    // ------------- Methods that returns objects from object lists ------------------

    public Location getLocation(String wantedLocation) {

        for (Location location : locationList) {

            //Instead of using equals, could use contains?
            if (location.getName().equalsIgnoreCase(wantedLocation))
                return location;
        }
        return null;
    }

    public Creature getCreature(String wantedCreature) {

        for (Creature creature : creatureList) {
            if (creature.getName().equalsIgnoreCase(wantedCreature))
                return creature;
        }
        return null;
    }

    public Item getItem(String wantedItem) {

        for (Item item : itemList) {
            if (item.getName().equalsIgnoreCase(wantedItem))
                return item;
        }
        return null;
    }

    public StationaryObject getStationaryObject(String wantedStationaryObject) {

        for (StationaryObject stationaryObject : stationaryObjectList) {
            if (stationaryObject.getName().equalsIgnoreCase(wantedStationaryObject))
                return stationaryObject;
        }
        return null;
    }

    public GenericObject getGenericObject(String wantedGenericObject) {

        for (GenericObject genericObject : genericList) {
            if (genericObject.getName().equalsIgnoreCase(wantedGenericObject))
                return genericObject;
        }
        return null;
    }

    public String returnFullName(String name) {

        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name
            if (generic.getShortName().equalsIgnoreCase(name)) {
                return generic.getName();
            }
        }
        return name;        //if not, just return the short name
    }

    // ------------- Boolean checks ---------------------

    public boolean isObjectPresent(String selected) {
        return ((getPlayerLocation() == (getGenericObject(selected).getLocation())) || (getPlayerLocation() == (getLocation("inventory"))));
    }

    public boolean doesObjectExist(String selected) {
        return genericList.contains(getGenericObject(selected));
    }

    // ------------------ The load function! --------------------------

    public void loadListsFromFile(String loadFilePath) {
        locationList = JsonBuilder.loadLocationList(loadFilePath);
        creatureList = JsonBuilder.loadCreatureList(loadFilePath);
        itemList = JsonBuilder.loadItemList(loadFilePath);
        stationaryObjectList = JsonBuilder.loadStationaryObjectList(loadFilePath);

        genericList = new ArrayList<>();
        genericList.addAll(locationList);
        genericList.addAll(creatureList);
        genericList.addAll(itemList);
        genericList.addAll(stationaryObjectList);
    }

    // ---------- The save function! -----------------------

    public boolean saveToFile(String saveFilePath) {

        boolean allSuccesses = true;
        Boolean[] successes = new Boolean[4];

        successes[0] = JsonBuilder.saveLocationList(saveFilePath, locationList);
        successes[1] = JsonBuilder.saveCreatureList(saveFilePath, creatureList);
        successes[2] = JsonBuilder.saveItemList(saveFilePath, itemList);
        successes[3] = JsonBuilder.saveStationaryObjectList(saveFilePath, stationaryObjectList);

        for (boolean boo : successes) {
            if (!boo)
                allSuccesses = false;
        }
        return allSuccesses;        //If it successfully saves all files, return true
    }

}
