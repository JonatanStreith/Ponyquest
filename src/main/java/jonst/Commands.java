package jonst;

import jonst.Data.DialogData;
import jonst.Models.Creature;
import jonst.Models.Item;
import jonst.Models.Location;

import java.io.Console;
import java.util.List;

public class Commands {


    public static void SaveGame(World world)
    {
        Console.Write("Name your save: ");
        string choice = Console.ReadLine();

        string savePath = $@"..\..\..\..\Textbased game\Textbased game\Saves\{choice}";

        if (Directory.Exists(savePath))
        { Console.WriteLine("A save file with that name already exists."); }
        else
        {
            Directory.CreateDirectory(savePath);
            world.SaveToFile(savePath);
            Console.WriteLine($"Game saved as \"{choice}\"");

        }





    }


    public static void LoadGame(World world)
    {

    }







    public static void Quit()
    {
        Console.WriteLine("Are you sure you want to quit? Y/N");
        if (Console.ReadKey(true).KeyChar.ToString().ToLower() == "y")
        {
            Console.WriteLine("Okay, bye!");
            Console.ReadLine();
            Environment.Exit(0);
        }
        else
            Console.WriteLine("Okay, let's continue.");
    }


    public static void Help()
    {
        Console.WriteLine("You are the Great and Powerful Trixie, on a quest to... do something. You haven't decided yet.");
        Console.WriteLine("To see available commands, type 'commands'. More help will be available when Trixie adds it.");
    }

    public static void ListCommands(World world)
    {

        //List<String> stringList = new List<string>(DataStorage.legitimateCommands);

        //Console.WriteLine($"Commands are: {HelpfulMethods.TurnStringListIntoString(stringList)}");

        Console.WriteLine($"Commands are: {HelpfulMethods.TurnStringListIntoString(world.legitimateCommands)}.");


    }



    public static void ListNouns(World world)
    {
        Console.WriteLine($"Nouns are: {HelpfulMethods.TurnStringListIntoString(world.legitimateNouns)}.");

    }


    public static void PickUp(string name, World world)
    {

        if (!(world.DoesObjectExist(name)))                                                             //Subject doesn't exist.
        { Console.WriteLine($"You don't know what that is."); }

        else if (!(world.IsObjectPresent(name)))                                                   //Subject isn't present.
        { Console.WriteLine($"You don't see {world.GetGenericObject(name).GetName()} here."); }

        else if (world.GetGenericObject(name) is Creature)                                              //Subject is a creature.
        { Console.WriteLine($"You pick up {world.GetGenericObject(name).GetName()} and hold them for a moment before putting them down again."); }

            else if (world.GetGenericObject(name) is StationaryObject)                                              //Subject is a stationary object.
        { Console.WriteLine($"You'd rather not try lifting {world.GetGenericObject(name).GetName()}. It's heavy."); }

            else if (world.GetGenericObject(name) is Location)                                              //Subject is a stationary object.
        { Console.WriteLine($"You're really not strong enough to lift that."); }


            else if ((world.GetGenericObject(name) is Item))
        {
            world.RemoveItemFromLocation(name, world.GetPlayerLocation().GetLocationName());              //Remove from loc

            world.AddToInventory(world.GetItem(name));                              //Add to inventory
            Console.WriteLine($"You pick up the {world.GetItem(name).GetShortName()}.");

        }
            else
        { Console.WriteLine("Debug code. If this is shown, something didn't go right."); }









    }

    public static void Drop(string name, World world)
    {
        if (world.IsInInventory(world.GetItem(name)))
        {
            //drop
            world.RemoveFromInventory(world.GetItem(name));
            world.AddItemToLocation(name, world.GetPlayerLocation().GetLocationName());              //Remove from loc

            Console.WriteLine($"You drop the {world.GetItem(name).GetShortName()}.");
        }
        else
        {
            Console.WriteLine("You're not carrying that.");
        }
    }


