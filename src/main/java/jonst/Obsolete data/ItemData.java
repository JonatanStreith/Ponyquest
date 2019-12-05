//package jonst.Data;
//
//import java.util.Dictionary;
//import java.util.HashMap;
//
//public class ItemData {
//
//    private HashMap<String, String> itemDescriptions = new HashMap<>();
//    private HashMap<String, String> itemShortNames = new HashMap<>();
//
//    public ItemData() {
//
//        itemDescriptions.put("a rock", "It's a rock. You saw enough of them during your time on the rock farm.");
//        itemDescriptions.put("a wooden crate", "A wooden crate for putting things in.");
//        itemDescriptions.put("a bundle of fireworks", "A bunch of magical fireworks, crafted by yours truly. Handle with care!");
//        itemDescriptions.put("Trixie's hat", "Your hat. Anypony who sees it will know that they're dealing with a magnificent magician. Good for keeping the sun out of your eyes, too.");
//        itemDescriptions.put("Trixie's cape", "Your cape. Anypony who sees it will know that they're dealing with a magnificent magician. Keeps you warm during travels, too.");
//        itemDescriptions.put("a juicy red apple", "Fragrant and delicious, just the thing for a quick snack.");
//        itemDescriptions.put("a heavy plow", "A piece of farming equipment used by earth ponies. You've done enough farming in your life already.");
//        itemDescriptions.put("a spool of thread", "A spool of blue thread. Rarity probably won't miss it.");
//        itemDescriptions.put("an unfinished dress", "This outfit is clearly far from complete, unless Rarity's fashion standards have changed very dramatically in the last week.");
//        itemDescriptions.put("a bucket of ice cream", "A bucket of chocolate-chip ice cream. You're certain you've earned yourself a treat today.");
////    itemDescriptions.put();
////    itemDescriptions.put();
//
//        itemShortNames.put("a bundle of fireworks", "fireworks");
//        itemShortNames.put("a juicy red apple", "apple");
//        itemShortNames.put("a heavy plow", "plow");
//        itemShortNames.put("a spool of thread", "thread");
//        itemShortNames.put("an unfinished dress", "dress");
//        itemShortNames.put("a bucket of ice cream", "ice cream");
////    itemShortNames.put();
////    itemShortNames.put();
////    itemShortNames.put();
////    itemShortNames.put();
//    }
//
//
//    public boolean hasItemDescription(String key) {
//        if (itemDescriptions.containsKey(key))
//            return true;
//        else
//            return false;
//    }
//
//
//    public String getItemDescription(String key) {
//        return itemDescriptions.get(key);
//    }
//
//    public boolean hasItemShortName(String key) {
//        if (itemShortNames.containsKey(key))
//            return true;
//        else
//            return false;
//    }
//
//
//    public String getItemShortName(String key) {
//        return itemShortNames.get(key);
//    }
//
//}
