package jonst;

import jonst.Data.SystemData;
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

    public World(String loadFilePath) {

        BuildGenericObjectLists(loadFilePath);                   //Create and add all objects to the main lists

        AddGenericObjectsToLocations(loadFilePath);              //Add all objects to their specified locations

        AddGameFlags(loadFilePath);                              //Add all game flags, to track conditions and stuff

        CreateProperNounList();                      //Create and sort lists for the parser
        SortCommandAndConjunctionLists();

        myReader.close();

    }  //End of World constructor


    public void AddCreatureToLocation(String creature, String location) {
        //Adds "creature" to "location"
        GetLocation(location).addCreature(GetCreature(creature));
        GetCreature(creature).setLocation(location);
    }

    public void RemoveCreatureFromLocation(String creature, String location) {
        GetLocation(location).removeCreature(GetCreature(creature));
    }


    public void AddItemToLocation(String item, String location) {
        //Adds "creature" to "location"
        GetLocation(location).addItem(GetItem(item));
        GetItem(item).setLocation(location);
    }

    public void RemoveItemFromLocation(String item, String location) {
        GetLocation(location).removeItem(GetItem(item));
    }


    public void AddObjectToLocation(String stationaryObject, String location) {
        //Adds "creature" to "location"
        GetLocation(location).addObject(GetStationaryObject(stationaryObject));
        GetStationaryObject(stationaryObject).setLocation(location);
    }

    public void RemoveObjectFromLocation(String stationaryObject, String location) {
        GetLocation(location).removeObject(GetStationaryObject(stationaryObject));
    }


    public void AddToInventory(Item item) {
        playerInventory.add(item);
        item.setLocation("inventory");

    }

    public void RemoveFromInventory(Item item) {
        playerInventory.remove(item);
    }

    public boolean IsInInventory(Item item) {
        return playerInventory.contains(item);
    }

    public ArrayList<Item> GetInventory() {
        return playerInventory;
    }


    public void CreateProperNounList() {
        for (GenericObject gen : genericList) {
            legitimateNouns.add(gen.getName());
            legitimateNouns.add(gen.getShortName());
        }

        Collections.sort(legitimateCommands);   //Sorts list; this avoids confusion (why do I do this?)

    }


    public void SortCommandAndConjunctionLists() {
        Collections.sort(legitimateCommands);
        Collections.sort(legitimateConjunctions);
    }


    public Creature GetPlayer() {
        //return creatureList.Find(x = > x.GetName().ToLower().Contains("trixie"));

        for (Creature creature : creatureList) {
            if (creature.getName().toLowerCase().equals("Trixie"))
                return creature;
        }
        return null;
    }

    public Location GetPlayerLocation() {
        return GetLocation(GetPlayer().getLocationName());
    }


    public Location GetLocation(String wantedLocation) {
        //return locationList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Location location : locationList) {
            if (location.getName().toLowerCase().contains(wantedLocation))
                return location;
        }
        return null;
    }


    public Creature GetCreature(String wantedCreature) {
        //return creatureList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Creature creature : creatureList) {
            if (creature.getName().toLowerCase().contains(wantedCreature))
                return creature;
        }
        return null;

    }

    public Item GetItem(String wantedItem) {
        //return itemList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(wantedItem))
                return item;
        }
        return null;
    }

    public StationaryObject GetStationaryObject(String wantedStationaryObject) {
        //return stationaryObjectList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (StationaryObject stationaryObject : stationaryObjectList) {
            if (stationaryObject.getName().toLowerCase().contains(wantedStationaryObject))
                return stationaryObject;
        }
        return null;
    }

    public GenericObject GetGenericObject(String wantedGenericObject) {
        //return genericList.Find(x = > x.GetName().ToLower().Contains(input.ToLower()));

        for (GenericObject genericObject : genericList) {
            if (genericObject.getName().toLowerCase().contains(wantedGenericObject))
                return genericObject;
        }
        return null;
    }


    public boolean IsObjectPresent(String generic) {
        return ((GetPlayer().getLocationName() == GetGenericObject(generic).getLocationName()) || (GetGenericObject(generic).getLocationName() == "inventory"));
    }

    public boolean DoesObjectExist(String generic) {
        return genericList.contains(GetGenericObject(generic));
    }

    public String ReturnFullName(String name) {
        String fullName = name;

        for (GenericObject generic : genericList) { //Check if something exists that has "name" as its short name, then return its full name
            if (generic.getShortName().equals(name)) {

                return generic.getName();
            }
        }

        return name;        //if not, just return the short name
    }


    public void BuildGenericObjectLists(String loadFilePath) {



        try {
            File locations = new File(loadFilePath + "/Locations.txt");
            myReader = new Scanner(locations);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {
                    locationList.add(new Location(line));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Locations file not found.");
        }

        try {
            File creatures = new File(loadFilePath + "/Creatures.txt");
            myReader = new Scanner(creatures);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {

                    String[] frag = line.split(": ");

                    creatureList.add(new Creature(frag[0], frag[1]));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Creatures file not found.");
        }


        try {
            File items = new File(loadFilePath + "/Items.txt");
            myReader = new Scanner(items);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {
                    itemList.add(new Item(line));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Items file not found.");
        }

        try {
            File stationary = new File(loadFilePath + "/StationaryObjects.txt");
            myReader = new Scanner(stationary);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {
                    stationaryObjectList.add(new StationaryObject(line));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. StationaryObjects file not found.");
        }


        genericList.addAll(creatureList);
        genericList.addAll(itemList);
        genericList.addAll(stationaryObjectList);
        genericList.addAll(locationList);
    }






    public void AddGenericObjectsToLocations(String loadFilePath) {

        try {
            File creatureToLocation = new File(loadFilePath + "/CreatureToLocation.txt");
            myReader = new Scanner(creatureToLocation);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {

                    String[] frag = line.split(": ");


                    AddCreatureToLocation(frag[0], frag[1]);
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
                if (line != "") {

                    String[] frag = line.split(": ");


                    AddItemToLocation(frag[0], frag[1]);
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
                if (line != "") {

                    String[] frag = line.split(": ");


                    AddObjectToLocation(frag[0], frag[1]);
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
                if (line != "") {

                    AddToInventory(GetItem(line));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Inventory file not found.");
        }





    }


    public void AddGameFlags(String loadFilePath) {


        try {
            File flags = new File(loadFilePath + "/GameFlags.txt");
            myReader = new Scanner(flags);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line != "") {

                    String[] frag = line.split(": ");


                    gameFlags.put(frag[0], frag[1]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. GameFlags file not found.");
        }
    }




    public void SaveToFile(String saveFilePath) throws IOException {

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
