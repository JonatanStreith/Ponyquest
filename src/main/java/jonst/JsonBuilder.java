package jonst;

import jonst.Models.Creature;
import jonst.Models.Item;
import jonst.Models.Location;
import jonst.Models.StationaryObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonBuilder {

    //---------- Experimental stuff
    public static void SaveJson() {

        Creature trixie = new Creature("Trixie", "yourself",
                "You are the Great and Powerful Trixie! You are Equestria's greatest stage magician, a unicorn of peerless beauty and magical ability, a brave and fearless hero, and also very humble.",
                "Castle of Friendship", "unicorn", new ArrayList() {{
                    //casual dialog
            add("Talking to yourself is usually pointless.");
            add("Much as you're a wonderful conversationalist, there's not much point.");
            add("You'd rather not. Other ponies think you're weird enough as it is.");

        }}


        );


        Item apple = new Item("Trixie's hat", "hat", "Your hat. Anypony who sees it will know that they're dealing with a magnificent magician. Good for keeping the sun out of your eyes, too.", "inventory");


        Location castle = new Location("Castle of Friendship", "castle", "The Castle of Friendship is a majestic crystal structure that looks incredibly out of place with the rest of Ponyville. It's a nice place to hang out, though.", "Castle of Friendship", new ArrayList() {{
            add("Sugarcube Corner");
            add("Carousel Boutique");
            add("Sweet Apple Acres");
            add("Castle main hall");
        }});

        StationaryObject map = new StationaryObject("Cutie Map", "map", "The Cutie Map serves as a magic display for 'friendship emergencies', or something. Starlight won't let you go near it after... the incident.", "Cutie Map room");


        List<Creature> creatureList = new ArrayList() {{
            add(trixie);

        }};
        List<Item> itemList = new ArrayList() {{
            add(apple);

        }};
        List<Location> locationList = new ArrayList() {{
            add(castle);
        }};
        List<StationaryObject> objectList = new ArrayList() {{
            add(map);
        }};

        //Okay, we now have a bunch of lists that hold the things we want to save.

        JSONObject creatures = new JSONObject();    //This will hold every creature as JSONObjects

        JSONArray creatureArray = new JSONArray();
        JSONArray itemArray = new JSONArray();
        JSONArray objectArray = new JSONArray();
        JSONArray locationArray = new JSONArray();


        for (Creature crea : creatureList) {        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"
            creatureArray.add(new JSONObject() {{
                put("FullName", crea.getName());
                put("ShortName", crea.getShortName());
                put("Race", crea.getRace());
                put("Description", crea.getDescription());
                put("Location", crea.getLocationName());

                put("CasualDialog", new JSONObject() {{

                            for (int i = 0; i < crea.getCasuaDialog().size(); i++) {   //Puts all exits into an array
                                put(i, crea.getCasuaDialog().get(i));
                            }
                        }}
                );

            }});
        }


        JSONObject items = new JSONObject();    //This will hold every item as JSONObjects

        for (Item ite : itemList) {        //This creates one JSONObject for every item in the list, populates it with data, and adds it to "items"
            itemArray.add(new JSONObject() {{
                put("FullName", ite.getName());
                put("ShortName", ite.getShortName());
                put("Description", ite.getDescription());
                put("Location", ite.getLocationName());
            }});
        }


        JSONObject objects = new JSONObject();    //This will hold every stationaryobject as JSONObjects

        for (StationaryObject sta : objectList) {        //This creates one JSONObject for every object in the list, populates it with data, and adds it to "objects"
            objectArray.add(new JSONObject() {{
                put("FullName", sta.getName());
                put("ShortName", sta.getShortName());
                put("Description", sta.getDescription());
                put("Location", sta.getLocationName());
            }});
        }

        JSONObject locations = new JSONObject();    //This will hold every creature as JSONObjects

        for (Location loc : locationList) {        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"
            locationArray.add(new JSONObject() {{
                put("FullName", loc.getName());
                put("ShortName", loc.getShortName());
                put("Description", loc.getDescription());
                put("Location", loc.getLocationName());
                put("Exits", new JSONObject() {{

                            for (int i = 0; i < loc.getExits().size(); i++) {   //Puts all exits into an array
                                put(i, loc.getExits().get(i));
                            }
                        }}
                );
            }});
        }


        try (FileWriter file = new FileWriter("src/main/java/jonst/Assets/JSON/creatures.json")) {

            file.write(creatureArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try (FileWriter file = new FileWriter("src/main/java/jonst/Assets/JSON/items.json")) {

            file.write(itemArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("src/main/java/jonst/Assets/JSON/objects.json")) {

            file.write(objectArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("src/main/java/jonst/Assets/JSON/locations.json")) {

            file.write(locationArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void LoadJson() {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Item> itemList = new ArrayList<>();
        List<Creature> creatureList = new ArrayList<>();
        List<StationaryObject> stationaryObjectList = new ArrayList<>();
        List<Location> locationList = new ArrayList<>();

        try {
            reader = new FileReader("src/main/java/jonst/Assets/JSON/items.json");
            JSONArray itemJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : itemJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                Item item = new Item(fullName, shortName, description, location);


                itemList.add(item);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/creatures.json");
            JSONArray creatureJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : creatureJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String race = (String) jObj.get("Race");
                String location = (String) jObj.get("Location");
                List<String> casualDialog = new ArrayList<>();

                JSONObject jsCD = (JSONObject) jObj.get("CasualDialog");

                for (Object xObj : jsCD.values()) {
                    casualDialog.add((String) xObj);
                }
                //Don't forget to populate!

                Creature creature = new Creature(fullName, shortName, description, location, race, casualDialog);
                creature.setLocation(location);

                creatureList.add(creature);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/objects.json");
            JSONArray stationaryObjectJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : stationaryObjectJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                StationaryObject object = new StationaryObject(fullName, shortName, description, location);
                object.setLocation(location);

                stationaryObjectList.add(object);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/locations.json");
            JSONArray locationJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : locationJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                ArrayList<String> exits = new ArrayList<>();

                JSONObject jsExits = (JSONObject) jObj.get("Exits");

                for (Object xObj : jsExits.values()) {
                    exits.add((String) xObj);
                }

                Location location = new Location(fullName, shortName, description, fullName, exits);
                location.setLocation(fullName);

                locationList.add(location);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (Item item : itemList) {
            System.out.print("Full name: " + item.getName() + "; Short name: " + item.getShortName() + "; Description: " + item.getDescription() + "; Location: " + item.getLocationName() + ".");
            System.out.println("");
        }

        for (StationaryObject stat : stationaryObjectList) {
            System.out.print("Full name: " + stat.getName() + "; Short name: " + stat.getShortName() + "; Description: " + stat.getDescription() + "; Location: " + stat.getLocationName() + ".");
            System.out.println("");
        }

        for (Creature creature : creatureList) {
            System.out.print("Full name: " + creature.getName() + "; Short name: " + creature.getShortName() + "; Race: " + creature.getRace() + "; Description: " + creature.getDescription() + "; Location: " + creature.getLocationName() + ". Casual lines: ");
            for (String casual : creature.getCasuaDialog()) {
                System.out.print(casual + ", ");
            }
            System.out.println("");
        }

        for (Location loc : locationList) {
            System.out.print("Full name: " + loc.getName() + "; Short name: " + loc.getShortName() + "; Description: " + loc.getDescription() + "; Location: " + loc.getLocationName() + ". Exits are: ");
            for (String exit : loc.getExits()) {
                System.out.print(exit + ", ");
            }

            System.out.println("");
        }

    }

    //---------- save methods
    public static boolean saveCreatureList(String filepath, List<Creature> creatureList){

        boolean success = true;
        JSONArray creatureArray = new JSONArray();

        for (Creature crea : creatureList) {        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"
            creatureArray.add(new JSONObject() {{
                put("FullName", crea.getName());
                put("ShortName", crea.getShortName());
                put("Race", crea.getRace());
                put("Description", crea.getDescription());
                put("Location", crea.getLocationName());

                put("CasualDialog", new JSONObject() {{

                            for (int i = 0; i < crea.getCasuaDialog().size(); i++) {   //Puts all casual dialog lines into an object
                                put(i, crea.getCasuaDialog().get(i));
                            }
                        }}
                );

            }});
        }

        try (FileWriter file = new FileWriter(filepath + "/creatures.json")) {

            file.write(creatureArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean saveLocationList(String filepath, List<Location> locationList){

        boolean success = true;
        JSONArray locationArray = new JSONArray();

        for (Location loc : locationList) {        //This creates one JSONObject for every location in the list, populates it with data, and adds it to "locations"
            locationArray.add(new JSONObject() {{
                put("FullName", loc.getName());
                put("ShortName", loc.getShortName());
                put("Description", loc.getDescription());
                put("Location", loc.getLocationName());
                put("Exits", new JSONObject() {{

                            for (int i = 0; i < loc.getExits().size(); i++) {   //Puts all exits into an array
                                put(i, loc.getExits().get(i));
                            }
                        }}
                );
            }});
        }

        try (FileWriter file = new FileWriter(filepath + "/locations.json")) {

            file.write(locationArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean saveItemList(String filepath, List<Item> itemList){

        boolean success = true;
        JSONArray itemArray = new JSONArray();

        for (Item ite : itemList) {        //This creates one JSONObject for every item in the list, populates it with data, and adds it to "items"
            itemArray.add(new JSONObject() {{
                put("FullName", ite.getName());
                put("ShortName", ite.getShortName());
                put("Description", ite.getDescription());
                put("Location", ite.getLocationName());
            }});
        }

        try (FileWriter file = new FileWriter(filepath + "/items.json")) {

            file.write(itemArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean saveStationaryObjectList(String filepath, List<StationaryObject> objectList){

        boolean success = true;
        JSONArray objectArray = new JSONArray();

        for (StationaryObject sta : objectList) {        //This creates one JSONObject for every object in the list, populates it with data, and adds it to "objects"
            objectArray.add(new JSONObject() {{
                put("FullName", sta.getName());
                put("ShortName", sta.getShortName());
                put("Description", sta.getDescription());
                put("Location", sta.getLocationName());
            }});
        }

        try (FileWriter file = new FileWriter(filepath + "/objects.json")) {

            file.write(objectArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    //---------- load methods
    public static List<Creature> loadCreatureList(String filepath){

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Creature> creatureList = new ArrayList<>();

        try {

            reader = new FileReader(filepath+"/creatures.json");
            JSONArray creatureJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : creatureJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String race = (String) jObj.get("Race");
                String location = (String) jObj.get("Location");
                List<String> casualDialog = new ArrayList<>();

                JSONObject jsCD = (JSONObject) jObj.get("CasualDialog");

                for (Object xObj : jsCD.values()) {
                    casualDialog.add((String) xObj);
                }

                Creature creature = new Creature(fullName, shortName, description, location, race, casualDialog);

                creatureList.add(creature);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return creatureList;
    }

    public static List<Location> loadLocationList(String filepath){

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Location> locationList = new ArrayList<>();

        try {

            reader = new FileReader(filepath+"/locations.json");
            JSONArray locationJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : locationJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                ArrayList<String> exits = new ArrayList<>();

                JSONObject jsExits = (JSONObject) jObj.get("Exits");

                for (Object xObj : jsExits.values()) {
                    exits.add((String) xObj);
                }

                Location location = new Location(fullName, shortName, description, fullName, exits);
                location.setLocation(fullName);

                locationList.add(location);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locationList;

    }

    public static List<StationaryObject> loadStationaryObjectList(String filepath){

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<StationaryObject> stationaryObjectList = new ArrayList<>();

        try {

        reader = new FileReader(filepath+"/objects.json");
        JSONArray stationaryObjectJSON = (JSONArray) jsonParser.parse(reader);

        for (Object obj : stationaryObjectJSON) {
            JSONObject jObj = (JSONObject) obj;
            String fullName = (String) jObj.get("FullName");
            String shortName = (String) jObj.get("ShortName");
            String description = (String) jObj.get("Description");
            String location = (String) jObj.get("Location");

            StationaryObject object = new StationaryObject(fullName, shortName, description, location);
            object.setLocation(location);

            stationaryObjectList.add(object);
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stationaryObjectList;
    }

    public static List<Item> loadItemList(String filepath){

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Item> itemList = new ArrayList<>();

        try {

            reader = new FileReader(filepath+"/items.json");
            JSONArray itemJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : itemJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                Item item = new Item(fullName, shortName, description, location);


                itemList.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return itemList;
    }

}
