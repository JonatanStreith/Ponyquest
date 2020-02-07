package jonst;

import jonst.Data.SystemData;
import jonst.Models.Exit;
import jonst.Models.Objects.*;


import java.util.ArrayList;
import java.util.List;

import static jonst.HelpfulMethods.*;


public class Commands {

    public static void activate(String[] commandArray, World world) {

        String fullName = world.matchLocalName(commandArray[1]);
        GenericObject target = world.getGenericObject(fullName);

        if (target == null) {
            System.out.println("Activate what?");
            return;
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println("You can't tamper with that. " + target.getOwner().getName() + " won't let you.");
        }

        boolean successful = target.runResponseScript("activate");

        if (!successful) {
            System.out.println("Hmm, nothing happened...");
        }
    }

    public static void harvest(String[] commandArray, World world) {

        String fullName = world.matchLocalName(commandArray[1]);
        GenericObject target = world.getGenericObject(fullName);

        if (target == null) {
            System.out.println("Harvest what?");
            return;
        }

        if (!target.hasAttribute("harvestable")) {
            System.out.println("You can't harvest that.");
        }

        if (target.isOwnerPayingAttention()) {
            System.out.println("You can't harvest that. " + target.getOwner().getName() + " doesn't appreciate thieves.");
        }


        boolean successful = target.runResponseScript("harvest");

        if (!successful) {
            System.out.println("Hmm, nothing happened...");
        }
    }

    public static void read(String subject, World world) {

        String fullName = world.matchLocalName(subject);

        if (!fullName.equals("")) {

            GenericObject gen = world.getGenericObject(fullName);

            if (gen == null) {
                System.out.println("Read what?");
                return;
            }

            if (!gen.hasAttribute("readable")) {
                System.out.println("There's nothing to read.");
                return;
            }

            if (gen.isOwnerPayingAttention()) {
                System.out.println(gen.getOwner().getName() + " won't let you read that.");
            }

            System.out.println(gen.getText());
            gen.runResponseScript("read");

        }

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

                if (savePath != "") {
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
            Item item = world.getPlayer().getOwnedItemByName(fullName);
            if (item == null) {
                System.out.println("You're not carrying that.");
            }

            if (item.hasAttribute("undroppable")) {
                System.out.println("You can't drop that.");
                return;
            }

            world.transferItemToNewHolder(item, world.getPlayer(), world.getPlayerLocation());
            System.out.println("You drop the " + name + ".");
            item.runResponseScript("drop");
        }
    }

    public static void open(String name, World world) {
        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if (target != null) {
            if (!target.hasAttribute("openable")) {
                System.out.println("That can't be opened.");
                return;
            }

            if (target.hasAttribute("open")) {
                System.out.println("It's already open.");
                return;
            }

            if (target.isOwnerPayingAttention()) {
                System.out.println(target.getOwner().getName() + " gives you a disapproving look. You better not tamper with that.");
                return;
            }

            target.addAttribute("open");
            target.removeAttribute("closed");
            System.out.println("You open the " + target.getName() + ".");
            target.runResponseScript("open");
        }
    }

    public static void close(String name, World world) {
        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if (target != null) {
            if (!target.hasAttribute("openable")) {
                System.out.println("That can't be closed.");
                return;
            }

            if (target.hasAttribute("closed")) {
                System.out.println("It's already closed.");
                return;
            }

            if (target.isOwnerPayingAttention()) {
                System.out.println(target.getOwner().getName() + " gives you a disapproving look. You better not tamper with that.");
                return;
            }

            target.addAttribute("closed");
            target.removeAttribute("open");
            System.out.println("You close the " + target.getName() + ".");
            target.runResponseScript("close");


        }
    }

