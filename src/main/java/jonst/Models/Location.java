package jonst.Models;

import jonst.Data.CreatureData;
import jonst.Data.LocationData;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {


    private ArrayList<String> legitimateExits;


    //IMPORTANT! Locations should store a list/array/Enumerator of legitimate exits: direction, and which location it leads to. FIGURE OUT!



    private ArrayList<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private ArrayList<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();
    private ArrayList<Item> itemsAtLocation = new ArrayList<Item>();

    public Location(String name, String shortName, String description, ArrayList<String> legitimateExits)
    {
        super(name, shortName, description);

        this.legitimateExits = legitimateExits;

        locationName = name;

    }


    public ArrayList<Creature> getCreaturesAtLocation()
    { return creaturesAtLocation; }
    public ArrayList<StationaryObject> getObjectsAtLocation()
    { return objectsAtLocation; }
    public ArrayList<Item> getItemsAtLocation()
    { return itemsAtLocation; }



    public void addCreature(Creature name)
    { creaturesAtLocation.add(name); }

    public void removeCreature(Creature name)
    { creaturesAtLocation.remove(name); }

    public void addObject(StationaryObject name)
    { objectsAtLocation.add(name); }

    public void removeObject(StationaryObject name)
    { objectsAtLocation.remove(name); }

    public void addItem(Item name)
    { itemsAtLocation.add(name); }

    public void removeItem(Item name)
    { itemsAtLocation.remove(name); }

    public ArrayList<String> getExits()
    { return legitimateExits; }


}
