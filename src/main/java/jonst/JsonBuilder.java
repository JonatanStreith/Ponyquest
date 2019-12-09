package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonBuilder {


    public static Map<Long, String> getSavesMenu() {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        Map<Long, String> saves = new HashMap();


        try {

            reader = new FileReader(SystemData.getGamepath() + "/sys/saverecord.json");
            JSONArray savesJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : savesJSON) {
                JSONObject jObj = (JSONObject) obj;
                long id = (long) jObj.get("id");
                String savename = (String) jObj.get("savename");

                saves.put(id, savename);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        for (SaveFile save2 : saves.values()
//             ) {
//            System.out.println("Name: " + save2.getName() + ", path: " + save2.getPath());
//        }


        return saves;

    }


    //---------- save methods
    public static boolean saveCreatureList(String filepath, List<Creature> creatureList) {

        Map<String, String> askTopics = new HashMap() {{
            put("Key1", "Response");
        }};

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
                            for (int i = 0; i < crea.getCasualDialog().size(); i++) {   //Puts all casual dialog lines into an object
                                put(i, crea.getCasualDialog().get(i));
                            }
                        }}
                );
                put("AskTopics", new JSONObject() {{
                    for (String key : askTopics.keySet() ) {
                        put(key, askTopics.get(key));
                    }
                }});

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

    public static boolean saveLocationList(String filepath, List<Location> locationList) {

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

    public static boolean saveItemList(String filepath, List<Item> itemList) {

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

    public static boolean saveStationaryObjectList(String filepath, List<StationaryObject> objectList) {

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
    public static List<Creature> loadCreatureList(String filepath) {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Creature> creatureList = new ArrayList<>();

        Map<String, String> askTopics = new HashMap<>();

        try {

            reader = new FileReader(filepath + "/creatures.json");
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

                JSONObject jsAT = (JSONObject) jObj.get("AskTopics");


                for (Object xObj : jsAT.keySet()) {
                    String key = (String) xObj;

                    askTopics.put(key, (String) jsAT.get(key));
                }


                Creature creature = new Creature(fullName, shortName, description, location, race, casualDialog, askTopics);

                creatureList.add(creature);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("Creature list loaded from file.");

        return creatureList;
    }

    public static List<Location> loadLocationList(String filepath) {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Location> locationList = new ArrayList<>();

        try {

            reader = new FileReader(filepath + "/locations.json");
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
                //location.setLocation(fullName);

                locationList.add(location);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println("Location list loaded from file.");

        return locationList;

    }

    public static List<StationaryObject> loadStationaryObjectList(String filepath) {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<StationaryObject> stationaryObjectList = new ArrayList<>();

        try {

            reader = new FileReader(filepath + "/objects.json");
            JSONArray stationaryObjectJSON = (JSONArray) jsonParser.parse(reader);

            for (Object obj : stationaryObjectJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String shortName = (String) jObj.get("ShortName");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");

                StationaryObject object = new StationaryObject(fullName, shortName, description, location);
                //object.setLocation(location);

                stationaryObjectList.add(object);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println("Stationary object list loaded from file.");

        return stationaryObjectList;
    }

    public static List<Item> loadItemList(String filepath) {

        FileReader reader;
        JSONParser jsonParser = new JSONParser();

        List<Item> itemList = new ArrayList<>();

        try {

            reader = new FileReader(filepath + "/items.json");
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
        //System.out.println("Item list loaded from file.");

        return itemList;
    }

}
