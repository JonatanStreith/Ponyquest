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

        System.out.println("Commands are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateCommands));
    }

    public static void listNouns(World world) {
        System.out.println("Nouns are: " + HelpfulMethods.turnStringListIntoString(world.getParser().legitimateNouns));
    }


    public static void pickUp(String name, World world) {

        //Todo: Not checked for functionality yet!

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

        String fullName = world.returnLocalFullName(name);

        //Todo: Not checked for functionality yet!

        if (world.isInInventory(world.getItem(fullName))) {
            //drop
            world.removeFromInventory(world.getItem(fullName));
            world.addItemToLocation(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

            System.out.println("You drop the " + world.getItem(name).getShortName() + ".");
        } else {
            System.out.println("You're not carrying that.");
        }
    }


    public static void showInventory(World world) {

        List<Item> items = world.getPlayerInventory();

        if (items.size() == 0) {
            System.out.println("You're not carrying anything.");
        } else {
            System.out.println("You are carrying: " + HelpfulMethods.turnItemListIntoString(items) + ".");
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


    public static void talkTo(String name, World world) {

        //Todo: Not checked for functionality yet!

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

        //Todo: Not checked for functionality yet!

        Location loc = world.getLocation(world.getPlayer().getLocationName());
        List<String> exits = loc.getExits();


        System.out.println("Exits are: " + HelpfulMethods.turnStringListIntoString(exits) + ".");
    }


    public static void teleportOther(String[] command, World world) {                            //TO DO Make sure you can teleport items and objects - different code?

        //Todo: Not checked for functionality yet!

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
            SystemData.getReply("[press enter to continue]");
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


    public static void teleportSelf(String[] command, World world) { //Make sure you can teleport items and objects - different code?

        //Todo: Not checked for functionality yet!

        world.transferCreatureToLocation(world.getPlayer().getName(), world.getPlayerLocation().getName(), command[1]);


        System.out.println("You vanish in a burst of smoke, and reappear at " + world.getLocation(command[1]).getName() + ".");
        SystemData.getReply("[press enter to continue]");
        System.out.flush();
        LookAround(world);


    }


    public static void ask(String[] command, World world) {

        //Todo: Not checked for functionality yet!

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
        List<Item> tempItemList = new ArrayList<>();
        tempItemList.addAll(world.getPlayerLocation().getItemsAtLocation());      //Create a list of items at the location.

        if (tempItemList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempItemList.size()) + HelpfulMethods.turnItemListIntoString(tempItemList) + " here.");
        }
    }

    public static void listStationaryObjects(World world) {
        List<StationaryObject> tempStationaryObjectList = new ArrayList<>();
        tempStationaryObjectList.addAll(world.getPlayerLocation().getObjectsAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.

        if (tempStationaryObjectList.size() > 0) {
            System.out.println("There" + HelpfulMethods.isOrAre(tempStationaryObjectList.size()) + HelpfulMethods.turnStationaryObjectListIntoString(tempStationaryObjectList) + " here.");
        }
    }

    public static void listCreatures(World world) {
        List<Creature> tempCreatureList = new ArrayList<>();
        tempCreatureList.addAll(world.getPlayerLocation().getCreaturesAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.
        tempCreatureList.remove(world.getPlayer());

        if (tempCreatureList.size() == 0) { //If only Trixie is here.
            System.out.println("There's nopony else here.");
        } else {
            System.out.println(HelpfulMethods.turnCreatureListIntoString(tempCreatureList) + HelpfulMethods.isOrAre(tempCreatureList.size()) + "here.");
        }
    }

}
