package jonst.Data;


public class DialogData {


     static String[][] casualDialog = new String[][]

 {

        { "Twilight Sparkle", "She looks at you with a frown. \"Oh... hello, Trixie.\"", "\"Do you want something?\"", "\"Please don't bother me while I'm busy.\"", "\"I'm sure Starlight is around here somewhere.\"" }
           , { "Rarity", "\"Hello, Trixie.\"", "\"Yes, dear?\"", "\"Please. I'm 'in the zone' at the moment.\"", "\"Do you want something? A new outfit?\"" }
           , { "Applejack", "\"Hey there, Trixie.\"", "\"You want somethin'?\"", "\"For the love o' Celestia, don't cause another mess.\"", "She snorts. \"Unlike some ponies, I'm too darn busy to stand around gawkin'.\"" }
           , { "Pinkie Pie", "\"Hi Trixie!\"", "\"Yeah? You want something? You want me to throw you a party?\"", "\"Hey, what's your favorite flavor? Blueberry? Chocolate? Chili pepper? Nooo reason...\"", "\"I think the programmer is trying to come up with new things for me to say.\"" }
           , { "Rainbow Dash", "\"Oh, hey.\"", "\"When did you get here?\"", "\"Hey, can you do fireworks on demand?\"", "\"Zzzzz... uh? Huh?\"" }
           , { "Fluttershy", "\"Umm...\"", "Fluttershy looks away bashfully.", "\"Um. Yes? D-do you want something?\"", "\"I... um... nevermind.\" *squeak*" }
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
