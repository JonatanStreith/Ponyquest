package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;
import jonst.Models.Objects.*;
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
                //put("Description", crea.getDescription());
                put("Location", crea.getLocationName());
                put("Text", crea.getText());
                put("DefaultUse", crea.getDefaultUse());

                put("InitialDialog", crea.getInitialDialog());

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
                put("DefaultRace", crea.getDefaultRace());
                put("Gender", crea.getGender());
                put("CasualDialog", new JSONArray() {{
                    for (String dialog : crea.getCasualDialog()) {
                        add(dialog);
                    }
                }});
                put("Descriptions", new JSONObject() {{
                    for (String key : crea.getDescriptions().keySet()) {
                        put(key, crea.getDescriptions().get(key));
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
                put("BehaviorCore", new JSONObject() {{
                    put("mood", crea.getMood());
                    put("activity", crea.getActivity());
                    put("allegiance", crea.getAllegiance());
                    put("status", crea.getStatus());


                }});

                put("ResponseScripts", new JSONObject() {{
                    for (String key : crea.getResponseScripts().keySet()) {
                        JSONArray scriptArray = new JSONArray() {{
                            for (String script : crea.getResponseScripts().get(key)) {
                                add(script);
                            }
                        }};
                        put(key, scriptArray);
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
                //put("Description", loc.getDescription());
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


                put("Descriptions", new JSONObject() {{
                    for (String key : loc.getDescriptions().keySet()) {
                        put(key, loc.getDescriptions().get(key));
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : loc.getComplexUse().keySet()) {
                        put(key, loc.getComplexUse().get(key));
                    }
                }});
//                put("ResponseScripts", new JSONObject() {{
//                    for (String key : loc.getResponseScripts().keySet()) {
//                        put(key, loc.getResponseScripts().get(key));
//                    }
//                }});

                put("ResponseScripts", new JSONObject() {{
                    for (String key : loc.getResponseScripts().keySet()) {
                        JSONArray scriptArray = new JSONArray() {{
                            for (String script : loc.getResponseScripts().get(key)) {
                                add(script);
                            }
                        }};
                        put(key, scriptArray);
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
                //put("Description", ite.getDescription());
                put("Location", ite.getLocationName());
                put("Text", ite.getText());
                put("DefaultUse", ite.getDefaultUse());

                put("Owner", ite.getOwner().getName());

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
                put("Descriptions", new JSONObject() {{
                    for (String key : ite.getDescriptions().keySet()) {
                        put(key, ite.getDescriptions().get(key));
                    }
                }});

                put("ComplexUse", new JSONObject() {{
                    for (String key : ite.getComplexUse().keySet()) {
                        put(key, ite.getComplexUse().get(key));
                    }
                }});
//                put("ResponseScripts", new JSONObject() {{
//                    for (String key : ite.getResponseScripts().keySet()) {
//                        put(key, ite.getResponseScripts().get(key));
//                    }
//                }});
                put("ResponseScripts", new JSONObject() {{
                    for (String key : ite.getResponseScripts().keySet()) {
                        JSONArray scriptArray = new JSONArray() {{
                            for (String script : ite.getResponseScripts().get(key)) {
                                add(script);
                            }
                        }};
                        put(key, scriptArray);
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
                //put("Description", sta.getDescription());
                put("Location", sta.getLocationName());
                put("Text", sta.getText());
                put("DefaultUse", sta.getDefaultUse());

                put("Owner", sta.getOwner().getName());

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
                put("Descriptions", new JSONObject() {{
                    for (String key : sta.getDescriptions().keySet()) {
                        put(key, sta.getDescriptions().get(key));
                    }
                }});
                put("ComplexUse", new JSONObject() {{
                    for (String key : sta.getComplexUse().keySet()) {
                        put(key, sta.getComplexUse().get(key));
                    }
                }});
//                put("ResponseScripts", new JSONObject() {{
//                    for (String key : sta.getResponseScripts().keySet()) {
//                        put(key, sta.getResponseScripts().get(key));
//                    }
//                }});
                put("ResponseScripts", new JSONObject() {{
                    for (String key : sta.getResponseScripts().keySet()) {
                        JSONArray scriptArray = new JSONArray() {{
                            for (String script : sta.getResponseScripts().get(key)) {
                                add(script);
                            }
                        }};
                        put(key, scriptArray);
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

                String tryName = "";
                try {

                    JSONObject jObj = (JSONObject) obj;
                    String fullName = (String) jObj.get("FullName");

                    tryName = fullName;

                    String id = (String) jObj.get("Id");
                    //String description = (String) jObj.get("Description");
                    String race = (String) jObj.get("Race");
                    String defaultRace = (String) jObj.get("DefaultRace");
                    String gender = (String) jObj.get("Gender");
                    String location = (String) jObj.get("Location");
                    String text = (String) jObj.get("Text");
                    String defaultUse = (String) jObj.get("DefaultUse");

                    String initialDialog = (String) jObj.get("InitialDialog");

                    List<String> casualDialog = new ArrayList<>();
                    Map<String, String> descriptions = new HashMap<>();
                    Map<String, String> askTopics = new HashMap<>();
                    Map<String, String> complexUse = new HashMap<>();
                    Map<String, ArrayList<String>> responseScripts = new HashMap<>();
                    List<String> alias = new ArrayList<>();
                    List<String> attributes = new ArrayList<>();

                    JSONArray jsCD = (JSONArray) jObj.get("CasualDialog");
                    if (jsCD != null)
                        for (Object xObj : jsCD) {
                            casualDialog.add((String) xObj);
                        }

                    JSONObject jsd = (JSONObject) jObj.get("Descriptions");
                    if (jsd != null)
                        for (Object xObj : jsd.keySet()) {
                            String key = (String) xObj;
                            descriptions.put(key.toLowerCase(), (String) jsd.get(key));
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


//                JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
//                if (jsRS != null)
//                    for (Object xObj : jsRS.keySet()) {
//                        String key = (String) xObj;
//                        responseScripts.put(key.toLowerCase(), (String) jsRS.get(key));
//                    }


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
                    if (jsBehaviorCore != null) {
                        String mood = (String) jsBehaviorCore.get("mood");
                        String activity = (String) jsBehaviorCore.get("activity");
                        String allegiance = (String) jsBehaviorCore.get("allegiance");
                        String status = (String) jsBehaviorCore.get("status");

                        Map<String, String> personalQuotes = new HashMap<>();
                        JSONObject jsPersonalQuotes = (JSONObject) jsBehaviorCore.get("PersonalQuotes");
                        if (jsPersonalQuotes != null) {
                            for (Object xObj : jsPersonalQuotes.keySet()) {
                                String key = (String) xObj;
                                personalQuotes.put(key.toLowerCase(), (String) jsPersonalQuotes.get(key));
                            }
                        }


                        bc = new BehaviorCore(mood, activity, allegiance, status);
                        bc.setPersonalQuotes(personalQuotes);
                    } else {
                        bc = new BehaviorCore();        //If a creature has no stated BC, it gets a default one.
                    }


                    JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                    if (jsRS != null) {
                        for (Object xObj : jsRS.keySet()) {
                            String key = (String) xObj;
                            JSONArray jsScripts = (JSONArray) jsRS.get(key);

                            ArrayList<String> scripts = new ArrayList<>();
                            scripts.addAll(jsScripts);
                            responseScripts.put(key.toLowerCase(), scripts);
                        }
                    }


                    Creature creature = new Creature(fullName, id, location.toLowerCase(), alias, attributes, race.toLowerCase(), defaultRace.toLowerCase(), gender.toLowerCase(), casualDialog, askTopics);
                    creature.setDescriptions(descriptions);
                    creature.setText(text);
                    creature.setDefaultUse(defaultUse);

                    creature.setComplexUse(complexUse);
                    creature.setResponseScripts(responseScripts);

                    creature.setBehaviorCore(bc);

                    creature.setInitialDialog(initialDialog);

                    creatureList.add(creature);
                } catch (Exception e) {
                    System.out.println("There was an error generating Creature: " + tryName);
                }
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

            String tryName = "";
            try {

                for (Object obj : locationJSON) {
                    JSONObject jObj = (JSONObject) obj;
                    String fullName = (String) jObj.get("FullName");

                    tryName = fullName;

                    String id = (String) jObj.get("Id");
                    //String description = (String) jObj.get("Description");

                    String defaultEnter = (String) jObj.get("DefaultEnter");
                    String defaultExit = (String) jObj.get("DefaultExit");
                    String text = (String) jObj.get("Text");
                    String defaultUse = (String) jObj.get("DefaultUse");

                    Map<String, String> descriptions = new HashMap<>();
                    Map<String, String> complexUse = new HashMap<>();
                    Map<String, ArrayList<String>> responseScripts = new HashMap<>();

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

                    JSONObject jsd = (JSONObject) jObj.get("Descriptions");
                    if (jsd != null)
                        for (Object xObj : jsd.keySet()) {
                            String key = (String) xObj;
                            descriptions.put(key.toLowerCase(), (String) jsd.get(key));
                        }

                    JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                    if (jsCU != null)
                        for (Object xObj : jsCU.keySet()) {
                            String key = (String) xObj;
                            complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                        }

                    JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                    if (jsRS != null) {
                        for (Object xObj : jsRS.keySet()) {
                            String key = (String) xObj;
                            JSONArray jsScripts = (JSONArray) jsRS.get(key);

                            ArrayList<String> scripts = new ArrayList<>();
                            scripts.addAll(jsScripts);
                            responseScripts.put(key.toLowerCase(), scripts);
                        }
                    }


                    Location location = new Location(fullName, id, fullName, alias, attributes, defaultEnter, defaultExit);
                    location.setDescriptions(descriptions);
                    location.setText(text);
                    location.setDefaultUse(defaultUse);

                    location.setComplexUse(complexUse);
                    location.setResponseScripts(responseScripts);


                    locationList.add(location);
                }
            } catch (Exception e) {
                System.out.println("There was an error generating Location: " + tryName);
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

            String tryName = "";
            try {
                for (Object obj : stationaryObjectJSON) {
                    JSONObject jObj = (JSONObject) obj;
                    String fullName = (String) jObj.get("FullName");

                    tryName = fullName;
                    String id = (String) jObj.get("Id");
                    //String description = (String) jObj.get("Description");
                    String location = (String) jObj.get("Location");
                    List<String> alias = new ArrayList<>();
                    List<String> attributes = new ArrayList<>();
                    String text = (String) jObj.get("Text");
                    String defaultUse = (String) jObj.get("DefaultUse");

                    String ownerName = (String) jObj.get("Owner");

                    Map<String, String> descriptions = new HashMap<>();
                    Map<String, String> complexUse = new HashMap<>();
                    Map<String, ArrayList<String>> responseScripts = new HashMap<>();

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

                    JSONObject jsd = (JSONObject) jObj.get("Descriptions");
                    if (jsd != null)
                        for (Object xObj : jsd.keySet()) {
                            String key = (String) xObj;
                            descriptions.put(key.toLowerCase(), (String) jsd.get(key));
                        }

                    JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                    if (jsCU != null)
                        for (Object xObj : jsCU.keySet()) {
                            String key = (String) xObj;
                            complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                        }

                    JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                    if (jsRS != null) {
                        for (Object xObj : jsRS.keySet()) {
                            String key = (String) xObj;
                            JSONArray jsScripts = (JSONArray) jsRS.get(key);

                            ArrayList<String> scripts = new ArrayList<>();
                            scripts.addAll(jsScripts);
                            responseScripts.put(key.toLowerCase(), scripts);
                        }
                    }

                    StationaryObject object = new StationaryObject(fullName, id, location, alias, attributes);
                    object.setDescriptions(descriptions);
                    object.setText(text);
                    object.setDefaultUse(defaultUse);

                    object.setComplexUse(complexUse);
                    object.setResponseScripts(responseScripts);
                    object.setOwnerName(ownerName);


                    stationaryObjectList.add(object);
                }
            } catch (Exception e) {
                System.out.println("There was an error generating StationaryObject: " + tryName);
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

            String tryName = "";
            try {
                for (Object obj : itemJSON) {
                    JSONObject jObj = (JSONObject) obj;
                    String fullName = (String) jObj.get("FullName");

                    tryName = fullName;

                    String id = (String) jObj.get("Id");
                    //String description = (String) jObj.get("Description");
                    String location = (String) jObj.get("Location");
                    List<String> alias = new ArrayList<>();
                    List<String> attributes = new ArrayList<>();
                    String text = (String) jObj.get("Text");
                    String defaultUse = (String) jObj.get("DefaultUse");

                    String ownerName = (String) jObj.get("Owner");

                    Map<String, String> descriptions = new HashMap<>();
                    Map<String, String> complexUse = new HashMap<>();
                    Map<String, ArrayList<String>> responseScripts = new HashMap<>();

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

                    JSONObject jsd = (JSONObject) jObj.get("Descriptions");
                    if (jsd != null)
                        for (Object xObj : jsd.keySet()) {
                            String key = (String) xObj;
                            descriptions.put(key.toLowerCase(), (String) jsd.get(key));
                        }

                    JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
                    if (jsCU != null)
                        for (Object xObj : jsCU.keySet()) {
                            String key = (String) xObj;
                            complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                        }

                    JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
                    if (jsRS != null) {
                        for (Object xObj : jsRS.keySet()) {
                            String key = (String) xObj;
                            JSONArray jsScripts = (JSONArray) jsRS.get(key);

                            ArrayList<String> scripts = new ArrayList<>();
                            scripts.addAll(jsScripts);
                            responseScripts.put(key.toLowerCase(), scripts);
                        }
                    }

                    Item item = new Item(fullName, id, location, alias, attributes);
                    item.setDescriptions(descriptions);
                    item.setText(text);
                    item.setDefaultUse(defaultUse);

                    item.setComplexUse(complexUse);
                    item.setResponseScripts(responseScripts);

                    item.setOwnerName(ownerName);

                    itemList.add(item);
                }
            } catch (Exception e) {
                System.out.println("There was an error generating Item: " + tryName);
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


    public static List<Exit> loadExitList(String filepath, List<Location> locationList) {

        List<Exit> exitList = new ArrayList<>();


        try (FileReader reader = new FileReader(filepath + "/exits.json")) {

            JSONArray exitJSON = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : exitJSON) {
                List<String> jObj = (List<String>) obj;
                Location[] locations = new Location[2];



                if (jObj.size() >= 2) {
                    for (Location loc : locationList) {
                        if (loc.getId().equalsIgnoreCase(jObj.get(0))) {
                            locations[0] = loc;
                        } else if (loc.getId().equalsIgnoreCase(jObj.get(1))) {
                            locations[1] = loc;
                        }
                        if (locations[0] != null && locations[1] != null) {
                            break;
                        }
                    }
                }

                boolean open = true;

                if (jObj.size() > 2)
                    if (jObj.get(2).equalsIgnoreCase("closed")) {
                        open = false;
                    }

                if(locations[0]!=null && locations[1]!=null) {
                    Exit exit = new Exit(locations, open);
                    exitList.add(exit);
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Exits file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the exits file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Exits file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }
        //System.out.println("Item list loaded from file.");

        return exitList;
    }


    //--------------------------------------------

    public static List<String> getTemplateItemNames() {

        List<String> templateItemNames = new ArrayList<>();

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateItems.json")) {


            JSONObject allItemsJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject
            templateItemNames.addAll(allItemsJSON.keySet());

            String tryName = "";
            try {
                for (Object key : ((JSONObject) allItemsJSON).keySet()) {

                    tryName = (String) key;

                    JSONObject jsObj = (JSONObject) allItemsJSON.get(key);
                    JSONArray jsAliases = (JSONArray) jsObj.get("Alias");

                    templateItemNames.addAll(jsAliases);
                }
            } catch (Exception e) {
                System.out.println("There was an error reading TemplateItem: " + tryName);
            }

        } catch (FileNotFoundException e) {
            System.out.println("TemplateItems file not found.");
        } catch (IOException e) {
            System.out.println("There was an error reading the TemplateItems file.");
        } catch (ParseException e) {
            System.out.println("TemplateItems file corrupt, or there was an error during the reading.");
        }

        return templateItemNames;
    }


    public static Item generateTemplateItem(String itemName) {

        Item item = null;

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateItems.json")) {

            JSONObject allItemsJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject

            JSONObject jsonItem = (JSONObject) allItemsJSON.get(itemName);  //Retrieve the jsonObject corresponding to that name.

            String tryName = itemName;
            try {

                //Only build the item if it exists. It should, since we can't get here if it doesn't, but just in case.
                //So if we get a null back, we can't build anything.
                if (jsonItem != null) {
                    String fullName = itemName;
                    String id = (String) jsonItem.get("Id");
                    //String description = (String) jsonItem.get("Description");
                    String locationName = "blank";

                    JSONArray jsAlias = (JSONArray) jsonItem.get("Alias");
                    JSONArray jsAttributes = (JSONArray) jsonItem.get("Attributes");

                    List<String> alias = new ArrayList<>();
                    for (Object ali : jsAlias) {
                        alias.add((String) ali);
                    }

                    List<String> attributes = new ArrayList<>();
                    for (Object attr : jsAttributes) {
                        attributes.add((String) attr);
                    }

                    String text = (String) jsonItem.get("Text");
                    String defaultUse = (String) jsonItem.get("DefaultUse");

                    JSONObject jsComplexUse = (JSONObject) jsonItem.get("ComplexUse");
                    JSONObject jsDescriptions = (JSONObject) jsonItem.get("Descriptions");

                    JSONObject jsResponseScripts = (JSONObject) jsonItem.get("ResponseScripts");
                    Map<String, ArrayList<String>> responseScripts = new HashMap<>();

                    if (jsResponseScripts != null) {
                        for (Object keyObj : jsResponseScripts.keySet()) {
                            String key = (String) keyObj;
                            JSONArray Scripts = (JSONArray) jsResponseScripts.get(key);
                            if (Scripts != null) {
                                List<String> tempArray = new ArrayList<>();
                                for (Object xObj2 : Scripts) {
                                    tempArray.add((String) xObj2);
                                }

                                responseScripts.put(key.toLowerCase(), (ArrayList<String>) Scripts);
                            }
                        }
                    }


                    Map<String, String> complexUse = new HashMap<>();
                    for (Object keyObj : jsComplexUse.keySet()) {
                        String key = (String) keyObj;
                        complexUse.put(key.toLowerCase(), (String) jsComplexUse.get(key));
                    }

                    Map<String, String> descriptions = new HashMap<>();
                    for (Object keyObj : jsDescriptions.keySet()) {
                        String key = (String) keyObj;
                        descriptions.put(key.toLowerCase(), (String) jsDescriptions.get(key));
                    }


                    //String name, String id, String description, String locationName, List<String> alias, List<String> attributes

                    item = new Item(fullName, id, locationName, alias, attributes);
                    item.setText(text);
                    item.setDefaultUse(defaultUse);
                    item.setResponseScripts(responseScripts);
                    item.setComplexUse(complexUse);
                    item.setDescriptions(descriptions);
                }
            } catch (Exception e) {
                System.out.println("There was an error reading TemplateItem: " + tryName);
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


        return item;
    }






    public static List<Dialog> generateDialogList() {


        List<Dialog> dialogList = new ArrayList<>();


        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/dialogs.json")) {

            JSONObject dialogJSON = (JSONObject) new JSONParser().parse(reader);

            for (Object obj : dialogJSON.keySet()) {

                String tryName = "";
                try {

                    String key = (String) obj;
                    tryName = key;
                    String text;
                    List<String> scripts = new ArrayList<>();



                    JSONObject dialog = (JSONObject) dialogJSON.get(key);

                    text = (String) dialog.get("Line");

                    JSONArray jsScripts = (JSONArray) dialog.get("Scripts");


                    if(jsScripts!=null) {
                        for (Object script: jsScripts) {
                            scripts.add((String) script);
                        }

                    }
                    else {
                        scripts = null;
                    }

                    JSONObject jsResponses = (JSONObject) dialog.get("Responses");

                    String[][] responses = new String[jsResponses.size()][2];

                    for (int i = 0; i < jsResponses.size(); i++) {
                        JSONArray responseArray = (JSONArray) jsResponses.get(""+i);

                        responses[i][0] = (String) responseArray.get(0);
                        responses[i][1] = (String) responseArray.get(1);


                    }




                    Dialog dialogEntry = new Dialog(key, text, responses);
                    dialogEntry.setScripts((ArrayList<String>) scripts);

                    dialogList.add(dialogEntry);

                } catch (Exception e) {
                    System.out.println("There was an error generating Creature: " + tryName);
                }
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

        return dialogList;
    }



}