    public static void ShowInventory(World world)
    {
        List<Item> items = world.GetInventory();

        if (items.Count() == 0)
        { Console.WriteLine("You're not carrying anything."); }
        else
        { Console.WriteLine($"You are carrying: {HelpfulMethods.TurnItemListIntoString(items)}."); }
    }

    public static void LookAround(World world)
    {
        Console.WriteLine(world.GetLocation(world.GetPlayer().GetLocationName()).GetName());
        Console.WriteLine();
        Console.WriteLine(world.GetLocation(world.GetPlayer().GetLocationName()).GetDescription());


        Console.WriteLine();
        ListCreatures(world);
        Console.WriteLine();
        ListItems(world);

        //To do: List items and objects

    }







    public static void LookAt(string argument, World world)          //Make sure you can't look at things that aren't present!
    {
        if (argument == "")
            Console.WriteLine("Look at what?");
        else if (world.GetPlayer().GetLocationName().Equals(argument, StringComparison.InvariantCultureIgnoreCase))      //Looks at place
        { Console.WriteLine(world.GetLocation(world.GetPlayer().GetLocationName()).GetDescription()); }

        else if (!(world.IsObjectPresent(argument)))                                                   //Looks at something that isn't there)
        { Console.WriteLine($"You can't see {world.GetGenericObject(argument).GetName()} here."); }

        else if (world.IsObjectPresent(argument))       //Subject is present.
        { Console.WriteLine(world.GetGenericObject(argument).GetDescription()); }
        else
        { Console.WriteLine("Look at what?"); }
    }







    public static void GoTo(string newArea, World world)
    {
        bool canGo = false;
        foreach (string place in world.GetLocation(world.GetPlayer().GetLocationName()).GetExits())     //Check if any of the legitimate exits is the place we want to go to
        {

            if (newArea == place)
            { canGo = true; }
        }

        if (canGo)               //Is newArea on the list of legitimate exits?
        {
            world.RemoveCreatureFromLocation("Trixie", world.GetPlayer().GetLocationName());            //Remove player from current location
            world.AddCreatureToLocation("Trixie", newArea);                                             //Add player to new location
            //world.GetPlayer().SetLocation(newArea);                                                     //Change player's location variable; already included in prev command
            Console.WriteLine($"You go to {world.GetLocation(newArea).GetName()}.");
            Console.ReadLine();
            Console.Clear();
            LookAround(world);
        }
        else { Console.WriteLine("You can't get there from here."); }
    }







    public static void TalkTo(string name, World world)
    {
        if (name== "")
        { Console.WriteLine("Talk to who?"); }

        else if (!(world.DoesObjectExist(name)))                                                             //Subject doesn't exist.
        { Console.WriteLine($"You don't know of anypony by that name."); }

        else if (!(world.IsObjectPresent(name)))                                                   //Subject isn't present.
        { Console.WriteLine($"{world.GetGenericObject(name).GetName()} isn't here right now."); }

        else if (!(world.GetGenericObject(name) is Creature))                                                //Subject isn't a creature.
        { Console.WriteLine($"You don't make a habit of talking to inanimate objects."); }

            else if ((world.GetGenericObject(name) is Creature))
        {
            if (!DialogData.casualDialog.ContainsKey(name))             //If no dialog entry exists for this character.
            { Console.WriteLine($"{name} doesn't have anything to say."); }
            else
            {
                string[] dialog = DialogData.casualDialog[name];                   //This runs if you successfully talk to someone.
                Console.WriteLine(dialog[world.diceRoll.Next(dialog.Length)]);
            }
        }
            else
        { Console.WriteLine("Debug code. If this is shown, something didn't go right."); }

    }



    public static void GetExits(World world)
    {
        Location loc = world.GetLocation(world.GetPlayer().GetLocationName());
        List<string> exits = loc.GetExits();


        Console.Write($"Exits are: {HelpfulMethods.TurnStringListIntoString(exits)}.");
    }