    public static void pickUp(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            GenericObject subject = world.getLocalGenericOnGround(fullName);

            if (subject == null) {
                System.out.println("You can't pick up things hidden in containers.");
                return;
            }


            if (subject instanceof Creature)                                              //Subject is a creature.
            {
                Creature creature = (Creature) subject;
                System.out.println("You pick up " + creature.getName() + " with your magic and hold " + himOrHer(creature.getGender()) + " for a moment before putting " + himOrHer(creature.getGender()) + " down again.");
                creature.runResponseScript("pick up");
            } else if (subject instanceof StationaryObject)                               //Subject is a stationary object.
            {
                System.out.println("You'd rather not try lifting " + subject.getName() + ". It's heavy.");

            } else if (subject instanceof Location)                                       //Subject is a location.
            {
                System.out.println("As great and powerful as you are, lifting entire areas is beyond your ability.");

            } else if ((subject instanceof Item)) {

                if (((Item) subject).getHolder() instanceof Location) {      //You can only pick up items from the ground. Others need to be taken from containers.

                    if (!subject.isOwnerPayingAttention()) {

                        world.transferItemToNewHolder((Item) subject, ((Item) subject).getHolder(), world.getPlayer());
                        System.out.println("You pick up the " + name + ".");
                        subject.runResponseScript("pick up");
                    } else {
                        System.out.println("You can't take that while " + subject.getOwner().getName() + " is looking.");
                    }


                } else if (((Item) subject).getHolder() instanceof Creature) {
                    System.out.println("You can't just take that from " + ((Item) subject).getHolder().getName() + ". Try asking nicely.");
                } else if (((Item) subject).getHolder() instanceof StationaryObject || ((Item) subject).getHolder() instanceof Item) {
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

            GenericObject gen = world.getGenericObject(fullName);

            if (!(gen instanceof Creature))                                                //Subject isn't a creature.
            {
                System.out.println("You don't make a habit of talking to inanimate objects.");


            } else if ((gen instanceof Creature)) {
                System.out.println(((Creature) gen).getRandomCasualDialog());         //This runs if you successfully talk to someone.
                gen.runResponseScript("talk to");
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

    public static void use(String[] commandArray, World world) {

        String subject = commandArray[1];

        String conjunction = commandArray[2];
        String target = commandArray[3];


        String subjectFullName = world.matchLocalName(subject);
        GenericObject genSub = world.getGenericObject(subjectFullName); //Remember, this will be null if subject doesn't exist here.


        if (!subjectFullName.equals("")) {


            if (conjunction.equals("") && genSub != null) {       //This is if you do a singular command without a proper conjunction. Also null check, though shouldn't be needed.
                String defaultUse = genSub.getDefaultUse();
                if (defaultUse == null) {
                    System.out.println("You have no clear idea of how to use that.");
                } else {
                    commandArray[0] = defaultUse;
                    world.getParser().runCommandArray(commandArray, world);
                }

            } else {
                //Multicommand

                String targetFullName = world.matchLocalName(target);


                if (!targetFullName.equals("")) {
                    String commandLine = genSub.getComplexUseCommand(targetFullName);



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


                    if (commandLine != null) {  //If we have a legitimate commandline, run it.


                        world.getParser().runCommand(commandLine, world);
                    } else {
                        System.out.println("You have no clear idea of how to use that.");
                    }
                }
            }
        }
    }

    public static void lookAt(String argument, World world) {         //Make sure you can't look at things that aren't present!

        if (argument == "") {     //If you just type "look", "look at" or "look around"
            lookAround(world);
        } else {
            String fullName = world.matchLocalName(argument);

            if (!fullName.equals("")) {

                GenericObject gen = world.getGenericObject(fullName);

                if (gen == world.getPlayerLocation()) {
                    lookAround(world);
                } else {

                    if (gen instanceof Item) {
                        GenericObject owner = ((Item) gen).getHolder();

                        if (owner instanceof Creature) {
                            System.out.print("(Carried by " + owner.getName() + ") ");
                        } else if (owner instanceof Item || owner instanceof StationaryObject) {
                            System.out.print("(In " + owner.getName() + ") ");
                        }
                    }

                    System.out.println(gen.getDescription());

                    if (gen instanceof Creature) {
                        Creature cre = (Creature) gen;
                        System.out.println(capitalize(heOrShe(cre.getGender())) + " looks " + cre.getMood() + ".");
                    }
                    System.out.println();

                    if (!gen.hasAttribute("closed")) {   //If it's closed, you can't see the contents.
                        listOwnedItems(world, gen);
                    } else {
                        System.out.println("It's closed.");
                    }
                }

                gen.runResponseScript("look at");

            }
        }
    }

    public static void goTo(String newArea, World world) {

        Location currentLoc = world.getPlayerLocation();

        //List<String> newAreasFullName = world.matchNameMultiple(newArea); //A list of all places matching the alias provided

        List<Location> potentialDestinations = world.matchLocationsMultiple(newArea);

        Location destination = null;

        outerloop:
        for (Location dest: potentialDestinations) {
            for (Exit exit : world.getExitList()){
                if(exit.connectionExists(currentLoc, dest)) { //If there's a connection between these two places
                    destination = dest;
                    break outerloop;
                }
            }
        }




        if (destination != null)               //Is a destination found?
        {


            world.transferCreatureToLocation(world.getPlayer(), currentLoc, destination);                                            //Add player to new location

            System.out.println("You go to " + destination.getName() + ".");
            moveFollowers(currentLoc, destination, world);

            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            lookAround(world);
            destination.runResponseScript("go to");

        } else {
            System.out.println("You can't get there from here.");
        }
    }

    public static void enter(String location, World world) {

        if (location.equals("")) {
            //If location has no default enter, or it's incorrect
            if (world.getPlayerLocation().getDefaultEnter() == null) {
                System.out.println("Enter where? You don't see any particular place that stands out.");
            } else {
                goTo(world.getPlayerLocation().getDefaultEnter().getName(), world);
            }
            return;
        }

        String targetName = world.matchLocalName(location);
        GenericObject genTarget = world.getLocalGenericOnGround(targetName);

        if (genTarget instanceof StationaryObject || genTarget instanceof Item) {

            if (genTarget.hasAttribute("canenter")) {

                System.out.println("You step into the " + genTarget.getName() + ".");
                genTarget.runResponseScript("enter");
            } else {
                System.out.println("You can't enter that.");
            }

            return;
        }


        List<String> possibleAreas = world.matchNameMultiple(location); //A list of all places matching the alias provided
        for (String area : possibleAreas) {
            if (area.equalsIgnoreCase(world.getPlayerLocation().getName())) {
                goTo(world.getPlayerLocation().getDefaultEnter().getName(), world);
                return;
            }
        }
        System.out.println("You can only enter your current location.");

    }

    public static void exit(String location, World world) {

        if (world.getPlayerLocation().getDefaultExit() == null) {
            System.out.println("You're not in a place with a clear exit.");
            return;
        }

        if (location.equals("")) {
            goTo(world.getPlayerLocation().getDefaultExit().getName(), world);
            return;
        }

        List<String> possibleAreas = world.matchNameMultiple(location); //A list of all places matching the alias provided
        for (String area : possibleAreas) {
            if (area.equalsIgnoreCase(world.getPlayerLocation().getName())) {
                goTo(world.getPlayerLocation().getDefaultExit().getName(), world);
                return;
            }
        }
        System.out.println("You can only exit your current location.");
    }

    public static void getExits(World world) {

        Location loc = world.getPlayerLocation();
        List<String> exits = loc.getExitNames();

        System.out.println("Exits are: " + turnStringListIntoString(exits, "and") + ".");
    }

    public static void teleportOther(String[] command, World world) {                            //TO DO Make sure you can teleport items and objects - different code?

        String targetName = world.matchLocalName(command[1]);

        String destinationName = world.matchName(command[3]);

        GenericObject target = world.getLocalGenericObject(targetName);
        Location destination = world.getLocation(destinationName);

        if (target != null && destination != null) {


            if (target == world.getPlayer())           //Are you instructing the game to teleport Trixie herself?
            {
                Commands.teleportSelf(command, world);

            } else if (target instanceof Location) {
                System.out.println("You'd rather not teleport " + target.getName() + " anywhere. You made that mistake once, and it wasn't pretty.");


            } else if (target instanceof Creature) {
                world.transferCreatureToLocation((Creature) target, world.getPlayerLocation(), destination);
                System.out.println(target.getName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");

            } else if (target instanceof Item) {
                world.transferItemToNewHolder((Item) target, world.getPlayerLocation(), destination);
                System.out.println("The " + target.getName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");

            } else if (target instanceof StationaryObject) {
                world.transferObjectToLocation((StationaryObject) target, world.getPlayerLocation(), destination);
                System.out.println("The " + target.getName() + " vanishes in a burst of smoke!");
                target.runResponseScript("teleport");


            } else {
                System.out.println("Your spell fizzles for some reason.");
            }

        }
    }

    public static void teleportSelf(String[] command, World world) { //Make sure you can teleport items and objects - different code?


        String destinationFullName = world.matchName(command[1]);

        Location destination = world.getLocation(destinationFullName);

        if (destination != null) {


            world.transferCreatureToLocation(world.getPlayer(), world.getPlayerLocation(), destination);


            System.out.println("You vanish in a burst of smoke, and reappear at " + destinationFullName + ".");
            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            lookAround(world);
            destination.runResponseScript("go to");

        } else {
            System.out.println("Your spell fizzles. You need a legitimate destination.");
        }
    }

    public static void hug(String[] command, World world) {
        String fullName = world.matchLocalName(command[1]);
        GenericObject gen = world.getLocalGenericObject(fullName);

        if (gen.hasAttribute("huggable")) {
            System.out.println("You hug " + gen.getName() + " affectionately.");
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
        String fullName = world.matchLocalName(creature);


        if (!fullName.equals("")) {

            GenericObject target = world.getGenericObject(fullName);

            if (!(target instanceof Creature)) {
                System.out.println("You don't make a habit of talking to inanimate objects.");
            } else {            //This runs if you successfully talk to someone.
                switch (conjunction) {
                    case "about":
                        System.out.println(((Creature) target).askAbout(parsedTopic));
                        target.runResponseScript("ask about " + parsedTopic);
                        break;

                    case "for":

                        String itemName = world.matchLocalName(topic);

                        Item item = target.getOwnedItemByName(itemName);

                        if (item == null) {
                            System.out.println("I don't have that.");
                            return;
                        }

                        String targetMood = ((Creature) target).getMood();
                        String targetAllegiance = ((Creature) target).getAllegiance();


                        if (targetAllegiance.equalsIgnoreCase("hostile")) {
                            System.out.println(target.getName() + " doesn't like you, and won't give you anything.");
                        } else if (targetMood.equalsIgnoreCase("annoyed")) {
                            System.out.println(target.getName() + " is in a bad mood and doesn't want to give you that.");
                        } else if (target.hasAttribute("refusetogive_" + item.getName())) {
                            System.out.println(target.getName() + " refuses to give you that for specific reasons.");
                        } else {
                            world.transferItemToNewHolder(item, target, world.getPlayer());
                            System.out.println(target.getName() + " gives you the " + item.getName() + ".");
                            target.runResponseScript("ask for " + itemName);
                        }

                        break;

                    default:
                        System.out.println("You may ask " + target.getName() + " 'about' or 'for' something. This is not correct.");
                        break;


                }


            }
        }

    }

    public static void create(String[] commandArray, World world) {
        Item newItem = JsonBuilder.generateTemplateItem(commandArray[1]);

        if (newItem != null) {
            Creature player = world.getPlayer();
            player.addItem(newItem);
            newItem.setHolder(player);
            newItem.setLocationName(player.getName());

            world.addNewItemToItemList(newItem);

            System.out.println("You create a " + commandArray[1] + " from nothing, and put it in your pocket.");
            newItem.runResponseScript("create");
        } else {
            System.out.println("You try to create something, but it fails somehow.");
        }
    }

    public static void transform(String[] commandArray, World world) {

        String startItemName = commandArray[1];

        String fullName = world.matchLocalName(startItemName);

        GenericObject startItem = world.getLocalGenericObject(fullName);

        if (!(startItem instanceof Item)) {
            System.out.println("You can only transform small, non-living items. Otherwise Starlight gets mad.");
        } else {

            Item newItem = JsonBuilder.generateTemplateItem(commandArray[3]);

            ((Item) startItem).transformInto(newItem);

            System.out.println("You magically transform the " + fullName + " into a " + startItem.getName() + ". Excellent!");
            startItem.runResponseScript("transform");
        }
    }

    public static void give(String[] commandArray, World world) {

        String subjectName = world.matchNameFromInventory(commandArray[1]);
        String targetName = world.matchLocalName(commandArray[3]);

        if (!(subjectName.equals("") || targetName.equals(""))) {

            Item subject = world.getPlayer().getOwnedItemByName(subjectName);
            GenericObject target = world.getGenericObject(targetName);

            if (!(target instanceof Creature)) {
                System.out.println("You realize you're trying to present a gift to a non-sentient object. This is stupid.");
                return;
            }

            if (subject != null) {
                //do gift thing
                world.transferItemToNewHolder(subject, world.getPlayer(), target);
                System.out.println(target.getName() + " accepts the " + subject.getName() + ". " + ((Creature) target).getPersonalQuote("thanks"));

                target.runResponseScript("receive gift");

            }


        }

    }

    public static void place(String[] commandArray, World world) {

        String containerName = world.matchLocalName(commandArray[3]);
        GenericObject container = world.getLocalGenericObject(containerName);

        if (container == null) {
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if (container instanceof Creature) {
            System.out.println(containerName + " doesn't appreciate you trying to force things into "
                    + hisOrHer(((Creature) container).getGender()) + " pockets. Try 'giving' it like a normal " + world.getPlayer().getRace() + ".");
            return;
        }

        if (!(container.hasAttribute("container"))) {
            System.out.println("You can't put anything into the " + containerName + ".");
            return;
        }

        if (container.isOwnerPayingAttention()) {
            System.out.println(container.getOwner().getName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }

        String itemName = world.matchNameFromInventory(commandArray[1]);
        GenericObject item = world.getLocalGenericObject(itemName);

        if (item == null) {
            System.out.println("You're not carrying that.");
            return;
        }

        //Assuming the container exists, the item exists, and the container is a legitimate "container", we'll get here.

        world.transferItemToNewHolder((Item) item, world.getPlayer(), container);
        System.out.println("You put the " + item.getName() + " into the " + container.getName() + ".");
        item.runResponseScript("get placed");
        container.runResponseScript("get something placed into");
    }

    public static void take(String[] commandArray, World world) {

        if (commandArray[3].equals("")) {
            pickUp(commandArray[1], world);
            return;
        }

        String containerName = world.matchLocalName(commandArray[3]);
        GenericObject container = world.getLocalGenericObject(containerName);


        if (container == null) {
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if (container instanceof Creature) {
            System.out.println(containerName + " doesn't appreciate you rummaging through "
                    + hisOrHer(((Creature) container).getGender()) + " things. Try asking nicely.");
            return;
        }

        if (!(container.hasAttribute("container"))) {
            System.out.println("That doesn't hold anything.");
            return;
        }

        if (container.isOwnerPayingAttention()) {
            System.out.println(container.getOwner().getName() + " gives you a disapproving look. You better not tamper with that.");
            return;
        }

        String itemName = world.matchLocalName(commandArray[1]);
        GenericObject item = world.getLocalGenericObject(itemName);

        if (item == null || !(container.hasItem((Item) item))) {
            System.out.println("That's not in there.");
            return;
        }

        if (item.isOwnerPayingAttention()) {
            System.out.println("You can't take that while " + item.getOwner().getName() + " is looking.");
            return;
        }

        world.transferItemToNewHolder((Item) item, container, world.getPlayer());
        System.out.println("You take the " + item.getName() + " from the " + container.getName() + ".");
        item.runResponseScript("pick up");
        container.runResponseScript("get something taken from");

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

    public static ArrayList<Creature> getFollowers(Location currentLocation, World world) {

        ArrayList<Creature> followers = new ArrayList<>();

        for (GenericObject gen : currentLocation.getAllAtLocation()) {

            if (gen instanceof Creature && gen.hasAttribute("following")) {
                followers.add((Creature) gen);
            }
        }
        return followers;
    }

    public static void moveFollowers(Location currentLoc, Location destination, World world) {
        ArrayList<Creature> followers = getFollowers(currentLoc, world);

        for (Creature follower : followers) {
            world.transferCreatureToLocation(follower, currentLoc, destination);
            System.out.println(follower.getName() + " follows you.");
        }
    }

}
