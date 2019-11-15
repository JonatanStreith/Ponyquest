package jonst.Models;

import jonst.Data.LocationData;

import java.io.Console;
import java.util.List;

public class Location extends GenericObject {


    private List<String> legitimateExits;


    //IMPORTANT! Locations should store a list/array/Enumerator of legitimate exits: direction, and which location it leads to. FIGURE OUT!



    private List<Creature> creaturesAtLocation = new List<Creature>();
    private List<StationaryObject> objectsAtLocation = new List<StationaryObject>();
    private List<Item> itemsAtLocation = new List<Item>();

    public Location(String inputName)
    {

        name = inputName;

        if (LocationData.LocationShortNames.ContainsKey(name))
        { shortName = LocationData.LocationShortNames[name]; }
        else
        { shortName = name; }




        if (LocationData.locationDescriptions.ContainsKey(name))
        { description = LocationData.locationDescriptions[name]; }
        else
        {
            Console.WriteLine($"{name} lacks description");
            description = "[description missing]";
        }



        if (LocationData.legitimateExits.ContainsKey(name))
        { legitimateExits = LocationData.legitimateExits[name]; }
        else
        {
            Console.WriteLine($"{name} has no established exits");
            legitimateExits = new List<String> { "Sugarcube Corner" };
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
    { creaturesAtLocation.Add(name); }

    public void RemoveCreature(Creature name)
    { creaturesAtLocation.Remove(name); }

    public void AddObject(StationaryObject name)
    { objectsAtLocation.Add(name); }

    public void RemoveObject(StationaryObject name)
    { objectsAtLocation.Remove(name); }

    public void AddItem(Item name)
    { itemsAtLocation.Add(name); }

    public void RemoveItem(Item name)
    { itemsAtLocation.Remove(name); }

    public List<String> GetExits()
    { return legitimateExits; }


}
