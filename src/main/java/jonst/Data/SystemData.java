package jonst.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SystemData {

    public static Scanner inputReader = new Scanner(System.in);

    public static String gamepath = "src/main/java/jonst";
    public static String savepath = "src/main/java/jonst/Assets/Saves/";
    public static String defaultWorld = "src/main/java/jonst/Assets/DefaultWorld";

    public static ArrayList getLegitimateCommands(){

        ArrayList<String> legitimateCommands =  new ArrayList<String>() {{
            add("save");
            add("load");
            add("nouns");
            add("look at");
            add("look around");
            add("look");
            add("go to");
            add("go");
            add("pick up");
            add("talk to");
            add("quit");
            add("drop");
            add("brandish");
            add("ask");
            add("cast");
            add("exits");
            add("teleport to");
            add("teleport");
            add("help");
            add("commands");
            add("inventory");
        }};


        return legitimateCommands;
    }

    public static ArrayList getLegitimateConjunctions(){

        ArrayList<String> legitimateConjunctions = new ArrayList<String>() {{
            add("to");
            add("about");
            add("behind");
            add("at");
            add("under");
            add("in front of");
            add("on");
            add("in");
        }};
        return legitimateConjunctions;
    }


}
