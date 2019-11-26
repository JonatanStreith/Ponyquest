package jonst.Models;

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

    public Location(String inputName)
    {

        name = inputName;

        if (LocationData.hasLocationShortName(name))
        { shortName = LocationData.getLocationShortName(name); }
        else
        { shortName = name; }




        if (LocationData.hasLocationDescription(name))
        { description = LocationData.getLocationDescription(name); }
        else
        {
            System.out.println(name + " lacks description");
            description = "[description missing]";
        }



        if (LocationData.hasLegitimateExits(name))
        { legitimateExits = LocationData.getLegitimateExits(name); }
        else
        {
            System.out.println(name + " has no established exits");
            legitimateExits.add("no exits");
        }



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
