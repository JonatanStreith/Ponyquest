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

        World world = new World(filePath);  //Build world

        world.runGame();

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
                }

                /*

                Todo:
                Rebuild the "load from save" later. Use Json to build a "directory" that gets saved somewhere.
                Something that maps an index (unique) to a save name (not) and a save path (use index and name to define it, maybe) - should be easy to build.
                Just make sure that a save ADDS to the list, and doesn't OVERWRITE it.

                */

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
}