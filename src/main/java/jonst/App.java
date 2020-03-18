package jonst;

import jonst.Data.SystemData;


import java.io.File;
import java.util.Map;

public class App {

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

            reply = SystemData.getReply("Do you want to start a (N)ew game, (L)oad a previous save, access (O)ptions, or (Q)uit? ");

            switch (reply.toLowerCase()) {
                case "n":
                    System.out.println(SystemData.getIntroBlurb());
                    return SystemData.getDefaultWorld();

                case "l":
                    String loadData = getLoadData();

                    if (loadData != "") {
                        return loadData;
                    } else {
                        System.out.println("Save file not found.");
                    }
                    break;

                case "o":
                    loadOptions();
                    break;

                case "q":
                    System.out.println("Goodbye.");
                    HelpfulMethods.pause();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Sorry, what?");
                    break;
            }

        }
    }

    public static String getLoadData() {

        System.out.println("Available saves:");

        Map<Long, String> saves = JsonBuilder.getSavesMenu();

        if (saves.size() > 0) {

            for (Long saveId : saves.keySet()) {
                System.out.println(saveId + ":\t" + saves.get(saveId));
            }

            while (true) {
                String reply = SystemData.getReply("Please input number of save file: ('c' to cancel) ");

                if (reply.equalsIgnoreCase("c")) {
                    System.out.println("Loading cancelled.");
                    return "";
                } else {
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
        } else {
            System.out.println("You have no saved games.");

            return "";
        }
    }

    public static World getWorld() {
        return world;
    }

    public static void loadOptions() {

        while (true) {

            System.out.println("Options menu");
            System.out.println("------------");
            System.out.println("1:\tDelete save files");
            System.out.println("0:\tReturn to main menu");
            System.out.println();
            int choice = SystemData.getNumericalReply("Your choice: ", 10);

            switch (choice) {
                case 1:
                    deleteSaves();
                    break;

                case 0:
                    System.out.println("Returning...");
                    return;

                default:
                    System.out.println("That's not an option.");
                    break;


            }

        }
    }

    public static void deleteSaves() {

        System.out.println("Available saves:");

        Map<Long, String> saves = JsonBuilder.getSavesMenu();


        if (saves.size() == 0) {
            System.out.println("You have no saved games.");
        } else {
            for (Long saveId : saves.keySet()) {
                System.out.println(saveId + ":\t" + saves.get(saveId));
            }

            while (true) {
                String reply = SystemData.getReply("Please input number of save file to delete: ('c' to cancel) ");

                if (reply.equalsIgnoreCase("c")) {
                    System.out.println("Loading cancelled.");
                    return;
                }


                try {
                    long saveReply = Long.parseLong(reply);

                    if (saves.keySet().contains(saveReply)) {
                        deleteSave(saves, saveReply);
                    } else {
                        System.out.println("That save does not exist.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input.");
                }

            }
        }
    }

    public static void deleteSave(Map<Long, String> saves, long saveReply) {

        String name = saveReply+saves.get(saveReply);
        //Delete folder
        String filePath = SystemData.getSavepath() + saveReply + saves.get(saveReply);
        File save = new File(filePath);
        SystemData.deleteDir(save);




        //Delete map entry
        saves.remove(saveReply);

        JsonBuilder.buildSavesMenu(saves);

        System.out.println("Save \"" + name + "\" deleted.");

    }

}














