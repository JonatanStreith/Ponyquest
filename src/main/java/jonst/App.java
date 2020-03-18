package jonst;

import jonst.Data.SystemData;


import java.io.File;
import java.util.Map;

public class App {

    private static World world;

    public static void main(String[] args) {

        System.out.println("Welcome to the game!");

        String filePath = Options.getLoadingPath();     //This allows the user to choose between loading a save or starting a new game (default world)

        world = new World(filePath);  //Build world

        world.runGame();

        System.out.println("Thanks for playing!");
    }


    public static World getWorld() {
        return world;
    }


}














