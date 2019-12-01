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

    public ArrayList<Location> locationList = new ArrayList<Location>();      //Main lists that store all game objects
    public ArrayList<Creature> creatureList = new ArrayList<Creature>();
    public ArrayList<StationaryObject> stationaryObjectList = new ArrayList<StationaryObject>();
    public ArrayList<Item> itemList = new ArrayList<Item>();
    public ArrayList<GenericObject> genericList = new ArrayList<GenericObject>();

    public ArrayList<Item> playerInventory = new ArrayList<Item>();

    public ArrayList<String> legitimateNouns = new ArrayList<String>();
    public ArrayList<String> legitimateCommands = SystemData.getLegitimateCommands();
    public ArrayList<String> legitimateConjunctions = SystemData.getLegitimateConjunctions();

     private Scanner myReader;

     DialogData dialogs;

    public World(String loadFilePath) {

        DataOrganizer dataOrganizer = new DataOrganizer();

        dialogs = dataOrganizer.getDialogData();



        System.out.println(loadFilePath);


        buildGenericObjectLists(loadFilePath, dataOrganizer);                   //Create and add all objects to the main lists

        //addGenericObjectsToLocations(loadFilePath);              //Add all objects to their specified locations

        addGameFlags(loadFilePath);                              //Add all game flags, to track conditions and stuff

        createProperNounList();                      //Create and sort lists for the parser
        sortCommandAndConjunctionLists();

        myReader.close();

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

    public ArrayList<Item> getInventory() {
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

    public void buildGenericObjectLists(String loadFilePath, DataOrganizer dataOrganizer) {

        LocationData loD = dataOrganizer.getLocationData();
        CreatureData crD = dataOrganizer.getCreatureData();
        ItemData itD = dataOrganizer.getItemData();
        StationaryObjectData soD = dataOrganizer.getStationaryObjectData();


        try {
            File locations = new File(loadFilePath + "/Locations.txt");

            System.out.println("Locations: " + locations);

            myReader = new Scanner(locations);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    Location loc =new Location(line, loD.getLocationShortName(line), loD.getLocationDescription(line), "", loD.getLegitimateExits(line));

                    locationList.add(loc);
                    genericList.add(loc);
                    loc.getFeedback();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Locations file not found.");
        }

        try {
            File creatures = new File(loadFilePath + "/Creatures.txt");
            myReader = new Scanner(creatures);

            System.out.println("creatures: " + creatures);

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    String[] frag = line.split(": ");
                    String name = frag[0];
                    String race = frag[1];


                        Creature cre = new Creature(name, crD.getCreatureShortName(name), crD.getCreatureDescription(name), "", race, new ArrayList<>());

                        creatureList.add(cre);
                        genericList.add(cre);
                        cre.getFeedback();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Creatures file not found.");
        }


        try {
            File items = new File(loadFilePath + "/Items.txt");

            System.out.println("items: " + items);

            myReader = new Scanner(items);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    Item ite = new Item(line, itD.getItemShortName(line), itD.getItemDescription(line), "");

                    itemList.add(ite);
                    genericList.add(ite);
                    ite.getFeedback();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Items file not found.");
        }

        try {
            File stationary = new File(loadFilePath + "/StationaryObjects.txt");

            System.out.println("Stationary objects: " + stationary);

            myReader = new Scanner(stationary);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    StationaryObject sta = new StationaryObject(line, soD.getStationaryObjectShortName(line), soD.getStationaryObjectDescription(line), "");

                    stationaryObjectList.add(sta);
                    genericList.add(sta);
                    sta.getFeedback();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. StationaryObjects file not found.");
        }


    }






    public void addGenericObjectsToLocations(String loadFilePath) {

        try {
            File creatureToLocation = new File(loadFilePath + "/CreatureToLocation.txt");
            myReader = new Scanner(creatureToLocation);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    String[] frag = line.split(": ");


                    addCreatureToLocation(frag[0], frag[1]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. CreatureToLocation file not found.");
        }

        try {
            File itemToLocation = new File(loadFilePath + "/ItemToLocation.txt");
            myReader = new Scanner(itemToLocation);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    String[] frag = line.split(": ");


                    addItemToLocation(frag[0], frag[1]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. ItemToLocation file not found.");
        }

        try {
            File objectToLocation = new File(loadFilePath + "/ObjectToLocation.txt");
            myReader = new Scanner(objectToLocation);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    String[] frag = line.split(": ");


                    addObjectToLocation(frag[0], frag[1]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. ObjectToLocation file not found.");
        }




        try {
            File inventory = new File(loadFilePath + "/Inventory.txt");
            myReader = new Scanner(inventory);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    addToInventory(getItem(line));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Inventory file not found.");
        }





    }


    public void addGameFlags(String loadFilePath) {


        try {
            File flags = new File(loadFilePath + "/GameFlags.txt");
            myReader = new Scanner(flags);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.equals("")) {

                    String[] frag = line.split(": ");


                    gameFlags.put(frag[0], frag[1]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. GameFlags file not found.");
        }
    }



    // ---------- The save function! -----------------------

    public void saveToFile(String saveFilePath) throws IOException {

        FileWriter myWriter = null;

        try {
            myWriter = new FileWriter(saveFilePath + "Locations.txt");

            for (Location location : locationList) {
                myWriter.write(location.getName());
            }
            System.out.println("Successfully wrote the Locations file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "Creatures.txt");

            for (Creature creature : creatureList) {
                myWriter.write(creature.getName() + ": " + creature.getRace());
            }
            System.out.println("Successfully wrote the Creatures file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "Items.txt");

            for (Item item : itemList) {
                myWriter.write(item.getName());
            }
            System.out.println("Successfully wrote the Items file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "StationaryObjects.txt");

            for (StationaryObject stationary : stationaryObjectList) {
                myWriter.write(stationary.getName());
            }
            System.out.println("Successfully wrote the StationaryObjects file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "CreatureToLocation.txt");

            for (Creature creature : creatureList) {
                myWriter.write(creature.getName() + ": " + creature.getLocationName());
            }
            System.out.println("Successfully wrote the CreatureToLocation file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }


        try {
            myWriter = new FileWriter(saveFilePath + "ItemToLocation.txt");

            for (Item item : itemList) {
                myWriter.write(item.getName() + ": " + item.getLocationName());
            }
            System.out.println("Successfully wrote the ItemToLocation file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "ObjectToLocation.txt");

            for (StationaryObject stationary : stationaryObjectList) {
                myWriter.write(stationary.getName() + ": " + stationary.getLocationName());
            }
            System.out.println("Successfully wrote the ObjectToLocation file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "GameFlags.txt");

            for (String key : gameFlags.keySet()) {
                myWriter.write(key + ": " + gameFlags.get(key));
            }
            System.out.println("Successfully wrote the GameFlags file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        try {
            myWriter = new FileWriter(saveFilePath + "Inventory.txt");

            for (Item item : playerInventory) {
                myWriter.write(item.getName());
            }
            System.out.println("Successfully wrote the StationaryObjects file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        myWriter.close();

    }

}
