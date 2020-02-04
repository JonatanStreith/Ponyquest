package jonst;

import jonst.Models.Objects.*;

import java.util.List;

public class Scripts {

    public static void setMood(GenericObject subject, String[] script, World world){

        if(subject instanceof Creature) {
            ((Creature) subject).setMood(script[1]);
        }
    }

    public static void deleteItem(GenericObject subject, World world) {
        if(subject instanceof Item) {
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getOwner());
        }
    }

    public static void destroyItem(GenericObject subject, World world) {
        if(subject instanceof Item) {
            System.out.println("The " +subject.getName()+ " is destroyed.");
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getOwner());
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
}
