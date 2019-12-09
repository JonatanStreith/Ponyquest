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


    public static void enter(String location, World world){
        //Todo: have locations have an "enterLoc" and "exitLoc" to point to.
        //So player can type "enter castle" and go inside to a specific "inside location". Same with "exit castle".
    }

    public static void exit(String location, World world){
        //Todo
    }


    public static void saveGame(World world) throws IOException {


        //Todo: Definitely fix up the save system better before doing something with this!

        String choice = SystemData.getReply("Name your save: ");

        String savePath = SystemData.getSavepath() + choice;

        if (new File(SystemData.getSavepath() + choice).exists()) {
            System.out.println("A save file with that name already exists.");
        } else {
            //Directory.CreateDirectory(savePath);
            world.saveToFile(savePath);
            System.out.println("Game saved as \"" + choice + "\"");
        }


    }


    public static void loadGame(World world) {
        System.out.println("Sorry, loading is not implemented yet.");

        //Todo: Make loading function.
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
        System.out.println("You are the Great and Powerful Trixie, on a quest to... do something. You haven't decided yet.");
        System.out.println("To see available commands, type 'commands'. More help will be available when Trixie adds it.");
    }

    public static void ListCommands(World world) {

        System.out.println("Commands are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateCommands, "and"));
    }

    public static void listNouns(World world) {
        System.out.println("Nouns are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateNouns, "and"));
    }

    public static void drop(String name, World world) {

        String fullName = world.returnLocalFullName(name);

        if (world.isInInventory(world.getItem(fullName))) {
            //drop
            world.removeFromInventory(world.getItem(fullName));
            world.addItemToLocation(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

            System.out.println("You drop the " + world.getItem(fullName).getShortName() + ".");
        } else {
            System.out.println("You're not carrying that.");
        }
    }

    public static void pickUp(String name, World world) {

        String fullName = world.returnFullName(name);
        String fullNameLocal = world.returnLocalFullName(name);


        if (!(world.doesObjectExist(fullName)))                                                             //Subject doesn't exist.
        {
            System.out.println("You don't know what that is.");

        } else if (!(world.isObjectPresent(fullNameLocal)))                                                   //Subject isn't present.
        {
            System.out.println("You don't see " + world.getGenericObject(name).getName() + " here.");

        } else if (world.getGenericObject(fullNameLocal) instanceof Creature)                                              //Subject is a creature.
        {
            System.out.println("You pick up " + world.getGenericObject(fullNameLocal).getName() + " and hold them for a moment before putting them down again.");

        } else if (world.getGenericObject(fullNameLocal) instanceof StationaryObject)                                              //Subject is a stationary object.
        {
            System.out.println("You'd rather not try lifting " + world.getGenericObject(fullNameLocal).getName() + ". It's heavy.");

        } else if (world.getGenericObject(fullNameLocal) instanceof Location)                                              //Subject is a stationary object.
        {
            System.out.println("As great and powerful as you are, lifting entire areas is beyond your ability.");

        } else if ((world.getGenericObject(fullNameLocal) instanceof Item)) {
            world.removeItemFromLocation(fullNameLocal, world.getPlayerLocation().getLocationName());              //Remove from loc

            world.addToInventory(world.getItem(fullNameLocal));                              //Add to inventory
            System.out.println("You pick up the " + world.getItem(fullNameLocal).getShortName() + ".");

        } else {
            System.out.println("Debug code. If this is shown, something didn't go right.");
        }


    }

    public static void talkTo(String name, World world) {

        String fullName = world.returnFullName(name);
        String fullNameLocal = world.returnLocalFullName(name);

        if (!(world.doesObjectExist(fullName)))                                                             //Subject doesn't exist.
        {
            System.out.println("Who is that?");

        } else if (!(world.isObjectPresent(fullNameLocal)))                                                   //Subject isn't present.
        {
            System.out.println(world.getGenericObject(fullName).getName() + " isn't here right now.");



        } else if (!(world.getGenericObject(fullNameLocal) instanceof Creature))                                                //Subject isn't a creature.
        {
            System.out.println("You don't make a habit of talking to inanimate objects.");


        } else if ((world.getGenericObject(fullNameLocal) instanceof Creature)) {
            System.out.println(world.getCreature(fullNameLocal).getRandomCasualDialog());         //This runs if you successfully talk to someone.
        } else {
            System.out.println("Debug code. If this is shown, something didn't go right.");
        }

    }

    public static void showInventory(World world) {

        List<Item> items = world.getPlayerInventory();

        if (items.size() == 0) {
            System.out.println("You're not carrying anything.");
        } else {
            System.out.println("You are carrying: " + HelpfulMethods.turnItemListIntoString(items, "and") + ".");
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

        String fullName = world.returnFullName(argument);
        String fullNameLocal = world.returnLocalFullName(argument);

        if (!(world.doesObjectExist(fullName))) { //Looks at something that doesn't exist
            System.out.println("Look at what?");

        } else if (!(world.isObjectPresent(fullNameLocal))) {      //Looks at something that exists, but isn't present
            System.out.println("You can't see that from here.");

        } else {
            System.out.println(world.getGenericObject(fullNameLocal).getDescription());
        }
    }


    public static void goTo(String newArea, World world) {

        List<String> newAreasFullName = world.returnFullNames(newArea); //A list of all places matching the shortname provided
        boolean canGo = false;

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

        if (destination!="")               //Is a destination set?
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

        String target = command[1];
        String destination = command[3];

        String targetFullName = world.returnFullName(target);
        String targetFullNameLocal = world.returnLocalFullName(target);

        String destinationFullName = world.returnFullName(destination);

        if (!world.doesObjectExist(targetFullName))             //Subject doesn't exist
        {
            System.out.println("You can't teleport something that doesn't exist.");
        } else if(!world.doesObjectExist(destinationFullName)){
            System.out.println("You can't teleport things to made-up places.");


        } else if (!world.isObjectPresent(targetFullNameLocal))        //Subject isn't in the area
        {
            System.out.println("You can only teleport things within sight.");

        } else if (world.getGenericObject(targetFullNameLocal) == world.getPlayer())           //Are you instructing the game to teleport Trixie herself?
        {
            Commands.teleportSelf(command, world);

        } else if (world.getGenericObject(targetFullNameLocal) instanceof Location) {
            System.out.println("You'd rather not teleport " + target + " anywhere. That will just end badly.");


        } else if (world.getGenericObject(targetFullNameLocal) instanceof Creature) {
            world.transferCreatureToLocation(targetFullNameLocal, world.getPlayerLocation().getName(), destinationFullName);
            System.out.println("The " + target + " vanishes in a burst of smoke!");

        } else if (world.getGenericObject(targetFullNameLocal) instanceof Item) {
            world.transferItemToLocation(targetFullNameLocal, world.getPlayerLocation().getName(), destinationFullName);
            System.out.println(targetFullNameLocal + " vanishes in a burst of smoke!");

        } else if (world.getGenericObject(targetFullNameLocal) instanceof StationaryObject) {
            world.transferObjectToLocation(targetFullNameLocal, world.getPlayerLocation().getName(), destinationFullName);
            System.out.println("The " + target + " vanishes in a burst of smoke!");


        } else {
            System.out.println("Your spell fizzles for some reason.");
        }

    }


    public static void teleportSelf(String[] command, World world) { //Make sure you can teleport items and objects - different code?

        String destinationFullName = world.returnFullName(command[1]);


        world.transferCreatureToLocation(world.getPlayer().getName(), world.getPlayerLocation().getName(), destinationFullName);


        System.out.println("You vanish in a burst of smoke, and reappear at " + destinationFullName + ".");
        SystemData.getReply("[press enter to continue]");
        System.out.flush();
        LookAround(world);


    }


    public static void ask(String[] command, World world) {

        //Todo: Not checked for functionality yet!

        String creature = command[1];
        String topic = command[3];

        String fullName = world.returnFullName(creature);
        String fullNameLocal = world.returnLocalFullName(creature);

        if (!(world.doesObjectExist(fullName)))                                                             //Subject doesn't exist.
        {
            System.out.println("Who is that?");

        } else if (!(world.isObjectPresent(fullNameLocal)))                                                   //Subject isn't present.
        {
            System.out.println(world.getGenericObject(fullName).getName() + " isn't here right now.");



        } else if (!(world.getGenericObject(fullNameLocal) instanceof Creature))                                                //Subject isn't a creature.
        {
            System.out.println("You don't make a habit of talking to inanimate objects.");


        } else if ((world.getGenericObject(fullNameLocal) instanceof Creature)) {
            System.out.println(world.getCreature(fullNameLocal).askAbout(topic));         //This runs if you successfully talk to someone.
        } else {
            System.out.println("Debug code. If this is shown, something didn't go right.");
        }



    }







    //--------------- Not for direct use -----------------------
    public static void listItems(World world) {
        List<Item> tempItemList = new ArrayList<>();
        tempItemList.addAll(world.getPlayerLocation().getItemsAtLocation());      //Create a list of items at the location.

        if (tempItemList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempItemList.size()) + HelpfulMethods.turnItemListIntoString(tempItemList, "and") + " here.");
        }
    }

    public static void listStationaryObjects(World world) {
        List<StationaryObject> tempStationaryObjectList = new ArrayList<>();
        tempStationaryObjectList.addAll(world.getPlayerLocation().getObjectsAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.

        if (tempStationaryObjectList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempStationaryObjectList.size()) + HelpfulMethods.turnStationaryObjectListIntoString(tempStationaryObjectList, "and") + " here.");
        }
    }

    public static void listCreatures(World world) {
        List<Creature> tempCreatureList = new ArrayList<>();
        tempCreatureList.addAll(world.getPlayerLocation().getCreaturesAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.
        tempCreatureList.remove(world.getPlayer());

        if (tempCreatureList.size() == 0) { //If only Trixie is here.
            System.out.println("There's nopony else here.");
        } else {
            System.out.println(HelpfulMethods.turnCreatureListIntoString(tempCreatureList, "and") + HelpfulMethods.isOrAre(tempCreatureList.size()) + "here.");
        }
    }

}
