package jonst;

import jonst.Data.*;
import jonst.Models.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class World {

    public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!

    private List<Location> locationList;      //Main lists that store all game objects
    private List<Creature> creatureList;
    private List<StationaryObject> stationaryObjectList;
    private List<Item> itemList;
    private List<GenericObject> genericList;

    private Creature playerCharacter = null;

    private Parser parser;


    public World(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void updateWorld(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void buildWorld(String loadFilePath){

        loadListsFromFile(loadFilePath);        //Build lists

        populateLocationLists();

        parser = new Parser(genericList);       //The parser holds lists of words, and parses input

        setMainCharacter(getCreature(SystemData.getProtagonist()));             //Establish protagonist

/*
        System.out.println("Number of errored items in junkyard: " + getLocation("Junkyard").getItemList().size());
        System.out.println("Number of errored creatures in junkyard: " + getLocation("Junkyard").getCreaturesAtLocation().size());
        System.out.println("Number of errored objects in junkyard: " + getLocation("Junkyard").getObjectsAtLocation().size());
*/


    }



    public void runGame() {

        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        Commands.lookAround(this);

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

    public void transferItemToNewOwner(String item, String oldOwner, String newOwner) {

        if (doesObjectExist(item) && doesObjectExist(oldOwner) && doesObjectExist(newOwner)) {
            removeItemFromGeneric(item, oldOwner);
            addItemToGeneric(item, newOwner);
        } else
            System.out.println("Illegal operation: transferItemToNewOwner, " + item + " from " + oldOwner + " to " + newOwner + ".");

    }

    public void transferObjectToLocation(String object, String oldLocation, String newLocation) {

        if (doesObjectExist(object) && doesObjectExist(oldLocation) && doesObjectExist(newLocation)) {
            removeObjectFromLocation(object, oldLocation);
            addObjectToLocation(object, newLocation);
        } else
            System.out.println("Illegal operation: transferObjectToLocation, " + object + " from " + oldLocation + " to " + newLocation + ".");
    }

    public void removeCreatureFromLocation(String creature, String location) {

        Location loc = getLocation(location);
        Creature cre = getCreature(creature);

        loc.removeCreature(cre);
        cre.setLocation(null);
    }

    public void addCreatureToLocation(String creature, String location) {
        //Adds "creature" to "location"

        Location loc = getLocation(location);
        Creature cre = getCreature(creature);

        loc.addCreature(cre);
        cre.setLocation(loc);
    }

    public void removeItemFromGeneric(String item, String generic) {

        GenericObject gen = getGenericObject(generic);
        Item it = getItem(item);

        gen.removeItem(it);
        it.setOwner(null);
    }

    public void addItemToGeneric(String item, String generic) {
        //Adds "item" to "location"
        GenericObject gen = getGenericObject(generic);
        Item it = getItem(item);

        gen.addItem(it);
        it.setOwner(gen);

        if(gen instanceof Location){        //If item is moved to a location, its location field is set to that place. Otherwise, it's set to null.
            it.setLocation((Location) gen);
        }
        else {
            it.setLocation(null);
        }
    }

    public void removeObjectFromLocation(String stationaryObject, String location) {

        Location loc = getLocation(location);
        StationaryObject obj = getStationaryObject(stationaryObject);

        loc.removeObject(obj);
        obj.setLocation(null);
    }

    public void addObjectToLocation(String stationaryObject, String location) {
        //Adds "stationary" to "location"

        Location loc = getLocation(location);
        StationaryObject obj = getStationaryObject(stationaryObject);

        loc.addObject(obj);
        obj.setLocation(loc);
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

            for (StationaryObject object : stationaryObjectList) {
                if (object.getLocationName().equalsIgnoreCase(location.getLocationName())) {
                    location.addObject(object);     //Adds object to the location's list of objects
                    object.setLocation(location);   //Sets object's location reference
                }
            }
        }

        for (Item item : itemList) {
            for (GenericObject gen: genericList) {

                if(item.getLocationName().equalsIgnoreCase(gen.getName())){
                    System.out.println(gen.getName() + ", " + item.getName());
                    gen.addItem(item);
                    item.setOwner(gen);

                    if(gen instanceof Location){
                        item.setLocation((Location) gen);
                    }
                    else {
                        item.setLocation(null);
                    }

                }
            }
        }



        Location junkyard = getLocation("Junkyard");

        for (GenericObject gen : genericList) {     //Everything with an incorrect location gets sent to the junkyard.
            if(gen.getLocation() == null){
                gen.setLocation(junkyard);

                if(gen instanceof Item)
                    junkyard.addItem((Item)gen);
                else if(gen instanceof Creature)
                    junkyard.addCreature((Creature)gen);
                else if(gen instanceof StationaryObject)
                    junkyard.addObject((StationaryObject)gen);
            }
        }
    }


    // --------------- Setters & Getters ------------------------

    public boolean setMainCharacter(Creature cre) {

        playerCharacter = cre;

        return !(playerCharacter == null);

    }

    public Creature getPlayer() {
        return playerCharacter;
    }

    public Location getPlayerLocation() {
        return getLocation(getPlayer().getLocationName());
    }

    public List<Item> getPlayerInventory() {
        return getPlayer().getItemList();
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

/*    public List<StationaryObject> getLocalStationaryObjects(Location location){
        List<StationaryObject> localObjectsList = new ArrayList<>();

        for (StationaryObject obj : stationaryObjectList) {
            if (obj.getLocation() == location)
                localObjectsList.add(obj);
        }

        return localObjectsList;
    }*/

/*    public List<Creature> getLocalCreatures(Location location){
        List<Creature> localCreaturesList = new ArrayList<>();

        for (Creature cre : creatureList) {
            if (cre.getLocation() == location)
                localCreaturesList.add(cre);
        }

        return localCreaturesList;
    }*/

/*    public List<Item> getLocalItems(Location location){
        List<Item> localItemsList = new ArrayList<>();

        for (Item item : itemList) {        //Add all loose items to the local items list
            if (item.getLocation() == location)
                localItemsList.add(item);
        }


        List<Item> tmpItemsList = new ArrayList<>();
        for (Item item : localItemsList){
            tmpItemsList.addAll(item.getItemList());
        }
        localItemsList.addAll(tmpItemsList);

        for (StationaryObject obj : getLocalStationaryObjects(location)) {
            localItemsList.addAll(obj.getItemList());       //Later, have some SO's closed so you can't reach contents
        }

        for (Creature cre : getLocalCreatures(location)) {
            localItemsList.addAll(cre.getItemList());
        }

        return localItemsList;
    }*/

/*    public List<GenericObject> getLocalAll(Location location){
        List<GenericObject> localGenericsList = new ArrayList<>();

        for (GenericObject gen : genericList) {
            if (gen.getLocation() == location)
                localGenericsList.add(gen);
        }

        return localGenericsList;
    }*/



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


    // --------------- Match name methods ------------------------

    public List<String> matchNameMultiple(String name) {

        List<String> results = new ArrayList<>();

        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name

            if (generic.getName().equalsIgnoreCase(name)) {
                results.add(generic.getName());     //If the name we're looking for is its full name
            } else {
                for (String alias : generic.getAlias()) {
                    if (alias.equalsIgnoreCase(name)) {
                        results.add(generic.getName());
                    }
                }
            }
        }

        return results;
    }

    public String matchName(String name) {

        List<String> results = new ArrayList<>();

        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name


            if (generic.getName().equalsIgnoreCase(name)) {
                results.add(generic.getName());     //If the name we're looking for is its full name
            } else {
                for (String alias : generic.getAlias()) {
                    if (alias.equalsIgnoreCase(name)) {
                        results.add(generic.getName());
                    }
                }
            }
        }

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("'" + name + "' doesn't exist.");
            return "";
        } else {

            return results.get(0);
        }

    }

    public String matchLocalName(String name) {

        List<GenericObject> genList = getPlayerLocation().getAllAtLocation();


        List<String> results = new ArrayList<>();
        //genList is now everything at the location.

        for (GenericObject generic : genList) { //Check if something exists that has "name" as its short name, then return its full name

            if (generic.getName().equalsIgnoreCase(name)) {
                results.add(generic.getName());     //If the name we're looking for is its full name
            } else {
                for (String alias : generic.getAlias()) {
                    if (alias.equalsIgnoreCase(name)) {
                        results.add(generic.getName());
                    }
                }
            }
        }

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("You don't see '" + name + "' here.");
            return "";
        } else {

            return results.get(0);
        }
    }

    // ------------- Boolean checks ---------------------

    public boolean doesObjectExist(String selected) {
        return genericList.contains(getGenericObject(selected));
    }

    // ------------------ The load function! --------------------------

    public void loadListsFromFile(String loadFilePath) {

        boolean loadingSuccess;

        int counter = 0;

        do {        //Checks to see if the lists are populated properly. Yes, this means they need to have one entry minimum by default.
            counter++;
            loadingSuccess = true;
            locationList = JsonBuilder.loadLocationList(loadFilePath);
            creatureList = JsonBuilder.loadCreatureList(loadFilePath);
            itemList = JsonBuilder.loadItemList(loadFilePath);
            stationaryObjectList = JsonBuilder.loadStationaryObjectList(loadFilePath);

            if(locationList.size()==0 || creatureList.size()==0 || itemList.size()==0 || stationaryObjectList.size()==0){
                loadingSuccess = false;
            }

        } while(!loadingSuccess && counter<10);     //Keep repeating loading tries ten times or until it succeeds, in case it's something temporary

        if(!loadingSuccess){
            System.out.println("World lists may not be populated properly due to errors. You may experience problems.");
        }

        genericList = new ArrayList<>();
        genericList.addAll(locationList);
        genericList.addAll(creatureList);
        genericList.addAll(itemList);
        genericList.addAll(stationaryObjectList);
    }

    // ---------- The save function! -----------------------

    public boolean saveToFile() {

        Boolean[] successes = new Boolean[4];

        String choice = SystemData.getReply("Name your save. Type 'Q' to abort. ");

        if(choice.equalsIgnoreCase("q")){
            System.out.println("Game not saved.");
            return false;
        }

        else {

            Long savekey = JsonBuilder.addToSavesMenu(choice);  //if savekey is -1, this failed.

            if (savekey == -1) {
                System.out.println("No free save slots left.");
                return false;
            }

            if (savekey == -100) {
                System.out.println("There was a problem accessing the saverecord.");
                return false;
            }

            String saveFilePath = SystemData.getSavepath() + savekey + choice;
            File saveFile = new File(saveFilePath + "/");

            if (!saveFile.exists())
                saveFile.mkdir();


            successes[0] = JsonBuilder.saveLocationList(saveFilePath, locationList);
            successes[1] = JsonBuilder.saveCreatureList(saveFilePath, creatureList);
            successes[2] = JsonBuilder.saveItemList(saveFilePath, itemList);
            successes[3] = JsonBuilder.saveStationaryObjectList(saveFilePath, stationaryObjectList);

            for (boolean boo : successes) {
                if (!boo)
                    System.out.println("Game failed to save correctly.");
                    return false;
            }


            System.out.println("Game saved successfully.");
            return true;        //If it successfully saves all files, return true

        }
    }



    public boolean quickSave() {

        Boolean[] successes = new Boolean[4];

        String saveFilePath = SystemData.getQuickSave();
        File saveFile = new File(saveFilePath + "/");

        if (!saveFile.exists())
            saveFile.mkdir();


        successes[0] = JsonBuilder.saveLocationList(saveFilePath, locationList);
        successes[1] = JsonBuilder.saveCreatureList(saveFilePath, creatureList);
        successes[2] = JsonBuilder.saveItemList(saveFilePath, itemList);
        successes[3] = JsonBuilder.saveStationaryObjectList(saveFilePath, stationaryObjectList);

        for (boolean boo : successes) {
            if (!boo)
                System.out.println("Game failed to quicksave correctly.");
                return false;
        }

        System.out.println("Game quicksaved successfully.");
        return true;        //If it successfully saves all files, return true
    }

}
