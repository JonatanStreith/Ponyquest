package jonst;

import jonst.Models.*;

import java.util.List;
import java.util.Random;

public class World {

    public Dictionary<String, String> GameFlags = new Dictionary<String, String>();     //Use this to store event flags and the like!




    public List<Location> locationList = new List<Location>();      //Main lists that store all game objects
    public List<Creature> creatureList = new List<Creature>();
    public List<StationaryObject> stationaryObjectList = new List<StationaryObject>();
    public List<Item> itemList = new List<Item>();

    public List<GenericObject> genericList = new List<GenericObject>();


    public List<Item> playerInventory = new List<Item>();



    public List<String> legitimateNouns = new List<String>();

    public List<String> legitimateCommands = new List<String> { "save", "load", "nouns", "look at", "look around", "look", "go to", "go", "pick up", "talk to", "quit", "drop", "brandish", "ask", "cast", "exits", "teleport to", "teleport", "help", "commands", "inventory" };
    public List<String> legitimateConjunctions = new List<String> { "to", "about", "behind", "at", "under", "in front of", "on", "in" };


    public Random diceRoll = new Random();

    public World(String loadFilePath)
    {



        BuildGenericObjectLists(loadFilePath);                   //Create and add all objects to the main lists

        AddGenericObjectsToLocations(loadFilePath);              //Add all objects to their specified locations

        AddGameFlags(loadFilePath);                              //Add all game flags, to track conditions and stuff

        CreateProperNounList();                      //Create and sort lists for the parser
        SortCommandAndConjunctionLists();


    }  //End of World constructor


























    public void AddCreatureToLocation(String creature, String location)
    {
        //Adds "creature" to "location"
        GetLocation(location).AddCreature(GetCreature(creature));
        GetCreature(creature).SetLocation(location);
    }

    public void RemoveCreatureFromLocation(String creature, String location)
    { GetLocation(location).RemoveCreature(GetCreature(creature)); }



    public void AddItemToLocation(String item, String location)
    {
        //Adds "creature" to "location"
        GetLocation(location).AddItem(GetItem(item));
        GetItem(item).SetLocation(location);
    }

    public void RemoveItemFromLocation(String item, String location)
    { GetLocation(location).RemoveItem(GetItem(item)); }


    public void AddObjectToLocation(String stationaryObject, String location)
    {
        //Adds "creature" to "location"
        GetLocation(location).AddObject(GetStationaryObject(stationaryObject));
        GetStationaryObject(stationaryObject).SetLocation(location);
    }

    public void RemoveObjectFromLocation(String stationaryObject, String location)
    { GetLocation(location).RemoveObject(GetStationaryObject(stationaryObject)); }


    public void AddToInventory(Item item)
    {
        playerInventory.Add(item);
        item.SetLocation("inventory");

    }

    public void RemoveFromInventory(Item item)
    { playerInventory.Remove(item); }

    public bool IsInInventory(Item item)
    { return playerInventory.Contains(item); }

    public List<Item> GetInventory()
    { return playerInventory; }


    public void CreateProperNounList()
    {
        foreach (GenericObject i in genericList)
        {
            legitimateNouns.Add(i.GetName());
            legitimateNouns.Add(i.GetShortName());
        }

        legitimateNouns.Sort((a, b) => b.CompareTo(a));     //Sorts list in reverse alphabetical order; this avoids confusion

    }


    public void SortCommandAndConjunctionLists()
    {
        legitimateCommands.Sort((a, b) => b.CompareTo(a));
        legitimateConjunctions.Sort((a, b) => b.CompareTo(a));
    }





    public Creature GetPlayer()
    { return creatureList.Find(x => x.GetName().ToLower().Contains("trixie")); }

    public Location GetPlayerLocation()
    {
        return GetLocation(GetPlayer().GetLocationName());
    }




    public Location GetLocation(String input)
    { return locationList.Find(x => x.GetName().ToLower().Contains(input.ToLower())); }


    public Creature GetCreature(String input)
    { return creatureList.Find(x => x.GetName().ToLower().Contains(input.ToLower())); }

    public Item GetItem(String input)
    { return itemList.Find(x => x.GetName().ToLower().Contains(input.ToLower())); }

    public StationaryObject GetStationaryObject(String input)
    { return stationaryObjectList.Find(x => x.GetName().ToLower().Contains(input.ToLower())); }

    public GenericObject GetGenericObject(String input)
    { return genericList.Find(x => x.GetName().ToLower().Contains(input.ToLower())); }




    public bool IsObjectPresent(String generic)
    { return ((GetPlayer().GetLocationName() == GetGenericObject(generic).GetLocationName()) || (GetGenericObject(generic).GetLocationName() == "inventory")); }

    public bool DoesObjectExist(String generic)
    { return genericList.Contains(GetGenericObject(generic)); }

