package jonst;

import jonst.Models.Objects.*;

import static jonst.HelpfulMethods.*;

public class Instructs {


    public static void drop(Creature subject, String name, World world) {
        String fullName = world.matchLocalName(name);
        if (!fullName.equals("")) {
            Item item = subject.getOwnedItemByName(fullName);
            if (item != null) {
                //drop
                if (item.hasAttribute("undroppable")) {
                    System.out.println(subject.getName() + " won't drop that.");
                } else {
                    world.transferItemToNewHolder(item, subject, subject.getLocation());
                    System.out.println(subject.getName() + " drops the " + name + ".");
                    item.runResponseScript("drop");
                }
            } else {
                System.out.println(subject.getName() + " isn't carrying that.");
            }
        }
    }

    public static void pickUp(Creature subject, String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            GenericObject target = world.getLocalGenericOnGround(fullName);

            if (target == null) {
                System.out.println(subject.getName() + " doesn't see it lying around.");
                return;
            }


            if (target instanceof Creature)                                              //Subject is a creature.
            {
                Creature creature = (Creature) target;
                System.out.println(subject.getName() + " grabs " + creature.getName() + " and holds " + himOrHer(creature) + " for a moment before putting " + himOrHer(creature) + " down again.");
                creature.runResponseScript("pick up");
            } else if (target instanceof StationaryObject)                               //Subject is a stationary object.
            {
                System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. That's a bit too heavy for them to humour you with.");

            } else if (target instanceof Location)                                       //Subject is a location.
            {
                System.out.println(subject.getName() + " stares at you incredulously.");

            } else if ((target instanceof Item)) {

                if (((Item) target).getHolder() instanceof Location) {      //You can only pick up items from the ground. Others need to be taken from containers.
                    world.transferItemToNewHolder((Item) target, ((Item) target).getHolder(), subject);
                    System.out.println(subject.getName() + " picks up the " + name + ".");

                } else if (((Item) target).getHolder() instanceof Creature) {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. Stealing is wrong.");
                } else if (((Item) target).getHolder() instanceof StationaryObject || ((Item) target).getHolder() instanceof Item) {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. " + capitalize(heOrShe(subject) + " doesn't quite get what you're saying."));
                } else {
                    System.out.println("That doesn't work.");
                }

            } else {
                System.out.println("Debug code. If this is shown, something didn't go right.");
            }

        }
    }

    public static void open(Creature subject, String name, World world) {

        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if (target != null) {
            if (target.hasAttribute("openable")) {

                if (!target.hasAttribute("open")) {
                    target.addAttribute("open");
                    target.removeAttribute("closed");
                    System.out.println(subject.getName() + " opens the " + target.getName() + ".");
                    target.runResponseScript("open");
                } else {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. It's already open.");
                }

            } else {
                System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. That can't be opened.");
            }
        }
    }

    public static void close(Creature subject, String name, World world) {
        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if (target != null) {
            if (target.hasAttribute("openable")) {

                if (!target.hasAttribute("closed")) {
                    target.addAttribute("closed");
                    target.removeAttribute("open");
                    System.out.println(subject.getName() + " closes the " + target.getName() + ".");
                    target.runResponseScript("close");
                } else {
                    System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. It's already closed.");
                }

            } else {
                System.out.println(subject.getName() + " shakes " + hisOrHer(subject) + " head. That can't be closed.");
            }
        }
    }

    public static void hug(Creature subject, String[] command, World world) {
        String fullName = world.matchLocalName(command[1]);
        GenericObject gen = world.getLocalGenericObject(fullName);

        if (gen.hasAttribute("huggable")) {
            System.out.println(subject.getName() + "nods, and hugs " + gen.getName() + " affectionately.");
            gen.runResponseScript("hug");
        } else
            System.out.println(subject.getName() + " gives " + gen.getName() + " a wary look and shakes " + hisOrHer(subject) + " head. They don't look very huggable.");

    }

    public static void follow(Creature subject, World world) {
        subject.addAttribute("following");
        System.out.println(subject.getPersonalQuote("yes"));
    }


    public static void stopFollow(Creature subject, World world) {
        subject.removeAttribute("following");
        System.out.println(subject.getPersonalQuote("yes"));

    }

    public static void wear(Creature subject, String name, World world) {

        String heOrShe = HelpfulMethods.capitalize(HelpfulMethods.heOrShe(subject));

        String fullName = world.matchLocalName(name);
        if (!fullName.equals("")) {
            Item item = subject.getOwnedItemByName(fullName);
            if (item == null) {
                System.out.println(heOrShe + " isn't carrying that.");
                return;
            }

            if (!item.hasAttribute("wearable")) {
                System.out.println(heOrShe + " can't wear that.");
                return;
            }

            if (item.hasAttribute("worn")) {
                System.out.println(heOrShe + "'s already wearing that.");
                return;
            }

            item.addAttribute("worn");
            System.out.println(heOrShe + " puts on the " + name + ".");
            item.runResponseScript("wear");
        }

    }

    public static void remove(Creature subject, String name, World world) {

        String heOrShe = HelpfulMethods.capitalize(HelpfulMethods.heOrShe(subject));

        String fullName = world.matchLocalName(name);
        if (!fullName.equals("")) {
            Item item = subject.getOwnedItemByName(fullName);
            if (item == null) {
                System.out.println(heOrShe + " isn't carrying that.");
                return;
            }

            if (!item.hasAttribute("worn")) {
                System.out.println(heOrShe + " isn't wearing that.");
                return;
            }

            item.removeAttribute("worn");
            System.out.println(heOrShe + " takes off the " + name + ".");
            item.runResponseScript("remove");
        }
    }

    public static void give(Creature subject, String[] commandArray, World world) {

        String subjectName = world.matchNameFromInventory(subject, commandArray[1]);
        String targetName = world.matchLocalName(commandArray[3]);

        if (!(subjectName.equals("") || targetName.equals(""))) {

            Item item = subject.getOwnedItemByName(subjectName);
            GenericObject target = world.getGenericObject(targetName);

            if (!(target instanceof Creature)) {
                System.out.println(subject.getName() + " wonders why you're asking them to present a gift to a non-sentient object.");
                return;
            }

            if (item == null) {
                System.out.println("They're not carrying that.");
                return;
            }

            if (item.hasAttribute("worn")) {
                remove(subject, item.getName(), world);
            }

            world.transferItemToNewHolder(item, subject, target);
            System.out.println(target.getName() + " accepts the " + item.getName() + ". " + ((Creature) target).getPersonalQuote("thanks"));


            item.runResponseScript("is given");
            target.runResponseScript("receive gift");


        }
    }
}
