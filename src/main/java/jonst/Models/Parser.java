package jonst.Models;

import jonst.CommandSheets.Commands;
import jonst.CommandSheets.Instructs;
import jonst.CommandSheets.Scripts;
import jonst.CommandSheets.Spellcasting;
import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jonst.Data.HelpfulMethods.*;

public class Parser {
    private List<String> legitimateNouns;
    private List<String> legitimateCommands;
    private List<String> legitimateConjunctions;
    private Map<String, String> topicParseList;
    private List<String> legitimateSpells;

    public Parser(World world) {
        legitimateCommands = SystemData.getLegitimateCommands();
        legitimateConjunctions = SystemData.getLegitimateConjunctions();
        legitimateNouns = new ArrayList<>();
        legitimateSpells = SystemData.getLegitimateSpells();

        world.getGenericList()
                .forEach(g -> {
                    legitimateNouns.add(g.getName());
                    legitimateNouns.add(g.getShortName());
                    legitimateNouns.addAll(g.getAlias());
                });

        world.getTemplateList()
                .forEach(g -> {
                    legitimateNouns.add(g.getName());
                    legitimateNouns.add(g.getShortName());
                    legitimateNouns.addAll(g.getAlias());
                });


        legitimateNouns = removeDuplicatesT((ArrayList<String>) legitimateNouns);

        reverseSortStringList(legitimateCommands);
        reverseSortStringList(legitimateConjunctions);
        reverseSortStringList(legitimateNouns);

        topicParseList = SystemData.getTopicParseList();

    }


    public List<String> getLegitimateNouns() {
        return legitimateNouns;
    }

    public List<String> getLegitimateCommands() {
        return legitimateCommands;
    }

    public List<String> getLegitimateConjunctions() {
        return legitimateConjunctions;
    }

    public List<String> getLegitimateSpells() {
        return legitimateSpells;
    }

    public void runCommand(String command, World world) {

        if (command.contains(",")) {
            String[] attemptedNameSplit = command.split(",");

            command = attemptedNameSplit[1].trim();

            GenericObject subject = world.match(world.getLocalGenericList(), Lambda.objectByName(attemptedNameSplit[0]));

            if (subject == null) {
                System.out.println("Who are you talking to?");
            } else if (!(subject instanceof Creature)) {
                System.out.println("The " + subject.getName() + " doesn't seem to listen.");
            } else if (!((Creature) subject).isAgreeable()) {
                System.out.println(subject.getName() + " refuses to take orders from you.");
            } else {
                //System.out.println(subject.getName() + " hears your instruction: \"" + command + "\".");

                String[] commandArray = parse(command);

                runInstructCommandArray((Creature) subject, commandArray, world);
            }
            return;
        }

        //This is if you don't try instructing anyone.
        String[] commandArray = parse(command);

        if (commandArray[0].equalsIgnoreCase("cast")) {
            runMagicCommandArray(commandArray, world);
        } else {
            runCommandArray(commandArray, world);
        }
    }

    public void runScriptCommand(GenericObject subject, String script, World world) {

        String[] scriptCommandArray = script.split(":");

        runScriptCommandArray(subject, scriptCommandArray, world);
    }

    public String[] parse(String commandInput) {
        //A "Command Phrase" contains four elements: a command, a subject, a preposition, and a last argument. Example: "Throw", "rock", "at", "window".

        StringBuilder commandLine = new StringBuilder(commandInput.toLowerCase().trim());

        String[] cleanCommand = {"", "", "", ""};

        cleanCommand[0] = extractCommand(commandLine);

        if (cleanCommand[0].equalsIgnoreCase("cast")) {
            cleanCommand[1] = extractSpellNoun(commandLine);
        } else {
            cleanCommand[1] = extractNoun(commandLine);
        }

        if (cleanCommand[1].equals("")) {                             //Is the second word not an acceptable noun? Maybe it's a conjunction instead.
            cleanCommand[1] = extractConjunction(commandLine);
            cleanCommand[2] = commandLine.toString().trim();
        } else {
            cleanCommand[2] = extractConjunction(commandLine);
            cleanCommand[3] = commandLine.toString().trim();      //Add the remainder
        }

        return cleanCommand;
    }

