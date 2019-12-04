package jonst;

import jonst.Data.DialogData;
import jonst.Data.SystemData;
import jonst.Models.*;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Commands {


    public static void saveGame(World world) throws IOException {


        System.out.println("Name your save: ");
        String choice = SystemData.inputReader.nextLine();

        String savePath = SystemData.savepath + choice;

        if (new File(SystemData.savepath + choice).exists()) {
            System.out.println("A save file with that name already exists.");
        } else {
            //Directory.CreateDirectory(savePath);
            world.saveToFile(savePath);
            System.out.println("Game saved as \"" + choice + "\"");

        }


    }


    public static void loadGame(World world) {
        System.out.println("Sorry, loading is not implemented yet.");


    }


    public static void quit() {
        System.out.println("Are you sure you want to quit? Y/N");
        if (SystemData.inputReader.nextLine().toLowerCase().equals("y")) {
            System.out.println("Okay, bye!");
            SystemData.inputReader.nextLine();

            SystemData.inputReader.close();
            System.exit(0);
        } else
            System.out.println("Okay, let's continue.");
    }


    public static void help() {
        System.out.println("You are the Great and Powerful Trixie, on a quest to... do something. You haven't decided yet.");
        System.out.println("To see available commands, type 'commands'. More help will be available when Trixie adds it.");
    }

    public static void ListCommands(World world) {

        //List<String> StringList = new List<String>(DataStorage.legitimateCommands);

        //Console.WriteLine($"Commands are: {HelpfulMethods.TurnStringListIntoString(StringList)}");

        System.out.println("Commands are: " + HelpfulMethods.turnStringListIntoString(world.legitimateCommands));


    }


    public static void listNouns(World world) {
        System.out.println("Nouns are: " + HelpfulMethods.turnStringListIntoString(world.legitimateNouns));

    }


    public static void pickUp(String name, World world) {

        if (!(world.doesObjectExist(name)))                                                             //Subject doesn't exist.
        {
            System.out.println("You don't know what that is.");
        } else if (!(world.isObjectPresent(name)))                                                   //Subject isn't present.
        {
            System.out.println("You don't see " + world.getGenericObject(name).getName() + " here.");
        } else if (world.getGenericObject(name) instanceof Creature)                                              //Subject is a creature.
        {
            System.out.println("You pick up " + world.getGenericObject(name).getName() + " and hold them for a moment before putting them down again.");
        } else if (world.getGenericObject(name) instanceof StationaryObject)                                              //Subject is a stationary object.
        {
            System.out.println("You'd rather not try lifting " + world.getGenericObject(name).getName() + ". It's heavy.");
        } else if (world.getGenericObject(name) instanceof Location)                                              //Subject is a stationary object.
        {
            System.out.println("You're really not strong enough to lift that.");
        } else if ((world.getGenericObject(name) instanceof Item)) {
            world.removeItemFromLocation(name, world.getPlayerLocation().getLocationName());              //Remove from loc

            world.addToInventory(world.getItem(name));                              //Add to inventory
            System.out.println("You pick up the " + world.getItem(name).getShortName() + ".");

        } else {
            System.out.println("Debug code. If this is shown, something didn't go right.");
        }


    }

    public static void drop(String name, World world) {
        if (world.isInInventory(world.getItem(name))) {
            //drop
            world.removeFromInventory(world.getItem(name));
            world.addItemToLocation(name, world.getPlayerLocation().getLocationName());              //Remove from loc

            System.out.println("You drop the " + world.getItem(name).getShortName() + ".");
        } else {
            System.out.println("You're not carrying that.");
        }
    }


    public static void showInventory(World world) {
        List<Item> items = world.getInventory();

        if (items.size() == 0) {
            System.out.println("You're not carrying anything.");
        } else {
            System.out.println("You are carrying: " + HelpfulMethods.TurnItemListIntoString(items) + ".");
        }
    }

    public static void LookAround(World world) {
        System.out.println(world.getLocation(world.getPlayer().getLocationName()).getName());
        System.out.println();
        System.out.println(world.getLocation(world.getPlayer().getLocationName()).getDescription());


        System.out.println();
        listCreatures(world);   //Lists all creatures on location.
        System.out.println();
        listItems(world);       //Lists all items on location.

        //To do: List items and objects

    }


    public static void lookAt(String argument, World world)          //Make sure you can't look at things that aren't present!
    {
        if (argument.equals(""))
            System.out.println("Look at what?");
        else if (world.getPlayer().getLocationName().toLowerCase().equals(argument.toLowerCase()))      //Looks at place
        {
            System.out.println(world.getLocation(world.getPlayer().getLocationName()).getDescription());
        } else if (!(world.isObjectPresent(argument)))                                                   //Looks at something that isn't there)
        {
            System.out.println("You can't see " + world.getGenericObject(argument).getName() + " here.");
        } else if (world.isObjectPresent(argument))       //Subject is present.
        {
            System.out.println(world.getGenericObject(argument).getDescription());
        } else {
            System.out.println("Look at what?");
        }
    }


    public static void goTo(String newArea, World world) {
        boolean canGo = false;
        for (String place : world.getLocation(world.getPlayer().getLocationName()).getExits())     //Check if any of the legitimate exits is the place we want to go to
        {

            if (newArea.equals(place)) {
                canGo = true;
            }
        }

        if (canGo)               //Is newArea on the list of legitimate exits?
        {
            world.removeCreatureFromLocation("Trixie", world.getPlayer().getLocationName());            //Remove player from current location
            world.addCreatureToLocation("Trixie", newArea);                                             //Add player to new location
            //world.GetPlayer().SetLocation(newArea);                                                     //Change player's location variable; already included in prev command
            System.out.println("You go to " + world.getLocation(newArea).getName() + ".");
            SystemData.inputReader.nextLine();
            System.out.flush();
            LookAround(world);
        } else {
            System.out.println("You can't get there from here.");
        }
    }


    public static void talkTo(String name, World world) {
        if (name.equals("")) {
            System.out.println("Talk to who?");
        } else if (!(world.doesObjectExist(name)))                                                             //Subject doesn't exist.
        {
            System.out.println("You don't know of anypony by that name.");
        } else if (!(world.isObjectPresent(name)))                                                   //Subject isn't present.
        {
            System.out.println(world.getGenericObject(name).getName() + " isn't here right now.");
        } else if (!(world.getGenericObject(name) instanceof Creature))                                                //Subject isn't a creature.
        {
            System.out.println("You don't make a habit of talking to inanimate objects.");
        } else if ((world.getGenericObject(name) instanceof Creature)) {
            System.out.println(world.getCreature(name).getRandomCasualDialog());         //This runs if you successfully talk to someone.
        } else {
            System.out.println("Debug code. If this is shown, something didn't go right.");
        }

    }


    public static void getExits(World world) {
        Location loc = world.getLocation(world.getPlayer().getLocationName());
        List<String> exits = loc.getExits();


        System.out.println("Exits are: " + HelpfulMethods.turnStringListIntoString(exits) + ".");
    }


    public static void teleportOther(String[] command, World world)                             //TO DO Make sure you can teleport items and objects - different code?
    {

        if (!world.doesObjectExist(command[1]))             //Subject doesn't exist
        {
            System.out.println("You can't teleport something that doesn't exist.");
        } else if (!world.isObjectPresent(command[1]))        //Subject isn't in the area
        {
            System.out.println("You can only teleport things within eyesight.");
        } else if (world.getGenericObject(command[1]).getName().toLowerCase().equals("Trixie"))           //Are you instructing the game to teleport Trixie herself?
        {
            world.removeCreatureFromLocation(world.getPlayer().getName(), world.getPlayer().getLocationName());
            world.addCreatureToLocation(world.getPlayer().getName(), command[3]);

            System.out.println("You vanish in a burst of smoke, and reappear at " + world.getLocation(command[3]).getName() + ".");
            SystemData.inputReader.nextLine();
            System.out.flush();
            LookAround(world);
        } else if (world.getGenericObject(command[1]) instanceof Creature) {
            world.removeCreatureFromLocation(world.getCreature(command[1]).getName(), world.getPlayer().getLocationName());
            world.addCreatureToLocation(command[1], command[3]);

            System.out.println(command[1] + " vanishes in a burst of smoke!");
        } else {
            System.out.println("Your spell fizzles for some reason.");
        }

    }


    public static void teleportSelf(String[] command, World world)  //Make sure you can teleport items and objects - different code?
    {
        world.removeCreatureFromLocation(world.getPlayer().getName(), world.getPlayer().getLocationName());
        world.addCreatureToLocation(world.getPlayer().getName(), command[1]);


        System.out.println("You vanish in a burst of smoke, and reappear at " + world.getLocation(command[1]).getName() + ".");
        SystemData.inputReader.nextLine();
        System.out.flush();
        LookAround(world);


    }


    public static void ask(String[] command, World world) {

        System.out.println("This command is still buggy.");


//        if (!world.doesObjectExist(command[1]))         //Nonexistent
//        {
//            System.out.println("Ask who?");
//        } else if (!world.isObjectPresent(command[1]))        //Not present
//        {
//            System.out.println("They might hear you better if they're actually present, you know.");
//        } else if (!(world.getGenericObject(command[1]) instanceof Creature))                                                //Subject isn't a creature.
//        {
//            System.out.println("There's not much point in asking inanimate objects.");
//        } else if (!(DialogData.askTopic.ContainsKey(command[3]))) {
//            System.out.println(world.getCreature(command[1]).getName() + " doesn't know anything about that.");
//        } else {
//            System.out.println(DialogData.askArray[DialogData.askCreature[command[1]], DialogData.askTopic[command[3]]]);
//        }
    }


    public static void listItems(World world) {
        List<Item> itemList = world.getLocation(world.getPlayer().getLocationName()).getItemsAtLocation();      //Create a list of npcs at the location. Make sure to exclude Trixie.

        int numItems = itemList.size();

        if (itemList.size() > 0) {
            System.out.println("There " + HelpfulMethods.isOrAre(numItems) + HelpfulMethods.TurnItemListIntoString(itemList) + " here.");
        }

    }

    public static void listCreatures(World world) {

        List<Creature> creatureList = world.getLocation(world.getPlayer().getLocationName()).getCreaturesAtLocation();      //Create a list of npcs at the location. Make sure to exclude Trixie.

        int numCreatures = creatureList.size();

        if (creatureList.size() == 1) {
            System.out.println("There's nopony else here.");
        } else {
            System.out.println(HelpfulMethods.turnCreatureListIntoString(creatureList) + HelpfulMethods.isOrAre(numCreatures - 1) + " here.");

        }

    }

}
