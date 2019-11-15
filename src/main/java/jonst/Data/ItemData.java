package jonst.Data;

import java.util.Dictionary;

public class ItemData {

    private static String[][] itemDescriptions = new String[][]

 {
        { "a rock", "It's a rock. You saw enough of them during your time on the rock farm." }
          , { "a wooden crate", "A wooden crate for putting things in." }
          , { "a bundle of fireworks", "A bunch of magical fireworks, crafted by yours truly. Handle with care!" }
          , { "Trixie's hat", "Your hat. Anypony who sees it will know that they're dealing with a magnificent magician. Good for keeping the sun out of your eyes, too." }
          , { "Trixie's cape", "Your cape. Anypony who sees it will know that they're dealing with a magnificent magician. Keeps you warm during travels, too." }
          , {"a juicy red apple", "Fragrant and delicious, just the thing for a quick snack."}
          , {"a heavy plow", "A piece of farming equipment used by earth ponies. You've done enough farming in your life already."}
          , {"a spool of thread", "A spool of blue thread. Rarity probably won't miss it."}
          , {"an unfinished dress", "This outfit is clearly far from complete, unless Rarity's fashion standards have changed very dramatically in the last week."}
          , {"a bucket of ice cream", "A bucket of chocolate-chip ice cream. You're certain you've earned yourself a treat today."}

    };
    private static String[][] itemShortNames = new String[][]

    {
        { "a bundle of fireworks", "fireworks" }
            , { "a juicy red apple", "apple" }
            , { "a heavy plow", "plow" }
            , { "a spool of thread", "thread" }
           ,  { "an unfinished dress", "dress" }
           ,  { "a bucket of ice cream", "ice cream" }
    };



    public static boolean hasItemDescription(String key){

        for (String[] line: itemDescriptions) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String getItemDescription(String key){
        for (String[] line: itemDescriptions) {
            if(line[0].equals(key)) return line[1];
        }
        return "Description not found";
    }

    public static boolean hasItemShortName(String key){

        for (String[] line: itemShortNames) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String getItemShortName(String key){
        for (String[] line: itemShortNames) {
            if(line[0].equals(key)) return line[1];
        }
        return "Description not found";
    }

}
