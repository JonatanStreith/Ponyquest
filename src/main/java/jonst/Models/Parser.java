package jonst.Models;

import jonst.Commands;
import jonst.Data.SystemData;
import jonst.World;

import java.io.IOException;
import java.util.ArrayList;
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
    }


    public static String[] parse(String command)
    {
        return command.split(" ");

        //Todo: Please rebuild the parser!
    }


    public static void runCommand(String[] command, World world) throws IOException {

        switch (command[0])     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
        {
            case "brandish":
                //stuff
                break;

            case "cast":
                //stuff
                break;

            case "save":
                Commands.saveGame(world);
                break;

            case "load":
                Commands.loadGame(world);
                break;

            case "pick up":
                Commands.pickUp(command[1], world);
                break;

            case "drop":
                Commands.drop(command[1], world);
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
                Commands.goTo(command[1], world);
                break;
            case "go":
                Commands.goTo(command[1], world);
                break;


            case "talk to":
                Commands.talkTo(command[1], world);
                break;

            case "look":
                Commands.LookAround(world);
                break;

            case "look around":
                Commands.LookAround(world);
                break;

            case "look at":
                Commands.lookAt(command[1], world);
                break;

            case "exits":
                Commands.getExits(world);
                break;

            case "teleport":
                Commands.teleportOther(command, world);
                break;

            case "teleport to":
                Commands.teleportSelf(command, world);
                break;

            case "ask":
                Commands.ask(command, world);
                break;

            default:
                System.out.println("What do you mean?");
                break;
        }


    }

}
