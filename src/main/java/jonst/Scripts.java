package jonst;

import jonst.Data.Lambda;
import jonst.Models.Exit;
import jonst.Models.Objects.*;

import java.util.ArrayList;
import java.util.List;

public class Scripts {

    public static void setMood(GenericObject subject, String[] script, World world) {

        if (subject instanceof Creature) {
            ((Creature) subject).setMood(script[1]);
        }
    }

    public static void deleteThisItem(GenericObject subject, World world) {
        if (subject instanceof Item) {
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getHolder());
            world.removeFromList(subject);
        }
    }

    public static void destroyItem(GenericObject subject, World world) {
        if (subject instanceof Item) {
            System.out.println("The " + subject.getName() + " is destroyed.");
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getHolder());
            world.removeFromList(subject);
        }
    }

    public static void fleeToRandomLocation(GenericObject subject, World world) {

        if (subject instanceof Creature) {
            Location currentLocation = subject.getLocation();

            List<Exit> viableExits = Lambda.subList(world.getExitList(), e -> e.containsLocation(currentLocation));

            Exit chosenExit = viableExits.get((int) Math.floor(Math.random() * viableExits.size()));

            Location destinationLocation = chosenExit.getConnectingLocation(currentLocation);

            world.moveToLocation(subject, currentLocation, destinationLocation);

            System.out.println("The " + subject + " flees towards " + destinationLocation + "!");
        }
    }

    public static void fleeTo(GenericObject subject, String[] scriptCommandArray, World world) {

        Location destinationLocation = world.getLocationByID(scriptCommandArray[1]);
        Location currentLocation = subject.getLocation();

        world.moveToLocation(subject, currentLocation, destinationLocation);


    }


    public static void addAttribute(GenericObject subject, String[] scriptCommandArray, World world) {
        subject.addAttribute(scriptCommandArray[1]);
    }

    public static void removeAttribute(GenericObject subject, String[] scriptCommandArray, World world) {
        subject.removeAttribute(scriptCommandArray[1]);
    }


    public static void writeLine(GenericObject subject, String[] scriptCommandArray, World world) {
        System.out.println(scriptCommandArray[1]);
    }


    public static void send(GenericObject subject, String[] scriptCommandArray, World world) {
        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        Location destination = world.getLocationByID(scriptCommandArray[2]);

        if (actor != null && destination != null) {
            world.moveToLocation(actor, actor.getLocation(), destination);

            Commands.lookAround(world);
        }
    }

    public static void changeRace(GenericObject subject, String[] scriptCommandArray, World world) {

        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        String newRace = scriptCommandArray[2];

        actor.setRace(newRace);

        System.out.println(actor.getName() + " turns into a " + newRace + "!");

    }

    public static void addNewItem(GenericObject subject, String[] scriptCommandArray, World world) {
        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        String newItemName = scriptCommandArray[2];

        Item newItem = JsonBuilder.generateTemplateItem(newItemName);

        world.addItemToHolder(newItem, actor);
        world.addNewToList(newItem);

    }

    public static void deleteCarriedItem(GenericObject subject, String[] scriptCommandArray, World world) {

        Creature actor;
        Item itemToBeDeleted;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        if (scriptCommandArray[2].equalsIgnoreCase("this")) {
            itemToBeDeleted = (Item) subject;
        } else {
            itemToBeDeleted = actor.getOwnedItemByName(scriptCommandArray[2]);
        }


        actor.removeItem(itemToBeDeleted);
        world.removeFromList(itemToBeDeleted);

    }

    public static void showDescription(GenericObject subject, String[] scriptCommandArray, World world) {
        String desc = subject.getSpecficDescription(scriptCommandArray[1]);
        System.out.println(desc);
    }

    public static void resetRace(String[] scriptCommandArray, World world) {
        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        actor.setRace(actor.getDefaultRace());

    }

    public static void spawnCreature(GenericObject subject, String[] scriptCommandArray, World world) {
        Location location;
        if (scriptCommandArray[1].equalsIgnoreCase("playerlocation")) {
            location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }


        Creature newCreature = JsonBuilder.generateTemplateCreature(scriptCommandArray[2]);


        world.addToLocation(newCreature, location);
        world.addNewToList(newCreature);
    }

    public static void spawnObject(GenericObject subject, String[] scriptCommandArray, World world) {

        Location location;
        if (scriptCommandArray[1].equalsIgnoreCase("playerlocation")) {
            location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }


        StationaryObject newObject = JsonBuilder.generateTemplateObject(scriptCommandArray[2]);

        world.addToLocation(newObject, location);
        world.addNewToList(newObject);

    }

    public static void changeInitDialog(GenericObject subject, String[] scriptCommandArray, World world) {
        if (subject instanceof Creature) {
            ((Creature) subject).setInitialDialog(scriptCommandArray[1]);
        }
    }

    public static void removeExit(String location1, String location2, World world) {

        List<Exit> allExits = world.getExitList();

        Location loc1 = world.getLocationByID(location1);
        Location loc2 = world.getLocationByID(location2);

        if (loc1 != null && loc2 != null) {

            Exit potentialExit = Lambda.getFirst(allExits, e -> e.connectionExists(loc1, loc2));

            if (potentialExit != null) {
                world.removeExit(potentialExit);
            }
        }
    }

    public static void addExit(String location1, String location2, World world) {

        List<Exit> allExits = world.getExitList();

        Location loc1 = world.getLocationByID(location1);
        Location loc2 = world.getLocationByID(location2);

        if (loc1 != null && loc2 != null) {

            Exit potentialExit = Lambda.getFirst(allExits, e -> e.connectionExists(loc1, loc2));

            if (potentialExit == null) {

                Exit newExit = new Exit(new Location[]{loc1, loc2}, true);

                world.addExit(newExit);
            }
        }
    }
}
