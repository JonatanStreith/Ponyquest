package jonst.Models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTest {


    @Test
    @SuppressWarnings("unchecked")
    public void SaveTest() {

        //Probably don't use this one, it's too complicated. Stick to simpler lists.

        Creature trixie = new Creature("Trixie", "Trixie", "This is Trixie.", "Unicorn");
        trixie.setLocation("The Castle of Friendship");
        Creature pinkie = new Creature("Pinkie Pie", "Pinkie", "This is Pinkie", "Earth pony");
        pinkie.setLocation("The Castle of Friendship");

        Item apple = new Item("A juicy red apple", "apple", "A fresh treat, straight from the Acres.");
        apple.setLocation("The Castle of Friendship");
        Item hat = new Item("Trixie's hat", "wizard hat", "Your great and powerful hat!");
        hat.setLocation("The Castle of Friendship");


        Location castle = new Location("The Castle of Friendship", "the castle", "A big castle.", new ArrayList() {{
            add("Sugarcube Corner");
            add("Carousel Boutique");
        }});

        StationaryObject map = new StationaryObject("The Cutie Map", "map", "The big map that shows friendship emergencies.");
        map.setLocation("The Castle of Friendship");


        List<Creature> creatureList = new ArrayList() {{
            add(trixie);
            add(pinkie);
        }};
        List<Item> itemList = new ArrayList() {{
            add(apple);
            add(hat);
        }};
        List<Location> locationList = new ArrayList() {{
            add(castle);
        }};
        List<StationaryObject> objectList = new ArrayList() {{
            add(map);
        }};

        //Okay, we now have a bunch of lists that hold the things we want to save.

        JSONObject creatures = new JSONObject();    //This will hold every creature as JSONObjects
        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"

        for (int i = 0; i < creatureList.size(); i++) {
            int finalI = i;
            creatures.put("Creature " + finalI, new JSONObject() {{
                put("FullName", creatureList.get(finalI).getName());
                put("ShortName", creatureList.get(finalI).getShortName());
                put("Race", creatureList.get(finalI).getRace());
                put("Description", creatureList.get(finalI).getDescription());
                put("Location", creatureList.get(finalI).getLocationName());
            }});
        }


        JSONObject items = new JSONObject();    //This will hold every item as JSONObjects
        //This creates one JSONObject for every item in the list, populates it with data, and adds it to "items"

        for (int i = 0; i < itemList.size(); i++) {
            int finalI = i;
            items.put("Item " + finalI, new JSONObject() {{
                put("FullName", itemList.get(finalI).getName());
                put("ShortName", itemList.get(finalI).getShortName());
                put("Description", itemList.get(finalI).getDescription());
                put("Location", itemList.get(finalI).getLocationName());
            }});
        }


        JSONObject objects = new JSONObject();    //This will hold every stationaryobject as JSONObjects
        //This creates one JSONObject for every object in the list, populates it with data, and adds it to "objects"

        for (int i = 0; i < objectList.size(); i++) {
            int finalI = i;
            objects.put("StationaryObject " + finalI, new JSONObject() {{
                put("FullName", objectList.get(finalI).getName());
                put("ShortName", objectList.get(finalI).getShortName());
                put("Description", objectList.get(finalI).getDescription());
                put("Location", objectList.get(finalI).getLocationName());
            }});
        }

        JSONObject locations = new JSONObject();    //This will hold every creature as JSONObjects
        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"

        for (int i = 0; i < locationList.size() ; i++) {
            int finalI = i;
            locations.put("Location " + finalI, new JSONObject() {{
                put("FullName", locationList.get(finalI).getName());
                put("ShortName", locationList.get(finalI).getShortName());
                put("Description", locationList.get(finalI).getDescription());
                put("Location", locationList.get(finalI).getLocationName());
                put("Exits", new JSONObject() {{

                            for (int i = 0; i < locationList.get(finalI).getExits().size(); i++) {   //Puts all exits into an array
                                put(i, locationList.get(finalI).getExits().get(i));
                            }
                        }}
                );
            }});
        }

        JSONArray outputArray = new JSONArray();    //This adds everything to the array we can write out
        outputArray.add(creatures);
        outputArray.add(items);
        outputArray.add(objects);
        outputArray.add(locations);


        try (FileWriter file = new FileWriter("src/main/java/jonst/Assets/JSON/data.json")) {

            file.write(outputArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    @SuppressWarnings("unchecked")
    public void SaveTestMk2() {

        Creature trixie = new Creature("Trixie", "Trixie", "This is Trixie.", "Unicorn");
        trixie.setLocation("The Castle of Friendship");
        Creature pinkie = new Creature("Pinkie Pie", "Pinkie", "This is Pinkie", "Earth pony");
        pinkie.setLocation("The Castle of Friendship");

        Item apple = new Item("A juicy red apple", "apple", "A fresh treat, straight from the Acres.");
        apple.setLocation("The Castle of Friendship");
        Item hat = new Item("Trixie's hat", "wizard hat", "Your great and powerful hat!");
        hat.setLocation("The Castle of Friendship");


        Location castle = new Location("The Castle of Friendship", "the castle", "A big castle.", new ArrayList() {{
            add("Sugarcube Corner");
            add("Carousel Boutique");
        }});

        StationaryObject map = new StationaryObject("The Cutie Map", "map", "The big map that shows friendship emergencies.");
        map.setLocation("The Castle of Friendship");


        List<Creature> creatureList = new ArrayList() {{
            add(trixie);
            add(pinkie);
        }};
        List<Item> itemList = new ArrayList() {{
            add(apple);
            add(hat);
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


    @Test
    public void LoadTest() {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Item> itemList = new ArrayList<>();
        List<Creature> creatureList = new ArrayList<>();
        List<StationaryObject> stationaryObjectList = new ArrayList<>();
        List<Location> locationList = new ArrayList<>();

        try
        {
            reader = new FileReader("src/main/java/jonst/Assets/JSON/items.json");
            JSONArray itemJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj: itemJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                Item item = new Item(fullName, shortName, description );
                item.setLocation(location);

                itemList.add(item);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/creatures.json");
            JSONArray creatureJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj: creatureJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String race = (String) jObj.get("Race");
                String location = (String) jObj.get("Location");

                Creature creature = new Creature(fullName, shortName, description, race );
                creature.setLocation(location);

                creatureList.add(creature);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/objects.json");
            JSONArray stationaryObjectJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj: stationaryObjectJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                StationaryObject object = new StationaryObject(fullName, shortName, description);
                object.setLocation(location);

                stationaryObjectList.add(object);
            }


            reader = new FileReader("src/main/java/jonst/Assets/JSON/locations.json");
            JSONArray locationJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj: locationJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                ArrayList<String> exits = new ArrayList<>();

                JSONObject jsExits = (JSONObject) jObj.get("Exits");

                for (Object xObj: jsExits.values()) {
                    exits.add((String) xObj);
                }

                Location location = new Location(fullName, shortName, description, exits);
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







        for (Item item: itemList) {
            System.out.println("Full name: " + item.name + "; Short name: " + item.shortName + "; Description: " + item.description + "; Location: " + item.locationName + ".");
        }

        for (Creature creature: creatureList) {
            System.out.println("Full name: " + creature.name + "; Short name: " + creature.shortName + "; Race: " + creature.getRace() + "; Description: " + creature.description + "; Location: " + creature.locationName + ".");
        }


    }
}
