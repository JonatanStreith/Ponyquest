package jonst;

import jonst.Models.Objects.*;

import java.util.List;

public class Scripts {

    public static void setMood(GenericObject subject, String[] script, World world){

        if(subject instanceof Creature) {
            ((Creature) subject).setMood(script[1]);
        }
    }

    public static void deleteThisItem(GenericObject subject, World world) {
        if(subject instanceof Item) {
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getHolder());
            world.removeItemFromItemList((Item) subject);
        }
    }

    public static void destroyItem(GenericObject subject, World world) {
        if(subject instanceof Item) {
            System.out.println("The " +subject.getName()+ " is destroyed.");
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getHolder());
            world.removeItemFromItemList((Item) subject);
        }
    }

    public static void fleeToRandomLocation(GenericObject subject, World world){
//        if(subject instanceof Creature){
//            Location currentLocation = subject.getLocation();
//            List<String> viableExits = currentLocation.getExits();
//
//            String chosenExit = viableExits.get((int) Math.floor(Math.random() * viableExits.size()));
//
//            world.transferCreatureToLocation((Creature) subject, currentLocation, world.getLocation(chosenExit));
//            System.out.println("The " + subject.getName() + " flees towards " + chosenExit + "!");
//        }
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

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        Location destination = world.getLocation(scriptCommandArray[2]);

        if(actor!=null && destination!=null){
            world.transferCreatureToLocation(actor, actor.getLocation(), destination);

            Commands.lookAround(world);
        }
    }

    public static void changeRace(GenericObject subject, String[] scriptCommandArray, World world) {

        Creature actor;

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
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

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        String newItemName = scriptCommandArray[2];

        Item newItem = JsonBuilder.generateTemplateItem(newItemName);

        world.addItemToGeneric(newItem, actor);
        world.addNewToList(newItem);

    }

    public static void deleteCarriedItem(GenericObject subject, String[] scriptCommandArray, World world) {

        Creature actor;
        Item itemToBeDeleted;

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        if(scriptCommandArray[2].equalsIgnoreCase("this")){
            itemToBeDeleted = (Item) subject;
        } else {
            itemToBeDeleted = actor.getOwnedItemByName(scriptCommandArray[2]);
        }




        actor.removeItem(itemToBeDeleted);
        world.removeItemFromItemList(itemToBeDeleted);

    }

    public static void showDescription(GenericObject subject, String[] scriptCommandArray, World world) {
        String desc = subject.getSpecficDescription(scriptCommandArray[1]);
        System.out.println(desc);
    }

    public static void resetRace(String[] scriptCommandArray, World world){
        Creature actor;

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        actor.setRace(actor.getDefaultRace());

    }

    public static void spawnCreature(GenericObject subject, String[] scriptCommandArray, World world) {
        Location location;
        if(scriptCommandArray[1].equalsIgnoreCase("playerlocation")){
           location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }


        Creature newCreature = JsonBuilder.generateTemplateCreature(scriptCommandArray[2]);




        world.addCreatureToLocation(newCreature, location);
        world.addNewToList(newCreature);
    }

    public static void spawnObject(GenericObject subject, String[] scriptCommandArray, World world) {

        Location location;
        if(scriptCommandArray[1].equalsIgnoreCase("playerlocation")){
            location = world.getPlayerLocation();
        } else {
            location = world.getLocationByID(scriptCommandArray[1]);
        }


        StationaryObject newObject = JsonBuilder.generateTemplateObject(scriptCommandArray[2]);




        world.addObjectToLocation(newObject, location);
        world.addNewToList(newObject);

    }

    public static void changeInitDialog(GenericObject subject, String[] scriptCommandArray, World world) {
        if(subject instanceof Creature) {
            ((Creature) subject).setInitialDialog(scriptCommandArray[1]);
        }
    }

}
