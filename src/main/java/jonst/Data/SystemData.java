package jonst.Data;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

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


    public static String getReply(String line) {
        System.out.print(line);
        return inputReader.nextLine();
    }

    public static int getNumericalReply(String line, int maxNum){

        while(true){
            System.out.print(line);
            String response = inputReader.nextLine();
            int output;
            try{
                output = Integer.parseInt(response);
            } catch (Exception e) {
                System.out.println("That's not a legitimate choice.");
                continue;
            }

            if(output <= maxNum && output >= 0){
                return output;
            } else {
                System.out.println("That's not a legitimate choice.");
            }
        }
    }



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
        return new ArrayList<>(Arrays.asList(
                "save",     "load",     "quicksave",    "quickload",    "look at",  "look around",  "look",
                "go to",    "go",       "enter",        "exit",         "use",      "pick up",      "talk to",
                "talk",     "quit",     "drop",         "ask",          "read",     "chat with",    "exits",
                "teleport", "teleport to",              "help",         "put",      "place",        "harvest",
                "commands", "nouns",    "inventory",    "give",         "take",     "retrieve",     "transform",
                "hug",      "wear",     "remove",       "put on",       "take off", "create",       "activate",
                "open",     "close",    "eat",          "board",        "ride",     "follow",       "follow me",
                "stop follow",          "stop following me",            "cast",
                "shop",     "barter",   "buy",          "sell",

                "attack",   "brandish"    //Not implemented
        ));
    }

    public static ArrayList getDefaultNouns() {
        return new ArrayList<>(Arrays.asList(
                "anyitem","anycreature","anylocation","anygenericobject"
        ));
    }

    public static ArrayList getLegitimateConjunctions() {
        return new ArrayList<>(Arrays.asList(
                "to",   "about",    "behind",   "at",   "under",    "in front of",
                "on",   "in",       "for",      "from", "inside",   "into",         "with"
        ));
    }

    public static ArrayList getLegitimateSpells() {
        return new ArrayList<>(Arrays.asList(
                "fireball",     "teleport",     "bodyswap", "telekinesis",      "cloudwalk",
                "disintegrate", "fireworks",    "wings",    "want it need it",  "sleep",
                "energize",     "transform",    "create"
        ));
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

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

}
