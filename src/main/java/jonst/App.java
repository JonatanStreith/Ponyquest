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

        //Todo: Better error handling! Main should not have throws IOException!

    public static void main(String[] args)  {

        System.out.println("Welcome to the game!");

        String filePath = getLoadingPath();     //This allows the user to choose between loading a save or starting a new game (default world)

        World world = new World(filePath);  //Build world

        world.runGame();

        System.out.println("Thanks for playing!");
    }



    public static String getLoadingPath(){

        String reply;
        boolean choiceMade = false;
        String filePath = "";

        do
        {
            reply = SystemData.getReply("Do you want to start a (N)ew game, or (L)oad a previous save?");
            if (reply.equalsIgnoreCase("n"))
            {
                filePath = SystemData.getDefaultWorld();
                choiceMade = true;
                System.out.println(SystemData.getIntroBlurb());
            }

            else if (reply.equalsIgnoreCase("l"))
            {
                System.out.println("Available saves:");
                for(String dir : new File(SystemData.getSavepath()).list()  )
                {
                    System.out.println(dir);
                }

                /*

                Todo:
                Rebuild the "load from save" later. Use Json to build a "directory" that gets saved somewhere.
                Something that maps an index (unique) to a save name (not) and a save path (use index and name to define it, maybe) - should be easy to build.
                Just make sure that a save ADDS to the list, and doesn't OVERWRITE it.

                */

                String choice = SystemData.getReply("Which save file do you want to load?");

                if (new File(SystemData.getSavepath() + choice).exists())
                {
                    filePath = SystemData.getSavepath() + choice;
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
            { System.out.println("Sorry, what?"); }

        } while (!choiceMade);

        return filePath;
    }
}