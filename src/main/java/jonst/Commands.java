package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Commands {


    public static void enter(String location, World world) {
        //Todo: have locations have an "enterLoc" and "exitLoc" to point to.
        //So player can type "enter castle" and go inside to a specific "inside location". Same with "exit castle".
    }

    public static void exit(String location, World world) {
        //Todo
    }


    public static void saveGame(World world) {


        boolean success = world.saveToFile();

        if (success)
            System.out.println("Game saved successfully.");
        else
            System.out.println("Game failed to save correctly.");

    }


    public static void loadGame(World world) {

        String reply = SystemData.getReply("If you load a game now, you will lose your current progress. Do you want to continue? (Y/N) ").toLowerCase();

        switch (reply) {
            case "y":
                String savePath = App.getLoadData();

                System.out.println("Loading game...");

                world.updateWorld(savePath);
                LookAround(world);

                break;

            default:
                System.out.println("Your current game will continue.");
        }
    }


    public static void quit() {
        String choice = SystemData.getReply("Are you sure you want to quit? Y/N ");
        if (choice.equalsIgnoreCase("y")) {
            SystemData.getReply("Okay, bye!\n[press return to continue]");
            System.exit(0);
        } else
            System.out.println("Okay, let's continue.");
    }


    public static void help() {
        System.out.println("You are the Great and Powerful Trixie, on a quest to... do something. You haven't decided yet.\n" +
                "To see available commands, nouns and conjunctions, type 'commands', 'nouns' or 'conjunctions'.\n" +
                "To see available exits, type 'exits'. More help will be available when Trixie adds it.");
    }

    public static void ListCommands(World world) {

        System.out.println("Commands are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateCommands, "and"));
    }

    public static void listNouns(World world) {
        System.out.println("Nouns are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateNouns, "and"));
    }

    public static void drop(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {


            if (world.isInInventory(world.getItem(fullName))) {
                //drop
                world.removeFromInventory(world.getItem(fullName));
                world.addItemToLocation(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

                System.out.println("You drop the " + name + ".");
            } else {
                System.out.println("You're not carrying that.");
            }

        }


    }

    public static void pickUp(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            if (world.getGenericObject(fullName) instanceof Creature)                                              //Subject is a creature.
            {
                System.out.println("You pick up " + world.getGenericObject(fullName).getName() + " and hold them for a moment before putting them down again.");

            } else if (world.getGenericObject(fullName) instanceof StationaryObject)                                              //Subject is a stationary object.
            {
                System.out.println("You'd rather not try lifting " + world.getGenericObject(fullName).getName() + ". It's heavy.");

            } else if (world.getGenericObject(fullName) instanceof Location)                                              //Subject is a stationary object.
            {
                System.out.println("As great and powerful as you are, lifting entire areas is beyond your ability.");

            } else if ((world.getGenericObject(fullName) instanceof Item)) {
                world.removeItemFromLocation(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

                world.addToInventory(world.getItem(fullName));                              //Add to inventory
                System.out.println("You pick up the " + name + ".");

            } else {
                System.out.println("Debug code. If this is shown, something didn't go right.");
            }

        }
    }

    public static void talkTo(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {


            if (!(world.getGenericObject(fullName) instanceof Creature))                                                //Subject isn't a creature.
            {
                System.out.println("You don't make a habit of talking to inanimate objects.");


            } else if ((world.getGenericObject(fullName) instanceof Creature)) {
                System.out.println(world.getCreature(fullName).getRandomCasualDialog());         //This runs if you successfully talk to someone.
            } else {
                System.out.println("Debug code. If this is shown, something didn't go right.");
            }
        }
    }

    public static void showInventory(World world) {

        List<Item> items = world.getPlayerInventory();

        if (items.size() == 0) {
            System.out.println("You're not carrying anything.");
        } else {
            System.out.println("You are carrying: " + HelpfulMethods.turnListIntoString(items, "and") + ".");
        }
    }

    public static void LookAround(World world) {

        System.out.println(world.getPlayerLocation().getName());
        System.out.println();
        System.out.println(world.getPlayerLocation().getDescription());


        System.out.println();
        listCreatures(world);   //Lists all creatures on location.
        System.out.println();
        listItems(world);       //Lists all items on location.

        //To do: List items and objects

    }


    public static void lookAt(String argument, World world) {         //Make sure you can't look at things that aren't present!

        String fullName = world.matchLocalName(argument);

        if (!fullName.equals("")) {


            System.out.println(world.getGenericObject(fullName).getDescription());

        }
    }

    public static void goTo(String newArea, World world) {

        List<String> newAreasFullName = world.matchNameMultiple(newArea); //A list of all places matching the alias provided

        String destination = "";

        outerLoop:
        for (String exit : world.getPlayerLocation().getExits())     //Check if any of the legitimate exits is the place we want to go to
        {
            for (String area : newAreasFullName) {

                if (area.equalsIgnoreCase(exit)) {
                    destination = area;

                    break outerLoop;
                }
            }

        }

        if (destination != "")               //Is a destination found?
        {
            world.transferCreatureToLocation("Trixie", world.getPlayerLocation().getName(), destination);                                            //Add player to new location
            //world.GetPlayer().SetLocation(newArea);                                                     //Change player's location variable; already included in prev command
            System.out.println("You go to " + world.getLocation(destination).getName() + ".");
            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            LookAround(world);
        } else {
            System.out.println("You can't get there from here.");
        }
    }


    public static void getExits(World world) {

        Location loc = world.getLocation(world.getPlayer().getLocationName());
        List<String> exits = loc.getExits();

        System.out.println("Exits are: " + HelpfulMethods.turnStringListIntoString(exits, "and") + ".");
    }


    public static void teleportOther(String[] command, World world) {                            //TO DO Make sure you can teleport items and objects - different code?

         String target = world.matchLocalName(command[1]);

        String destination = world.matchName(command[3]);


        if(!target.equals("") && !destination.equals("")) {


            if (world.getGenericObject(target) == world.getPlayer())           //Are you instructing the game to teleport Trixie herself?
            {
                Commands.teleportSelf(command, world);

            } else if (world.getGenericObject(target) instanceof Location) {
                System.out.println("You'd rather not teleport " + target + " anywhere. That will just end badly.");


            } else if (world.getGenericObject(target) instanceof Creature) {
                world.transferCreatureToLocation(target, world.getPlayerLocation().getName(), destination);
                System.out.println("The " + target + " vanishes in a burst of smoke!");

            } else if (world.getGenericObject(target) instanceof Item) {
                world.transferItemToLocation(target, world.getPlayerLocation().getName(), destination);
                System.out.println(target + " vanishes in a burst of smoke!");

            } else if (world.getGenericObject(target) instanceof StationaryObject) {
                world.transferObjectToLocation(target, world.getPlayerLocation().getName(), destination);
                System.out.println("The " + target + " vanishes in a burst of smoke!");


            } else {
                System.out.println("Your spell fizzles for some reason.");
            }

        }
    }


    public static void teleportSelf(String[] command, World world) { //Make sure you can teleport items and objects - different code?


        String destinationFullName = world.matchName(command[1]);

        if (!destinationFullName.equals("")) {


            world.transferCreatureToLocation(world.getPlayer().getName(), world.getPlayerLocation().getName(), destinationFullName);


            System.out.println("You vanish in a burst of smoke, and reappear at " + destinationFullName + ".");
            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            LookAround(world);

        }
    }


    public static void ask(String[] command, World world) {

        //Todo: Not checked for functionality yet!

        String creature = command[1];
        String topic = command[3];
        String parsedTopic = world.getParser().parseTopic(topic.toLowerCase());


        String fullName = world.matchLocalName(creature);

        if (!fullName.equals("")) {

            if (!(world.getGenericObject(fullName) instanceof Creature))                                                //Subject isn't a creature.
            {
                System.out.println("You don't make a habit of talking to inanimate objects.");


            } else if ((world.getGenericObject(fullName) instanceof Creature)) {
                System.out.println(world.getCreature(fullName).askAbout(parsedTopic));         //This runs if you successfully talk to someone.
            } else {
                System.out.println("Debug code. If this is shown, something didn't go right.");
            }
        }

    }


    //--------------- Not for direct use -----------------------
    public static void listItems(World world) {
        List<Item> tempItemList = new ArrayList<>();
        tempItemList.addAll(world.getPlayerLocation().getItemsAtLocation());      //Create a list of items at the location.

        if (tempItemList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempItemList.size()) + HelpfulMethods.turnListIntoString(tempItemList, "and") + " here.");
        }
    }

    public static void listStationaryObjects(World world) {
        List<StationaryObject> tempStationaryObjectList = new ArrayList<>();
        tempStationaryObjectList.addAll(world.getPlayerLocation().getObjectsAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.

        if (tempStationaryObjectList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempStationaryObjectList.size()) + HelpfulMethods.turnListIntoString(tempStationaryObjectList, "and") + " here.");
        }
    }

    public static void listCreatures(World world) {
        List<Creature> tempCreatureList = new ArrayList<>();
        tempCreatureList.addAll(world.getPlayerLocation().getCreaturesAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.
        tempCreatureList.remove(world.getPlayer());

        if (tempCreatureList.size() == 0) { //If only Trixie is here.
            System.out.println("There's nopony else here.");
        } else {
            System.out.println(HelpfulMethods.turnListIntoString(tempCreatureList, "and") + HelpfulMethods.isOrAre(tempCreatureList.size()) + "here.");
        }
    }


}
