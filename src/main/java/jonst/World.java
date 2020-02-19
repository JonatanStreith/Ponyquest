package jonst;

import jonst.Data.*;

import jonst.Models.Dialog;
import jonst.Models.Exit;
import jonst.Models.Objects.*;
import jonst.Models.Parser;

import java.io.File;
import java.util.*;


public class World {

    public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!

    private List<Location> locationList;      //Main lists that store all game objects
    private List<Creature> creatureList;
    private List<StationaryObject> stationaryObjectList;
    private List<Item> itemList;
    private List<GenericObject> genericList;

    private List<Exit> exitList;
    private List<Dialog> dialogList;

    private Creature playerCharacter = null;

    private Parser parser;


    public World(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void updateWorld(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void buildWorld(String loadFilePath) {

        loadListsFromFile(loadFilePath);        //Build lists

        populateLocationLists();

        parser = new Parser(genericList);       //The parser holds lists of words, and parses input

        setMainCharacter(getCreature(SystemData.getProtagonist()));             //Establish protagonist

    }


    public void runGame() {

        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        Commands.lookAround(this);

        while (true)                //Continuously running play loop that parses instructions

        {
            parser.runCommand(SystemData.getReply("\nPlease input command: "), this);
        }
    }


//-------- Add/Remove stuff -----------------------------------

    public void moveToLocation(GenericObject object, Location oldLocation, Location newLocation) {

        if (object != null && oldLocation != null && newLocation != null) {
            removeFromLocation(object, oldLocation);
            addToLocation(object, newLocation);
        } else
            System.out.println("Illegal operation: transferToLocation.");
    }

    public void transferItemToNewHolder(Item item, GenericObject oldHolder, GenericObject newHolder) {

        if (item != null && oldHolder != null && newHolder != null) {
            removeItemFromGeneric(item, oldHolder);
            addItemToHolder(item, newHolder);
        } else
            System.out.println("Illegal operation: transferItemToNewOwner.");
    }


    public void removeFromLocation(GenericObject object, Location location) {
        location.remove(object);
        object.setLocation(null);
    }

    public void removeItemFromGeneric(Item it, GenericObject gen) {
        gen.removeItem(it);
        it.setHolder(null);
    }

    public void addToLocation(GenericObject object, Location location) {

        location.add(object);
        object.setLocation(location);
    }

    public void addItemToHolder(Item thing, GenericObject holder) {
        //Adds "item" to "location"
        holder.addItem(thing);
        thing.setHolder(holder);

        if (holder instanceof Location) {        //If item is moved to a location, its location field is set to that place. Otherwise, it's set to null.
            thing.setLocation((Location) holder);
        } else {
            thing.setLocation(null);
        }
    }

    //------------- If new objects are created, they need to be added to the world lists ----------

    public void addNewToList(GenericObject newGen) {
        if (newGen instanceof Item) {
            itemList.add((Item) newGen);
        } else if (newGen instanceof Creature) {
            creatureList.add((Creature) newGen);
        } else if (newGen instanceof Location) {
            locationList.add((Location) newGen);
        } else if (newGen instanceof StationaryObject) {
            stationaryObjectList.add((StationaryObject) newGen);
        }
        genericList.add(newGen);
    }

    //------------------ If items are removed permanently, they need to be removed from the world lists

    public void removeFromList(GenericObject gen){
        if (gen instanceof Item) {
            itemList.remove((Item) gen);
        } else if (gen instanceof Creature) {
            creatureList.remove((Creature) gen);
        } else if (gen instanceof Location) {
            locationList.remove((Location) gen);
        } else if (gen instanceof StationaryObject) {
            stationaryObjectList.remove((StationaryObject) gen);
        }
        genericList.remove(gen);
    }

/*    public void removeItemFromItemList(Item item) {
        itemList.remove(item);
        genericList.remove(item);
    }

    public void removeCreatureFromCreatureList(Creature creature) {
        creatureList.remove(creature);
        genericList.remove(creature);
    }

    public void removeLocationFromLocationList(Location location) {
        locationList.remove(location);
        genericList.remove(location);
    }

    public void removeStationaryObjectFromStationaryObjectList(StationaryObject stationaryObject) {
        stationaryObjectList.remove(stationaryObject);
        genericList.remove(stationaryObject);
    }*/

    //-------- List handling -----------------------

    public void populateLocationLists() {

        for (Location location : locationList) {

            for (Creature creature : creatureList) {
                if (creature.getLocationId().equalsIgnoreCase(location.getId())) {
                    location.add(creature); //Adds creature to the location's list of creatures
                    creature.setLocation(location); //Sets creature's location reference
                }
            }

            for (StationaryObject object : stationaryObjectList) {
                if (object.getLocationId().equalsIgnoreCase(location.getId())) {
                    location.add(object);     //Adds object to the location's list of objects
                    object.setLocation(location);   //Sets object's location reference
                }
            }

            for (Location loc : locationList) {
                if (location.getDefaultEnterId() != null && location.getDefaultEnterId().equals(loc.getId())) {
                    location.setDefaultEnter(loc);
                } else if (location.getDefaultExitId() != null && location.getDefaultExitId().equals(loc.getId())) {
                    location.setDefaultExit(loc);
                }
                if (location.getDefaultEnter() != null && location.getDefaultExit() != null) {
                    break;
                }
            }
        }

        for (Item item : itemList) {
            for (GenericObject gen : genericList) {

                if (item.getLocationId().equalsIgnoreCase(gen.getId())) {

                    gen.addItem(item);
                    item.setHolder(gen);

                    if (gen instanceof Location) {
                        item.setLocation((Location) gen);
                    } else {
                        item.setLocation(null);
                    }
                }
            }
        }

        for (
                Item item : itemList) {
            if (item.getOwnerName() != null) {
                for (Creature creature : creatureList) {
                    if (item.getOwnerName().equalsIgnoreCase(creature.getName())) {
                        item.setOwner(creature);
                    }
                }
            }
        }

        for (
                StationaryObject obj : stationaryObjectList) {
            if (obj.getOwnerName() != null) {
                for (Creature creature : creatureList) {
                    if (obj.getOwnerName().equalsIgnoreCase(creature.getName())) {
                        obj.setOwner(creature);
                    }
                }
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
        return getLocationByID(getPlayer().getLocationId());
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

    public List<Exit> getExitList() {
        return exitList;
    }

    public List<Dialog> getDialogList() {
        return dialogList;
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

    public Location getLocationByName(String wantedLocation) {

        for (Location location : locationList) {

            //Instead of using equals, could use contains?
            if (location.getName().equalsIgnoreCase(wantedLocation))
                return location;
        }
        return null;
    }

    public Location getLocationByID(String id) {

        for (Location location : locationList) {

            //Instead of using equals, could use contains?
            if (location.getId().equalsIgnoreCase(id))
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

    public Dialog getDialogEntry(String dialogKey) {

        for (Dialog dialog : dialogList) {
            if (dialog.getKey().equalsIgnoreCase(dialogKey))
                return dialog;
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

    public GenericObject getLocalGenericObject(String wantedGenericObject) {

        List<GenericObject> localList = getPlayerLocation().getAllAtLocation();

        for (GenericObject genericObject : localList) {
            if (genericObject.getName().equalsIgnoreCase(wantedGenericObject))
                return genericObject;
        }
        return null;

    }

    public GenericObject getLocalGenericOnGround(String wantedGenericObject) {

        List<GenericObject> localList = getPlayerLocation().getAllGroundOnly();

        for (GenericObject genericObject : localList) {
            if (genericObject.getName().equalsIgnoreCase(wantedGenericObject))
                return genericObject;
        }
        return null;
    }


    // --------------- Match name methods ------------------------

    public List<Location> matchLocationsMultiple(String name) {

        List<Location> results = new ArrayList<>();

        for (Location loc : locationList) {
            if (loc.getName().equalsIgnoreCase(name)) {
                results.add(loc);     //If the name we're looking for is its full name
            } else {
                for (String alias : loc.getAlias()) {
                    if (alias.equalsIgnoreCase(name)) {
                        results.add(loc);
                        break;
                    }
                }
            }
        }
        return results;
    }


    public List<String> matchNameMultiple(String name) {

        List<GenericObject> resultsGen = Lambda.subList(genericList, (GenericObject generic) -> {return ((GenericObject) generic).getName().equalsIgnoreCase(name) ||
                ( (GenericObject) generic).getAlias().contains(name); } );

        return Lambda.getSubvalues(resultsGen, g -> g.getName());

    }

    public String matchId(String name) {

        List<GenericObject> resultsGen = Lambda.subList(genericList, (GenericObject generic) -> {return ((GenericObject) generic).getName().equalsIgnoreCase(name) ||
                ( (GenericObject) generic).getAlias().contains(name); } );

        List<String> results = Lambda.getSubvalues(resultsGen, g -> g.getName());

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("'" + name + "' doesn't exist.");
            return "";
        } else {
            return getGenericObject(results.get(0)).getId();
        }
    }

    public String matchName(String name) {


        List<GenericObject> resultsGen = Lambda.subList(genericList, (GenericObject generic) -> {return ((GenericObject) generic).getName().equalsIgnoreCase(name) ||
                ( (GenericObject) generic).getAlias().contains(name); } );

        List<String> results = Lambda.getSubvalues(resultsGen, g -> g.getName());


//        List<String> results = new ArrayList<>();
//
//        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name
//
//            if (generic.getName().equalsIgnoreCase(name)) {
//                results.add(generic.getName());     //If the name we're looking for is its full name
//            } else {
//                for (String alias : generic.getAlias()) {
//                    if (alias.equalsIgnoreCase(name)) {
//                        results.add(generic.getName());
//                    }
//                }
//            }
//        }

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

    public String matchNameFromInventory(GenericObject holder, String name) {

        List<Item> itemList = holder.getItemList();



        List<Item> resultsItm = Lambda.subList(itemList, (Item generic) -> {return ((Item) generic).getName().equalsIgnoreCase(name) ||
                ( (Item) generic).getAlias().contains(name); } );

        List<String> results = Lambda.getSubvalues(resultsItm, g -> g.getName());




//        for (Item item : itemList) { //Check if something exists that has "name" as its short name, then return its full name
//
//            if (item.getName().equalsIgnoreCase(name)) {
//                results.add(item.getName());     //If the name we're looking for is its full name
//            } else {
//                for (String alias : item.getAlias()) {
//                    if (alias.equalsIgnoreCase(name)) {
//                        results.add(item.getName());
//                    }
//                }
//            }
//        }

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("You don't carry a '" + name + "'.");
            return "";
        } else {

            return results.get(0);
        }
    }


    public String matchLocalName(String name) {

        List<GenericObject> genList = getPlayerLocation().getAllAtLocation();


        List<GenericObject> resultsGen = Lambda.subList(genList, (GenericObject generic) -> {return generic.getName().equalsIgnoreCase(name) ||
                 generic.getAlias().contains(name); } );

        List<String> results = Lambda.getSubvalues(resultsGen, g -> g.getName());



//        List<String> results = new ArrayList<>();
//        //genList is now everything at the location.
//
//        for (GenericObject generic : genList) { //Check if something exists that has "name" as its short name, then return its full name
//
//            if (generic.getName().equalsIgnoreCase(name)) {
//                results.add(generic.getName());     //If the name we're looking for is its full name
//            } else {
//                for (String alias : generic.getAlias()) {
//                    if (alias.equalsIgnoreCase(name)) {
//                        results.add(generic.getName());
//                    }
//                }
//            }
//        }

        results = HelpfulMethods.removeDuplicatesT(results);

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("You don't see a '" + name + "' here.");
            return "";
        } else {

            return results.get(0);
        }
    }


    public String matchLocalOnGround(String name) {

        List<GenericObject> genList = getPlayerLocation().getAllGroundOnly();


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

        results = HelpfulMethods.removeDuplicatesT(results);

        if (results.size() > 1) {
            System.out.println("Which do you mean, " + HelpfulMethods.turnStringListIntoString(results, "or") + "?");
            return "";
        } else if (results.size() == 0) {
            System.out.println("You don't see a '" + name + "' here.");
            return "";
        } else {

            return results.get(0);
        }
    }

    // ------------- Boolean checks ---------------------

    public boolean doesObjectExist(String selected) {
        return getGenericObject(selected) != null;
    }

    // ------------------ The load function! --------------------------

    public void loadListsFromFile(String loadFilePath) {

        boolean loadingSuccess;

        int counter = 0;

        do
        {        //Checks to see if the lists are populated properly. Yes, this means they need to have one entry minimum by default.
            counter++;
            loadingSuccess = true;
            locationList = JsonBuilder.loadLocationList(loadFilePath);
            creatureList = JsonBuilder.loadCreatureList(loadFilePath);
            itemList = JsonBuilder.loadItemList(loadFilePath);
            stationaryObjectList = JsonBuilder.loadStationaryObjectList(loadFilePath);

            exitList = JsonBuilder.loadExitList(loadFilePath, locationList);

            dialogList = JsonBuilder.generateDialogList();

            if (locationList.size() == 0 || creatureList.size() == 0 || itemList.size() == 0 || stationaryObjectList.size() == 0) {
                loadingSuccess = false;
            }

        } while (!loadingSuccess && counter < 10);     //Keep repeating loading tries ten times or until it succeeds, in case it's something temporary

        if (!loadingSuccess) {
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


        String choice = SystemData.getReply("Name your save. Type 'Q' to abort. ");

        if (choice.equalsIgnoreCase("q")) {
            System.out.println("Game not saved.");
            return false;
        } else {

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


            return save(saveFilePath);

        }
    }

    public boolean quickSave() {


        String saveFilePath = SystemData.getQuickSave();

        return save(saveFilePath);

    }

    public boolean save(String saveFilePath) {

        File saveFile = new File(saveFilePath + "/");

        if (!saveFile.exists())
            saveFile.mkdir();

        Boolean[] successes = new Boolean[5];
        successes[0] = JsonBuilder.saveLocationList(saveFilePath, locationList);
        successes[1] = JsonBuilder.saveCreatureList(saveFilePath, creatureList);
        successes[2] = JsonBuilder.saveItemList(saveFilePath, itemList);
        successes[3] = JsonBuilder.saveStationaryObjectList(saveFilePath, stationaryObjectList);
        successes[4] = JsonBuilder.saveExitList(saveFilePath, exitList);

        for (boolean boo : successes) {
            if (!boo) {
                System.out.println("Game failed to save correctly.");
                return false;
            }
        }

        System.out.println("Game saved successfully.");
        return true;        //If it successfully saves all files, return true

    }


}