    public static void TeleportOther(string[] command, World world)                             //TO DO Make sure you can teleport items and objects - different code?
    {

        if (!world.DoesObjectExist(command[1]))             //Subject doesn't exist
        { Console.WriteLine("You can't teleport something that doesn't exist."); }

        else if (!world.IsObjectPresent(command[1]))        //Subject isn't in the area
        { Console.WriteLine("You can only teleport things within eyesight."); }

        else if (world.GetGenericObject(command[1]).GetName().Equals("Trixie", StringComparison.InvariantCultureIgnoreCase))           //Are you instructing the game to teleport Trixie herself?
        {
            world.RemoveCreatureFromLocation(world.GetPlayer().GetName(), world.GetPlayer().GetLocationName());
            world.AddCreatureToLocation(world.GetPlayer().GetName(), command[3]);

            Console.WriteLine($"You vanish in a burst of smoke, and reappear at {world.GetLocation(command[3]).GetName()}");
            Console.ReadLine();
            Console.Clear();
            LookAround(world);
        }
        else if (world.GetGenericObject(command[1]) is Creature)
        {
            world.RemoveCreatureFromLocation(world.GetCreature(command[1]).GetName(), world.GetPlayer().GetLocationName());
            world.AddCreatureToLocation(command[1], command[3]);

            Console.WriteLine($"{command[1]} vanishes in a burst of smoke!");
        }
            else
        { Console.WriteLine("Your spell fizzles for some reason."); }

    }


    public static void TeleportSelf(string[] command, World world)  //Make sure you can teleport items and objects - different code?
    {
        world.RemoveCreatureFromLocation(world.GetPlayer().GetName(), world.GetPlayer().GetLocationName());
        world.AddCreatureToLocation(world.GetPlayer().GetName(), command[1]);




        Console.WriteLine($"You vanish in a burst of smoke, and reappear at {world.GetLocation(command[1]).GetName()}");
        Console.ReadLine();
        Console.Clear();
        LookAround(world);


    }




    public static void Ask(string[] command, World world)
    {
        if (!world.DoesObjectExist(command[1]))         //Nonexistent
        { Console.WriteLine("Ask who?"); }
        else if (!world.IsObjectPresent(command[1]))        //Not present
        { Console.WriteLine("They might hear you better if they're actually present, you know."); }

        else if (!(world.GetGenericObject(command[1]) is Creature))                                                //Subject isn't a creature.
        { Console.WriteLine("There's not much point in asking inanimate objects."); }

            else if (!(DialogData.askTopic.ContainsKey(command[3])))
    {
        Console.WriteLine($"{world.GetCreature(command[1]).GetName()} doesn't know anything about that.");
    }

    else
    { Console.WriteLine(DialogData.askArray[DialogData.askCreature[command[1]], DialogData.askTopic[command[3]]]); }
    }


    public static void ListItems(World world)
    {
        List<Item> itemList = world.GetLocation(world.GetPlayer().GetLocationName()).GetItemsAtLocation();      //Create a list of npcs at the location. Make sure to exclude Trixie.

        int numItems = itemList.Count;

        if (itemList.Count() > 0) { Console.WriteLine($"There {HelpfulMethods.IsOrAre(numItems)} {HelpfulMethods.TurnItemListIntoString(itemList)} here."); }

    }

    public static void ListCreatures(World world)
    {

        List<Creature> creatureList = world.GetLocation(world.GetPlayer().GetLocationName()).GetCreaturesAtLocation();      //Create a list of npcs at the location. Make sure to exclude Trixie.

        int numCreatures = creatureList.Count;

        if (creatureList.Count() == 1) { Console.WriteLine("There's nopony else here."); }

        else
        { Console.WriteLine($"{HelpfulMethods.TurnCreatureListIntoString(creatureList)} {HelpfulMethods.IsOrAre(numCreatures - 1)} here."); }

    }

}
