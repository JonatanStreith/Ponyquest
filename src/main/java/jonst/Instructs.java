package jonst;

import jonst.Models.Objects.*;

import static jonst.HelpfulMethods.*;

public class Instructs {

    public static void pickUp(Creature subject, String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            GenericObject target = world.getLocalGenericOnGround(fullName);

            if(target == null) {
                System.out.println(subject.getName() + " doesn't see it lying around.");
                return;
            }


            if (target instanceof Creature)                                              //Subject is a creature.
            {
                Creature creature = (Creature) target;
                System.out.println(subject.getName() + " grabs " + creature.getName() + " and holds " + himOrHer(creature.getGender()) + " for a moment before putting " + himOrHer(creature.getGender()) + " down again.");
                creature.runResponseScript("pick up");
            } else if (target instanceof StationaryObject)                               //Subject is a stationary object.
            {
                System.out.println(subject.getName() + " shakes " + hisOrHer(subject.getName()) + " head. That's a bit too heavy for them to humour you with.");

            } else if (target instanceof Location)                                       //Subject is a location.
            {
                System.out.println(subject.getName() + " stares at you incredulously.");

            } else if ((target instanceof Item)) {

                if (((Item) target).getOwner() instanceof Location) {      //You can only pick up items from the ground. Others need to be taken from containers.
                    world.transferItemToNewOwner((Item) target, ((Item) target).getOwner(), subject);
                    System.out.println(subject.getName() + " picks up the " + name + ".");

                } else if (((Item) target).getOwner() instanceof Creature) {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject.getName()) + " head. Stealing is wrong.");
                } else if (((Item) target).getOwner() instanceof StationaryObject || ((Item) target).getOwner() instanceof Item) {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject.getName()) + " head. " + capitalize(heOrShe(subject.getName()) + " doesn't quite get what you're saying."));
                } else {
                    System.out.println("That doesn't work.");
                }

            } else {
                System.out.println("Debug code. If this is shown, something didn't go right.");
            }

        }
    }

}
