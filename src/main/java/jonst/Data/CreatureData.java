package jonst.Data;

import java.util.Dictionary;

public class CreatureData {

    private static String[][] creatureDescriptions = new String[][]

    {
        { "Twilight Sparkle", "You're mostly over your issues with Twilight Sparkle, the princess of friendship and books and obnoxious... mostly." }
          , { "Celestia", "The Princess of the Sun, diarch of Equestria. It's one of your dreams to one day get to perform at her court."}
          , { "Luna", "The Princess of the Moon, diarch of Equestria. You've always felt a certain kinship with her, and her plight. She keeps the night safe."}
          , { "Discord", "The grand Chaos Noodle himself. Weird, obnoxious, egocentric, annoying... okay, sometimes he's kind of fun, but don't let him hear that."}

          , { "Rarity", "Ponyville's resident fashion pony. Really melodramatic and self-obsessed, in your opinion." }
          , { "Trixie", "You are the Great and Powerful Trixie! You are Equestria's greatest stage magician, a unicorn of peerless beauty and magical ability, a brave and fearless hero, and also very humble." }
          , { "Starlight Glimmer", "Starlight Glimmer is your best friend. She's talented, understanding, fun... and she knows what it's like to have a troubled past. She's the best."}

          , { "Pinkie Pie", "Ponyville's premier party pony. A total weirdo. Frankly, she unnerves you." }
          , { "Applejack", "A down-to-earth apple farmer and general rustic country bumpkin. Boring, but fairly nice." }
          , { "Maud Pie", "Maud Pie is... strange.  In some ways she's even weirder than Pinkie Pie. Are they really sisters? At least she doesn't judge you."}

          , { "Rainbow Dash", "One of the most arrogant and loudmouthed ponies you've ever known. Nice to have on your side, though." }
          , { "Fluttershy", "A meek and shy pegasus who spends most of her time keeping animals. Also has some thing with Discord? She puzzles you." }

          , { "Spike", "Twilight Sparkle's runty dragon assistant. He's kind of annoying." }

          , { "Placeholder4", ""}

    };


    private static String[][] creatureShortNames = new String[][]
 {

        { "Twilight Sparkle", "Twilight" }
            , { "Celestia", "Princess Celestia" }
            , { "Luna", "Princess Luna" }
            , { "Discord", "Discord" }


           ,  { "Rarity", "Rarara" }
           ,  { "Trixie", "yourself" }
           ,  { "Starlight Glimmer", "Starlight" }

           ,  { "Pinkie Pie", "Pinkie" }
           ,  { "Applejack", "Jackie" }
           ,  { "Maud Pie", "Maud"  }

           ,  { "Rainbow Dash", "Rainbow" }
           ,  { "Fluttershy", "Flutters" }

           ,  { "Spike", "Spike the Dragon" }

    };


    public static boolean hasCreatureDescription(String key){

        for (String[] line: creatureDescriptions) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String getCreatureDescription(String key){
        for (String[] line: creatureDescriptions) {
            if(line[0].equals(key)) return line[1];
        }
        return "Description not found";
    }



    public static boolean hasCreatureShortName(String key){

        for (String[] line: creatureShortNames) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String getCreatureShortName(String key){
        for (String[] line: creatureShortNames) {
            if(line[0].equals(key)) return line[1];
        }
        return "Short name not found";
    }

}
