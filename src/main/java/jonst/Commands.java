package jonst;

import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Dialog;
import jonst.Models.Exit;
import jonst.Models.Merchandise;
import jonst.Models.Objects.*;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static jonst.HelpfulMethods.*;


public class Commands {

    // ------- System commands ------------

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

                if (savePath != "") {
                    System.out.println("Loading game...");

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

        System.out.println("Commands are: " + turnStringListIntoString(world.getParser().getLegitimateCommands(), "and"));
    }

    public static void listNouns(World world) {
        System.out.println("Nouns are: " + turnStringListIntoString(world.getParser().getLegitimateNouns(), "and"));
    }

    // ------- Interact commands ------------

    public static void shop(String[] commandArray, World world){

        //Check if we're asking to buy something specific.

        boolean isNoun = world.getParser().getLegitimateConjunctions().contains(commandArray[1]);


        if(isNoun){

            GenericObject merchant = world.match(world.getLocalGenericList(), commandArray[2], Lambda.predicateByName(commandArray[2]));

            if(merchant == null){
                System.out.println("Who or what are you trying to do business with?");
                return;
            }

            if(!(merchant instanceof Creature)){
                System.out.println("Nice try. Try to do your shopping from actual sentient creatures, okay?");
                return;
            }

            if(!(merchant instanceof Merchant)){
                System.out.println(merchant + " doesn't have anything to offer.");
                return;
            }
            //We didn't specify a proper thing to buy, so let's open the shop interface.
            openShop();
            return;
        }

        if(!(world.getParser().getLegitimateNouns().contains(commandArray[1]))){
            System.out.println("What are you trying to shop for?");
            return;
        }

        GenericObject merchant = world.match(world.getLocalGenericList(), commandArray[3], Lambda.predicateByName(commandArray[3]));

        if(merchant == null){
            System.out.println("Who or what are you trying to do business with?");
        return;
        }

        if(!(merchant instanceof Creature)){
            System.out.println("Nice try. Try to do your shopping from actual sentient creatures, okay?");
            return;
        }

        if(!(merchant instanceof Merchant)){
            System.out.println(merchant + " doesn't have anything to offer.");
            return;
        }

        String prodName = commandArray[1];

        Merchandise product = Lambda.getFirst(((Merchant) merchant).getMerchandiseList(), p -> p.getNames().contains(prodName));
        if(product == null){
            System.out.println(merchant + " doesn't sell that.");
            return;
        }


        if(commandArray[0].equalsIgnoreCase("buy")){
            buyFrom(merchant, product, world);
            return;
        }

        if(commandArray[0].equalsIgnoreCase("sell")){
            sellTo(merchant, product, world);
            return;
        }

    }

    public static void sellTo(GenericObject merchant, Merchandise product, World world){
        //System.out.println("sellTo: " + product.getName());
    }
    public static void openShop(){
        System.out.println(("openShop"));
    }

    public static void buyFrom(GenericObject merchant, Merchandise product, World world){

        Item newItem = Item.create(product.getId());

        System.out.println("You buy a " + newItem.getShortName() + " from " + merchant + ".");

        world.addItemToHolder(newItem, world.getPlayer());
    }



    public static void activate(String[] commandArray, World world) {

//        String fullName = world.matchLocalName(commandArray[1]);
//        GenericObject target = world.getGenericObject(fullName);
//        List<GenericObject> genericList = world.getGenericList();
        //Predicate<GenericObject> pred = (GenericObject g) -> g.getName().equals(commandArray[1]) || g.getAlias().contains(commandArray[1]);

        GenericObject target = world.match(world.getLocalGroundOnly(), commandArray[1], Lambda.predicateByName(commandArray[1]));


        if (target == null) {
            System.out.println("Activate what?");
            return;
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println("You can't tamper with that. " + target.getOwner().getName() + " won't let you.");
            return;
        }

        System.out.println("You attempt to activate the " + target.getShortName());

        boolean successful = target.runResponseScript("activate");

        if (!successful) {
            System.out.println("Hmm, nothing happened...");
        }
    }

