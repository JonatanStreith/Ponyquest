package jonst.Data;


import java.util.ArrayList;
import java.util.HashMap;

public class StationaryObjectData {


    private HashMap<String, String> stationaryObjectDescriptions = new HashMap<>();
    private HashMap<String, String> stationaryObjectShortNames = new HashMap<>();


    public StationaryObjectData() {

        stationaryObjectDescriptions.put("Cutie Map", "The Cutie Map serves as a magic display for 'friendship emergencies', or something. Starlight won't let you go near it after... the incident.");
        stationaryObjectDescriptions.put("Friendship thrones", "The thrones of the Council of Friendship, or whatever Twilight's group call themselves. Each bear the cutie mark of its owner. Despite being made of crystal, they're surprisingly comfortable.");
        stationaryObjectDescriptions.put("Twilight's throne", "Twilight's throne. You sometimes sit here when she's not around. Or sometimes when she is.");
        stationaryObjectDescriptions.put("Rarity's throne", "Rarity's throne. She probably brings cushions.");
        stationaryObjectDescriptions.put("Applejack's throne", "Applejack's throne. You can tell by the dried apple juice stains.");
        stationaryObjectDescriptions.put("Pinkie Pie's throne", "Pinkie Pie's throne. What marechiavellian party plans have been planned from this seat, you wonder?");
        stationaryObjectDescriptions.put("Fluttershy's throne", "Fluttershy's throne. She sits on it like she's afraid it'll break.");
        stationaryObjectDescriptions.put("Rainbow Dash's throne", "Rainbow Dash's throne. It's just another napping spot to her.");
        stationaryObjectDescriptions.put("Spike's throne", "Spike's throne. A diminuitive seat for a diminuitive guy.");
        stationaryObjectDescriptions.put("apple tree", "The tree has no apples right now... somepony must've harvested them already.");
        stationaryObjectDescriptions.put("Rarity's sewing machine", "A sewing machine for advanced stitchwork. You could use it to fix damaged clothes... or better yet, let Rarity do it for you. It's her job, not yours.");
        stationaryObjectDescriptions.put("baking oven", "A typical oven for baking. You could probably use it... if you wanted to. Or you could get somepony else to do it for you.");
        //stationaryObjectDescriptions.put();

        stationaryObjectShortNames.put("Cutie Map", "map");
        stationaryObjectShortNames.put("Friendship thrones", "thrones");
        stationaryObjectShortNames.put("Twilight's throne", "Throne of Magic");
        stationaryObjectShortNames.put("Rarity's throne", "Throne of Generosity");
        stationaryObjectShortNames.put("Applejack's throne", "Throne of Honesty");
        stationaryObjectShortNames.put("Pinkie Pie's throne", "Throne of Laughter");
        stationaryObjectShortNames.put("Fluttershy's throne", "Throne of Kindness");
        stationaryObjectShortNames.put("Rainbow Dash's throne", "Throne of Loyalty");
        stationaryObjectShortNames.put("Spike's throne", "tiny throne");
        stationaryObjectShortNames.put("Rarity's sewing machine", "sewing machine");
        stationaryObjectShortNames.put("apple tree", "tree");
        stationaryObjectShortNames.put("baking oven", "oven");
        //stationaryObjectShortNames.put();


    }


    public boolean hasStationaryObjectDescription(String key) {
        if (stationaryObjectDescriptions.containsKey(key))
            return true;
        else
            return false;
    }

    public String getStationaryObjectDescription(String key) {
        return stationaryObjectDescriptions.get(key);
    }

    public boolean hasStationaryObjectShortName(String key) {
        if (stationaryObjectShortNames.containsKey(key))
            return true;
        else
            return false;
    }

    public String getStationaryObjectShortName(String key) {
        return stationaryObjectShortNames.get(key);
    }

}
