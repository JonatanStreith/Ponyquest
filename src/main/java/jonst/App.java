package jonst;

import jonst.Data.SystemData;
import jonst.Models.SaveFile;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    //Todo: Better error handling! Main should not have throws IOException!

    private static World world;

    public static void main(String[] args) {

        System.out.println("Welcome to the game!");

        String filePath = getLoadingPath();     //This allows the user to choose between loading a save or starting a new game (default world)



        world = new World(filePath);  //Build world

        world.runGame();

        System.out.println("Thanks for playing!");
    }


    public static String getLoadingPath() {

        String reply;


        while (true) {

            reply = SystemData.getReply("Do you want to start a (N)ew game, or (L)oad a previous save?");
            if (reply.equalsIgnoreCase("n")) {
                System.out.println(SystemData.getIntroBlurb());

                return SystemData.getDefaultWorld();

            } else if (reply.equalsIgnoreCase("l")) {

                return getLoadData();

            } else {
                System.out.println("Sorry, what?");
            }
        }
    }


    public static String getLoadData(){

        System.out.println("Available saves:");

        Map<Long, String> saves = JsonBuilder.getSavesMenu();

        for (Long saveId : saves.keySet()) {
            System.out.println(saveId + ": " + saves.get(saveId));
        }

        while (true) {
            String reply = SystemData.getReply("Please input number of save file: ");
            try {
                long saveReply = Long.parseLong(reply);

                if (saves.keySet().contains(saveReply)) {
                    return SystemData.getSavepath() + saveReply + saves.get(saveReply);
                } else {
                    System.out.println("That save does not exist.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input.");
            }
        }



    }

    public static World getWorld() {
        return world;
    }
}














