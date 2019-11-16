package jonst.Models;

import jonst.Data.LocationData;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Location extends GenericObject {


    private List<String> legitimateExits;


    //IMPORTANT! Locations should store a list/array/Enumerator of legitimate exits: direction, and which location it leads to. FIGURE OUT!



    private List<Creature> creaturesAtLocation = new ArrayList<Creature>();
    private List<StationaryObject> objectsAtLocation = new ArrayList<StationaryObject>();
    private List<Item> itemsAtLocation = new ArrayList<Item>();

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


    public List<Creature> GetCreaturesAtLocation()
    { return creaturesAtLocation; }
    public List<StationaryObject> GetObjectsAtLocation()
    { return objectsAtLocation; }
    public List<Item> GetItemsAtLocation()
    { return itemsAtLocation; }



    public void AddCreature(Creature name)
    { creaturesAtLocation.add(name); }

    public void RemoveCreature(Creature name)
    { creaturesAtLocation.remove(name); }

    public void AddObject(StationaryObject name)
    { objectsAtLocation.add(name); }

    public void RemoveObject(StationaryObject name)
    { objectsAtLocation.remove(name); }

    public void AddItem(Item name)
    { itemsAtLocation.add(name); }

    public void RemoveItem(Item name)
    { itemsAtLocation.remove(name); }

    public List<String> GetExits()
    { return legitimateExits; }


}
