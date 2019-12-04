package jonst.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SystemData {

    public static Scanner inputReader = new Scanner(System.in);

    public static String gamepath = "src/main/java/jonst";
    public static String testpath = "src/main/java/jonst/Assets/Test/";
    public static String savepath = "src/main/java/jonst/Assets/Saves/";
    public static String defaultWorld = "src/main/java/jonst/Assets/DefaultWorld";

    public static ArrayList getLegitimateCommands() {

        ArrayList<String> legitimateCommands = new ArrayList<String>() {{
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

    public static ArrayList getLegitimateConjunctions() {

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


    public static String introBlurb = "Once upon a time, in the magical land of Equestria...\n" +
            "A great and powerful magician went to Ponyville to awe and impress. That didn't end very well. Later, she returned for vengeance. That didn't quite work out either.\n" +
            "Then she returned again and made a great friend, and later helped save Equestria from the changeling menace, proving how all those neighsayers were foolish and wrong for doubting Trixie.\n" +
            "Now, Trixie has returned to Ponyville once again. What adventures await her this time?";


}
