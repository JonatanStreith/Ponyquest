package jonst.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SystemData {

    public static Scanner inputReader = new Scanner(System.in);

    public static String gamepath = "src/main/java/jonst/";
    public static String savepath = "src/main/java/jonst/Assets/Saves/";

    public static ArrayList getLegitimateCommands(){

        String[] tempArray = new String[] { "save", "load", "nouns", "look at", "look around", "look", "go to", "go", "pick up", "talk to", "quit", "drop",
                                            "brandish", "ask", "cast", "exits", "teleport to", "teleport", "help", "commands", "inventory" };

        ArrayList<String> legitimateCommands = new ArrayList<String>(Arrays.asList(tempArray));

        return legitimateCommands;
    }

    public static ArrayList getLegitimateConjunctions(){

        String[] tempArray = new String[] { "to", "about", "behind", "at", "under", "in front of", "on", "in" };

        ArrayList<String> legitimateConjunctions = new ArrayList<String>(Arrays.asList(tempArray));

        return legitimateConjunctions;
    }


}