    //-------- Extract commands for the parser --------------------

    public String extractCommand(StringBuilder sb) {
        for (String command : legitimateCommands) {
            if (sb.toString().startsWith(command.toLowerCase())) {     //Now separate the command
                sb.delete(0, command.length());    //Delete the legit command from the stringbuilder
                if (sb.length() > 0)
                    while (sb.charAt(0) == ' ')    //Trim off empty spaces
                        sb.deleteCharAt(0);
                return command;
            }
        }
        return "";  //A return if the command isn't found
    }

    public String extractNoun(StringBuilder sb) {
        for (String noun : legitimateNouns) {
            if (sb.toString().startsWith(noun.toLowerCase())) {        //Now separate the noun
                sb.delete(0, noun.length());    //Delete the legit noun from the stringbuilder
                if (sb.length() > 0)
                    while (sb.charAt(0) == ' ')    //Trim off empty spaces
                        sb.deleteCharAt(0);
                return noun;
            }
        }
        return "";  //A return if the command isn't found
    }

    public String extractSpellNoun(StringBuilder sb) {
        for (String spell : legitimateSpells) {
            if (sb.toString().startsWith(spell.toLowerCase())) {        //Now separate the noun
                sb.delete(0, spell.length());    //Delete the legit spell from the stringbuilder
                if (sb.length() > 0)
                    while (sb.charAt(0) == ' ')    //Trim off empty spaces
                        sb.deleteCharAt(0);
                return spell;
            }
        }
        return "";  //A return if the command isn't found
    }

    public String extractConjunction(StringBuilder sb) {
        for (String conj : legitimateConjunctions) {
            if (sb.toString().startsWith(conj.toLowerCase())) {
                sb.delete(0, conj.length());    //Delete the legit conjunction from the stringbuilder
                if (sb.length() > 0)
                    while (sb.charAt(0) == ' ')    //Trim off empty spaces
                        sb.deleteCharAt(0);
                return conj;
            }
        }
        return "";
    }

    // ----------------------------------------------------------------------

    public void addToNouns(String specificAlias) {
        if (!legitimateNouns.contains(specificAlias)) {
            legitimateNouns.add(specificAlias);
            reverseSortStringList(legitimateNouns);
        }
    }

    public void removeFromNouns(String specificAlias) {
        if (legitimateNouns.contains(specificAlias)) {
            legitimateNouns.remove(specificAlias);
        }
    }

    public String parseTopic(String term) {
        if (topicParseList.containsKey(term))
            return topicParseList.get(term);
        else
            return term;
    }
    // ----------------------------------------------------------------------

