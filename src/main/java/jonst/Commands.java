package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jonst.HelpfulMethods.*;


public class Commands {


    public static void enter(String location, World world) {
        //Todo: have locations have an "enterLoc" and "exitLoc" to point to.
        //So player can type "enter castle" and go inside to a specific "inside location". Same with "exit castle".
    }

    public static void exit(String location, World world) {
        //Todo
    }

    public static void saveQuick(World world) {
        boolean success = world.quickSave();
    }




    public static void saveGame(World world) {
        boolean success = world.saveToFile();
    }


    public static void loadQuick(World world) {


                String savePath = SystemData.getQuickSave();

                System.out.println("Loading game...");

        System.out.println(savePath);

                world.updateWorld(savePath);
                lookAround(world);




    }

    public static void loadGame(World world) {

        String reply = SystemData.getReply("If you load a game now, you will lose your current progress. Do you want to continue? (Y/N) ").toLowerCase();

        switch (reply) {
            case "y":
                String savePath = App.getLoadData();

                if(savePath!="") {
                    System.out.println("Loading game...");

                    System.out.println(savePath);

                    world.updateWorld(savePath);
                    lookAround(world);
                }
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

        System.out.println("Commands are: " + turnStringListIntoString(world.getParser().legitimateCommands, "and"));
    }

    public static void listNouns(World world) {
        System.out.println("Nouns are: " + turnStringListIntoString(world.getParser().legitimateNouns, "and"));
    }

    public static void drop(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            Item item = world.getItem(fullName);

            if (world.getPlayer().hasItem(item)) {
                //drop

                world.transferItemToNewOwner(fullName, world.getPlayer().getName(), world.getPlayerLocation().getLocationName());


                //world.removeFromInventory(item);
                //world.addItemToGeneric(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

                System.out.println("You drop the " + name + ".");
            } else {
                System.out.println("You're not carrying that.");
            }

        }


    }

    public static void pickUp(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            GenericObject subject = world.getGenericObject(fullName);


            if (subject instanceof Creature)                                              //Subject is a creature.
            {
                Creature creature = (Creature) subject;
                System.out.println("You pick up " + creature.getName() + " with your magic and hold " + himOrHer(creature.getGender()) + " for a moment before putting " + himOrHer(creature.getGender()) + " down again.");

            } else if (subject instanceof StationaryObject)                               //Subject is a stationary object.
            {
                System.out.println("You'd rather not try lifting " + subject.getName() + ". It's heavy.");

            } else if (subject instanceof Location)                                       //Subject is a location.
            {
                System.out.println("As great and powerful as you are, lifting entire areas is beyond your ability.");

            } else if ((subject instanceof Item)) {

                if (((Item) subject).getOwner() instanceof Location) {      //You can only pick up items from the ground. Others need to be taken from containers.
                    world.transferItemToNewOwner(fullName, ((Item) subject).getOwner().getName(), world.getPlayer().getName());
                    System.out.println("You pick up the " + name + ".");

                } else if (((Item) subject).getOwner() instanceof Creature) {
                    System.out.println("You can't just take that from " + ((Item) subject).getOwner().getName() + ". Try asking nicely.");
                } if (((Item) subject).getOwner() instanceof StationaryObject || ((Item) subject).getOwner() instanceof Item) {
                    System.out.println("Currently, items in containers need to be TAKEn specifically from the container.");
                } else {
                    System.out.println("That doesn't work.");
                }

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
            System.out.println("You are carrying: " + turnListIntoString(items, "and") + ".");
        }
    }

    public static void lookAround(World world) {

        Location loc = world.getPlayerLocation();

        System.out.println(loc.getName());
        System.out.println();
        System.out.println(loc.getDescription());
        System.out.println();
        listCreatures(world);   //Lists all creatures on location.
        System.out.println();
        listStationaryObjects(world);   //Lists all objects on location.
        System.out.println();
        listOwnedItems(world, loc);       //Lists all items on location.

        //To do: List items and objects

    }


    public static void lookAt(String argument, World world) {         //Make sure you can't look at things that aren't present!

        if(argument == ""){     //If you just type "look", "look at" or "look around"
            lookAround(world);
        }
        else {
            String fullName = world.matchLocalName(argument);

            if (!fullName.equals("")) {

                GenericObject gen = world.getGenericObject(fullName);

                if (gen == world.getPlayerLocation()) {
                    lookAround(world);
                } else {
                    System.out.println(gen.getDescription());
                    System.out.println();
                    listOwnedItems(world, gen);
                }
            }
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
            lookAround(world);
        } else {
            System.out.println("You can't get there from here.");
        }
    }