    public String ReturnFullName(String name)
    {
        String fullName = name;

        if (genericList.Exists(x => x.GetShortName().Equals(name, StringComparison.InvariantCultureIgnoreCase)))        //If there exists a generic object whose short name is (name)...
        { fullName = genericList.Find(x => x.GetShortName().ToLower().Contains(name.ToLower())).GetName(); }
            else
        { fullName = genericList.Find(x => x.GetName().ToLower().Contains(name.ToLower())).GetName(); }

        return fullName;
    }






    public void BuildGenericObjectLists(String loadFilePath)
    {

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\Locations.txt"))
        {
            String s = "";

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    locationList.Add(new Location(s));
                }
            }
        }

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\Creatures.txt"))
        {
            String s = "";
            String[] s_sep;

            String[] sep = new String[] { ": " };

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    s_sep = s.Split(sep, StringSplitOptions.None);


                    creatureList.Add(new Creature(s_sep[0], s_sep[1]));
                }
            }
        }

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\Items.txt"))
        {
            String s = "";

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {

                    itemList.Add(new Item(s));
                }
            }
        }

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\StationaryObjects.txt"))
        {
            String s = "";

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {

                    stationaryObjectList.Add(new StationaryObject(s));
                }
            }
        }


        genericList.AddRange(creatureList);
        genericList.AddRange(itemList);
        genericList.AddRange(stationaryObjectList);
        genericList.AddRange(locationList);
    }


    public void AddGenericObjectsToLocations(String loadFilePath)
    {

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\CreatureToLocation.txt"))
        {
            String s = "";
            String[] s_sep;

            String[] sep = new String[] { ": " };

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    s_sep = s.Split(sep, StringSplitOptions.None);

                    AddCreatureToLocation(s_sep[0], s_sep[1]);
                }
            }
        }



        using (StreamReader sr = File.OpenText($@"{loadFilePath}\ItemToLocation.txt"))
        {
            String s = "";
            String[] s_sep;

            String[] sep = new String[] { ": " };

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    s_sep = s.Split(sep, StringSplitOptions.None);

                    AddItemToLocation(s_sep[0], s_sep[1]);
                }
            }
        }



        using (StreamReader sr = File.OpenText($@"{loadFilePath}\ObjectToLocation.txt"))
        {
            String s = "";
            String[] s_sep;

            String[] sep = new String[] { ": " };

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    s_sep = s.Split(sep, StringSplitOptions.None);

                    AddObjectToLocation(s_sep[0], s_sep[1]);
                }
            }
        }



        using (StreamReader sr = File.OpenText($@"{loadFilePath}\Inventory.txt"))
        {
            String s = "";


            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {

                    AddToInventory(GetItem(s));
                }
            }
        }





    }










    public void AddGameFlags(String loadFilePath)
    {

        using (StreamReader sr = File.OpenText($@"{loadFilePath}\GameFlags.txt"))
        {
            String s = "";
            String[] s_sep;

            String[] sep = new String[] { ": " };

            while ((s = sr.ReadLine()) != null)
            {
                if (s != "")
                {
                    s_sep = s.Split(sep, StringSplitOptions.None);

                    GameFlags.Add(s_sep[0], s_sep[1]);
                }
            }
        }



    }










    public void SaveToFile(String saveFilePath)
    {
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\Locations.txt"))
        {
            foreach (Location item in locationList)
            { sr.WriteLine(item.GetName()); }
        }
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\Creatures.txt"))
        {
            foreach (Creature item in creatureList)
            { sr.WriteLine($"{item.GetName()}: {item.GetRace()}"); }
        }
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\Items.txt"))
        {
            foreach (Item item in itemList)
            { sr.WriteLine(item.GetName()); }
        }
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\StationaryObjects.txt"))
        {
            foreach (StationaryObject item in stationaryObjectList)
            { sr.WriteLine(item.GetName()); }
        }


        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\CreatureToLocation.txt"))
        {
            foreach (Creature item in creatureList)
            { sr.WriteLine($"{item.GetName()}: {item.GetLocationName()}"); }
        }
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\ItemToLocation.txt"))
        {
            foreach (Item item in itemList)
            { sr.WriteLine($"{item.GetName()}: {item.GetLocationName()}"); }
        }
        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\ObjectToLocation.txt"))
        {
            foreach (StationaryObject item in stationaryObjectList)
            { sr.WriteLine($"{item.GetName()}: {item.GetLocationName()}"); }
        }


        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\GameFlags.txt"))
        {
            foreach (KeyValuePair<String, String> kvp in GameFlags)
            { sr.WriteLine($"{kvp.Key}: {kvp.Value}"); }
        }

        using (StreamWriter sr = File.CreateText($@"{saveFilePath}\Inventory.txt"))
        {
            foreach (Item item in playerInventory)
            { sr.WriteLine(item.GetName()); }
        }


    }

}
