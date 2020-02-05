package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.*;


import java.util.ArrayList;
import java.util.List;

import static jonst.HelpfulMethods.*;


public class Commands {

    public static void activate(String[] commandArray, World world){

        String fullName = world.matchLocalName(commandArray[1]);
        GenericObject target = world.getGenericObject(fullName);

        boolean successful = target.runResponseScript("activate");

        if(!successful){
            System.out.println("Hmm, nothing happened...");
        }
    }

    public static void read(String subject, World world) {


        String fullName = world.matchLocalName(subject);

        if (!fullName.equals("")) {

            GenericObject gen = world.getGenericObject(fullName);

            if (gen.hasAttribute("readable")) {
                System.out.println(gen.getText());
            } else {
                System.out.println("There's nothing to read.");
            }
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

            if (item != null) {
                //drop

                world.transferItemToNewOwner(item, world.getPlayer(), world.getPlayerLocation());


                //world.removeFromInventory(item);
                //world.addItemToGeneric(fullName, world.getPlayerLocation().getLocationName());              //Remove from loc

                System.out.println("You drop the " + name + ".");
            } else {
                System.out.println("You're not carrying that.");
            }

        }


    }

    public static void open(String name, World world){
        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if(target != null){
            if(target.hasAttribute("openable")){

                if(!target.hasAttribute("open")){
                    target.addAttribute("open");
                    target.removeAttribute("closed");
                    System.out.println("You open the " + target.getName() + ".");
                } else {
                    System.out.println("It's already open.");
                }

            } else {
                System.out.println("That can't be opened.");
            }
        }
    }

    public static void close(String name, World world){
        String fullName = world.matchLocalName(name);
        GenericObject target = world.getGenericObject(fullName);

        if(target != null){
            if(target.hasAttribute("openable")){

                if(!target.hasAttribute("closed")){
                    target.addAttribute("closed");
                    target.removeAttribute("open");
                    System.out.println("You close the " + target.getName() + ".");
                } else {
                    System.out.println("It's already closed.");
                }

            } else {
                System.out.println("That can't be closed.");
            }
        }
    }