    public static void harvest(String[] commandArray, World world) {

//        String fullName = world.matchLocalName(commandArray[1]);
//        GenericObject target = world.getGenericObject(fullName);

        GenericObject target = world.match(world.getLocalGroundOnly(), commandArray[1], Lambda.predicateByName(commandArray[1]));


        if (target == null) {
            System.out.println("Harvest what?");
            return;
        }

        if (!target.hasAttribute("harvestable")) {
            System.out.println("You can't harvest that.");
            return;
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println("You can't harvest that. " + target.getOwner().getName() + " doesn't appreciate thieves.");
            return;
        }


        boolean successful = target.runResponseScript("harvest");

        if (!successful) {
            System.out.println("Hmm, nothing happened...");
            return;
        }
    }

    public static void read(String subject, World world) {

        GenericObject target = world.match(world.getLocalGroundOnly(), subject, Lambda.predicateByName(subject));


        if (target == null) {
            System.out.println("Read what?");
            return;
        }


        if (target.isOwnerPayingAttention()) {
            System.out.println(target.getOwner().getName() + " won't let you read that.");
            return;
        }

        target.read();

        target.runResponseScript("read");


    }

    public static void drop(String subject, World world) {

        GenericObject target = world.match(world.getLocalGenericList(), subject, Lambda.predicateByName(subject));

        if (target == null) {
            System.out.println("Drop what?");
            return;
        }

        if (!(target instanceof Item)) {
            System.out.println("You can't drop that. You can't even hold it in the first place!");
            return;
        }

        Item targetItem = (Item) target;

        if (!(world.getPlayer().hasItem(targetItem))) {
            System.out.println("You're not carrying that.");
            return;
        }

        if (targetItem.hasAttribute("undroppable")) {
            System.out.println("You refuse to drop that.");
            return;
        }

        if (targetItem.hasAttribute("worn")) {
            remove(targetItem.getName(), world);
        }

        world.transferItemToNewHolder(targetItem, world.getPlayer(), world.getPlayerLocation());
        System.out.println("You drop the " + targetItem.getShortName() + ".");
        targetItem.runResponseScript("drop");

    }

