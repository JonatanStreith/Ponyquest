package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;

public class Spellcasting {


    //This class should work like Commands, but for spells.

    public static void fireball(String[] magicCommandArray, World world) {

        if (magicCommandArray[3].equalsIgnoreCase("")) {
            //If there's no specified target, you have to enter one.
            magicCommandArray[3] = SystemData.getReply("What do you want to cast a fireball at? ");
        }


        String fullName = world.matchLocalName(magicCommandArray[3]);   //Try to match the name to a local object.

        if (fullName.equals("")) {
            System.out.println("You need a specific target.");
            return;
        }

        GenericObject gen = world.getLocalGenericOnGround(fullName);

        if (gen == null) {
            System.out.println("You can't target objects inside containers or carried by other creatures. You're not that precise.");
            return;
        }

        if (gen instanceof Creature && !((Creature) gen).getAllegiance().equalsIgnoreCase("hostile")) {
            System.out.println("You can't throw fireballs at other creatures! Unless they're hostile and you need to defend yourself, that is.");
            return;
        }

        System.out.println("You throw a big fireball from your horn at the " + gen.getName() + ", setting " + HelpfulMethods.himOrHer(gen) + " aflame!");
        gen.addAttribute("onfire");
        gen.runResponseScript("fireball");

        if (gen.getOwner() != null) {
            gen.getOwner().runResponseScript("attacked possession");
        }


    }

    public static void energize(String[] magicCommandArray, World world) {
        String fullName = world.matchLocalName(magicCommandArray[3]);
        GenericObject genTarget = world.getLocalGenericObject(fullName);

        if (genTarget != null) {
            if (genTarget instanceof Creature) {
                System.out.println("You're only supposed to do that with non-living, magical items that require a charge. " + genTarget.getName() + " doesn't apply.");
                return;
            }

            if (genTarget.isOwnerPayingAttention()) {
                System.out.println("You can't do that. " + genTarget.getOwner().getName() + " is looking.");
                return;
            }

            System.out.println("You charge the " + genTarget.getName() + " with power.");
            genTarget.addAttribute("energized");
            genTarget.runResponseScript("energize");

        }
    }

    public static void sleep(String[] magicCommandArray, World world) {
        String fullName = world.matchLocalName(magicCommandArray[3]);
        GenericObject genTarget = world.getLocalGenericObject(fullName);

        if (genTarget != null) {
            if (!(genTarget instanceof Creature)) {
                System.out.println("The sleep spell only works on living creatures.");
                return;
            }

            if (genTarget.hasAttribute("sleep immunity")) {
                System.out.println("You sprinkle " + genTarget.getName() + " with magic sand, but it doesn't seem to have any effect.");
                genTarget.runResponseScript("failed sleep");
                return;
            }

            System.out.println("You sprinkle " + genTarget.getName() + " with magic sand. They immediately turn drowsy, and fall asleep.");
            ((Creature) genTarget).setStatus("sleeping");
            genTarget.runResponseScript("sleep");
        }
    }

}