    public static void pickUp(String name, World world) {

        String fullName = world.matchLocalName(name);

        if (!fullName.equals("")) {

            GenericObject subject = world.getLocalGenericOnGround(fullName);

            if(subject == null) {
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

                if (((Item) subject).getOwner() instanceof Location) {      //You can only pick up items from the ground. Others need to be taken from containers.
                    world.transferItemToNewOwner((Item) subject, ((Item) subject).getOwner(), world.getPlayer());
                    System.out.println("You pick up the " + name + ".");

                } else if (((Item) subject).getOwner() instanceof Creature) {
                    System.out.println("You can't just take that from " + ((Item) subject).getOwner().getName() + ". Try asking nicely.");
                } else if (((Item) subject).getOwner() instanceof StationaryObject || ((Item) subject).getOwner() instanceof Item) {
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


            if (!(world.getGenericObject(fullName) instanceof Creature))                                                //Subject isn't a creature.
            {
                System.out.println("You don't make a habit of talking to inanimate objects.");


            } else if ((world.getGenericObject(fullName) instanceof Creature)) {
                System.out.println(world.getCreature(fullName).getRandomCasualDialog());         //This runs if you successfully talk to someone.
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

                    if(gen instanceof Item){
                        GenericObject owner = ((Item) gen).getOwner();

                        if(owner instanceof Creature){
                            System.out.print("(Carried by " + owner.getName() + ") ");
                        }
                        else if (owner instanceof Item || owner instanceof StationaryObject){
                            System.out.print("(In " + owner.getName() + ") ");
                        }
                    }

                    System.out.println(gen.getDescription());

                    if(gen instanceof Creature){
                        Creature cre = (Creature) gen;
                    System.out.println(capitalize(heOrShe(cre.getGender())) + " looks " + cre.getMood() + ".");
                    }
                    System.out.println();

                    if(!gen.hasAttribute("closed")) {   //If it's closed, you can't see the contents.
                        listOwnedItems(world, gen);
                    } else {
                        System.out.println("It's closed.");
                    }
                }
            }
        }
    }

    public static void goTo(String newArea, World world) {

        List<String> newAreasFullName = world.matchNameMultiple(newArea); //A list of all places matching the alias provided

        String destination = "";

        outerLoop:
        for (String exit : world.getPlayerLocation().getExits())     //Check if any of the legitimate exits is the place we want to go to
        {
            for (String area : newAreasFullName) {

                if (area.equalsIgnoreCase(exit)) {
                    destination = area;

                    break outerLoop;
                }
            }

        }

        if (destination != "")               //Is a destination found?
        {
            world.transferCreatureToLocation(world.getPlayer(), world.getPlayerLocation(), world.getLocation(destination));                                            //Add player to new location
            //world.GetPlayer().SetLocation(newArea);                                                     //Change player's location variable; already included in prev command
            System.out.println("You go to " + world.getLocation(destination).getName() + ".");
            SystemData.getReply("[press enter to continue]");
            System.out.flush();
            lookAround(world);
        } else {
            System.out.println("You can't get there from here.");
        }
    }

    public static void enter(String location, World world) {

        if (location.equals("")) {
            //If location has no default enter, or it's incorrect
            if (world.getPlayerLocation().getDefaultEnter() == null || world.getLocation(world.getPlayerLocation().getDefaultEnter()) == null) {
                System.out.println("Enter where? You don't see any particular place that stands out.");
            } else {
                goTo(world.getPlayerLocation().getDefaultEnter(), world);
            }
            return;
        }

        String targetName = world.matchLocalName(location);
        GenericObject genTarget = world.getLocalGenericOnGround(targetName);

        if(genTarget instanceof StationaryObject || genTarget instanceof Item){

            if(genTarget.hasAttribute("canenter")) {

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
                goTo(world.getPlayerLocation().getDefaultEnter(), world);
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
            goTo(world.getPlayerLocation().getDefaultExit(), world);
            return;
        }

        List<String> possibleAreas = world.matchNameMultiple(location); //A list of all places matching the alias provided
        for (String area : possibleAreas) {
            if (area.equalsIgnoreCase(world.getPlayerLocation().getName())) {
                goTo(world.getPlayerLocation().getDefaultExit(), world);
                return;
            }
        }
        System.out.println("You can only exit your current location.");
    }


    public static void getExits(World world) {

        Location loc = world.getLocation(world.getPlayer().getLocationName());
        List<String> exits = loc.getExits();

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
                System.out.println("You'd rather not teleport " + target + " anywhere. That will just end badly.");


            } else if (target instanceof Creature) {
                world.transferCreatureToLocation((Creature)target, world.getPlayerLocation(), destination);
                System.out.println("The " + target + " vanishes in a burst of smoke!");

            } else if (target instanceof Item) {
                world.transferItemToNewOwner((Item)target, world.getPlayerLocation(), destination);
                System.out.println(target + " vanishes in a burst of smoke!");

            } else if (target instanceof StationaryObject) {
                world.transferObjectToLocation((StationaryObject)target, world.getPlayerLocation(), destination);
                System.out.println("The " + target + " vanishes in a burst of smoke!");


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

        } else {
            System.out.println("Your spell fizzles. You need a legitimate destination.");
        }
    }


    public static void hug(String[] command, World world){
        String fullName = world.matchLocalName(command[1]);
        GenericObject gen = world.getLocalGenericObject(fullName);

        if(gen.hasAttribute("huggable")){
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
                        break;

                    case "for":

                        String itemName = world.matchLocalName(topic);

                        Item item = target.getOwnedItemByName(itemName);

                        if(item==null) {
                            System.out.println("I don't have that.");
                            return;
                        }

                        String targetMood = ((Creature) target).getMood();
                        String targetAllegiance = ((Creature) target).getAllegiance();

                        if(targetAllegiance.equalsIgnoreCase("hostile")){
                            System.out.println(target.getName() + " doesn't like you, and won't give you anything.");
                        } else if(targetMood.equalsIgnoreCase("annoyed")){
                            System.out.println(target.getName() + " is in a bad mood and doesn't want to give you that.");
                        } else if(target.hasAttribute("refusetogive_" + item.getName())){
                            System.out.println(target.getName() + " refuses to give you that for specific reasons.");
                        } else {
                            world.transferItemToNewOwner(item, target, world.getPlayer());
                            System.out.println(target.getName() + " gives you the " + item.getName() + ".");
                        }



//                        if(!(targetMood.equalsIgnoreCase("annoyed") || targetAllegiance.equalsIgnoreCase("hostile"))){
//
//                            world.transferItemToNewOwner(item, target, world.getPlayer());
//
//                            System.out.println(target.getName() + " gives you the " + item.getName() + ".");
//                        } else
//                            System.out.println(target.getName() + " refuses.");

                        break;

                    default:
                        System.out.println("You may ask " + target.getName() + " 'about' or 'for' something. This is not correct.");
                        break;


                }


            }
        }

    }

