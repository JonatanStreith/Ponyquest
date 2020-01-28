package jonst;

import jonst.Data.SystemData;


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

            reply = SystemData.getReply("Do you want to start a (N)ew game, or (L)oad a previous save? ");
            if (reply.equalsIgnoreCase("n")) {
                System.out.println(SystemData.getIntroBlurb());

                return SystemData.getDefaultWorld();

            } else if (reply.equalsIgnoreCase("l")) {

                String loadData = getLoadData();

                if (loadData != "") {
                    return loadData;
                }

            } else {
                System.out.println("Sorry, what?");
            }
        }
    }

    public static String getLoadData() {

        System.out.println("Available saves:");

        Map<Long, String> saves = JsonBuilder.getSavesMenu();

        if (saves.size() > 0) {

            for (Long saveId : saves.keySet()) {
                System.out.println(saveId + ": " + saves.get(saveId));
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
}














