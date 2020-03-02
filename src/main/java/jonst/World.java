package jonst;

import jonst.Data.*;

import jonst.Models.Dialog;
import jonst.Models.Exit;
import jonst.Models.Merchandise;
import jonst.Models.Objects.*;
import jonst.Models.Parser;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

import static jonst.HelpfulMethods.*;


public class World {

    public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!

    private List<Location> locationList;      //Main lists that store all game objects
    private List<Creature> creatureList;
    private List<StationaryObject> stationaryObjectList;
    private List<Item> itemList;
    private List<GenericObject> genericList;

    private List<GenericObject> templateList;

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

        parser = new Parser(this);       //The parser holds lists of words, and parses input

        setMainCharacter(getCreature(SystemData.getProtagonist()));             //Establish protagonist

    }


    public void runGame() {

        pause();

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

//        if (holder instanceof Location) {        //If item is moved to a location, its location field is set to that place. Otherwise, it's set to null.
//            thing.setLocation((Location) holder);
//        } else {
//            thing.setLocation(null);
//        }
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

    public void addExit(Exit exit) {
        exitList.add(exit);
    }

    //------------------ If items are removed permanently, they need to be removed from the world lists

    public void removeFromList(GenericObject gen) {
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

    public void removeExit(Exit exit) {
        exitList.remove(exit);
    }

    //-------- List handling -----------------------

    public void populateLocationLists() {


        Lambda.processLists(locationList, new ArrayList<GenericObject>() {{
                    addAll(creatureList);
                    addAll(stationaryObjectList);
                }},
                (l, g) -> g.getLocationId().equalsIgnoreCase(l.getId()), (l, g) -> {
                    l.add(g);
                    g.setLocation(l);
                });

        Lambda.processLists(itemList, genericList, (i, g) -> i.getLocationId().equalsIgnoreCase(g.getId()), (i, g) -> {
            g.addItem(i);
            i.setHolder(g);
            if (g instanceof Location) {
                i.setLocation((Location) g);
            } else {
                i.setLocation(null);
            }
        });

        Lambda.processLists(new ArrayList<GenericObject>() {{
                                addAll(itemList);
                                addAll(stationaryObjectList);
                            }}, creatureList,
                (t, c) -> t.getOwnerId() != null && t.getOwnerId().equalsIgnoreCase(c.getId()), (t, c) -> {
                    t.setOwner(c);
                });

        for (Location location : locationList) {
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


        Lambda.processList(stationaryObjectList, p -> p instanceof Vehicle, p -> {

            if (((Vehicle) p).getDestinationIds() != null) {


                Lambda.processList(((Vehicle) p).getDestinationIds(), q -> {
                    ((Vehicle) p).addDestination(getLocationByID(q));
                });
            }
        });

        Lambda.processList(creatureList, c -> c instanceof Merchant, c -> {

            if (((Merchant) c).getMerchandiseIds() != null) {

                Lambda.processList(((Merchant) c).getMerchandiseIds(), q -> {
                    ((Merchant) c).addMerchandise((Item) Lambda.getFirst(templateList, p -> p.getId().equals(q) && p instanceof Item));
                });

            }
        });

        //List<StationaryObject> vehicleList = Lambda.subList(stationaryObjectList, p -> p instanceof Vehicle);

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

    public List<GenericObject> getTemplateList() {
        return templateList;
    }

    public Parser getParser() {
        return parser;
    }

    public List<GenericObject> getLocalGenericList() {

        return getPlayerLocation().getAllAtLocation();
    }

    public List<GenericObject> getLocalGroundOnly() {

        return getPlayerLocation().getAllGroundOnly();
    }


    // ------------- Methods that returns objects from object lists ------------------


    public Location getLocationByName(String wantedLocation) {

        return Lambda.getFirst(locationList, l -> l.getName().equalsIgnoreCase(wantedLocation));
    }

    public Location getLocationByID(String id) {

        return Lambda.getFirst(locationList, l -> l.getId().equalsIgnoreCase(id));
    }

    public Creature getCreature(String wantedCreature) {

        return Lambda.getFirst(creatureList, c -> c.getName().equalsIgnoreCase(wantedCreature));
    }

    public Item getItem(String wantedItem) {

        return Lambda.getFirst(itemList, i -> i.getName().equalsIgnoreCase(wantedItem));
    }

    public Item getItemById(String wantedItem) {

        return Lambda.getFirst(itemList, i -> i.getId().equalsIgnoreCase(wantedItem));
    }

    public StationaryObject getStationaryObject(String wantedStationaryObject) {

        return Lambda.getFirst(stationaryObjectList, s -> s.getName().equalsIgnoreCase(wantedStationaryObject));
    }

    public Dialog getDialogEntry(String dialogKey) {

        return Lambda.getFirst(dialogList, d -> d.getKey().equalsIgnoreCase(dialogKey));
    }

    public GenericObject getGenericObject(String wantedGenericObject) {

        return Lambda.getFirst(genericList, g -> g.getName().equalsIgnoreCase(wantedGenericObject));
    }

    public GenericObject getLocalGenericObject(String wantedGenericObject) {

        return Lambda.getFirst(getPlayerLocation().getAllAtLocation(), g -> g.getName().equalsIgnoreCase(wantedGenericObject));
    }

    public GenericObject getLocalGenericOnGround(String wantedGenericObject) {

        return Lambda.getFirst(getPlayerLocation().getAllGroundOnly(), g -> g.getName().equalsIgnoreCase(wantedGenericObject));
    }


    // --------------- Match name methods ------------------------

    public <T extends GenericObject> T match(List<T> list, String name, Predicate<T> predicate) {

//        if (name.equals("")) {
//            System.out.println("Incomplete command.");
//            return null;
//        }

        List<T> results = Lambda.subList(list, predicate);

        if (results.size() == 1 || isIdentical(results)) {
            return results.get(0);
        } else if (results.size() > 1) {
            System.out.println("Which do you mean, " + turnListIntoString(results, "or") + "?");
            return null;
        } else {
            //System.out.println("'" + name + "' doesn't exist.");
            return null;
        }
    }

    public <T extends GenericObject> List<T> matchMultiple(List<T> list, String name, Predicate<T> predicate) {

//        if (name.equals("")) {
//            System.out.println("Incomplete command.");
//            return null;
//        }

        return Lambda.subList(list, predicate);
    }

    // ------------------ The load function! --------------------------

    public void loadListsFromFile(String loadFilePath) {

        boolean loadingSuccess = true;

        locationList = JsonBuilder.loadLocationList(loadFilePath);
        creatureList = JsonBuilder.loadCreatureList(loadFilePath);
        itemList = JsonBuilder.loadItemList(loadFilePath);
        stationaryObjectList = JsonBuilder.loadStationaryObjectList(loadFilePath);

        exitList = JsonBuilder.loadExitList(loadFilePath, locationList);

        dialogList = JsonBuilder.generateDialogList();
        templateList = JsonBuilder.generateTemplateList();

        if (locationList.size() == 0 || creatureList.size() == 0 || itemList.size() == 0 || stationaryObjectList.size() == 0) {
            loadingSuccess = false;
        }


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
