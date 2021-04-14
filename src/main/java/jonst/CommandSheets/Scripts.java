package jonst.CommandSheets;

import jonst.Data.Lambda;
import jonst.Data.HelpfulMethods;
import jonst.Models.Exit;
import jonst.Models.Objects.*;
import jonst.Models.World;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Scripts {

    public static void setMood(GenericObject subject, String[] script, World world) {

        if (subject instanceof Creature) {
            ((Creature) subject).setMood(script[1]);
        }
    }

    public static boolean deleteThisItem(GenericObject subject, World world) {
        if (subject instanceof Item) {
            subject.dropContents();
            world.removeItemFromHolder((Item) subject, ((Item) subject).getHolder());
            world.removeFromList(subject);
            return true;
        }
        return false;
    }

    public static boolean destroyItem(GenericObject subject, World world) {
        if (subject instanceof Item) {
            if (!subject.hasAttribute("indestructible")) {
                System.out.println(format("The %s is destroyed.", subject.getName()));
                deleteThisItem(subject, world);
                return true;
            }
            System.out.println(format("The %s is indestructible.", subject.getName()));
            return false;
        }
        System.out.println("Illegal target of script.");
        return false;
    }

    public static void fleeToRandomLocation(GenericObject subject, World world) {

        if (subject instanceof Creature) {
            Location currentLocation = subject.getLocation();

            List<Exit> viableExits = world.getExitList().stream().filter(e -> e.containsLocation(currentLocation)).collect(Collectors.toList());

            Exit chosenExit = viableExits.get((int) Math.floor(Math.random() * viableExits.size()));

            Location destinationLocation = chosenExit.getConnectingLocation(currentLocation);

            world.moveToLocation(subject, currentLocation, destinationLocation);

            System.out.println("The " + subject + " flees towards " + destinationLocation + "!");
        }
    }

    public static void fleeTo(GenericObject subject, String location, World world) {

        Location destinationLocation = world.getLocationByID(location);
        Location currentLocation = subject.getLocation();

        world.moveToLocation(subject, currentLocation, destinationLocation);
    }

    public static void dropContents(GenericObject subject) {
        subject.dropContents();
    }





    public static boolean addAttribute(GenericObject subject, String attribute) {
        return subject.addAttribute(attribute);
    }

    public static boolean removeAttribute(GenericObject subject, String attribute) {
        return subject.removeAttribute(attribute);
    }


    public static void writeLine(String line) {
        System.out.println(line);
    }


    public static void send(String[] scriptCommandArray, World world) {
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

    public static void changeRace(String[] scriptCommandArray, World world) {

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

    public static void addNewItem(String[] scriptCommandArray, World world) {
        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        GenericObject template = world.getTemplate(scriptCommandArray[2]);

        if(template != null && template instanceof Item) {

            Item newItem = new Item((Item) template);

            world.addItemToHolder(newItem, actor);
            world.addNewToList(newItem);
        }

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

    public static void returnToDefaultLocation(String[] scriptCommandArray, World world) {
        Creature actor;

        if (scriptCommandArray[1].equalsIgnoreCase("player")) {
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        if(actor.getDefaultLocationId() != null) {
            world.moveToLocation(actor, actor.getLocation(), world.getLocationByID(actor.getDefaultLocationId()));
        }
    }

    public static void spawnCreature(GenericObject subject, String[] scriptCommandArray, World world) {
        Location location;
        if (scriptCommandArray[1].equalsIgnoreCase("playerlocation")) {
            location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }

        GenericObject template = world.getTemplate(scriptCommandArray[2]);

        if(template != null && template instanceof Creature) {
            Creature newCreature = new Creature((Creature) template);
            world.addToLocation(newCreature, location);
            world.addNewToList(newCreature);
        }
    }

    public static void spawnObject(GenericObject subject, String[] scriptCommandArray, World world) {

        Location location;
        if (scriptCommandArray[1].equalsIgnoreCase("playerlocation")) {
            location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }

        GenericObject template = world.getTemplateById(scriptCommandArray[2]);

        if(template != null && template instanceof StationaryObject) {
            StationaryObject newObject = new StationaryObject((StationaryObject) template);
            world.addToLocation(newObject, location);
            world.addNewToList(newObject);

            newObject.runResponseScript("create");
        }

    }

    public static void moveFollowers(GenericObject subject, String[] scriptCommandArray, World world){
        //moveFollowers:Location_Acres
        //Move all followers at the location of the thing activating the script, to the location specified by ID
        Location start = subject.getLocation();
        Location destination = world.getLocationByID(scriptCommandArray[1]);

        List<Creature> followers = Lambda.subList(start.getCreaturesAtLocation(), c -> c.hasAttribute("following"));

        Lambda.processList(followers, f -> {world.moveToLocation(f, start, destination); System.out.println(f.getName() + " follows you.");});


    }

    public static void changeInitDialog(GenericObject subject, String[] scriptCommandArray, World world) {
        if (subject instanceof Creature) {
            ((Creature) subject).setInitialDialog(scriptCommandArray[1]);
        }
    }

//    public static void removeExit(String location1, String location2, World world) {
//
//        List<Exit> allExits = world.getExitList();
//
//        Location loc1 = world.getLocationByID(location1);
//        Location loc2 = world.getLocationByID(location2);
//
//        if (loc1 != null && loc2 != null) {
//
//            Exit potentialExit = Lambda.getFirst(allExits, e -> e.connectionExists(loc1, loc2));
//
//            if (potentialExit != null) {
//                world.removeExit(potentialExit);
//            }
//        }
//    }
//
//    public static void addExit(String location1, String location2, World world) {
//
//        List<Exit> allExits = world.getExitList();
//
//        Location loc1 = world.getLocationByID(location1);
//        Location loc2 = world.getLocationByID(location2);
//
//        if (loc1 != null && loc2 != null) {
//
//            Exit potentialExit = Lambda.getFirst(allExits, e -> e.connectionExists(loc1, loc2));
//
//            if (potentialExit == null) {
//
//                Exit newExit = new Exit(new Location[]{loc1, loc2}, true);
//
//                world.addExit(newExit);
//            }
//        }
//    }


    public static void startTimedScript(GenericObject subject, String[] scriptCommandArray, World world) {

        int time = Integer.parseInt(scriptCommandArray[1]);
        boolean recurring = new Boolean(scriptCommandArray[2]);
        String message = scriptCommandArray[3];

        String script = HelpfulMethods.arrayToScriptString(HelpfulMethods.shortenArray(scriptCommandArray, 4));

        //String[] trimmedArray = HelpfulMethods.shortenArray(scriptCommandArray, 4);

        world.getTimeKeeper().addScript(script, subject, time, recurring, message);

        //String script, GenericObject subject, int time, boolean recurring, String message
    }

    public static void transformObject(GenericObject subject, String[] scriptCommandArray, World world) {

        GenericObject template = world.getTemplateById(scriptCommandArray[1]);

        if(template == null){
            template = world.getGenericObjectById(scriptCommandArray[1]);
        }

        if(template== null){
            return;
        }

        //if(template.getClass() == subject.getClass()){
            subject.transformInto(template);
        //}

    }
}
