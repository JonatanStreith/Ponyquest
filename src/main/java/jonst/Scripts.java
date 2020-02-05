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
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getOwner());
            world.removeItemFromItemList((Item) subject);
        }
    }

    public static void destroyItem(GenericObject subject, World world) {
        if(subject instanceof Item) {
            System.out.println("The " +subject.getName()+ " is destroyed.");
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getOwner());
            world.removeItemFromItemList((Item) subject);
        }
    }

    public static void fleeToRandomLocation(GenericObject subject, World world){
        if(subject instanceof Creature){
            Location currentLocation = subject.getLocation();
            List<String> viableExits = currentLocation.getExits();

            String chosenExit = viableExits.get((int) Math.floor(Math.random() * viableExits.size()));

            world.transferCreatureToLocation((Creature) subject, currentLocation, world.getLocation(chosenExit));
            System.out.println("The " + subject.getName() + " flees towards " + chosenExit + "!");
        }
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

        Item newItem = JsonBuilder.generateDefaultItem(newItemName);

        world.addItemToGeneric(newItem, actor);
        world.addNewItemToItemList(newItem);

    }

    public static void deleteCarriedItem(GenericObject subject, String[] scriptCommandArray, World world) {

        Creature actor;

        if(scriptCommandArray[1].equalsIgnoreCase("player")){
            actor = world.getPlayer();
        } else {
            actor = world.getCreature(scriptCommandArray[1]);
        }

        String itemToBeDeletedName = scriptCommandArray[2];

        Item itemToBeDeleted = actor.getOwnedItemByName(itemToBeDeletedName);

        actor.removeItem(itemToBeDeleted);
        world.removeItemFromItemList(itemToBeDeleted);

    }
}