//    public static void cast(String[] command, World world) {
//        System.out.println("You cast " + command[1] + " on " + command[3] + ". Unfortunately, casting hasn't been implemented yet.");
//    }



    public static void create(String[] commandArray, World world) {
        Item newItem = JsonBuilder.generateDefaultItem(commandArray[1]);

        if(newItem != null) {
            Creature player = world.getPlayer();
            player.addItem(newItem);
            newItem.setOwner(player);
            newItem.setLocationName(player.getName());

            world.addNewItemToItemList(newItem);

            System.out.println("You create a " + commandArray[1] + " from nothing, and put it in your pocket.");
        } else {
            System.out.println("You try to create something, but it fails somehow.");
        }
    }


    public static void transform(String[] commandArray, World world) {

        String startItemName = commandArray[1];

        String fullName = world.matchLocalName(startItemName);

        GenericObject startItem = world.getLocalGenericObject(fullName);

        if(!(startItem instanceof Item)){
            System.out.println("You can only transform small, non-living items. Otherwise Starlight gets mad.");
        } else {

            Item newItem = JsonBuilder.generateDefaultItem(commandArray[3]);

            ((Item) startItem).transformInto(newItem);

            System.out.println("You magically transform the " + fullName + " into a " + startItem.getName() + ". Excellent!");
        }
    }


    public static void give(String[] commandArray, World world) {

        String subjectName = world.matchNameFromInventory(commandArray[1]);
        String targetName = world.matchLocalName(commandArray[3]);

        if(!(subjectName.equals("") || targetName.equals(""))) {

            Item subject = world.getPlayer().getOwnedItemByName(subjectName);
            GenericObject target = world.getGenericObject(targetName);

            if(!(target instanceof Creature)){
                System.out.println("You realize you're trying to present a gift to a non-sentient object. This is stupid.");
                return;
            }

            if(subject != null){
                //do gift thing
                world.transferItemToNewOwner(subject, world.getPlayer(), target);
                System.out.println(target.getName() + " accepts the " + subject.getName() + ". " + ((Creature) target).getPersonalQuote("thanks") );


            }



        }

    }

    public static void place(String[] commandArray, World world){

        String containerName = world.matchLocalName(commandArray[3]);
        GenericObject container = world.getLocalGenericObject(containerName);

        if(container == null){
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if(container instanceof Creature){
            System.out.println(containerName + " doesn't appreciate you trying to force things into "
                    + hisOrHer(((Creature) container).getGender()) + " pockets. Try 'giving' it like a normal " + world.getPlayer().getRace() + ".");
            return;
        }

        if(!(container.hasAttribute("container"))){
            System.out.println("You can't put anything into the " + containerName + ".");
            return;
        }

        String itemName = world.matchNameFromInventory(commandArray[1]);
        GenericObject item = world.getLocalGenericObject(itemName);

        if(item == null){
            System.out.println("You're not carrying that.");
            return;
        }

        //Assuming the container exists, the item exists, and the container is a legitimate "container", we'll get here.

        world.transferItemToNewOwner((Item) item, world.getPlayer(), container);
        System.out.println("You put the " + item.getName() + " into the " + container.getName() + ".");
    }

    public static void take(String[] commandArray, World world){

        if(commandArray[3].equals("")){
            pickUp(commandArray[1], world);
            return;
        }

        String containerName = world.matchLocalName(commandArray[3]);
        GenericObject container = world.getLocalGenericObject(containerName);



        if(container == null){
            System.out.println("You can't find the " + commandArray[3] + " here.");
            return;
        }

        if(container instanceof Creature){
            System.out.println(containerName + " doesn't appreciate you rummaging through "
                    + hisOrHer(((Creature) container).getGender()) + " things. Try asking nicely.");
            return;
        }

        if(!(container.hasAttribute("container"))){
            System.out.println("That doesn't hold anything.");
            return;
        }

        String itemName = world.matchLocalName(commandArray[1]);
        GenericObject item = world.getLocalGenericObject(itemName);

        if(item == null || !(container.hasItem((Item) item))){
            System.out.println("That's not in there.");
            return;
        }

        world.transferItemToNewOwner((Item) item, container, world.getPlayer());
        System.out.println("You take the " + item.getName() + " from the " + container.getName() + ".");

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


}
