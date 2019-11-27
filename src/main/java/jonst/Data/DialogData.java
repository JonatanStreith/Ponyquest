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

        casualDialog.put("Spike", new ArrayList<String>() {{
            add("\"What do you want?\"");
            add("\"Please tell me you're not going to break stuff again.\"");
            add("\"Oh... I think I've got stuff to do. Elsewhere.\"");
            add("\"Starlight's in the library, I think. Go bother her.\"");
            //add();
        }});

        casualDialog.put("Trixie", new ArrayList<String>() {{
            add("Talking to yourself is usually pointless.");
            add("Much as you're a wonderful conversationalist, there's not much point.");
            add("You'd rather not. Other ponies think you're weird enough as it is.");
            //add();
            //add();
        }});

        casualDialog.put("Starlight Glimmer", new ArrayList<String>() {{
            add("\"Oh, hi, Trixie!\"");
            add("\"So what have you been up to lately? Anything exciting?\"");
            add("\"Wanna hang out later? We could work on magic or something. You choose.\"");
            add("\"You haven't been bothering Twilight, have you?\"");
            //add();
        }});

        casualDialog.put("Maud Pie", new ArrayList<String>() {{
            add("\"Trixie. Hello.\"");
            add("\"Do you want something?\"");
            add("\"...\"");
            add("\"I seem to have misplaced my chisel...\"");
            //add();
        }});


    }




    public boolean hasCasualDialog(String key){
if(casualDialog.containsKey(key))
    return true;
else
    return false;
    }


    public ArrayList<String> getCasualDialog(String key){
        return casualDialog.get(key);
    }

    public String getRandomCasualDialog(String key){

        ArrayList<String> choices = casualDialog.get(key);

        return choices.get((int) Math.ceil(Math.random() * choices.size()));
    }
}
