package jonst;

import jonst.Data.SystemData;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{



    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to the game!");

        String filePath = getLoadingPath();     //This allows the user to choose between loading a save or starting a new game (default world)

        World Equestria = new World(filePath);  //Build world


        Equestria.runGame();      //Todo: Have a method run things instead

        System.out.println("Thanks for playing!");
    }



    public static String getLoadingPath(){
        SystemData.inputReader = new Scanner(System.in);

        String reply;
        boolean choiceMade = false;
        String filePath = "";

        System.out.println("Do you want to start a (N)ew game, or (L)oad a previous save?");  //Maybe list saves?

        do
        {
            reply = SystemData.inputReader.nextLine().toLowerCase();
            if (reply.equals("n"))
            {
                filePath = SystemData.defaultWorld;
                choiceMade = true;
                System.out.println(SystemData.introBlurb);
            }

            else if (reply.equals("l"))
            {
                System.out.println("Available saves:");
                for(String dir : new File(SystemData.savepath).list()  )
                {
                    System.out.println(dir);
                } //List save files - clean up later

                System.out.println("Which save file do you want to load? ");

                String choice = SystemData.inputReader.nextLine();

                if (new File(SystemData.savepath + choice).exists())
                {
                    filePath = SystemData.savepath + choice;
                    System.out.println("Restoring from " + choice + "...");
                    choiceMade = true;
                }
                else
                {
                    System.out.println("That file doesn't exist.");
                }
                //Ask for one
            }
            else
            { System.out.println("Sorry, what? Do you want to start a (N)ew game, or (L)oad a previous save?"); }

        } while (!choiceMade);

        return filePath;
    }


//    public static void runCommand(String[] command, World world) throws IOException {
//
//        switch (command[0])     //This can be used to parse similar expressions, i.e. "examine" points to "look at".
//        {
//            case "brandish":
//                //stuff
//                break;
//
//            case "cast":
//                //stuff
//                break;
//
//            case "save":
//                Commands.saveGame(world);
//                break;
//
//            case "load":
//                Commands.loadGame(world);
//                break;
//
//            case "pick up":
//                Commands.pickUp(command[1], world);
//                break;
//
//            case "drop":
//                Commands.drop(command[1], world);
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
//                Commands.goTo(command[1], world);
//                break;
//            case "go":
//                Commands.goTo(command[1], world);
//                break;
//
//
//            case "talk to":
//                Commands.talkTo(command[1], world);
//                break;
//
//            case "look":
//                Commands.LookAround(world);
//                break;
//
//            case "look around":
//                Commands.LookAround(world);
//                break;
//
//            case "look at":
//                Commands.lookAt(command[1], world);
//                break;
//
//            case "exits":
//                Commands.getExits(world);
//                break;
//
//            case "teleport":
//                Commands.teleportOther(command, world);
//                break;
//
//            case "teleport to":
//                Commands.teleportSelf(command, world);
//                break;
//
//            case "ask":
//                Commands.ask(command, world);
//                break;
//
//            default:
//                System.out.println("What do you mean?");
//                break;
//        }
//
//
//    }
















}
