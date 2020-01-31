package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Item;
import jonst.Models.Objects.Location;
import jonst.Models.Objects.StationaryObject;
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

    public static long addToSavesMenu(String saveName) {
        //Method takes a proposed save name, checks for a free place in the saves menu;
        //If possible, builds an entry and saves it, then returns the used number.
        //If return is -1, something went wrong (usually out of saves slots)
        //If return is -100, there was an error reading/writing to the file.

        long freeNumber = -1;
        Map<Long, String> saves = getSavesMenu();

        if (saves == null) {
            return -100;        //Oh no, file writing error
        }

        for (long i = 1; i <= 1000; i++) {  //Checks to find a number that isn't used
            if (!saves.containsKey(i)) {
                freeNumber = i;
                break;
            }
        }

        if (freeNumber != -1) {        //If we didn't find a free number, this will still be -1, so if it's not, we found a number
            saves.put(freeNumber, saveName); //Add an entry
            boolean success = buildSavesMenu(saves);    //Make method that builds the Json anew

            if (!success) {
                return -100;
            }
        }

        return freeNumber;
    }

    public static boolean buildSavesMenu(Map<Long, String> saves) {


        JSONArray savesJSON = new JSONArray();

        for (long key : saves.keySet()) {
            savesJSON.add(new JSONObject() {{
                              put("id", key);
                              put("savename", saves.get(key));
                          }}
            );
        }

        try (FileWriter file = new FileWriter(SystemData.getGamepath() + "/sys/saverecord.json")) {

            file.write(savesJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            System.out.println("There was an error writing to the saverecord.");
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public static Map<Long, String> getSavesMenu() {


        Map<Long, String> saves = new HashMap();


        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/sys/saverecord.json")) {       //If this part fails, it just won't access the save record.

            JSONArray savesJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : savesJSON) {
                JSONObject jObj = (JSONObject) obj;
                long id = (long) jObj.get("id");
                String savename = (String) jObj.get("savename");

                saves.put(id, savename);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Saverecord file not found.");
            return saves;
        } catch (IOException e) {
            System.out.println("There was an error reading the saverecord.");
            return saves;            //Return null to confirm that something went wrong!
        } catch (ParseException e) {
            System.out.println("Saverecord file corrupt, or there was an error during the reading.");
            return saves;            //Return null to confirm that something went wrong!
        }

        return saves;

    }

    //---------- save methods
    public static boolean saveCreatureList(String filepath, List<Creature> creatureList) {

//        Map<String, String> askTopics = new HashMap() {{
//            put("Key1", "Response");
//        }};

        boolean success = true;
        JSONArray creatureArray = new JSONArray();


        for (Creature crea : creatureList) {        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"
            creatureArray.add(new JSONObject() {{
                put("FullName", crea.getName());
                put("Id", crea.getId());
                put("Description", crea.getDescription());
                put("Location", crea.getLocationName());
                put("Text", crea.getText());
                put("DefaultUse", crea.getDefaultUse());

                put("Alias", new JSONArray() {{
                    for (String alias : crea.getAlias()) {
                        add(alias);
                    }
                }});
                put("Attributes", new JSONArray() {{
                    for (String attribute : crea.getAttributes()) {
                        add(attribute);
                    }
                }});

                put("Race", crea.getRace());
                put("Gender", crea.getGender());
                put("CasualDialog", new JSONArray() {{
                    for (String dialog : crea.getCasualDialog()) {
                        add(dialog);
                    }
                }});
                put("AskTopics", new JSONObject() {{
                    for (String key : crea.getAskTopics().keySet()) {
                        put(key, crea.getAskTopics().get(key));
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : crea.getComplexUse().keySet()) {
                        put(key, crea.getComplexUse().get(key));
                    }
                }});
                put("BehaviorCore", new JSONObject(){{
                    put("mood", crea.getMood());
                    put("activity", crea.getActivity());
                    put("allegiance", crea.getAllegiance());
                    put("status", crea.getStatus());


                }});

                put("ResponseScripts", new JSONObject() {{
                    for (String key : crea.getResponseScripts().keySet()) {
                        put(key, crea.getResponseScripts().get(key));
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
                put("Id", loc.getId());
                put("Description", loc.getDescription());
                put("Location", loc.getLocationName());
                put("Text", loc.getText());
                put("DefaultUse", loc.getDefaultUse());

                put("Alias", new JSONArray() {{
                    for (String alias : loc.getAlias()) {
                        add(alias);
                    }
                }});
                put("Attributes", new JSONArray() {{
                    for (String attribute : loc.getAttributes()) {
                        add(attribute);
                    }
                }});

                put("DefaultEnter", loc.getDefaultEnter());
                put("DefaultExit", loc.getDefaultExit());


                put("Exits", new JSONObject() {{

                    for (int i = 0; i < loc.getExits().size(); i++) {   //Puts all exits into an array
                        put(i, loc.getExits().get(i));
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : loc.getComplexUse().keySet()) {
                        put(key, loc.getComplexUse().get(key));
                    }
                }});
                put("ResponseScripts", new JSONObject() {{
                    for (String key : loc.getResponseScripts().keySet()) {
                        put(key, loc.getResponseScripts().get(key));
                    }
                }});

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
                put("Id", ite.getId());
                put("Description", ite.getDescription());
                put("Location", ite.getLocationName());
                put("Text", ite.getText());
                put("DefaultUse", ite.getDefaultUse());


                put("Alias", new JSONArray() {{
                    for (String alias : ite.getAlias()) {
                        add(alias);
                    }
                }});
                put("Attributes", new JSONArray() {{
                    for (String attribute : ite.getAttributes()) {
                        add(attribute);
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : ite.getComplexUse().keySet()) {
                        put(key, ite.getComplexUse().get(key));
                    }
                }});
                put("ResponseScripts", new JSONObject() {{
                    for (String key : ite.getResponseScripts().keySet()) {
                        put(key, ite.getResponseScripts().get(key));
                    }
                }});

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
                put("Id", sta.getId());
                put("Description", sta.getDescription());
                put("Location", sta.getLocationName());
                put("Text", sta.getText());
                put("DefaultUse", sta.getDefaultUse());

                put("Alias", new JSONArray() {{
                    for (String alias : sta.getAlias()) {
                        add(alias);
                    }
                }});
                put("Attributes", new JSONArray() {{
                    for (String attribute : sta.getAttributes()) {
                        add(attribute);
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : sta.getComplexUse().keySet()) {
                        put(key, sta.getComplexUse().get(key));
                    }
                }});
                put("ResponseScripts", new JSONObject() {{
                    for (String key : sta.getResponseScripts().keySet()) {
                        put(key, sta.getResponseScripts().get(key));
                    }
                }});

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


        List<Creature> creatureList = new ArrayList<>();


        try (FileReader reader = new FileReader(filepath + "/creatures.json")) {

            JSONArray creatureJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : creatureJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String id = (String) jObj.get("Id");
                String description = (String) jObj.get("Description");
                String race = (String) jObj.get("Race");
                String gender = (String) jObj.get("Gender");
                String location = (String) jObj.get("Location");
                String text = (String) jObj.get("Text");
                String defaultUse = (String) jObj.get("DefaultUse");

                List<String> casualDialog = new ArrayList<>();
                Map<String, String> askTopics = new HashMap<>();
                Map<String, String> complexUse = new HashMap<>();
                Map<String, String> responseScripts = new HashMap<>();
                List<String> alias = new ArrayList<>();
                List<String> attributes = new ArrayList<>();

                JSONArray jsCD = (JSONArray) jObj.get("CasualDialog");
                if (jsCD != null)
                    for (Object xObj : jsCD) {
                        casualDialog.add((String) xObj);
                    }

                JSONObject jsAT = (JSONObject) jObj.get("AskTopics");
                if (jsAT != null)
                    for (Object xObj : jsAT.keySet()) {
                        String key = (String) xObj;
                        askTopics.put(key.toLowerCase(), (String) jsAT.get(key));
                    }

                JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                if (jsRS != null)
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        responseScripts.put(key.toLowerCase(), (String) jsRS.get(key));
                    }

                JSONArray jsAlias = (JSONArray) jObj.get("Alias");
                if (jsAlias != null)
                    for (Object xObj : jsAlias) {
                        String newAlias = (String) xObj;
                        alias.add(newAlias.toLowerCase());
                    }

                JSONArray jsAttributes = (JSONArray) jObj.get("Attributes");
                if (jsAttributes != null)
                    for (Object xObj : jsAttributes) {
                        String newAttribute = (String) xObj;
                        attributes.add(newAttribute.toLowerCase());
                    }

                JSONObject jsBehaviorCore = (JSONObject) jObj.get("BehaviorCore");
                    BehaviorCore bc;
                    if(jsBehaviorCore != null){
                        String mood = (String) jsBehaviorCore.get("mood");
                        String activity = (String) jsBehaviorCore.get("activity");
                        String allegiance = (String) jsBehaviorCore.get("allegiance");
                        String status = (String) jsBehaviorCore.get("status");
                        bc = new BehaviorCore(mood, activity, allegiance, status);
                    } else {
                        bc = new BehaviorCore();        //If a creature has no stated BC, it gets a default one.
                    }


                Creature creature = new Creature(fullName, id, description, location.toLowerCase(), alias, attributes, race.toLowerCase(), gender.toLowerCase(), casualDialog, askTopics);
                creature.setText(text);
                creature.setDefaultUse(defaultUse);

                creature.setComplexUse(complexUse);
                creature.setResponseScripts(responseScripts);

                creature.setBehaviorCore(bc);

                creatureList.add(creature);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Creatures file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the creatures file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Creatures file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }
        //System.out.println("Creature list loaded from file.");

        return creatureList;
    }

    public static List<Location> loadLocationList(String filepath) {


        List<Location> locationList = new ArrayList<>();

        try (FileReader reader = new FileReader(filepath + "/locations.json")) {

            JSONArray locationJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : locationJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String id = (String) jObj.get("Id");
                String description = (String) jObj.get("Description");

                String defaultEnter = (String) jObj.get("DefaultEnter");
                String defaultExit = (String) jObj.get("DefaultExit");
                String text = (String) jObj.get("Text");
                String defaultUse = (String) jObj.get("DefaultUse");

                Map<String, String> complexUse = new HashMap<>();
                Map<String, String> responseScripts = new HashMap<>();

                ArrayList<String> exits = new ArrayList<>();
                List<String> alias = new ArrayList<>();
                List<String> attributes = new ArrayList<>();

                JSONArray jsAlias = (JSONArray) jObj.get("Alias");
                if (jsAlias != null)
                    for (Object xObj : jsAlias) {
                        String newAlias = (String) xObj;
                        alias.add(newAlias.toLowerCase());
                    }

                JSONArray jsAttributes = (JSONArray) jObj.get("Attributes");
                if (jsAttributes != null)
                    for (Object xObj : jsAttributes) {
                        String newAttribute = (String) xObj;
                        attributes.add(newAttribute.toLowerCase());
                    }

                JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                if (jsRS != null)
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        responseScripts.put(key.toLowerCase(), (String) jsRS.get(key));
                    }


                JSONObject jsExits = (JSONObject) jObj.get("Exits");
                if (jsExits != null)
                    for (Object xObj : jsExits.values()) {
                        exits.add((String) xObj);
                    }

                Location location = new Location(fullName, id, description, fullName, alias, attributes, exits, defaultEnter, defaultExit);
                location.setText(text);
                location.setDefaultUse(defaultUse);

                location.setComplexUse(complexUse);
                location.setResponseScripts(responseScripts);


                locationList.add(location);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Locations file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the locations file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Locations file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }
        //System.out.println("Location list loaded from file.");

        return locationList;

    }

    public static List<StationaryObject> loadStationaryObjectList(String filepath) {

        List<StationaryObject> stationaryObjectList = new ArrayList<>();

        try (FileReader reader = new FileReader(filepath + "/objects.json")) {

            JSONArray stationaryObjectJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : stationaryObjectJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String id = (String) jObj.get("Id");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");
                List<String> alias = new ArrayList<>();
                List<String> attributes = new ArrayList<>();
                String text = (String) jObj.get("Text");
                String defaultUse = (String) jObj.get("DefaultUse");

                Map<String, String> complexUse = new HashMap<>();
                Map<String, String> responseScripts = new HashMap<>();

                JSONArray jsAlias = (JSONArray) jObj.get("Alias");
                if (jsAlias != null)
                    for (Object xObj : jsAlias) {
                        String newAlias = (String) xObj;
                        alias.add(newAlias.toLowerCase());
                    }

                JSONArray jsAttributes = (JSONArray) jObj.get("Attributes");
                if (jsAttributes != null)
                    for (Object xObj : jsAttributes) {
                        String newAttribute = (String) xObj;
                        attributes.add(newAttribute.toLowerCase());
                    }

                JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                if (jsRS != null)
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        responseScripts.put(key.toLowerCase(), (String) jsRS.get(key));
                    }

                StationaryObject object = new StationaryObject(fullName, id, description, location, alias, attributes);
                object.setText(text);
                object.setDefaultUse(defaultUse);

                object.setComplexUse(complexUse);
                object.setResponseScripts(responseScripts);


                stationaryObjectList.add(object);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Stationaryobjects file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the stationaryobjects file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Stationaryobjects file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }
        //System.out.println("Stationary object list loaded from file.");

        return stationaryObjectList;
    }

    public static List<Item> loadItemList(String filepath) {

        List<Item> itemList = new ArrayList<>();

        try (FileReader reader = new FileReader(filepath + "/items.json")) {

            JSONArray itemJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : itemJSON) {
                JSONObject jObj = (JSONObject) obj;
                String fullName = (String) jObj.get("FullName");
                String id = (String) jObj.get("Id");
                String description = (String) jObj.get("Description");
                String location = (String) jObj.get("Location");
                List<String> alias = new ArrayList<>();
                List<String> attributes = new ArrayList<>();
                String text = (String) jObj.get("Text");
                String defaultUse = (String) jObj.get("DefaultUse");

                Map<String, String> complexUse = new HashMap<>();
                Map<String, String> responseScripts = new HashMap<>();

                JSONArray jsAlias = (JSONArray) jObj.get("Alias");
                if (jsAlias != null)
                    for (Object xObj : jsAlias) {
                        String newAlias = (String) xObj;
                        alias.add(newAlias.toLowerCase());
                    }

                JSONArray jsAttributes = (JSONArray) jObj.get("Attributes");
                if (jsAttributes != null)
                    for (Object xObj : jsAttributes) {
                        String newAttribute = (String) xObj;
                        attributes.add(newAttribute.toLowerCase());
                    }

                JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                if (jsRS != null)
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        responseScripts.put(key.toLowerCase(), (String) jsRS.get(key));
                    }


                Item item = new Item(fullName, id, description, location, alias, attributes);
                item.setText(text);
                item.setDefaultUse(defaultUse);

                item.setComplexUse(complexUse);
                item.setResponseScripts(responseScripts);

                itemList.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Items file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the items file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Items file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }
        //System.out.println("Item list loaded from file.");

        return itemList;
    }

    //--------------------------------------------

    public static Item generateDefaultItem(String itemName){

        Item item = null;

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/DefaultItems.json")) {

            JSONObject allItemsJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject

            JSONObject jsonItem = (JSONObject) allItemsJSON.get(itemName);  //Retrieve the jsonObject corresponding to that name.

            String fullName = (String) jsonItem.get("FullName");
            String id = (String) jsonItem.get("Id");
            String description = (String) jsonItem.get("Description");
            String locationName = "blank";

            JSONArray jsAlias = (JSONArray) jsonItem.get("Alias");
            JSONArray jsAttributes = (JSONArray) jsonItem.get("Attributes");

            List<String> alias = new ArrayList<>();
            for (Object ali: jsAlias) {
                alias.add((String) ali);
            }

            List<String> attributes = new ArrayList<>();
            for (Object attr: jsAttributes) {
                attributes.add((String) attr);
            }

            String text = (String) jsonItem.get("Text");
            String defaultUse = (String) jsonItem.get("DefaultUse");

            JSONObject jsResponseScripts = (JSONObject) jsonItem.get("ResponseScripts");
            JSONObject jsComplexUse = (JSONObject) jsonItem.get("ComplexUse");

            Map<String, String> responseScripts = new HashMap<>();
            for (Object keyObj: jsResponseScripts.keySet() ) {
                String key = (String) keyObj;
                responseScripts.put(key.toLowerCase(), (String) jsResponseScripts.get(key));
            }

            Map<String, String> complexUse = new HashMap<>();
            for (Object keyObj: jsComplexUse.keySet() ) {
                String key = (String) keyObj;
                complexUse.put(key.toLowerCase(), (String) jsComplexUse.get(key));
            }




            //String name, String id, String description, String locationName, List<String> alias, List<String> attributes

            item = new Item(fullName, id, description, locationName, alias, attributes);
            item.setText(text);
            item.setDefaultUse(defaultUse);
            item.setResponseScripts(responseScripts);
            item.setComplexUse(complexUse);


        } catch (FileNotFoundException e) {
            System.out.println("Stationaryobjects file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the stationaryobjects file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Stationaryobjects file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }


        return item;
    }


}
