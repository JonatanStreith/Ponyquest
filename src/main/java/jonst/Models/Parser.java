package jonst.Models;

import jonst.Commands;
import jonst.Data.SystemData;
import jonst.HelpfulMethods;
import jonst.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

            legitimateNouns = HelpfulMethods.removeDuplicates((ArrayList<String>) legitimateNouns);
        }

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


    public void runCommand(String command, World world) throws IOException {

        String[] commandArray = parse(command);

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

            case "board":
                //Can only board vehicles. Some vehicles need tickets.
                break;

            case "use":
                //stuff
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

            case "take":
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

            default:
                System.out.println("What do you mean?");
                break;
        }


    }

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
}
