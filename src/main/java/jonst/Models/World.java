package jonst.Models;

import jonst.CommandSheets.Commands;
import jonst.Data.*;

import jonst.Data.JsonBuilder;
//import jonst.Models.Merchandise;
import jonst.Models.Roles.MerchantRole;
import jonst.Models.Roles.VehicleRole;
import jonst.Models.Objects.*;
import jonst.Models.Time.TimeKeeper;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

import static jonst.Data.HelpfulMethods.*;


public class World {


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

    // ---------- Unused ----------
    private TimeKeeper timeKeeper;
    public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!
    // ---------- ------ ----------


    public World(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void updateWorld(String loadFilePath) {
        buildWorld(loadFilePath);
    }

    public void buildWorld(String loadFilePath) {

        loadListsFromFile(loadFilePath);        //Build lists

        populateLists();

        parser = new Parser(this);       //The parser holds lists of words, and parses input

        timeKeeper = new TimeKeeper();

        setMainCharacter(getCreature(SystemData.getProtagonist()));             //Establish protagonist

    }


    public void runGame() {

        pause();

        Commands.lookAround(this);

        while (true)                //Continuously running play loop that parses instructions

        {
            parser.runCommand(SystemData.getReply("\nPlease input command: "), this);

            timeKeeper.advance();
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
            removeItemFromHolder(item, oldHolder);
            addItemToHolder(item, newHolder);
        } else
            System.out.println("Illegal operation: transferItemToNewOwner.");
    }


    public void removeFromLocation(GenericObject object, Location location) {
        location.remove(object);
        object.setLocation(null);
    }

    public void removeItemFromHolder(Item it, GenericObject gen) {
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

    public void populateLists() {

        //Assign locations to all creatures and stationary objects;
        //Populate each location's list of creatures and stationary objects
        Lambda.processLists(
                locationList, new ArrayList<GenericObject>() {{
                    addAll(creatureList);
                    addAll(stationaryObjectList);
                }},
                (l, g) -> g.getLocationId().equalsIgnoreCase(l.getId()), (l, g) -> {
                    l.add(g);
                    g.setLocation(l);
                }
        );

        //Assign holders to all held items; assign locations to all items held by locations
        Lambda.processLists(itemList, genericList, (i, g) -> i.getLocationId().equalsIgnoreCase(g.getId()), (i, g) -> {
            g.addItem(i);
            i.setHolder(g);
            if (g instanceof Location) {
                i.setLocation((Location) g);
            } else {
                i.setLocation(null);
            }
        });

        //Assign ownership
        Lambda.processLists(new ArrayList<GenericObject>() {{
                                addAll(itemList);
                                addAll(stationaryObjectList);
                            }}, creatureList,
                (t, c) -> t.getOwnerId() != null && t.getOwnerId().equalsIgnoreCase(c.getId()), (t, c) -> {
                    t.setOwner(c);
                });

        //Assign default enters and exits
        for (Location superLocation : locationList) {
            for (Location subLocation : locationList) {
                if (superLocation.getDefaultEnterId() != null && superLocation.getDefaultEnterId().equals(subLocation.getId())) {
                    superLocation.setDefaultEnter(subLocation);
                } else if (superLocation.getDefaultExitId() != null && superLocation.getDefaultExitId().equals(subLocation.getId())) {
                    superLocation.setDefaultExit(subLocation);
                }
                if (superLocation.getDefaultEnter() != null && superLocation.getDefaultExit() != null) {
                    break;
                }
            }
        }

        //Assign destinations to vehicles
        Lambda.processList(stationaryObjectList, p -> p.getRoles().containsKey("VehicleRole"), p -> {
            if (((VehicleRole) p.getRoleByKey("VehicleRole")).getDestinationIds() != null) {
                Lambda.processList(((VehicleRole) p.getRoleByKey("VehicleRole")).getDestinationIds(), q -> {
                    ((VehicleRole) p.getRoleByKey("VehicleRole")).addDestination(getLocationByID(q));
                });
            }
        });

        //Assign merchandise to merchants
        Lambda.processList(creatureList, c -> c.getRoles().containsKey("MerchantRole"), c -> {
            if (((MerchantRole) c.getRoleByKey("MerchantRole")).getMerchandiseIds() != null) {
                Lambda.processList(((MerchantRole) c.getRoleByKey("MerchantRole")).getMerchandiseIds(), q -> {
                    ((MerchantRole) c.getRoleByKey("MerchantRole")).addMerchandise((Item) Lambda.getFirst(templateList, p -> p.getId().equals(q) && p instanceof Item));
                });
            }
        });

    }


    // --------------- Setters & Getters ------------------------

    public boolean setMainCharacter(Creature creature) {

        playerCharacter = creature;
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

    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }

    public List<GenericObject> getLocalGenericList() {

        return getPlayerLocation().getAllAtLocation();
    }

    public List<GenericObject> getLocalGroundOnly() {

        return getPlayerLocation().getAllGroundOnly();
    }


    // ------------- Methods that returns objects from object lists ------------------

    public Location getLocationByName(String wantedLocation) {
        return Lambda.getFirst(locationList, Lambda.objectByName(wantedLocation));
    }

    public Location getLocationByID(String wantedLocationId) {
        return Lambda.getFirst(locationList, Lambda.predicateById(wantedLocationId));
    }

    public Creature getCreature(String wantedCreature) {
        return Lambda.getFirst(creatureList, Lambda.objectByName(wantedCreature));
    }

    public Creature getCreatureById(String wantedCreatureId) {
        return Lambda.getFirst(creatureList, Lambda.predicateById(wantedCreatureId));
    }

    public Item getItem(String wantedItem) {
        return Lambda.getFirst(itemList, Lambda.objectByName(wantedItem));
    }

    public Item getItemById(String wantedItemId) {
        return Lambda.getFirst(itemList, Lambda.predicateById(wantedItemId));
    }

    public GenericObject getTemplate(String wantedTemplate) {
        return Lambda.getFirst(templateList, Lambda.objectByName(wantedTemplate));
    }

    public GenericObject getTemplateById(String wantedTemplateId) {
        return Lambda.getFirst(templateList, Lambda.predicateById(wantedTemplateId));
    }

    public StationaryObject getStationaryObject(String wantedStationaryObject) {
        return Lambda.getFirst(stationaryObjectList, Lambda.objectByName(wantedStationaryObject));
    }

    public StationaryObject getStationaryObjectById(String wantedStationaryObjectId) {
        return Lambda.getFirst(stationaryObjectList, Lambda.predicateById(wantedStationaryObjectId));
    }

    public GenericObject getGenericObject(String wantedGenericObject) {
        return Lambda.getFirst(genericList, Lambda.objectByName(wantedGenericObject));
    }

    public GenericObject getGenericObjectById(String wantedGenericObjectId) {
        return Lambda.getFirst(genericList, Lambda.predicateById(wantedGenericObjectId));
    }

    public GenericObject getLocalGenericObject(String wantedGenericObject) {
        return Lambda.getFirst(getPlayerLocation().getAllAtLocation(), Lambda.objectByName(wantedGenericObject));
    }

    public GenericObject getLocalGenericOnGround(String wantedGenericObject) {
        return Lambda.getFirst(getPlayerLocation().getAllGroundOnly(), Lambda.objectByName(wantedGenericObject));
    }

    public GenericObject getFromInventory(String wantedGenericObject) {
        return Lambda.getFirst(getPlayerInventory(), Lambda.objectByName(wantedGenericObject));
    }

    public Dialog getDialogEntry(String dialogKey) {
        return Lambda.getFirst(dialogList, d -> d.getKey().equalsIgnoreCase(dialogKey));
    }

    // --------------- Match name methods ------------------------

    public <T extends GenericObject> T match(List<T> list, Predicate<T> predicate) {

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

    public <T extends GenericObject> List<T> matchMultiple(List<T> list, Predicate<T> predicate) {
        return Lambda.subList(list, predicate);
    }

    // ------------------ The load function! --------------------------

    public void loadListsFromFile(String loadFilePath) {

        boolean loadingSuccess = true;

        creatureList = JsonBuilder.loadList(loadFilePath + "/creatures.json", "creature");
        locationList = JsonBuilder.loadList(loadFilePath + "/locations.json", "location");
        itemList = JsonBuilder.loadList(loadFilePath + "/items.json", "item");
        stationaryObjectList = JsonBuilder.loadList(loadFilePath + "/objects.json", "stationaryobject");

        exitList = JsonBuilder.loadExitList(loadFilePath, locationList);

        dialogList = JsonBuilder.generateDialogList();
        templateList = JsonBuilder.generateTemplateList();

        if (locationList.size() == 0 || creatureList.size() == 0 || itemList.size() == 0 || stationaryObjectList.size() == 0) {
            loadingSuccess = false;
        }

        if (!loadingSuccess) {
            System.out.println("World lists may not be populated properly due to errors. You may experience problems.");
        }

        genericList = new ArrayList<GenericObject>(){{
        addAll(locationList);
        addAll(creatureList);
        addAll(itemList);
        addAll(stationaryObjectList);
        }};
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
        return save(SystemData.getQuickSave());
    }

//    public static boolean saveCreatureList(String filepath, List<Creature> creatureList) {
//        return JsonBuilder.saveGenericList(filepath + "/creatures.json", creatureList);
//
//    }

    public boolean save(String saveFilePath) {

        File saveFile = new File(saveFilePath + "/");

        if (!saveFile.exists())
            saveFile.mkdir();

        Boolean[] successes = new Boolean[5];

        successes[0] = JsonBuilder.saveGenericList(saveFilePath + "/locations.json", locationList);
        successes[1] = JsonBuilder.saveGenericList(saveFilePath + "/creatures.json", creatureList);
        successes[2] = JsonBuilder.saveGenericList(saveFilePath + "/items.json", itemList);
        successes[3] = JsonBuilder.saveGenericList(saveFilePath + "/objects.json", stationaryObjectList);

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