    public void runCommandArray(String[] commandArray, World world) {
        switch (commandArray[0].toLowerCase())     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {
            case "activate":
                Commands.activate(commandArray, world);
                break;

            case "quicksave":
                Commands.saveQuick(world);
                break;

            case "quickload":
                Commands.loadQuick(world);
                break;

            case "brandish":
                //stuff
                break;

            case "shop":
            case "barter":
            case "buy":
            case "sell":
                //"Buy" command. Structure: [buy] [item] [from] [creature]. Creature needs to have attribute "merchant", possibly a sell list(?), and a sell blurb.
                // Trixie has a bottomless purse for now.
                Commands.shop(commandArray, world);
                break;

            case "harvest":
                Commands.harvest(commandArray, world);
                break;


            case "read":
                Commands.read(commandArray[1], world);
                break;

            case "board":
            case "ride":
                //Can only board vehicles. Some vehicles need tickets.
                Commands.board(commandArray, world);
                break;

            case "use":
                Commands.use(commandArray, world);
                break;

            case "attack":
                //check if target is hostile or not
                break;

            case "enter":
                Commands.enter(commandArray[1], world);
                break;

            case "exit":
                Commands.exit(commandArray[1], world);
                break;

            case "save":
                Commands.saveGame(world);
                break;

            case "load":
                Commands.loadGame(world);
                break;

            case "pick up":
                Commands.pickUp(commandArray[1], world);
                break;

            case "drop":
                Commands.drop(commandArray[1], world);
                break;

            case "inventory":
                Commands.showInventory(world);
                break;

            case "nouns":
                Commands.listNouns(world);
                break;

            case "help":
                Commands.help();
                break;

            case "commands":
                Commands.ListCommands(world);
                break;


            case "quit":
                Commands.quit();
                break;

            case "go to":
            case "go":
                Commands.goTo(commandArray[1], world);
                break;


            case "chat with":
                Commands.chatWith(commandArray[1], world);
                break;

            case "talk to":
            case "talk":

                Commands.talkTo(commandArray[1], world);
                break;


            case "look":
            case "look around":
            case "look at":
                Commands.lookAt(commandArray, world);
                break;

            case "exits":
                Commands.getExits(world);
                break;

            case "teleport":
                Commands.teleportOther(commandArray, world);
                break;

            case "teleport to":
                Commands.teleportSelf(commandArray, world);
                break;

            case "ask":
                Commands.ask(commandArray, world);
                break;

            case "put":
            case "place":
                Commands.place(commandArray, world);
                break;

            case "give":
                Commands.give(commandArray, world);
                break;


            case "take":
            case "retrieve":
                Commands.take(commandArray, world);
                break;

            default:
                System.out.println("What do you mean?");
                break;


            case "hug":
                Commands.hug(commandArray, world);
                break;

            case "create":
                Commands.create(commandArray, world);
                break;

            case "transform":
                Commands.transform(commandArray, world);
                break;

            case "open":
                Commands.open(commandArray[1], world);
                break;

            case "close":
                Commands.close(commandArray[1], world);
                break;

            case "wear":
            case "put on":
                Commands.wear(commandArray[1], world);
                break;

            case "remove":
            case "take off":
                Commands.remove(commandArray[1], world);
                break;

            case "eat":
                Commands.eat(commandArray[1], world);
                break;


        }
    }

    // ----------------------------------------------------------------------

    public void runInstructCommandArray(Creature subject, String[] commandArray, World world) {
        switch (commandArray[0].toLowerCase())     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {

//            case "brandish":
//                //stuff
//                break;
//
//            case "cast":
//                Commands.cast(commandArray, world);
//                break;
//
//            case "buy":
//                //"Buy" command. Structure: [buy] [item] [from] [creature]. Creature needs to have attribute "merchant", possibly a sell list(?), and a sell blurb.
//                // Trixie has a bottomless purse for now.
//
//                break;
//
//            case "harvest":
//
//                break;
//

//            case "board":
//                //Can only board vehicles. Some vehicles need tickets.
//                break;
//
//            case "use":
//                Commands.use(commandArray, world);
//                break;
//
//            case "attack":
//                //check if target is hostile or not
//                break;
//
//            case "enter":
//                Commands.enter(commandArray[1], world);
//                break;
//
//            case "exit":
//                Commands.exit(commandArray[1], world);
//                break;
//


            case "follow":
            case "follow me":
                Instructs.follow(subject, world);
                break;

            case "stop follow":
            case "stop following me":
                Instructs.stopFollow(subject, world);
                break;

            case "pick up":
                Instructs.pickUp(subject, commandArray[1], world);
                break;

            case "drop":
                Instructs.drop(subject, commandArray[1], world);
                break;

            case "wear":
                Instructs.wear(subject, commandArray[1], world);
                break;

            case "remove":
                Instructs.remove(subject, commandArray[1], world);
                break;

//            case "go to":
//            case "go":
//                Instructs.goTo(subject, commandArray[1], world);
//                break;


//            case "put":
//            case "place":
//                Instructs.place(commandArray, world);
//                break;
//
            case "give":
                Instructs.give(subject, commandArray, world);
                break;
//
//
//            case "take":
//            case "retrieve":
//                Instructs.take(commandArray, world);
//                break;


            case "hug":
                Instructs.hug(subject, commandArray, world);
                break;


            case "open":
                Instructs.open(subject, commandArray[1], world);
                break;

            case "close":
                Instructs.close(subject, commandArray[1], world);
                break;


            default:
                System.out.print(subject.getName() + " doesn't seem to understand your request. ");
                System.out.println(subject.getPersonalQuote("excuseme?"));
                break;


        }
    }

