package jonst;

import jonst.Data.Lambda;
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

    //----------------------------

    public static JSONObject saveJsonGeneric(GenericObject gen) {
        return new JSONObject() {{

            //Generic attributes here
            put("FullName", gen.getName());
            put("ShortName", gen.getShortName());
            put("Type", gen.getType());
            put("Id", gen.getId());
            put("Location", gen.getHolderId());
            put("DefaultLocation", gen.getDefaultLocationId());

            put("Text", gen.getText());
            put("DefaultUse", gen.getDefaultUse());
            put("OwnerId", gen.getOwnerId());

            if (gen.getOwner() != null) {
                put("Owner", gen.getOwner().getName());
            }
            put("Alias", new JSONArray() {{
                for (String alias : gen.getAlias()) {
                    add(alias);
                }
            }});
            put("Attributes", new JSONArray() {{
                for (String attribute : gen.getAttributes()) {
                    add(attribute);
                }
            }});
            put("Descriptions", new JSONObject() {{
                for (String key : gen.getDescriptions().keySet()) {
                    put(key, gen.getDescriptions().get(key));
                }
            }});
            put("ComplexUse", new JSONObject() {{
                for (String key : gen.getComplexUse().keySet()) {
                    put(key, gen.getComplexUse().get(key));
                }
            }});
            put("ResponseScripts", new JSONObject() {{
                for (String key : gen.getResponseScripts().keySet()) {
                    JSONArray scriptArray = new JSONArray() {{
                        for (String script : gen.getResponseScripts().get(key)) {
                            add(script);
                        }
                    }};
                    put(key, scriptArray);
                }
            }});

            if (gen instanceof Creature) {

                //Creature-specific attributes here
                put("InitialDialog", ((Creature) gen).getInitialDialog());
                put("Race", ((Creature) gen).getRace());
                put("DefaultRace", ((Creature) gen).getDefaultRace());
                put("Gender", gen.getGender());
                put("CasualDialog", new JSONArray() {{
                    for (String dialog : ((Creature) gen).getCasualDialog()) {
                        add(dialog);
                    }
                }});

                put("AskTopics", new JSONObject() {{
                    for (String key : ((Creature) gen).getAskTopics().keySet()) {
                        put(key, ((Creature) gen).getAskTopics().get(key));
                    }
                }});

                put("BehaviorCore", new JSONObject() {{
                    put("mood", ((Creature) gen).getMood());
                    put("activity", ((Creature) gen).getActivity());
                    put("allegiance", ((Creature) gen).getAllegiance());
                    put("status", ((Creature) gen).getStatus());
                }});

                if(gen instanceof Merchant){
                    put("Merchandise", new JSONArray() {{
                        for (String ware : ((Merchant) gen).getMerchandiseIds()) {
                            add(ware);
                        }
                    }});
                }

            } else if (gen instanceof Location) {

                //Location-specific attriibutes here
                if (((Location) gen).getDefaultEnter() != null)
                    put("DefaultEnter", ((Location) gen).getDefaultEnter().getId());
                if (((Location) gen).getDefaultExit() != null)
                    put("DefaultExit", ((Location) gen).getDefaultExit().getId());
            } else if (gen instanceof Item) {
                //Reserved space in case items get more stuff



            } else if (gen instanceof StationaryObject) {
                //Reserved space in case SOs get more stuff

                if(gen instanceof Vehicle){
                    put("Destinations", ((Vehicle) gen).getDestinationIds());
                }
            }
        }};


    }

    //---------- save methods

    public static <T extends GenericObject> boolean saveGenericList(String filepath, List<T> list) {

        boolean success = true;
        JSONArray array = new JSONArray();

        for (T t : list) {        //This creates one JSONObject for every creature in the list, populates it with data, and adds it to "creatures"
            array.add(saveJsonGeneric(t));
        }

        try (FileWriter file = new FileWriter(filepath)) {

            file.write(array.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean saveExitList(String filepath, List<Exit> exitList) {

        boolean success = true;
        JSONArray objectArray = new JSONArray();

        for (Exit exit : exitList) {        //This creates one JSONObject for every object in the list, populates it with data, and adds it to "objects"
            objectArray.add(new JSONArray() {{

                add(exit.getLocations()[0].getId());
                add(exit.getLocations()[1].getId());

                if (exit.isOpen()) {
                    add("open");
                } else {
                    add("closed");
                }

            }});
        }

        try (FileWriter file = new FileWriter(filepath + "/exits.json")) {

            file.write(objectArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    //---------- Get objects

    public static Map<String, String> getMap(JSONObject MainObj, String key){

        Map<String, String> map = new HashMap();
        Object obj = MainObj.get(key);
            if (obj != null && obj instanceof JSONObject) {
                for (Object InternalKey : ((JSONObject) obj).keySet()) {
                    map.put(((String) InternalKey).toLowerCase(), (String) ((JSONObject) obj).get((String) InternalKey));
                }
            }
        return map;
    }

    public static List<String> getList(JSONObject MainObj, String key){

        List<String> list = new ArrayList<>();

        Object arr = MainObj.get(key);



        if (arr != null && arr instanceof JSONArray) {
            for (Object xObj : (JSONArray) arr) {

                list.add(((String) xObj).toLowerCase());
            }
        }
        return list;
    }





    //---------- load methods

    public static GenericObject loadGenericFromJson(JSONObject jObj, String typeKey) {
        //Generic

        String fullName = (String) jObj.get("FullName");
        String shortName = (String) jObj.get("ShortName");

        if(shortName == null){
            shortName = fullName;
        }

        String type = (String) jObj.get("Type");
        String id = (String) jObj.get("Id");
        String location = (String) jObj.get("Location");
        String defaultLocation = (String) jObj.get("DefaultLocation");

        if (defaultLocation == null) {        //If there's no specified defaultLocation, set current location to default.
            defaultLocation = location;     //Since the default world is the "start" anyway, this works fine.
        }

        String text = (String) jObj.get("Text");
        String defaultUse = (String) jObj.get("DefaultUse");

        Map<String, String> descriptions = getMap(jObj, "Descriptions");
        Map<String, String> complexUse = getMap(jObj, "ComplexUse");


//                new HashMap() {{
//            JSONObject jsCU = (JSONObject) jObj.get("ComplexUse");
//            if (jsCU != null)
//                for (Object xObj : jsCU.keySet()) {
//                    String key = (String) xObj;
//                    put(key.toLowerCase(), (String) jsCU.get(key));
//                }
//        }};

        Map<String, ArrayList<String>> responseScripts = new HashMap() {{
            JSONObject jsRS = (JSONObject) jObj.get("ResponseScripts");
            if (jsRS != null) {
                for (Object xObj : jsRS.keySet()) {
                    String key = (String) xObj;
                    JSONArray jsScripts = (JSONArray) jsRS.get(key);

                    ArrayList<String> scripts = new ArrayList<>();
                    scripts.addAll(jsScripts);
                    put(key.toLowerCase(), scripts);
                }
            }
        }};

        List<String> alias = getList(jObj, "Alias");

        if(!alias.contains(shortName)){
            alias.add(shortName);
        }

//                new ArrayList() {{
//            JSONArray jsAlias = (JSONArray) jObj.get("Alias");
//            if (jsAlias != null)
//                for (Object xObj : jsAlias) {
//                    String newAlias = (String) xObj;
//                    add(newAlias.toLowerCase());
//                }
//        }};



        List<String> attributes = getList(jObj, "Attributes");


//                new ArrayList() {{
//            JSONArray jsAttributes = (JSONArray) jObj.get("Attributes");
//            if (jsAttributes != null)
//                for (Object xObj : jsAttributes) {
//                    String newAttribute = (String) xObj;
//                    add(newAttribute.toLowerCase());
//                }
//        }};


        if (typeKey.equals("creature")) {
            //Creature-specific
            String race = (String) jObj.get("Race");
            String defaultRace = (String) jObj.get("DefaultRace");
            String gender = (String) jObj.get("Gender");
            String initialDialog = (String) jObj.get("InitialDialog");

            Map<String, String> askTopics = getMap(jObj, "AskTopics");

//                    new HashMap() {{
//                JSONObject jsAT = (JSONObject) jObj.get("AskTopics");
//                if (jsAT != null)
//                    for (Object xObj : jsAT.keySet()) {
//                        String key = (String) xObj;
//                        put(key.toLowerCase(), (String) jsAT.get(key));
//                    }
//            }};

            List<String> casualDialog = getList(jObj, "CasualDialog");



//                    new ArrayList() {{
//                JSONArray jsCD = (JSONArray) jObj.get("CasualDialog");
//                if (jsCD != null)
//                    for (Object xObj : jsCD) {
//                        add((String) xObj);
//                    }
//            }};

            BehaviorCore bc;
            JSONObject jsBehaviorCore = (JSONObject) jObj.get("BehaviorCore");

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

                bc = new BehaviorCore(mood, activity, allegiance, status, personalQuotes);

            } else {
                bc = new BehaviorCore();        //If a creature has no stated BC, it gets a default one.
            }

            JSONArray jsonMerch = (JSONArray) jObj.get("Merchandise");

            if(jsonMerch != null){

                List<String> merchandiseList = new ArrayList() {{

                    addAll(jsonMerch);

                }};

                return new Merchant(fullName, shortName, type, id, location, defaultLocation, alias, attributes, race, defaultRace,
                        gender, casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, null, bc, initialDialog, merchandiseList);

            }
            return new Creature(fullName, shortName, type, id, location, defaultLocation, alias, attributes, race, defaultRace,
                    gender, casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, null, bc, initialDialog);



        } else if (typeKey.equals("item")) {

            String ownerId = (String) jObj.get("OwnerId");

            Item test = new Item(fullName, shortName, type, id, location, defaultLocation, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerId);

            return test;

        } else if (typeKey.equals("location")) {

            String defaultEnter = (String) jObj.get("DefaultEnter");
            String defaultExit = (String) jObj.get("DefaultExit");

            return new Location(fullName, shortName, type, id, fullName, null, alias, attributes, defaultEnter, defaultExit, descriptions,
                    text, defaultUse, complexUse, responseScripts, null);

        } else if (typeKey.equals("stationaryobject")) {
            String ownerId = (String) jObj.get("OwnerId");

            JSONArray jsDestinations = (JSONArray) jObj.get("Destinations");

            if(jsDestinations != null){
                //If it has destinations, it's a vehicle

                List<String> destinations = new ArrayList() {{
                    for (Object dest: jsDestinations) {
                        add((String) dest);
                    }
                }};

                return new Vehicle(fullName, shortName, type, id, location, defaultLocation, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerId, destinations);

            }

            return new StationaryObject(fullName, shortName, type, id, location, defaultLocation, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerId);

        }


        return null;
    }




    //----------------
    public static List<Creature> loadCreatureList(String filepath) {

        List<Creature> creatureList = new ArrayList<>();

        try (FileReader reader = new FileReader(filepath + "/creatures.json")) {

            JSONArray creatureJSON = (JSONArray) new JSONParser().parse(reader);
            Lambda.processList(creatureJSON, c -> creatureList.add((Creature) loadGenericFromJson((JSONObject) c, "creature")));

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
            Lambda.processList(locationJSON, l -> locationList.add((Location) loadGenericFromJson((JSONObject) l, "location")));

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
            Lambda.processList(stationaryObjectJSON, s -> stationaryObjectList.add((StationaryObject) loadGenericFromJson((JSONObject) s, "stationaryobject")));

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
            Lambda.processList(itemJSON, i -> itemList.add((Item) loadGenericFromJson((JSONObject) i, "item")));

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

                if (locations[0] != null && locations[1] != null) {
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

    //-------------------------------------------------

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


                    if (jsScripts != null) {
                        for (Object script : jsScripts) {
                            scripts.add((String) script);
                        }

                    } else {
                        scripts = null;
                    }

                    JSONObject jsResponses = (JSONObject) dialog.get("Responses");

                    String[][] responses = new String[jsResponses.size()][2];

                    for (int i = 0; i < jsResponses.size(); i++) {
                        JSONArray responseArray = (JSONArray) jsResponses.get("" + i);

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

    //--------------------------------------------
/*

    public static List<String> getItemNamesFromTemplateId(String id){

        Item item = generateTemplateItem(id);

        List<String> returns = new ArrayList<>(item.getAlias());
        returns.add(item.getName());


        return returns;
    }

*/

/*    public static List<String> getTemplateItemNames() {

        List<String> templateItemNames = new ArrayList<>();

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateItems.json")) {


            JSONObject allItemsJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject


            String tryName = "";
            try {
                for (Object key : ((JSONObject) allItemsJSON).keySet()) {

                    tryName = (String) key;
                    JSONObject jsObj = (JSONObject) allItemsJSON.get(key);

                    String name = (String) jsObj.get("FullName");
                    JSONArray jsAliases = (JSONArray) jsObj.get("Alias");

                    templateItemNames.add(name);
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

            String testId = null;

            for (Object tmpObj : allItemsJSON.values()) {       //Check each object

                if (((String) ((JSONObject) tmpObj).get("FullName")).equalsIgnoreCase(itemName)) {
                    testId = (String) ((JSONObject) tmpObj).get("Id");
                    break;
                } else {
                    JSONArray aliases = (JSONArray) ((JSONObject) tmpObj).get("Alias");

                    for (Object alias : aliases) {
                        if (((String) alias).equalsIgnoreCase(itemName)) {
                            testId = (String) ((JSONObject) tmpObj).get("Id");
                            break;
                        }
                    }
                }
            }

            JSONObject jsonItem = (JSONObject) allItemsJSON.get(testId);  //Retrieve the jsonObject corresponding to that name.

            if (jsonItem != null) {
                String fullName = (String) jsonItem.get("FullName");
                String shortName = (String) jsonItem.get("ShortName");

                if(shortName == null){
                    shortName = fullName;
                }

                String id = (String) jsonItem.get("Id");
                String type = (String) jsonItem.get("Type");
                String defaultLocation = (String) jsonItem.get("DefaultLocation");

                String locationId = "blank";

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

                String ownerName = (String) jsonItem.get("Owner");
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

                item = new Item(fullName, shortName, type, id, locationId, defaultLocation, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerName);

            }

        } catch (FileNotFoundException e) {
            System.out.println("TemplateItems file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the TemplateItems file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("TemplateItems file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }


        return item;
    }

    public static Creature generateTemplateCreature(String creatureId) {

        Creature creature = null;

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateCreatures.json")) {

            JSONObject allCreaturesJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject


            JSONObject jsonCreature = (JSONObject) allCreaturesJSON.get(creatureId);  //Retrieve the jsonObject corresponding to that Id.

            if (jsonCreature != null) {
                String fullName = (String) jsonCreature.get("FullName");
                String shortName = (String) jsonCreature.get("ShortName");

                if(shortName == null){
                    shortName = fullName;
                }

                String id = (String) jsonCreature.get("Id");
                String type = (String) jsonCreature.get("Type");
                String defaultLocation = (String) jsonCreature.get("DefaultLocation");

                String locationId = "blank";
                String race = (String) jsonCreature.get("Race");
                String gender = (String) jsonCreature.get("Gender");
                String text = (String) jsonCreature.get("Text");

                String ownerName = (String) jsonCreature.get("Owner");

                String defaultRace = (String) jsonCreature.get("DefaultRace");
                String defaultUse = (String) jsonCreature.get("DefaultUse");

                String initialDialog = (String) jsonCreature.get("InitialDialog");


                JSONArray jsAlias = (JSONArray) jsonCreature.get("Alias");
                List<String> alias = new ArrayList<>();
                for (Object ali : jsAlias) {
                    alias.add((String) ali);
                }

                JSONArray jsAttributes = (JSONArray) jsonCreature.get("Attributes");
                List<String> attributes = new ArrayList<>();
                for (Object attr : jsAttributes) {
                    attributes.add((String) attr);
                }

                JSONObject jsd = (JSONObject) jsonCreature.get("Descriptions");
                Map<String, String> descriptions = new HashMap<>();
                if (jsd != null)
                    for (Object xObj : jsd.keySet()) {
                        String key = (String) xObj;
                        descriptions.put(key.toLowerCase(), (String) jsd.get(key));
                    }

                JSONObject jsAT = (JSONObject) jsonCreature.get("AskTopics");
                Map<String, String> askTopics = new HashMap<>();
                if (jsAT != null)
                    for (Object xObj : jsAT.keySet()) {
                        String key = (String) xObj;
                        askTopics.put(key.toLowerCase(), (String) jsAT.get(key));
                    }

                JSONObject jsCU = (JSONObject) jsonCreature.get("ComplexUse");
                Map<String, String> complexUse = new HashMap<>();
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jsonCreature.get("ResponseScripts");
                Map<String, ArrayList<String>> responseScripts = new HashMap<>();
                if (jsRS != null) {
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        JSONArray jsScripts = (JSONArray) jsRS.get(key);

                        ArrayList<String> scripts = new ArrayList<>();
                        scripts.addAll(jsScripts);
                        responseScripts.put(key.toLowerCase(), scripts);
                    }
                }

                JSONArray jsCD = (JSONArray) jsonCreature.get("CasualDialog");
                List<String> casualDialog = new ArrayList<>();
                if (jsCD != null)
                    for (Object xObj : jsCD) {
                        casualDialog.add((String) xObj);
                    }


                JSONObject jsBehaviorCore = (JSONObject) jsonCreature.get("BehaviorCore");
                BehaviorCore bc;
                if (jsBehaviorCore != null) {
                    String mood = (String) jsBehaviorCore.get("mood");
                    String activity = (String) jsBehaviorCore.get("activity");
                    String allegiance = (String) jsBehaviorCore.get("allegiance");
                    String status = (String) jsBehaviorCore.get("status");

                    Map<String, String> personalQuotes = new HashMap() {{
                        put("thanks", "\"Thanks!\"");
                        put("yes", "\"Yes.\"");
                        put("no", "\"No.\"");
                        put("angryprotest", "\"Cut that out!\"");
                        put("okay", "\"Okay.\"");
                    }};


                    JSONObject jsPersonalQuotes = (JSONObject) jsBehaviorCore.get("PersonalQuotes");
                    if (jsPersonalQuotes != null) {
                        for (Object xObj : jsPersonalQuotes.keySet()) {
                            String key = (String) xObj;
                            personalQuotes.put(key.toLowerCase(), (String) jsPersonalQuotes.get(key));
                        }
                    }
                    bc = new BehaviorCore(mood, activity, allegiance, status, personalQuotes);
                } else {
                    bc = new BehaviorCore();        //If a creature has no stated BC, it gets a default one.
                }


                creature = new Creature(fullName, shortName, type, id, locationId, defaultLocation, alias, attributes, race.toLowerCase(), defaultRace.toLowerCase(), gender.toLowerCase(), casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, ownerName, bc, initialDialog);


            }

        } catch (FileNotFoundException e) {
            System.out.println("TemplateCreatures file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the TemplateCreatures file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("TemplateCreatures file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }


        return creature;
    }

    public static StationaryObject generateTemplateObject(String objectId) {

        StationaryObject object = null;

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateObjects.json")) {

            JSONObject allObjectsJSON = (JSONObject) new JSONParser().parse(reader);  //Get all data as a jsonObject


            JSONObject jsonObject = (JSONObject) allObjectsJSON.get(objectId);  //Retrieve the jsonObject corresponding to that Id.

            if (jsonObject != null) {
                String fullName = (String) jsonObject.get("FullName");
                String shortName = (String) jsonObject.get("ShortName");

                if(shortName == null){
                    shortName = fullName;
                }

                String id = (String) jsonObject.get("Id");
                String type = (String) jsonObject.get("Type");
                String defaultLocation = (String) jsonObject.get("DefaultLocation");

                String locationId = "blank";

                String text = (String) jsonObject.get("Text");

                String defaultUse = (String) jsonObject.get("DefaultUse");

                String ownerName = (String) jsonObject.get("Owner");


                JSONArray jsAlias = (JSONArray) jsonObject.get("Alias");
                List<String> alias = new ArrayList<>();
                for (Object ali : jsAlias) {
                    alias.add((String) ali);
                }

                JSONArray jsAttributes = (JSONArray) jsonObject.get("Attributes");
                List<String> attributes = new ArrayList<>();
                for (Object attr : jsAttributes) {
                    attributes.add((String) attr);
                }

                JSONObject jsd = (JSONObject) jsonObject.get("Descriptions");
                Map<String, String> descriptions = new HashMap<>();
                if (jsd != null)
                    for (Object xObj : jsd.keySet()) {
                        String key = (String) xObj;
                        descriptions.put(key.toLowerCase(), (String) jsd.get(key));
                    }


                JSONObject jsCU = (JSONObject) jsonObject.get("ComplexUse");
                Map<String, String> complexUse = new HashMap<>();
                if (jsCU != null)
                    for (Object xObj : jsCU.keySet()) {
                        String key = (String) xObj;
                        complexUse.put(key.toLowerCase(), (String) jsCU.get(key));
                    }

                JSONObject jsRS = (JSONObject) jsonObject.get("ResponseScripts");
                Map<String, ArrayList<String>> responseScripts = new HashMap<>();
                if (jsRS != null) {
                    for (Object xObj : jsRS.keySet()) {
                        String key = (String) xObj;
                        JSONArray jsScripts = (JSONArray) jsRS.get(key);

                        ArrayList<String> scripts = new ArrayList<>();
                        scripts.addAll(jsScripts);
                        responseScripts.put(key.toLowerCase(), scripts);
                    }
                }


                object = new StationaryObject(fullName, shortName, type, id, locationId, defaultLocation, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerName);


            }

        } catch (FileNotFoundException e) {
            System.out.println("TemplateObjects file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an error reading the TemplateObjects file.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("TemplateObjects file corrupt, or there was an error during the reading.");
            e.printStackTrace();
        }


        return object;
    }
*/


    public static List<GenericObject> generateTemplateList(){

        List<GenericObject> templateList = new ArrayList<>();

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateItems.json")) {
            JSONArray itemJSON = (JSONArray) new JSONParser().parse(reader);
            Lambda.processList(itemJSON, t -> templateList.add((Item) loadGenericFromJson((JSONObject) t, "item")));

        } catch (IOException | ParseException e) {
            System.out.println("There was an error reading the file.");
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateCreatures.json")) {
            JSONArray creatureJSON = (JSONArray) new JSONParser().parse(reader);

            Lambda.processList(creatureJSON, t -> templateList.add((Creature) loadGenericFromJson((JSONObject) t, "creature")));

        } catch (IOException | ParseException e) {
            System.out.println("There was an error reading the file.");
            e.printStackTrace();
        }


        try (FileReader reader = new FileReader(SystemData.getGamepath() + "/Data/Json/TemplateObjects.json")) {
            JSONArray objectJSON = (JSONArray) new JSONParser().parse(reader);
            Lambda.processList(objectJSON, t -> templateList.add((StationaryObject) loadGenericFromJson((JSONObject) t, "stationaryobject")));

        } catch (IOException | ParseException e) {
            System.out.println("There was an error reading the file.");
            e.printStackTrace();
        }

        return templateList;
    }


}

