package jonst.Models;

import jonst.Commands;
import jonst.Data.SystemData;
import jonst.HelpfulMethods;
import jonst.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
    public List<String> legitimateNouns;
    public List<String> legitimateCommands;
    private List<String> legitimateConjunctions;

    public Parser(List<GenericObject> genericList) {
        legitimateCommands = SystemData.getLegitimateCommands();
        legitimateConjunctions = SystemData.getLegitimateConjunctions();
        legitimateNouns = new ArrayList<>();

        for (GenericObject gen : genericList) {
            legitimateNouns.add(gen.getName());
            legitimateNouns.add(gen.getShortName());
        }

        HelpfulMethods.reverseSortStringList(legitimateCommands);
        HelpfulMethods.reverseSortStringList(legitimateConjunctions);
        HelpfulMethods.reverseSortStringList(legitimateNouns);


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

        switch (commandArray[0])     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {
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

            case "drop":
                Commands.drop(commandArray[1], world);
                break;

            case "inventory":
                Commands.showInventory(world);
                break;

            case "list nouns":
                Commands.listNouns(world);
                break;

            case "help":
                Commands.help();
                break;

            case "list commands":
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
                Commands.LookAround(world);
                break;

            case "look around":
                Commands.LookAround(world);
                break;

            case "look at":
                Commands.lookAt(commandArray[1], world);
                break;

            case "list exits":
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

}
