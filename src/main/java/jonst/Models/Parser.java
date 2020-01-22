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


    public Parser(List<GenericObject> genericList) {
        legitimateCommands = SystemData.getLegitimateCommands();
        legitimateConjunctions = SystemData.getLegitimateConjunctions();
        legitimateNouns = new ArrayList<>();

        for (GenericObject gen : genericList) {
            legitimateNouns.add(gen.getName());
            legitimateNouns.addAll(gen.getAlias());

            legitimateNouns =  HelpfulMethods.removeDuplicates((ArrayList<String>) legitimateNouns);
        }

        HelpfulMethods.reverseSortStringList(legitimateCommands);
        HelpfulMethods.reverseSortStringList(legitimateConjunctions);
        HelpfulMethods.reverseSortStringList(legitimateNouns);


        topicParseList = SystemData.getTopicParseList();

    }


    public String[] parse(String commandInput)
    {
        //A "Command Phrase" contains four elements: a command, a subject, a preposition, and a last argument. Example: "Throw", "rock", "at", "window".

        StringBuilder commandLine = new StringBuilder(commandInput.toLowerCase().trim());

        String[] cleanCommand = {"", "", "", ""};

        for (String command : legitimateCommands) {
            if (commandLine.toString().startsWith(command.toLowerCase())) {     //Now separate the command

                cleanCommand[0] = command;

                commandLine.delete(0, command.length());    //Delete the legit command from the stringbuilder

                if (commandLine.length() > 0)
                    while (commandLine.charAt(0) == ' ')    //Trim off empty spaces
                        commandLine.deleteCharAt(0);
                break;
            }
        }

        for (String noun : legitimateNouns) {
            if (commandLine.toString().startsWith(noun.toLowerCase())) {        //Now separate the noun

                cleanCommand[1] = noun;

                commandLine.delete(0, noun.length());    //Delete the legit noun from the stringbuilder

                if (commandLine.length() > 0)
                    while (commandLine.charAt(0) == ' ')    //Trim off empty spaces
                        commandLine.deleteCharAt(0);
                break;
            }
        }

        for (String conj : legitimateConjunctions) {
            if (commandLine.toString().startsWith(conj.toLowerCase())) {

                cleanCommand[2] = conj;

                commandLine.delete(0, conj.length());    //Delete the legit conjunction from the stringbuilder

                if (commandLine.length() > 0)
                    while (commandLine.charAt(0) == ' ')    //Trim off empty spaces
                        commandLine.deleteCharAt(0);

                break;
            }
        }

        cleanCommand[3] = commandLine.toString();      //Add the remainder


        return cleanCommand;
    }


    public void runCommand(String command, World world) throws IOException {

        String[] commandArray = parse(command);

        for (String str: commandArray) {
            System.out.println(str);
        }



        switch (commandArray[0])     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {

            case "quicksave":
                //stuff
                break;

            case "quickload":
                //stuff
                break;

            case "brandish":
                //stuff
                break;

            case "cast":
                //stuff
                break;

            case "use":
                //stuff
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
                Commands.goTo(commandArray[1], world);
                break;
            case "go":
                Commands.goTo(commandArray[1], world);
                break;


            case "talk to":
                Commands.talkTo(commandArray[1], world);
                break;

            case "talk":

                Commands.talkTo(commandArray[1], world);
                break;


            case "look":
                Commands.lookAround(world);
                break;

            case "look around":
                Commands.lookAround(world);
                break;

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
        if(legitimateNouns.contains(specificAlias)) {
            legitimateNouns.remove(specificAlias);
        }
    }

    public String parseTopic(String term){
        if(topicParseList.containsKey(term))
            return topicParseList.get(term);
        else
            return term;
    }




}
