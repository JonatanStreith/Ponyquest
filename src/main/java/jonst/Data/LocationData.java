package jonst.Data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LocationData {

    private HashMap<String, String> locationDescriptions = new HashMap<>();
    private HashMap<String, String> locationShortNames = new HashMap<>();

    private HashMap<String, ArrayList<String>> legitimateExits = new HashMap<>();



    public LocationData() {

        //Put these in separate methods later. Better, generate from a file!

        locationDescriptions.put("Sugarcube Corner", "Sugarcube Corner is Ponyville's premier confectionery store. You don't know why it's built like a giant gingerbread house.");
        locationDescriptions.put("Sugarcube Corner interior", "The shop is busy with ponies enjoying baked goods.");
        locationDescriptions.put("Sugarcube Corner kitchen", "This is where the magic happens! And by magic, you mean baking and not actually magic. Why are you even here?");
        locationDescriptions.put("Pinkie Pie's room", "This is Pinkie Pie's room. It's a complete mess.");
        locationDescriptions.put("Party Cave", "This underground sanctum is where Pinkie Pie stores most of her party supplies, and also where she maintains surveillance on everypony's birthdays and other anniversaries. EVERYPONY'S. The implications are terrifying.");
        locationDescriptions.put("Carousel Boutique", "The Carousel Boutique is a bright and airy shop full of outfits of all kinds, all hoof-made by its proprietor, Rarity. Of course, already being dressed to perfection, you have no need of her services.");
        locationDescriptions.put("Carousel Boutique interior", "The shop is full of ponyquins and displays showing Rarity's latest creations. It's surprisingly roomy.");
        locationDescriptions.put("Sweet Apple Acres", "Sweet Apple Acres is a beautiful pastoral farmstead, run by the Apple family. Unsurprisingly, they grow apples.");
        locationDescriptions.put("Farmhouse interior", "This rustic home is where Applejack and her family lives. It's cozy, quaint and so stereotypical you almost gag.");
        locationDescriptions.put("Castle of Friendship", "The Castle of Friendship is a majestic crystal structure that looks incredibly out of place with the rest of Ponyville. It's a nice place to hang out, though.");
        locationDescriptions.put("Castle main hall", "This is the main hall, which leads to every other room in the castle. Try not to get lost again.");
        locationDescriptions.put("Cutie Map room", "The room is dominated by the grand Cutie Map, and the six... seven thrones from which Twilight Sparkle and her friends do friendship stuff, or something.");
        locationDescriptions.put("Castle library", "The library holds copies of every book in Equestria that Twilight Sparkle could get her hooves on. If you need to look something up, you suppose you could come here.");
        locationDescriptions.put("Castle kitchen", "The castle kitchen is a great place to stock up on snacks. As long as Spike doesn't catch you.");
        locationDescriptions.put("Starlight's room", "Starlight's room. You hang out here sometimes.");
        //locationDescriptions.put();

        locationShortNames.put("Sugarcube Corner", "bakery");
        locationShortNames.put("Sugarcube Corner interior", "bakery interior");
        locationShortNames.put("Sugarcube Corner kitchen", "bakery kitchen");
        locationShortNames.put("Pinkie Pie's room", "Pinkie room");
        locationShortNames.put("Party Cave", "partycave");
        locationShortNames.put("Carousel Boutique", "boutique");
        locationShortNames.put("Carousel Boutique interior", "boutique interior");
        locationShortNames.put("Sweet Apple Acres", "Acres");
        locationShortNames.put("Farmhouse interior", "Acres interior");
        locationShortNames.put("Castle of Friendship", "castle");
        locationShortNames.put("Castle main hall", "main hall");
        locationShortNames.put("Cutie Map room", "Map room");
        locationShortNames.put("Castle library", "library");
        locationShortNames.put("Castle kitchen", "kitchen");
        locationShortNames.put("Starlight's room", "starlight room");
        //locationShortNames.put();


        legitimateExits.put("Sugarcube Corner", new ArrayList<String>() {{
            add("Castle of Friendship");
            add("Carousel Boutique");
            add("Sweet Apple Acres");
            add("Sugarcube Corner interior");
        }});
        legitimateExits.put("Carousel Boutique", new ArrayList<String>() {{
            add("Castle of Friendship");
            add("Sugarcube Corner");
            add("Sweet Apple Acres");
            add("Carousel Boutique interior");
        }});
        legitimateExits.put("Sweet Apple Acres", new ArrayList<String>() {{
            add("Castle of Friendship");
            add("Carousel Boutique");
            add("Sugarcube Corner");
            add("Farmhouse interior");
        }});
        legitimateExits.put("Castle of Friendship", new ArrayList<String>() {{
            add("Sugarcube Corner");
            add("Carousel Boutique");
            add("Sweet Apple Acres");
            add("Castle main hall");
        }});

        legitimateExits.put("Sugarcube Corner interior", new ArrayList<String>() {{
            add("Sugarcube Corner");
            add("Sugarcube Corner kitchen");
            add("Pinkie Pie's room");
            add("Party Cave");
        }});
        legitimateExits.put("Sugarcube Corner kitchen", new ArrayList<String>() {{
            add("Sugarcube Corner interior");
        }});
        legitimateExits.put("Pinkie Pie's room", new ArrayList<String>() {{
            add("Sugarcube Corner interior");
        }});
        legitimateExits.put("Party Cave", new ArrayList<String>() {{
            add("Sugarcube Corner interior");
        }});

        legitimateExits.put("Carousel Boutique interior", new ArrayList<String>() {{
            add("Carousel Boutique");
        }});
        legitimateExits.put("Farmhouse interior", new ArrayList<String>() {{
            add("Sweet Apple Acres");
        }});
        legitimateExits.put("Castle main hall", new ArrayList<String>() {{
            add("Castle of Friendship");
            add("Cutie Map room");
            add("Castle library");
            add("Castle kitchen");
            add("Starlight's room");
        }});
        legitimateExits.put("Cutie Map room", new ArrayList<String>() {{
            add("Castle main hall");
        }});
        legitimateExits.put("Castle library", new ArrayList<String>() {{
            add("Castle main hall");
        }});
        legitimateExits.put("Castle kitchen", new ArrayList<String>() {{
            add("Castle main hall");
        }});
        legitimateExits.put("Starlight's room", new ArrayList<String>() {{
            add("Castle main hall");
        }});


    }


    public boolean hasLocationDescription(String key) {
        if (locationDescriptions.containsKey(key))
            return true;
        else
            return false;
    }

    public String getLocationDescription(String key) {
        return locationDescriptions.get(key);
    }


    public boolean hasLocationShortName(String key) {
        if (locationShortNames.containsKey(key))
            return true;
        else
            return false;
    }


    public String getLocationShortName(String key) {
        return locationShortNames.get(key);
    }


    public boolean hasLegitimateExits(String key) {
        if (legitimateExits.containsKey(key))
            return true;
        else
            return false;
    }

    public ArrayList<String> getLegitimateExits(String key) {
        return legitimateExits.get(key);
    }

}
