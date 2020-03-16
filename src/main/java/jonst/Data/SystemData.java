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
            add("enter");       //Works!
            add("exit");        //Works!
            add("use");         //Works!
            add("pick up");     //Works
            add("talk to");     //Works!
            add("talk");        //Works!
            add("quit");        //Works!
            add("drop");        //Works!
            add("ask");         //Works
            add("read");        //Works

            add("chat with");   //Works



            add("exits");       //Works!
            add("teleport to"); //Works!
            add("teleport");    //Works!
            add("help");        //Works!
            add("commands");    //Works!
            add("nouns");       //Works!
            add("inventory");   //Works!

            add("give");        //Works
            add("take");        //Works
            add("retrieve");    //Works
            add("put");         //Works
            add("place");       //Works
            add("hug");         //Works

            add("wear");        //Works
            add("remove");      //Works
            add("put on");      //Works
            add("take off");    //Works

            add("create");      //Works!


            add("transform");   //Works!


            add("harvest");     //Broken

            add("open");        //Works!
            add("close");       //Works!
            add("activate");    //Works!

            add("eat");         //Works!

            add("board");       //Works!
            add("ride");        //Works!


            add("follow");      //Works!
            add("follow me");   //Works!
            add("stop follow"); //Works!
            add("stop following me");   //Works!

            add("cast");        //Test spells for specifics.

            add("shop");
            add("barter");
            add("buy");
            add("sell");

            add("attack");      //Not implemented
            add("brandish");    //Not implemented



        }};
    }

    public static ArrayList getDefaultNouns() {
        return new ArrayList<String>() {{
            add("anyitem");
            add("anycreature");
            add("anylocation");
            add("anygenericobject");
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
            add("from");
            add("inside");
            add("into");
            add("with");
        }};
    }


    public static ArrayList getLegitimateSpells() {
        return new ArrayList<String>() {{
            add("fireball");
            add("teleport");
            add("bodyswap");
            add("telekinesis");
            add("cloudwalk");
            add("disintegrate");
            add("fireworks");
            add("wings");
            add("want it need it");
            add("sleep");
            add("energize");
            add("transform");
            add("create");
        }};
    }

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
