package jonst.Data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationData {


    private static String[][] locationDescriptions = new String[][]

        {
        { "Sugarcube Corner", "Sugarcube Corner is Ponyville's premier confectionery store. You don't know why it's built like a giant gingerbread house." }
          , { "Sugarcube Corner interior", "The shop is busy with ponies enjoying baked goods." }
          , { "Sugarcube Corner kitchen", "This is where the magic happens! And by magic, you mean baking and not actually magic. Why are you even here?" }
          , { "Pinkie Pie's room", "This is Pinkie Pie's room. It's a complete mess." }
          , { "Party Cave", "This underground sanctum is where Pinkie Pie stores most of her party supplies, and also where she maintains surveillance on everypony's birthdays and other anniversaries. EVERYPONY'S. The implications are terrifying." }


          , { "Carousel Boutique", "The Carousel Boutique is a bright and airy shop full of outfits of all kinds, all hoof-made by its proprietor, Rarity. Of course, already being dressed to perfection, you have no need of her services." }
          , { "Carousel Boutique interior", "The shop is full of ponyquins and displays showing Rarity's latest creations. It's surprisingly roomy." }


          , { "Sweet Apple Acres", "Sweet Apple Acres is a beautiful pastoral farmstead, run by the Apple family. Unsurprisingly, they grow apples." }
          , { "Farmhouse interior", "This rustic home is where Applejack and her family lives. It's cozy, quaint and so stereotypical you almost gag." }

          , { "Castle of Friendship", "The Castle of Friendship is a majestic crystal structure that looks incredibly out of place with the rest of Ponyville. It's a nice place to hang out, though." }
          , { "Castle main hall", "This is the main hall, which leads to every other room in the castle. Try not to get lost again." }
          , { "Cutie Map room", "The room is dominated by the grand Cutie Map, and the six... seven thrones from which Twilight Sparkle and her friends do friendship stuff, or something." }
          , { "Castle library","The library holds copies of every book in Equestria that Twilight Sparkle could get her hooves on. If you need to look something up, you suppose you could come here." }
          , { "Castle kitchen","The castle kitchen is a great place to stock up on snacks. As long as Spike doesn't catch you." }
          , { "Starlight's room","Starlight's room. You hang out here sometimes." }


    };

    public static boolean hasLocationDescription(String key){

        for (String[] line: locationDescriptions) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }

    public static String getLocationDescription(String key){
        for (String[] line: locationDescriptions) {
            if(line[0].equals(key)) return line[1];
        }
        return "Description not found";
    }




    private static String[][] locationShortNames = new String[][]
    {

        { "Sugarcube Corner", "bakery" }
            , { "Sugarcube Corner interior", "bakery interior" }
            , { "Sugarcube Corner kitchen", "bakery kitchen" }
            , { "Pinkie Pie's room", "Pinkie room" }
            , { "Party Cave", "partycave" }

            , { "Carousel Boutique", "boutique" }
            , { "Carousel Boutique interior", "boutique interior" }

            , { "Sweet Apple Acres", "Acres" }
            , { "Farmhouse interior", "Acres interior" }

            , { "Castle of Friendship", "castle" }


            , { "Castle main hall", "main hall" }
            , { "Cutie Map room", "Map room" }
            , { "Castle library", "library" }
            , { "Castle kitchen", "kitchen" }
            , { "Starlight's room", "starlight room" }



    };

    public static boolean hasLocationShortName(String key){

        for (String[] line: locationShortNames) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }


    public static String getLocationShortName(String key){
        for (String[] line: locationShortNames) {
            if(line[0].equals(key)) return line[1];
        }
        return "Short name not found";
    }

    private static String[][] legitimateExits = new String[][]
    {
        //Main cells (outside)
        { "Sugarcube Corner", "Castle of Friendship", "Carousel Boutique", "Sweet Apple Acres", "Sugarcube Corner interior" }
           , { "Carousel Boutique", "Castle of Friendship", "Sugarcube Corner", "Sweet Apple Acres", "Carousel Boutique interior" }
           , { "Sweet Apple Acres", "Castle of Friendship", "Carousel Boutique", "Sugarcube Corner", "Farmhouse interior" }
           , { "Castle of Friendship", "Sugarcube Corner", "Carousel Boutique", "Sweet Apple Acres", "Castle main hall" }

        //Sugercube Corner
           , { "Sugarcube Corner interior", "Sugarcube Corner", "Sugarcube Corner kitchen", "Pinkie Pie's room", "Party Cave" }
           , { "Sugarcube Corner kitchen", "Sugarcube Corner interior" }
           , { "Pinkie Pie's room", "Sugarcube Corner interior" }
           , { "Party Cave", "Sugarcube Corner interior" }


        //Carousel Boutique
           , { "Carousel Boutique interior", "Carousel Boutique" }


        //Sweet Apple Acres
           , { "Farmhouse interior", "Sweet Apple Acres" }

        //Castle of Friendship
           , { "Castle main hall", "Castle of Friendship", "Cutie Map room", "Castle library", "Castle kitchen", "Starlight's room" }
           , { "Cutie Map room", "Castle main hall" }
           , { "Castle library", "Castle main hall" }
           , { "Castle kitchen", "Castle main hall"  }
           , { "Starlight's room", "Castle main hall"  }
    };

    public static boolean hasLegitimateExits(String key){

        for (String[] line: legitimateExits) {
            if(line[0].equals(key)) return true;
        }
        return false;
    }

    public static List<String> getLegitimateExits(String key){

        List<String> returnList = new ArrayList<String>();

        //String[] returnData = new String[legitimateExits.length-1];

        for (String[] line: legitimateExits) {
            if(line[0].equals(key)) {
                //System.arraycopy(legitimateExits, 1, returnData, 0, legitimateExits.length-1);  //Always ignore first entry, as it is the "key".
                 //return Arrays.copyOfRange(line, 1, line.length-1);
                return Arrays.asList(Arrays.copyOfRange(line, 1, line.length-1));
            }
        }
        return returnList;
    }

}
