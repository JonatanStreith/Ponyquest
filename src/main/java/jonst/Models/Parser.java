package jonst.Models;

import jonst.*;
import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;
import jonst.Models.Objects.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    public List<String> legitimateNouns;
    public List<String> legitimateCommands;
    private List<String> legitimateConjunctions;
    private Map<String, String> topicParseList;
    private List<String> legitimateSpells;

    public Parser(List<GenericObject> genericList) {
        legitimateCommands = SystemData.getLegitimateCommands();
        legitimateConjunctions = SystemData.getLegitimateConjunctions();
        legitimateNouns = new ArrayList<>();
        legitimateSpells = SystemData.getLegitimateSpells();

        for (GenericObject gen : genericList) {
            legitimateNouns.add(gen.getName());
            legitimateNouns.addAll(gen.getAlias());


        }

        legitimateNouns.addAll(JsonBuilder.getDefaultItemNames());

        legitimateNouns = HelpfulMethods.removeDuplicates((ArrayList<String>) legitimateNouns);

        HelpfulMethods.reverseSortStringList(legitimateCommands);
        HelpfulMethods.reverseSortStringList(legitimateConjunctions);
        HelpfulMethods.reverseSortStringList(legitimateNouns);

        topicParseList = SystemData.getTopicParseList();

    }



    public String[] parse(String commandInput) {
        //A "Command Phrase" contains four elements: a command, a subject, a preposition, and a last argument. Example: "Throw", "rock", "at", "window".

        StringBuilder commandLine = new StringBuilder(commandInput.toLowerCase().trim());

        String[] cleanCommand = {"", "", "", ""};

        cleanCommand[0] = extractCommand(commandLine);

        if(cleanCommand[0].equalsIgnoreCase("cast")) {
            cleanCommand[1] = extractSpellNoun(commandLine);
        } else {
            cleanCommand[1] = extractNoun(commandLine);
        }

        cleanCommand[2] = extractConjunction(commandLine);
        cleanCommand[3] = commandLine.toString().trim();      //Add the remainder
        return cleanCommand;
    }





    public void runCommand(String command, World world) {



        if(command.contains(",")){
            String[] attemptedNameSplit = command.split(",");
            String targetName = world.matchLocalName(attemptedNameSplit[0]);

            command = attemptedNameSplit[1].trim();

            GenericObject subject = world.getLocalGenericObject(targetName);

            if(subject == null) {
                System.out.println("Who are you talking to?");
            }
            else if (!(subject instanceof Creature)){
                System.out.println("The " + subject.getName() + " doesn't seem to listen.");
            }
            else {
                System.out.println(subject.getName() + " hears your instruction: \"" + command + "\".");

                String[] commandArray = parse(command);

                runInstructCommandArray((Creature) subject, commandArray, world);
            }
            return;
        }

        String[] commandArray = parse(command);

        if(commandArray[0].equalsIgnoreCase("cast")){
            runMagicCommandArray(commandArray, world);
        } else {
            runCommandArray(commandArray, world);
        }
    }


    public void runCommandArray(String[] commandArray, World world){
        switch (commandArray[0].toLowerCase())     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {
            case "quicksave":
                Commands.saveQuick(world);
                break;

            case "quickload":
                Commands.loadQuick(world);
                break;

            case "brandish":
                //stuff
                break;

            case "cast":
                Commands.cast(commandArray, world);
                break;

            case "buy":
                //"Buy" command. Structure: [buy] [item] [from] [creature]. Creature needs to have attribute "merchant", possibly a sell list(?), and a sell blurb.
                // Trixie has a bottomless purse for now.

                break;

            case "harvest":

                break;


            case "read":
                Commands.read(commandArray[1], world);
                break;

            case "board":
                //Can only board vehicles. Some vehicles need tickets.
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


            case "talk to":
            case "talk":

                Commands.talkTo(commandArray[1], world);
                break;


            case "look":
            case "look around":
            case "look at":
                Commands.lookAt(commandArray[1], world);
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

        }
    }

    //---------------------------------------------

    public void runInstructCommandArray(Creature subject, String[] commandArray, World world){
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
//
//            case "read":
//                Commands.read(commandArray[1], world);
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
//            case "save":
//                Commands.saveGame(world);
//                break;
//
//            case "load":
//                Commands.loadGame(world);
//                break;

            case "pick up":
                Instructs.pickUp(subject, commandArray[1], world);
                break;

//            case "drop":
//                Commands.drop(commandArray[1], world);
//                break;
//
//            case "inventory":
//                Commands.showInventory(world);
//                break;
//
//            case "nouns":
//                Commands.listNouns(world);
//                break;
//
//            case "help":
//                Commands.help();
//                break;
//
//            case "commands":
//                Commands.ListCommands(world);
//                break;
//
//
//            case "quit":
//                Commands.quit();
//                break;
//
//            case "go to":
//            case "go":
//                Commands.goTo(commandArray[1], world);
//                break;
//
//
//            case "talk to":
//            case "talk":
//
//                Commands.talkTo(commandArray[1], world);
//                break;
//
//
//            case "look":
//            case "look around":
//            case "look at":
//                Commands.lookAt(commandArray[1], world);
//                break;
//
//            case "exits":
//                Commands.getExits(world);
//                break;
//
//            case "teleport":
//                Commands.teleportOther(commandArray, world);
//                break;
//
//            case "teleport to":
//                Commands.teleportSelf(commandArray, world);
//                break;
//
//            case "ask":
//                Commands.ask(commandArray, world);
//                break;
//
//            case "put":
//            case "place":
//                Commands.place(commandArray, world);
//                break;
//
//            case "give":
//                Commands.give(commandArray, world);
//                break;
//
//
//            case "take":
//            case "retrieve":
//                Commands.take(commandArray, world);
//                break;

            default:
                System.out.println(subject.getPersonalQuote("excuseme"));
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

        }
    }

    //---------------------------------------------

    public void addToNouns(String specificAlias) {
        legitimateNouns.add(specificAlias);
        HelpfulMethods.reverseSortStringList(legitimateNouns);
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


    //-------- Magic stuff -------------------------------


    public void runMagicCommandArray(String[] magicCommandArray, World world){

        switch (magicCommandArray[1].toLowerCase()) {
            case "fireball":
                Spellcasting.fireball(magicCommandArray, world);
                break;

            case "energize":
                Spellcasting.energize(magicCommandArray, world);

            default:
                System.out.println("You don't know of any spell by that name.");
                break;
        }

    }

    //-------- Scriptparser stuff ------------------------

    public void runScriptCommand(GenericObject subject, String script, World world) {

        String[] scriptCommandArray = script.split(" ");

        runScriptCommandArray(subject, scriptCommandArray, world);
    }


    public void runScriptCommandArray(GenericObject subject, String[] scriptCommandArray, World world){
        switch (scriptCommandArray[0].toLowerCase())
        {
            case "setmood":
                Scripts.setMood(subject, scriptCommandArray, world);
                break;

            case "goto":

                break;

            case "createexit":

                break;

            case "changerace":

                break;

            case "deleteitem":
                Scripts.deleteItem(subject, world);
                break;

                case "destroyitem":
            Scripts.destroyItem(subject, world);
            break;

            case "createitem":

                break;

            case "fleerandom":
                Scripts.fleeToRandomLocation(subject, world);
                break;


            case "addattribute":
                Scripts.addAttribute(subject, scriptCommandArray, world);
                break;

            case "removeattribute":
                Scripts.removeAttribute(subject, scriptCommandArray, world);
                break;
        }
    }


}