    // ----------------------------------------------------------------------

    public void runMagicCommandArray(String[] magicCommandArray, World world) {

        switch (magicCommandArray[1].toLowerCase()) {
            case "fireball":
                Spellcasting.fireball(magicCommandArray, world);
                break;

            case "energize":
                Spellcasting.energize(magicCommandArray, world);
                break;

            case "sleep":
                Spellcasting.sleep(magicCommandArray, world);
                break;


            default:
                System.out.println("You don't know of any spell by that name.");
                break;
        }

    }

    // ----------------------------------------------------------------------




    public void runScriptCommandArray(GenericObject subject, String[] scriptCommandArray, World world) {
        switch (scriptCommandArray[0].toLowerCase()) {
            case "setmood":
                Scripts.setMood(subject, scriptCommandArray, world);
                break;

            case "send":
                //Syntax is send:subject:location
                Scripts.send(scriptCommandArray, world);
                break;

            case "createexit":

                break;

            case "changerace":
                //Syntax is changerace:subject:newrace
                Scripts.changeRace(scriptCommandArray, world);
                break;

            case "resetrace":
                Scripts.resetRace(scriptCommandArray, world);
                break;

            case "deletethisitem":
                Scripts.deleteThisItem(subject, world);
                break;

            case "destroyitem":
                Scripts.destroyItem(subject, world);
                break;

            case "fleerandom":
                Scripts.fleeToRandomLocation(subject, world);
                break;

            case "fleeto":
                //fleeto:locationId
                Scripts.fleeTo(subject, scriptCommandArray[1], world);
                break;

            case "addattribute":
                Scripts.addAttribute(subject, scriptCommandArray[1]);
                break;

            case "removeattribute":
                Scripts.removeAttribute(subject, scriptCommandArray[1]);
                break;

            case "addnewitem":
                //addnewitem:owner:itemId
                Scripts.addNewItem(scriptCommandArray, world);
                break;

            case "deletecarrieditem":
                //deletecarrieditem:owner:itemId
                Scripts.deleteCarriedItem(subject, scriptCommandArray, world);
                break;

            case "writeline":
                Scripts.writeLine(scriptCommandArray[1]);
                break;

            case "showdescription":
                //showdescription:descriptionkey
                Scripts.showDescription(subject, scriptCommandArray, world);
                break;

            case "spawncreature":
                //spawncreature:location:Id
                Scripts.spawnCreature(subject, scriptCommandArray, world);
                break;

            case "spawnobject":
                //spawnobject:location:Id
                Scripts.spawnObject(subject, scriptCommandArray, world);
                break;

            case "transformobject":
                //transformobject:objectid
                Scripts.transformObject(subject, scriptCommandArray, world);
                break;

            case "changeinitdialog":
                //changeinitdialog:dialogId
                Scripts.changeInitDialog(subject, scriptCommandArray, world);
                break;

            case "movefollowers":
                Scripts.moveFollowers(subject, scriptCommandArray, world);
                break;

            case "starttimedscript":
                Scripts.startTimedScript(subject, scriptCommandArray, world);
        }
    }


}
