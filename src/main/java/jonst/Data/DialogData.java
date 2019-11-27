package jonst.Data;


import java.util.ArrayList;
import java.util.HashMap;

public class DialogData {

    private HashMap<String, ArrayList<String>> casualDialog = new HashMap<>();

    public DialogData(){


        casualDialog.put("Twilight Sparkle", new ArrayList<String>() {{
            add("She looks at you with a frown. \"Oh... hello, Trixie.\"");
            add("\"Do you want something?\"");
            add("\"Please don't bother me while I'm busy.\"");
            add("\"I'm sure Starlight is around here somewhere.\"");
            //add("");
        }});

        casualDialog.put("Rarity", new ArrayList<String>() {{
            add("\"Hello, Trixie.\"");
            add("\"Yes, dear?\"");
            add("\"Please. I'm 'in the zone' at the moment.\"");
            add("\"Do you want something? A new outfit?\"");
            //add();
        }});

        casualDialog.put("Applejack", new ArrayList<String>() {{
            add("\"Hey there, Trixie.\"");
            add("\"You want somethin'?\"");
            add("\"For the love o' Celestia, don't cause another mess.\"");
            add("She snorts. \"Unlike some ponies, I'm too darn busy to stand around gawkin'.\"");
            //add();
        }});

        casualDialog.put("Pinkie Pie", new ArrayList<String>() {{
            add("\"Hi Trixie!\"");
            add("\"Yeah? You want something? You want me to throw you a party?\"");
            add("\"Hey, what's your favorite flavor? Blueberry? Chocolate? Chili pepper? Nooo reason...\"");
            add("\"I think the programmer is trying to come up with new things for me to say.\"");
            //add();
        }});

        casualDialog.put("Rainbow Dash", new ArrayList<String>() {{
            add("\"Oh, hey.\"");
            add("\"When did you get here?\"");
            add("\"Hey, can you do fireworks on demand?\"");
            add("\"Zzzzz... uh? Huh?\"");
            //add();
        }});

        casualDialog.put("Fluttershy", new ArrayList<String>() {{
            add("\"Umm...\"");
            add("Fluttershy looks away bashfully.");
            add("\"Um. Yes? D-do you want something?\"");
            add("\"I... um... nevermind.\" *squeak*");
            //add();
        }});

        casualDialog.put(, new ArrayList<String>() {{
            add();
            add();
            add();
            add();
            //add();
        }});

        casualDialog.put(, new ArrayList<String>() {{
            add();
            add();
            add();
            add();
            //add();
        }});


    }

     static String[][] casualDialog = new String[][]



 {

           , { "Spike", "\"What do you want?\"", "\"Please tell me you're not going to break stuff again.\"", "\"Oh... I think I've got stuff to do. Elsewhere.\"", "\"Starlight's in the library, I think. Go bother her.\"" }
           , { "Trixie",  "Talking to yourself is usually pointless.", "Much as you're a wonderful conversationalist, there's not much point.", "You'd rather not. Other ponies think you're weird enough as it is." }
           , { "Starlight Glimmer", "\"Oh, hi, Trixie!\"", "\"So what have you been up to lately? Anything exciting?\"", "\"Wanna hang out later? We could work on magic or something. You choose.\"", "\"You haven't been bothering Twilight, have you?\"" }
           , { "Maud Pie", "\"Trixie. Hello.\"", "\"Do you want something?\"", "\"...\"", "\"I seem to have misplaced my chisel...\"" }
           , { "Placeholder", "\"\"", "\"\"", "\"\"", "\"\"" }
        };


    public static boolean hasCasualDialog(String key){

        for (String[] line: casualDialog) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String[] getCasualDialog(String key){
        String[] returnData = new String[casualDialog.length-1];

        for (String[] line: casualDialog) {
            if(line[0].equals(key)) {
                System.arraycopy(casualDialog, 1, returnData, 0, casualDialog.length-1);  //Always ignore first entry, as it is the "key".
            }
        }
        return returnData;
    }

    public static String getRandomCasualDialog(String name){
        for (String[] line: casualDialog) {
            if(line[0].equals(name)) {

                int pick = (int) Math.ceil(Math.random() * line.length-1);
                return line[pick];
            }
        }
        return "You shouldn't get this response. Check DialogData.";

    }
}
