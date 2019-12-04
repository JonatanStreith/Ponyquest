package jonst;

import jonst.Data.*;
import jonst.Models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class World {

    public HashMap<String, String> gameFlags = new HashMap<String, String>();     //Use this to store event flags and the like!

    public List<Location> locationList = new ArrayList<Location>();      //Main lists that store all game objects
    public List<Creature> creatureList = new ArrayList<Creature>();
    public List<StationaryObject> stationaryObjectList = new ArrayList<StationaryObject>();
    public List<Item> itemList = new ArrayList<Item>();
    public List<GenericObject> genericList = new ArrayList<GenericObject>();

    public List<Item> playerInventory = new ArrayList<Item>();

    public List<String> legitimateNouns = new ArrayList<String>();
    public List<String> legitimateCommands = SystemData.getLegitimateCommands();
    public List<String> legitimateConjunctions = SystemData.getLegitimateConjunctions();


     DialogData dialogs;

    public World(String loadFilePath) {


        loadListsFromFile(loadFilePath);

        //Build lists



        createProperNounList();                      //Create and sort lists for the parser
        sortCommandAndConjunctionLists();

    }  //End of World constructor




//-------- Add/Remove stuff -----------------------------------

    public void addCreatureToLocation(String creature, String location) {
        //Adds "creature" to "location"

        Location loc = getLocation(location);
        Creature cre = getCreature(creature);

        loc.addCreature(cre);
        cre.setLocation(location);
    }

    public void removeCreatureFromLocation(String creature, String location) {
        getLocation(location).removeCreature(getCreature(creature));
    }

    public void addItemToLocation(String item, String location) {
        //Adds "item" to "location"
        getLocation(location).addItem(getItem(item));
        getItem(item).setLocation(location);
    }

    public void removeItemFromLocation(String item, String location) {
        getLocation(location).removeItem(getItem(item));
    }

    public void addObjectToLocation(String stationaryObject, String location) {
        //Adds "stationary" to "location"

        Location loc = getLocation(location);
        StationaryObject sta = getStationaryObject(stationaryObject);

        loc.addObject(sta);
        sta.setLocation(location);
    }

    public void removeObjectFromLocation(String stationaryObject, String location) {
        getLocation(location).removeObject(getStationaryObject(stationaryObject));
    }

    public void addToInventory(Item item) {
        playerInventory.add(item);
        item.setLocation("inventory");
    }

    public void removeFromInventory(Item item) {
        playerInventory.remove(item);
    }

    public boolean isInInventory(Item item) {
        return playerInventory.contains(item);
    }

    public List<Item> getInventory() {
        return playerInventory;
    }


    //-------- List handling -----------------------

    public void createProperNounList() {
        for (GenericObject gen : genericList) {
            legitimateNouns.add(gen.getName());
            legitimateNouns.add(gen.getShortName());
        }
        Collections.sort(legitimateCommands);   //Sorts list; this avoids confusion (why do I do this?)
    }


    public void sortCommandAndConjunctionLists() {
        Collections.sort(legitimateCommands);
        Collections.sort(legitimateConjunctions);
    }



    // --------------- Getters ------------------------

    public Creature getPlayer() {
        for (Creature creature : creatureList) {
            if (creature.getName().toLowerCase().equals("Trixie"))
                return creature;
        }
        return null;
    }

    public Location getPlayerLocation() {
        return getLocation(getPlayer().getLocationName());
    }

    public Location getLocation(String wantedLocation) {
        //return locationList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Location location : locationList) {
            if (location.getName().toLowerCase().contains(wantedLocation.toLowerCase()))
                return location;
        }
        return null;
    }

    public Creature getCreature(String wantedCreature) {
        //return creatureList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Creature creature : creatureList) {
            if (creature.getName().toLowerCase().contains(wantedCreature.toLowerCase()))
                return creature;
        }
        return null;
    }

    public Item getItem(String wantedItem) {
        //return itemList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(wantedItem.toLowerCase()))
                return item;
        }
        return null;
    }

    public StationaryObject getStationaryObject(String wantedStationaryObject) {
        //return stationaryObjectList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (StationaryObject stationaryObject : stationaryObjectList) {
            if (stationaryObject.getName().toLowerCase().equals(wantedStationaryObject.toLowerCase()))
                return stationaryObject;
        }
        return null;
    }

    public GenericObject getGenericObject(String wantedGenericObject) {
        //return genericList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (GenericObject genericObject : genericList) {
            if (genericObject.getName().toLowerCase().contains(wantedGenericObject.toLowerCase()))
                return genericObject;
        }
        return null;
    }

    public String returnFullName(String name) {
        String fullName = name;

        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name
            if (generic.getShortName().equals(name)) {
                return generic.getName();
            }
        }
        return name;        //if not, just return the short name
    }

    // ------------- Boolean checks ---------------------

    public boolean isObjectPresent(String generic) {
        return ((getPlayer().getLocationName().equals(getGenericObject(generic).getLocationName())) || (getGenericObject(generic).getLocationName().equals("inventory")));
    }

    public boolean doesObjectExist(String generic) {
        return genericList.contains(getGenericObject(generic));
    }







    // ------------------ The build methods --------------------------

    public void loadListsFromFile(String loadFilePath){
        locationList = JsonBuilder.loadLocationList(loadFilePath);
        creatureList = JsonBuilder.loadCreatureList(loadFilePath);
        itemList = JsonBuilder.loadItemList(loadFilePath);
        stationaryObjectList = JsonBuilder.loadStationaryObjectList(loadFilePath);

        genericList.addAll(locationList);
        genericList.addAll(creatureList);
        genericList.addAll(itemList);
        genericList.addAll(stationaryObjectList);
    }

    // ---------- The save function! -----------------------

    public boolean saveToFile(String saveFilePath) throws IOException {

        boolean allSuccesses = true;
        Boolean[] successes = new Boolean[4];

        successes[0] = JsonBuilder.saveLocationList(saveFilePath, locationList);
        successes[1] = JsonBuilder.saveCreatureList(saveFilePath, creatureList);
        successes[2] = JsonBuilder.saveItemList(saveFilePath, itemList);
        successes[3] = JsonBuilder.saveStationaryObjectList(saveFilePath, stationaryObjectList);

        for (boolean boo : successes) {
            if(!boo)
                allSuccesses= false;
        }
        return allSuccesses;        //If it successfully saves all files, return true
    }

}
