//package jonst.Data;
//
//import java.util.Dictionary;
//import java.util.HashMap;
//
//public class CreatureData {
//
//
//    private HashMap<String, String> creatureDescriptions = new HashMap<>();
//
//    private HashMap<String, String> creatureShortNames = new HashMap<>();
//
//    public CreatureData() {
//
//        creatureDescriptions.put("Twilight Sparkle", "You're mostly over your issues with Twilight Sparkle, the princess of friendship and books and obnoxious... mostly.");
//        creatureDescriptions.put("Celestia", "The Princess of the Sun, diarch of Equestria. It's one of your dreams to one day get to perform at her court.");
//        creatureDescriptions.put("Luna", "The Princess of the Moon, diarch of Equestria. You've always felt a certain kinship with her, and her plight. She keeps the night safe.");
//        creatureDescriptions.put("Discord", "The grand Chaos Noodle himself. Weird, obnoxious, egocentric, annoying... okay, sometimes he's kind of fun, but don't let him hear that.");
//        creatureDescriptions.put("Rarity", "Ponyville's resident fashion pony. Really melodramatic and self-obsessed, in your opinion.");
//        creatureDescriptions.put("Trixie", "You are the Great and Powerful Trixie! You are Equestria's greatest stage magician, a unicorn of peerless beauty and magical ability, a brave and fearless hero, and also very humble.");
//        creatureDescriptions.put("Starlight Glimmer", "Starlight Glimmer is your best friend. She's talented, understanding, fun... and she knows what it's like to have a troubled past. She's the best.");
//
//        creatureDescriptions.put("Pinkie Pie", "Ponyville's premier party pony. A total weirdo. Frankly, she unnerves you.");
//        creatureDescriptions.put("Applejack", "A down-to-earth apple farmer and general rustic country bumpkin. Boring, but fairly nice.");
//        creatureDescriptions.put("Maud Pie", "Maud Pie is... strange.  In some ways she's even weirder than Pinkie Pie. Are they really sisters? At least she doesn't judge you.");
//        creatureDescriptions.put("Rainbow Dash", "One of the most arrogant and loudmouthed ponies you've ever known. Nice to have on your side, though.");
//        creatureDescriptions.put("Fluttershy", "A meek and shy pegasus who spends most of her time keeping animals. Also has some thing with Discord? She puzzles you.");
//        creatureDescriptions.put("Spike", "Twilight Sparkle's runty dragon assistant. He's kind of annoying.");
//        //creatureDescriptions.put();
//
//        creatureShortNames.put("Twilight Sparkle", "Twilight");
//        creatureShortNames.put("Celestia", "Princess Celestia");
//        creatureShortNames.put("Luna", "Princess Luna");
//        creatureShortNames.put("Discord", "Discord");
//        creatureShortNames.put("Rarity", "Rarara");
//        creatureShortNames.put("Trixie", "yourself");
//        creatureShortNames.put("Starlight Glimmer", "Starlight");
//        creatureShortNames.put("Pinkie Pie", "Pinkie");
//        creatureShortNames.put("Applejack", "Jackie");
//        creatureShortNames.put("Maud Pie", "Maud");
//        creatureShortNames.put("Rainbow Dash", "Rainbow");
//        creatureShortNames.put("Fluttershy", "Flutters");
//        creatureShortNames.put("Spike", "Spike the Dragon");
//        //creatureShortNames.put();
//
//    }
//
//
//    public boolean hasCreatureDescription(String key) {
//        if (creatureDescriptions.containsKey(key))
//            return true;
//        else
//            return false;
//    }
//
//    public String getCreatureDescription(String key) {
//        if(!hasCreatureDescription(key))
//        return creatureDescriptions.get(key);
//        else
//            return "Description missing.";
//    }
//
//    public boolean hasCreatureShortName(String key) {
//        if (creatureShortNames.containsKey(key))
//            return true;
//        else
//            return false;
//    }
//
//    public String getCreatureShortName(String key) {
//        if (!hasCreatureShortName(key))
//            return creatureShortNames.get(key);
//        else
//            return key;
//    }
//
//
//}