    public static void getExits(World world) {

        Location loc = world.getLocation(world.getPlayer().getLocationName());
        List<String> exits = loc.getExits();

        System.out.println("Exits are: " + turnStringListIntoString(exits, "and") + ".");
    }


    public static void teleportOther(String[] command, World world) {                            //TO DO Make sure you can teleport items and objects - different code?

        String target = world.matchLocalName(command[1]);

        String destination = world.matchName(command[3]);


        if (!target.equals("") && !destination.equals("")) {


            if (world.getGenericObject(target) == world.getPlayer())           //Are you instructing the game to teleport Trixie herself?
            {
                Commands.teleportSelf(command, world);

            } else if (world.getGenericObject(target) instanceof Location) {
                System.out.println("You'd rather not teleport " + target + " anywhere. That will just end badly.");


            } else if (world.getGenericObject(target) instanceof Creature) {
                world.transferCreatureToLocation(target, world.getPlayerLocation().getName(), destination);
                System.out.println("The " + target + " vanishes in a burst of smoke!");

            } else if (world.getGenericObject(target) instanceof Item) {
                world.transferItemToNewOwner(target, world.getPlayerLocation().getName(), destination);
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
            lookAround(world);

        }
    }


    public static void ask(String[] command, World world) {

        //Todo: Not checked for functionality yet!

        String creature = command[1];
        String conjunction = command[2];
        String topic = command[3];

        String parsedTopic = world.getParser().parseTopic(topic.toLowerCase());
        String fullName = world.matchLocalName(creature);


        if (!fullName.equals("")) {

            GenericObject target = world.getGenericObject(fullName);

            if (!(target instanceof Creature)) {
                System.out.println("You don't make a habit of talking to inanimate objects.");
            } else {            //This runs if you successfully talk to someone.
                switch (conjunction) {
                    case "about":
                        System.out.println(((Creature) target).askAbout(parsedTopic));
                        break;

                    case "for":
                        System.out.println("You ask for a certain item. But this code isn't written yet...");
                        break;

                    default:
                        System.out.println("You may ask " + target.getName() + " 'about' or 'for' something. This is not correct.");
                        break;


                }


            }
        }

    }


    //--------------- Not for direct use -----------------------
    public static void listItems(World world) {
        List<Item> tempItemList = new ArrayList<>();
        tempItemList.addAll(world.getPlayerLocation().getItemList());      //Create a list of items at the location.

        if (tempItemList.size() > 0) {
            System.out.println("There" + isOrAre(tempItemList.size()) + turnListIntoString(tempItemList, "and") + " here.");
        }
    }

    public static void listOwnedItems(World world, GenericObject owner) {
        List<Item> itemList = owner.getItemList();

        if (owner instanceof Location) {
            if (itemList.size() > 0) {
                System.out.println("There is " + turnListIntoString(itemList, "and") + " here.");
            }
        } else if (owner instanceof Creature) {
            if (itemList.size() > 0) {
                System.out.println(capitalize(heOrShe(((Creature) owner).getGender())) + " carries " + turnListIntoString(itemList, "and") + ".");
            }
        } else if (owner instanceof StationaryObject || owner instanceof Item) {
            if (itemList.size() > 0) {
                System.out.println("It contains " + turnListIntoString(itemList, "and") + ".");
            }
        } else {
            System.out.println("This is not my beautiful code! This is not my beautiful string! How did I get here?");
        }

    }


    public static void listStationaryObjects(World world) {
        List<StationaryObject> tempStationaryObjectList = new ArrayList<>();
        tempStationaryObjectList.addAll(world.getPlayerLocation().getObjectsAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.

        if (tempStationaryObjectList.size() > 0) {
            System.out.println("There" + isOrAre(tempStationaryObjectList.size()) + turnListIntoString(tempStationaryObjectList, "and") + " here.");
        }
    }

    public static void listCreatures(World world) {
        List<Creature> tempCreatureList = new ArrayList<>();
        tempCreatureList.addAll(world.getPlayerLocation().getCreaturesAtLocation());     //Create a list of npcs at the location. Make sure to exclude Trixie.
        tempCreatureList.remove(world.getPlayer());

        if (tempCreatureList.size() == 0) { //If only Trixie is here.
            System.out.println("There's nopony else here.");
        } else {
            System.out.println(turnListIntoString(tempCreatureList, "and") + isOrAre(tempCreatureList.size()) + "here.");
        }
    }


}