    public static void eat(String name, World world) {

        GenericObject target = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), name, Lambda.predicateByName(name));

        if (target == null) {
            //If not, you can't eat it.
            System.out.println("Eat what?");
            return;
        }

        if (target instanceof Creature) {
            System.out.println("You're not quite hungry enough to consider resorting to cannibalism.");
            return;
        }

        if (target instanceof Location) {
            System.out.println("You're... not that hungry.");
            return;
        }

        if (!target.hasAttribute("edible")) {
            System.out.println("You can't eat that.");
            return;
        }

        if (target.hasAttribute("poisonous") && !(world.getPlayer().hasAttribute("poison immunity"))) {
            System.out.println("You can't eat that! It's poisonous!");
            return;
        }

        System.out.println("You consume the " + target.getShortName() + ".");
        target.runResponseScript("eat");
        target.destroy();
    }

    public static void wear(String name, World world) {

        GenericObject target = world.match(world.getPlayerInventory(), name, Lambda.predicateByName(name));


        if (target == null) {
            System.out.println("You're not carrying that.");
            return;
        }

        if (!target.hasAttribute("wearable")) {
            System.out.println("You can't wear that.");
            return;
        }


        if (target.hasAttribute("worn")) {
            System.out.println("You're already wearing that.");
            return;
        }

        if (world.getPlayer().isWearing(target.getType())) {
            System.out.println("You're already wearing a " + target.getType() + ".");
            return;
        }

        target.addAttribute("worn");
        System.out.println("You put on the " + target.getShortName() + ".");
        target.runResponseScript("wear");

    }

    public static void remove(String name, World world) {

        GenericObject target = world.match(world.getPlayerInventory(), name, Lambda.predicateByName(name));


        if (target == null) {
            System.out.println("You're not carrying that.");
            return;
        }

        if (!target.hasAttribute("worn")) {
            System.out.println("You're not wearing that.");
            return;
        }

        target.removeAttribute("worn");
        System.out.println("You take off the " + target.getShortName() + ".");
        target.runResponseScript("remove");

    }

    public static void open(String name, World world) {

        GenericObject target = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), name, Lambda.predicateByName(name));

        if (target == null) {
            //If not, you can't open it.
            System.out.println("Open what?");
            return;
        }

        if (!target.hasAttribute("openable")) {
            System.out.println("That can't be opened.");
            return;
        }

        if (target.hasAttribute("locked")) {
            System.out.println("It's locked.");
            return;
        }

        if (target.hasAttribute("open")) {
            System.out.println("It's already open.");
            return;
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println(target.getOwner().getShortName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }

        target.addAttribute("open");
        target.removeAttribute("closed");
        System.out.println("You open the " + target.getShortName() + ".");
        target.runResponseScript("open");

    }

    public static void close(String name, World world) {

        GenericObject target = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), name, Lambda.predicateByName(name));

        if (target == null) {
            //If not, you can't close it.
            System.out.println("Close what?");
            return;
        }


        if (!target.hasAttribute("openable")) {
            System.out.println("That can't be closed.");
            return;
        }

        if (target.hasAttribute("closed")) {
            System.out.println("It's already closed.");
            return;
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println(target.getOwner().getShortName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }

        target.addAttribute("closed");
        target.removeAttribute("open");
        System.out.println("You close the " + target.getShortName() + ".");
        target.runResponseScript("close");


    }

    public static void pickUp(String name, World world) {

        GenericObject target = world.match(world.getLocalGenericList(), name, Lambda.predicateByName(name));

        if (target == null) {
            System.out.println("Pick up what?");
            return;
        }

        if (target instanceof Creature) {                                             //Subject is a creature.
            Creature creature = (Creature) target;
            System.out.println("You pick up " + creature.getShortName() + " with your magic and hold " + himOrHer(creature) + " for a moment before putting " + himOrHer(creature) + " down again.");
            creature.runResponseScript("pick up");
            return;
        }

        if (target instanceof StationaryObject) {                              //Subject is a stationary object
            System.out.println("You'd rather not try lifting " + target.getShortName() + ". It's heavy.");
            return;
        }

        if (target instanceof Location) {                                      //Subject is a location.
            System.out.println("As great and powerful as you are, lifting entire areas is beyond your ability.");
            return;
        }

        if (!(target instanceof Item)) {
            System.out.println("You can't pick that up.");
            return;
        }

        Item targetItem = (Item) target;
        GenericObject itemHolder = targetItem.getHolder();

        if (itemHolder instanceof StationaryObject || itemHolder instanceof Item) {
            System.out.println("You can't pick up things hidden in containers. You need to TAKE them from there.");
            return;
        }

        if (itemHolder instanceof Creature) {
            System.out.println("You can't just take that from " + itemHolder.getShortName() + ". Try asking nicely.");
            return;
        }

        if (itemHolder instanceof Location) {

            if (targetItem.isOwnerPayingAttention()) {
                System.out.println("You can't take that while " + targetItem.getOwner().getShortName() + " is looking.");
            } else {
                world.transferItemToNewHolder(targetItem, targetItem.getHolder(), world.getPlayer());
                System.out.println("You pick up the " + targetItem.getShortName() + ".");
            }

            return;
        }

    }

    public static void talkTo(String name, World world) {

        GenericObject target = world.match(world.getLocalGenericList(), name, Lambda.predicateByName(name));

        if (!(target instanceof Creature))                                                //Subject isn't a creature.
        {
            System.out.println("You don't make a habit of talking to inanimate objects.");

        } else if ((target instanceof Creature)) {

            Commands.initiateDialog((Creature) target, world);

            target.runResponseScript("talk to");
        }
    }

    public static void chatWith(String name, World world) {

        GenericObject target = world.match(world.getLocalGenericList(), name, Lambda.predicateByName(name));

        if (!(target instanceof Creature))                                                //Subject isn't a creature.
        {
            System.out.println("You don't make a habit of talking to inanimate objects.");


        } else if ((target instanceof Creature)) {
            System.out.println(((Creature) target).getRandomCasualDialog());         //This runs if you successfully talk to someone.
            target.runResponseScript("chat with");
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

        System.out.println(loc.getName() + "\n" + loc.getDescription() + "\n");

        listCreatures(world);   //Lists all creatures on location.
        System.out.println();
        listStationaryObjects(world);   //Lists all objects on location.
        System.out.println();
        listOwnedItems(world, loc);       //Lists all items on location.

        //To do: List items and objects

    }

    public static void use(String[] commandArray, World world) {


        String conjunction = commandArray[2];

        GenericObject subject = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), commandArray[1], Lambda.predicateByName(commandArray[1]));


        if (conjunction.equals("") && subject != null) {       //This is if you do a singular command without a proper conjunction. Also null check.
            String defaultUse = subject.getDefaultUse();
            if (defaultUse == null) {
                System.out.println("You have no clear idea of how to use that.");
            } else {
                commandArray[0] = defaultUse;
                world.getParser().runCommandArray(commandArray, world);
            }
            return;
        }

        GenericObject target = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), commandArray[3], Lambda.predicateByName(commandArray[3]));


        /*                  This is a bit too advanced right now. It requires replacing parts of the command line...
                    if(commandLine == null){
                        GenericObject gen = world.getGenericObject(targetFullName);
                        if(gen instanceof Item){
                            commandLine = genSub.getComplexUseCommand("anyitem");
                        } else if(gen instanceof Creature){
                             commandLine = genSub.getComplexUseCommand("anycreature");
                        }
                    }
*/

        //Todo: Instead of checking if the subject is a specific specimen, try to match against a certain 'type'.
        //That way, we have more flexibility.

        if (target != null) {
            String commandLine = subject.getComplexUseCommand(target.getName());

            if (commandLine == null) {    //If there isn't a complex command line, check if there's one for the target type!
                commandLine = subject.getComplexUseCommand(target.getType());
            }

            if (commandLine == null) {  //If we have a legitimate commandline, run it.
                System.out.println("You have no clear idea of how to use that.");
                return;
            }

            world.getParser().runCommand(commandLine, world);

        }
    }

    public static void lookAt(String[] argument, World world) {         //Make sure you can't look at things that aren't present!

        if (argument[1].equals("") && !argument[3].equals("")) {      //This is to check if we caught junk that isn't found in the nounslist.
            System.out.println("You don't see '" + argument[3] + "' here.");
            return;
        }

        if (argument[1].equals("")) {     //If you just type "look", "look at" or "look around"
            lookAround(world);
            return;
        }

        GenericObject subject = world.match(world.getLocalGenericList(), argument[1], Lambda.predicateByName(argument[1]));


        if (subject == null) {
            System.out.println("You don't see it anywhere.");
            return;
        }


        if (subject == world.getPlayerLocation()) {
            lookAround(world);
            return;
        }


        if (subject instanceof Item) {
            GenericObject owner = ((Item) subject).getHolder();

            if (owner instanceof Creature) {

                if (subject.hasAttribute("worn")) {
                    System.out.print("(Worn by " + owner.getName() + ") ");
                } else {
                    System.out.print("(Carried by " + owner.getName() + ") ");
                }

            } else if (owner instanceof Item || owner instanceof StationaryObject) {
                System.out.print("(In " + owner.getName() + ") ");
            }
        }

        System.out.println(subject.getDescription());

        if (!subject.hasAttribute("closed")) {   //If it's closed, you can't see the contents.
            listOwnedItems(world, subject);
        } else {
            System.out.println("It's closed.");
        }

        subject.runResponseScript("look at");
    }

    public static void goTo(String newArea, World world) {

        Location currentLoc = world.getPlayerLocation();

        List<Location> potentialDestinations = world.matchMultiple(world.getLocationList(), newArea, Lambda.predicateByName(newArea));

        if (potentialDestinations == null) {
            System.out.println("You don't know how to get to ' " + newArea + ".");
            return;
        }

        Location destination = Lambda.getFirst(potentialDestinations, world.getExitList(), (l, e) -> e.connectionExists(currentLoc, l));

        if (destination != null)               //Is a destination found?
        {
            goTo(destination, world);
        } else {
            System.out.println("You can't get there from here.");
        }
    }

    public static void enter(String location, World world) {


        //This is for if you just type 'enter'.
        if (location.equals("")) {
            //If location has no default enter, or it's incorrect
            if (world.getPlayerLocation().getDefaultEnter() == null) {
                System.out.println("Enter where? You don't see any particular place that stands out.");
            } else {
                goTo(world.getPlayerLocation().getDefaultEnter(), world);
            }
            return;
        }


        //This is for if you want to enter your current location(s enterlocation).
        {
            List<Location> potentialDestinations = world.matchMultiple(world.getLocationList(), location, Lambda.predicateByName(location));

            //world.matchLocationsMultiple(location);
            Location currentLoc = world.getPlayerLocation();


            //TODO: This may be more complicated than necessary.
            if (potentialDestinations != null) {

                Location destination = Lambda.getFirst(potentialDestinations, l -> l == currentLoc);

                if (destination != null) {
                    goTo(destination.getDefaultEnter(), world);
                    return;
                }

                System.out.println("You can only enter your current location.");
            }
        }

        //This is if you want to enter an object or item... somehow.
        GenericObject genTarget = world.match(world.getLocalGroundOnly(), location, Lambda.predicateByName(location));

        if (genTarget == null) {
            System.out.println("Enter what?");
            return;
        }

        if (genTarget instanceof StationaryObject || genTarget instanceof Item) {

            if (genTarget.hasAttribute("canenter")) {

                System.out.println("You step into the " + genTarget.getShortName() + ".");
                genTarget.runResponseScript("enter");
            } else {
                System.out.println("You can't enter that.");
            }

            return;
        }
    }

    public static void exit(String location, World world) {


        if (location.equals("")) {
            if (world.getPlayerLocation().getDefaultExit() == null) {
                System.out.println("You're not in a place with a clear exit.");
                return;
            }
            goTo(world.getPlayerLocation().getDefaultExit(), world);
            return;
        }


        {
            List<Location> potentialDestinations = world.matchMultiple(world.getLocationList(), location, Lambda.predicateByName(location));

            //world.matchLocationsMultiple(location);


            //TODO: This may be more complicated than necessary.
            if (potentialDestinations != null) {

                Location currentLoc = world.getPlayerLocation();


                Location destination = Lambda.getFirst(potentialDestinations, l -> l == currentLoc);

                if (destination != null) {
                    goTo(currentLoc.getDefaultExit(), world);
                    return;
                }

                System.out.println("You can only exit your current location.");
            }
        }
    }

    public static void getExits(World world) {

        Location loc = world.getPlayerLocation();
        List<String> exits = loc.getExitNames();

        System.out.println("Exits are: " + turnStringListIntoString(exits, "and") + ".");
    }

    public static void teleportOther(String[] command, World world) {                            //TO DO Make sure you can teleport items and objects - different code?


        GenericObject target = world.match(world.getLocalGenericList(), command[1], Lambda.predicateByName(command[1]));

        Location destination = world.match(world.getLocationList(), command[3], Lambda.predicateByName(command[3]));


        if (target != null && destination != null) {


            if (target == world.getPlayer())           //Are you instructing the game to teleport Trixie herself?
            {
                Commands.teleportSelf(command, world);

            } else if (target instanceof Location) {
                System.out.println("You'd rather not teleport " + target.getShortName() + " anywhere. You made that mistake once, and it wasn't pretty.");


            } else if (target instanceof Creature) {
                world.moveToLocation((Creature) target, world.getPlayerLocation(), destination);
                System.out.println(target.getShortName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");

            } else if (target instanceof Item) {
                world.transferItemToNewHolder((Item) target, world.getPlayerLocation(), destination);
                System.out.println("The " + target.getShortName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");

            } else if (target instanceof StationaryObject) {
                world.moveToLocation((StationaryObject) target, world.getPlayerLocation(), destination);
                System.out.println("The " + target.getShortName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");


            } else {
                System.out.println("Your spell fizzles for some reason.");
            }

        }
    }

    public static void teleportSelf(String[] command, World world) { //Make sure you can teleport items and objects - different code?


        Location destination = world.match(world.getLocationList(), command[1], Lambda.predicateByName(command[1]));


        if (destination != null) {


            world.moveToLocation(world.getPlayer(), world.getPlayerLocation(), destination);


            System.out.println("You vanish in a burst of smoke, and reappear at " + destination.getShortName() + ".");
            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            lookAround(world);
            destination.runResponseScript("go to");

        } else {
            System.out.println("Your spell fizzles. You need a legitimate destination.");
        }
    }

    public static void hug(String[] command, World world) {

        GenericObject gen = world.match(world.getLocalGroundOnly(), command[1], Lambda.predicateByName(command[1]));

        if (gen == null) {
            System.out.println("You can only hug nearby things.");
        }


        if (gen.hasAttribute("huggable")) {
            System.out.println("You hug " + gen.getShortName() + " affectionately.");
            gen.runResponseScript("hug");
        } else
            System.out.println("They don't look very huggable.");

    }

    public static void ask(String[] command, World world) {

        //Todo: Not checked for functionality yet!

        String creature = command[1];
        String conjunction = command[2];
        String topic = command[3];

        String parsedTopic = world.getParser().parseTopic(topic.toLowerCase());


        GenericObject target = world.match(world.getLocalGenericList(), creature, Lambda.predicateByName(creature));


        if (target == null) {
            System.out.println("Ask who?");
            return;
        }


        if (!(target instanceof Creature)) {
            System.out.println("You don't make a habit of talking to inanimate objects.");
            return;
        }


        //This runs if you successfully talk to someone.
        switch (conjunction) {
            case "about":
                System.out.println(((Creature) target).askAbout(parsedTopic));
                target.runResponseScript("ask about " + parsedTopic);
                break;

            case "for":

                Item item = world.match(target.getItemList(), topic, Lambda.predicateByName(topic));


                if (item == null) {
                    System.out.println("I don't have that.");
                    return;
                }

                String targetMood = ((Creature) target).getMood();
                String targetAllegiance = ((Creature) target).getAllegiance();


                if (targetAllegiance.equalsIgnoreCase("hostile")) {
                    System.out.println(target.getShortName() + " doesn't like you, and won't give you anything.");
                } else if (targetMood.equalsIgnoreCase("annoyed")) {
                    System.out.println(target.getShortName() + " is in a bad mood and doesn't want to give you that.");
                } else if (target.hasAttribute("refusetogive_" + item)) {
                    System.out.println(target.getShortName() + " refuses to give you that for specific reasons.");
                } else {
                    world.transferItemToNewHolder(item, target, world.getPlayer());
                    System.out.println(target + " gives you the " + item.getShortName() + ".");
                    target.runResponseScript("ask for " + item);
                }

                break;

            default:
                System.out.println("You may ask " + target + " 'about' or 'for' something. This is not correct.");
                break;
        }
    }

    public static void create(String[] commandArray, World world) {
        Item newItem = JsonBuilder.generateTemplateItem(commandArray[1]);

        if (newItem != null) {
            Creature player = world.getPlayer();
            player.addItem(newItem);
            newItem.setHolder(player);
            newItem.setLocationId(player.getName());

            world.addNewToList(newItem);

            System.out.println("You create a " + newItem.getShortName() + " from nothing, and put it in your pocket.");
            newItem.runResponseScript("create");
        } else {
            System.out.println("You try to create something, but it fails somehow.");
        }
    }

    public static void transform(String[] commandArray, World world) {

        GenericObject item = world.match(world.getLocalGenericList(), commandArray[1], Lambda.predicateByName(commandArray[1]));

        String originalName = item.getShortName();

        if (!(item instanceof Item)) {
            System.out.println("You can only transform small, non-living items. Otherwise Starlight gets mad.");
        } else {

            Item newItem = JsonBuilder.generateTemplateItem(commandArray[3]);

            ((Item) item).transformInto(newItem);

            System.out.println("You magically transform the " + originalName + " into a " + item.getShortName() + ". Excellent!");
            item.runResponseScript("transform");
        }
    }

    public static void give(String[] commandArray, World world) {


        Item subject = world.match(world.getPlayerInventory(), commandArray[1], Lambda.predicateByName(commandArray[1]));
        GenericObject target = world.match(world.getLocalGenericList(), commandArray[3], Lambda.predicateByName(commandArray[3]));

        if (subject == null) {
            System.out.println("Give what?");
            return;
        }
        if (target == null) {
            System.out.println("Give to whom?");
            return;
        }


        if (!(target instanceof Creature)) {
            System.out.println("You realize you're trying to present a gift to a non-sentient object. This is stupid.");
            return;
        }

        //do gift thing
        if (subject.hasAttribute("undroppable")) {
            System.out.println("You don't want to relinquish that.");
            return;
        }

        if (subject.hasAttribute("worn")) {
            remove(subject.getName(), world);
        }

        world.transferItemToNewHolder(subject, world.getPlayer(), target);
        System.out.println(target.getShortName() + " accepts the " + subject.getShortName() + ". " + ((Creature) target).getPersonalQuote("thanks"));


        subject.runResponseScript("is given");
        target.runResponseScript("receive gift");

    }

    public static void place(String[] commandArray, World world) {

        GenericObject container = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), commandArray[3], Lambda.predicateByName(commandArray[3]));

        if (container == null) {
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if (container instanceof Creature) {
            System.out.println(container + " doesn't appreciate you trying to force things into "
                    + hisOrHer((Creature) container) + " pockets. Try 'giving' it like a normal " + world.getPlayer().getRace() + ".");
            return;
        }

        if (!(container.hasAttribute("container"))) {
            System.out.println("You can't put anything into the " + container.getShortName() + ".");
            return;
        }

        if (container.hasAttribute("closed")) {
            System.out.println("The " + container.getShortName() + " is closed. You can't put anything into it.");
            return;
        }

        if (container.isOwnerPayingAttention()) {
            System.out.println(container.getOwner().getShortName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }


        Item item = world.match(world.getPlayerInventory(), commandArray[1], Lambda.predicateByName(commandArray[1]));


        if (item == null) {
            System.out.println("You're not carrying that.");
            return;
        }

        if (item.hasAttribute("undroppable")) {
            System.out.println("You don't want to relinquish that.");
            return;
        }

        //Assuming the container exists, the item exists, and the container is a legitimate "container", we'll get here.

        world.transferItemToNewHolder((Item) item, world.getPlayer(), container);
        System.out.println("You put the " + item.getShortName() + " into the " + container.getShortName() + ".");
        item.runResponseScript("get placed");
        container.runResponseScript("get something placed into");
    }

    public static void take(String[] commandArray, World world) {

        if (commandArray[3].equals("")) {
            pickUp(commandArray[1], world);
            return;
        }

        GenericObject container = world.match(fuseLists(world.getPlayerInventory(), world.getLocalGroundOnly()), commandArray[3], Lambda.predicateByName(commandArray[3]));


        if (container == null) {
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if (container instanceof Creature) {
            System.out.println(container.getShortName() + " doesn't appreciate you rummaging through "
                    + hisOrHer((Creature) container) + " things. Try asking nicely.");
            return;
        }

        if (!(container.hasAttribute("container"))) {
            System.out.println("That doesn't hold anything.");
            return;
        }

        if (container.hasAttribute("closed")) {
            System.out.println("The " + container.getShortName() + " is closed. You can't take anything from it.");
            return;
        }

        if (container.isOwnerPayingAttention()) {
            System.out.println(container.getOwner().getShortName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }

        GenericObject item = world.match(world.getLocalGenericList(), commandArray[1], Lambda.predicateByName(commandArray[1]));

        if (!(item instanceof Item)) {
            System.out.println("You can't pick that up. It also shouldn't be in a container.");
            return;
        }

        if (item == null || !(container.hasItem((Item) item))) {
            System.out.println("That's not in there.");
            return;
        }

        if (item.isOwnerPayingAttention()) {
            System.out.println("You can't take that while " + item.getOwner().getShortName() + " is looking.");
            return;
        }

        world.transferItemToNewHolder((Item) item, container, world.getPlayer());
        System.out.println("You take the " + item.getShortName() + " from the " + container.getShortName() + ".");
        item.runResponseScript("pick up");
        container.runResponseScript("get something taken from");

    }

    public static void board(String[] commandArray, World world) {
        GenericObject target = world.match(world.getLocalGroundOnly(), commandArray[1], Lambda.predicateByName(commandArray[1]));

        if (target instanceof Creature) {
            System.out.println("That's a bit too intimate for your taste.");
            return;
        }

        if (!(target instanceof Vehicle)) {
            System.out.println("You can't ride that.");
            return;
        }

        Location current = world.getPlayerLocation();
        Location destination;

        if (commandArray[3].equalsIgnoreCase("")) {                     //This is if you don't type in a destination.
            List<Location> destinations = ((Vehicle) target).getDestinations();
            System.out.println("Where do you want to go?\n");
            for (int i = 0; i < destinations.size(); i++) {
                System.out.println("" + (i + 1) + "\t" + destinations.get(i).getName());
            }
            System.out.println("0:\t Abort");
            int reply = SystemData.getNumericalReply("\n(0 - " + destinations.size() + "): ", destinations.size());

            if (reply == 0) {
                System.out.println("You decide not to go anywhere.");
                return;
            }
            destination = destinations.get(reply - 1);
        } else {
            destination = world.match(((Vehicle) target).getDestinations(), commandArray[3], Lambda.predicateByName(commandArray[3]));
        }

        if(destination == null){
            System.out.println("The " + target.getShortName() + " doesn't go there.");
        }

        if(destination == world.getPlayerLocation()){
            System.out.println("You're already here! Well, that was a short trip.");
            return;
        }


        System.out.println("You board the " + target.getShortName() + " and travel to " + destination + ".");
        moveFollowers(current, destination, world);

        world.moveToLocation(target, current, destination);

        world.moveToLocation(world.getPlayer(), current, destination);

        pause();

        lookAround(world);

    }

    //--------------- Not for direct use -----------------------


    public static void listOwnedItems(World world, GenericObject owner) {
        List<Item> itemList = owner.getItemList();

        if (owner == world.getPlayer()) {
            if (itemList.size() > 0) {
                System.out.println("You carry " + turnListIntoString(itemList, "and") + ".");
            }
        } else if (owner instanceof Location) {
            if (itemList.size() > 0) {
                System.out.println("There is " + turnListIntoString(itemList, "and") + " here.");
            }
        } else if (owner instanceof Creature) {
            if (itemList.size() > 0) {
                System.out.println(capitalize(heOrShe(((Creature) owner))) + " carries " + turnListIntoString(itemList, "and") + ".");
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

    public static List<Creature> getFollowers(Location currentLocation, World world) {

        List<Creature> followers = new ArrayList<>();

        Lambda.processList(currentLocation.getAllAtLocation(), gen -> gen instanceof Creature && gen.hasAttribute("following"), gen -> followers.add((Creature) gen));

        return followers;
    }

    public static void moveFollowers(Location currentLoc, Location destination, World world) {
        Lambda.processList(getFollowers(currentLoc, world), follower -> {
            world.moveToLocation(follower, currentLoc, destination);
            System.out.println(follower.getShortName() + " follows you.");
        });
    }

    public static void initiateDialog(Creature speaker, World world) {

        String dialogKey = speaker.getInitialDialog();

        if (dialogKey == null) {
            System.out.println(speaker.getShortName() + " has little to speak about.");
            return;
        }

        while (true) {
            Dialog currentDialog = world.getDialogEntry(dialogKey);     //Get dialog entry

            if (currentDialog == null) {                                  //Check so the entry exists; otherwise, restart. Should not happen.
                System.out.println("[Dialog missing, returning to root.]\n");
                dialogKey = speaker.getInitialDialog();
                continue;
            }

            System.out.println(currentDialog.getText());                //Print the speaker's line.

            List<String> scripts = currentDialog.getScripts();

            if (scripts != null) {
                Lambda.processList(scripts, s -> world.getParser().runScriptCommand(speaker, s, world));
            }

            if (dialogKey.substring(dialogKey.length() - 3).equalsIgnoreCase("END")) {  //If this was an ending choice, this is where it stops.
                break;
            }

            String[][] responses = currentDialog.getResponses();

            System.out.println();
            for (int i = 0; i < responses.length; i++) {                //Print each potential response

                System.out.println(i + 1 + ": " + responses[i][1]);
            }
            System.out.println("0: End conversation.");                 //Default "stop talking", just in case
            System.out.println();

            int choice = SystemData.getNumericalReply("Your choice? ", responses.length);

            if (choice == 0) {
                System.out.println("You suddenly end the conversation.\n");
                break;
            } else {
                dialogKey = (String) responses[choice - 1][0];
            }

        }


    }

    public static void goTo(Location destination, World world) {

        //This method is not directly called from the parser.
        Location currentLoc = world.getPlayerLocation();

        world.moveToLocation(world.getPlayer(), currentLoc, destination);                                            //Add player to new location

        System.out.println("You go to " + destination.getShortName() + ".");
        moveFollowers(currentLoc, destination, world);

        pause();

        System.out.flush();
        lookAround(world);
        destination.runResponseScript("go to");
    }
}
