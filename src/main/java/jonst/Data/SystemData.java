package jonst.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SystemData {

    private static Scanner inputReader = new Scanner(System.in);

    private static String gamepath = "src/main/java/jonst";
    private static String testpath = "src/main/java/jonst/Assets/Test/";
    private static String savepath = "src/main/java/jonst/Assets/Saves/";
    private static String defaultWorld = "src/main/java/jonst/Assets/DefaultWorld";
    private static String quickSave = "src/main/java/jonst/Assets/Saves/QuickSave";
    private static String protagonist = "Trixie";

    private static String introBlurb = "Once upon a time, in the magical land of Equestria...\n" +
            "A great and powerful magician went to Ponyville to awe and impress. That didn't end very well. Later, she returned for vengeance. That didn't quite work out either.\n" +
            "Then she returned again and made a great friend, and later helped save Equestria from the changeling menace, proving how all those neighsayers were foolish and wrong for doubting Trixie.\n" +
            "Now, Trixie has returned to Ponyville once again. What adventures await her this time?";

    public static String getGamepath() {
        return gamepath;
    }

    public static String getTestpath() {
        return testpath;
    }

    public static String getSavepath() {
        return savepath;
    }

    public static String getDefaultWorld() {
        return defaultWorld;
    }

    public static String getQuickSave() {
        return quickSave;
    }

    public static String getIntroBlurb() {
        return introBlurb;
    }

    public static String getProtagonist() {
        return protagonist;
    }

    public static ArrayList getLegitimateCommands() {
        return new ArrayList<String>() {{
            add("save");        //Works!
            add("load");        //Works!
            add("quicksave");   //Works!
            add("quickload");   //Works!
            add("look at");     //Works!
            add("look around"); //Works!
            add("look");        //Works!
            add("go to");       //Works!
            add("go");          //Works!
            add("enter");
            add("exit");
            add("use");
            add("pick up");     //Works!
            add("take");        //Works!
            add("talk to");     //Works!
            add("talk");        //Works!
            add("quit");        //Works!
            add("drop");        //Works!
            add("brandish");
            add("ask");
            add("cast");
            add("exits");       //Works!
            add("teleport to"); //Works!
            add("teleport");    //Works!
            add("help");        //Works!
            add("commands");    //Works!
            add("nouns");       //Works!
            add("inventory");   //Works!
        }};
    }

    public static ArrayList getLegitimateConjunctions() {
        return new ArrayList<String>() {{
            add("to");
            add("about");
            add("behind");
            add("at");
            add("under");
            add("in front of");
            add("on");
            add("in");
            add("for");
        }};
    }

    public static String getReply(String line) {
        System.out.print(line);
        return inputReader.nextLine();
    }


    public static Map<String, String> getTopicParseList(){
        return new HashMap(){{
            //First entry is a word that could be inputted; second is a True Topic.
            put("Twilight Sparkle", "Twilight Sparkle");
            put("Twilight", "Twilight Sparkle");
            put("Rarity", "Rarity");
            put("Fluttershy", "Fluttershy");
            put("Rainbow Dash", "Rainbow Dash");
            put("Rainbow", "Rainbow Dash");
            put("Applejack", "Applejack");
            put("Pinkie Pie", "Pinkie Pie");
            put("Pinkie", "Pinkie Pie");
            put("alicorn amulet", "alicorn amulet");
            put("amulet", "alicorn amulet");
            put("evil amulet", "alicorn amulet");
            put("magic","magic");
            put("magics", "magic");
            put("magik", "magic");
            put("teleportation", "teleportation");
            put("teleport", "teleportation");
            put("transformation", "transformation");
            put("transform", "transformation");
            put("discord", "discord");
            put("draconequus", "discord");
            put("cupcakes", "cupcakes");
            put("cupcake", "cupcakes");
            put("pies", "pies");
            put("pie", "pies");
            put("self", "self");
            put("herself", "self");
            put("himself", "self");

        }};


    }

}
