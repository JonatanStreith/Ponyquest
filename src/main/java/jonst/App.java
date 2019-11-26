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



    static void Main(String[] args) throws IOException {

        SystemData.inputReader = new Scanner(System.in);





        //create locations, creatures, objects and items




        String defaultWorld = SystemData.gamepath +"/Assets/DefaultWorld";
        String reply;
        boolean choiceMade = false;
        String filePath = defaultWorld;
        System.out.println("Welcome to the game! Do you want to start a (N)ew game, or (L)oad a previous save?");  //Maybe list saves?

        do
        {
            reply = SystemData.inputReader.nextLine();


            if (reply == "n")
            {
                filePath = defaultWorld;
                choiceMade = true;
                rollIntro();

            }

            else if (reply == "l")
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


        World Equestria = new World(filePath);




        String input;
        String[] commandPhrase;         //A "Command Phrase" contains four elements: a command, a subject, a preposition, and a last argument. Example: "Throw", "rock", "at", "window".






        System.out.println("Game begins!");

        System.out.println("------------------------------------------------------------------------------------------------------------------------");






        Commands.LookAround(Equestria);




        while (true)                //Continously running play loop that parses instructions

        {


            System.out.println();
            System.out.println("Please input command: ");
            input = SystemData.inputReader.nextLine().toLowerCase();
            commandPhrase = parser(input, Equestria);


            System.out.println(commandPhrase[0]);
            System.out.println(commandPhrase[1]);
            System.out.println(commandPhrase[2]);
            System.out.println(commandPhrase[3]);


            runCommand(commandPhrase, Equestria);


        }





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






    public static String[] parser(String command, World world)
    {
        return command.split(" ");


    }









    public static void rollIntro()
    {
        //Console.Clear();
        System.out.println("Once upon a time, in the magical land of Equestria...");
        System.out.println("\n");
        System.out.println("A great and powerful magician went to Ponyville to awe and impress. That didn't end very well. Later, she returned for vengeance. That didn't quite work out either.");
        System.out.println("\n");
        System.out.println("Then she returned again and made a great friend, and later helped save Equestria from the changeling menace, proving how all those neighsayers were foolish and wrong for doubting Trixie.");
        System.out.println("\n");
        System.out.println("Now, Trixie has returned to Ponyville once again. What adventures await her this time?");

        SystemData.inputReader.nextLine();
    }
}
