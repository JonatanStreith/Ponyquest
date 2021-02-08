package jonst;

import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.*;
import jonst.Models.Cores.*;
import jonst.Models.Objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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


        } catch (IOException | ParseException e) {
            System.out.println("There was an error reading the saverecord.");
            return null;            //Return null to confirm that something went wrong!
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

            put("Alias", gen.getAlias());
            put("Attributes", gen.getAttributes());
            put("Descriptions", gen.getDescriptions());
            put("ComplexUse", gen.getComplexUse());
            put("ResponseScripts", gen.getResponseScripts());

//            put("ResponseScripts", new JSONObject() {{
//                for (String key : gen.getResponseScripts().keySet()) {
//                    JSONArray scriptArray = new JSONArray() {{
//                        for (String script : gen.getResponseScripts().get(key)) {
//                            add(script);
//                        }
//                    }};
//                    put(key, scriptArray);
//                }
//            }});

            if (gen instanceof Creature) {

                //Creature-specific attributes here
                put("InitialDialog", ((Creature) gen).getInitialDialog());
                put("Race", ((Creature) gen).getRace());
                put("DefaultRace", ((Creature) gen).getDefaultRace());
                put("Gender", gen.getGender());
                put("CasualDialog", ((Creature) gen).getCasualDialog());

                put("AskTopics", ((Creature) gen).getAskTopics());

                put("BehaviorCore", new JSONObject() {{
                    put("mood", ((Creature) gen).getMood());
                    put("activity", ((Creature) gen).getActivity());
                    put("allegiance", ((Creature) gen).getAllegiance());
                    put("status", ((Creature) gen).getStatus());
                }});

                if(gen instanceof Merchant){
                    put("Merchandise", ((Merchant) gen).getMerchandiseIds());
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

                add(exit.getConnectsLocations()[0].getId());
                add(exit.getConnectsLocations()[1].getId());

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
                for (Object internalKey : ((JSONObject) obj).keySet()) {
                    map.put(((String) internalKey).toLowerCase(), (String) ((JSONObject) obj).get((String) internalKey));
                }
            }
        return map;
    }

    public static Map<String, ArrayList<String>> getSubMap(JSONObject MainObj, String key){
        Map<String, ArrayList<String>> map = new HashMap();
        Object obj = MainObj.get("ResponseScripts");
        if (obj != null && obj instanceof JSONObject) {
            for (Object internalKey : ((JSONObject) obj).keySet()) {

                JSONArray arr = (JSONArray) ((JSONObject) obj).get((String) internalKey);

                ArrayList<String> lines = new ArrayList<>();
                lines.addAll(arr);
                map.put(((String) internalKey).toLowerCase(), lines);

            }
        }
        return map;
    }

    public static List<String> getList(JSONObject MainObj, String key){

        List<String> list = new ArrayList<>();

        Object arr = MainObj.get(key);

        if (arr != null && arr instanceof JSONArray) {
            list.addAll((JSONArray) arr);

//            for (Object xObj : (JSONArray) arr) {
//                 list.add(((String) xObj).toLowerCase());
//            }
        }
        return list;
    }





    //---------- load methods

    public static GenericObject loadGenericFromJson(JSONObject jObj, String typeKey) {
        //Generic

        IdentityCore identityCore = new IdentityCore(
                (String) jObj.get("FullName"),
                (String) jObj.get("ShortName"),
                (String) jObj.get("Type"),
                (String) jObj.get("Id"),
                getList(jObj, "Alias"),
                getMap(jObj, "Descriptions")
        ){{
                    if(getShortName() == null)
                        setShortName(getName());

            if(!getAlias().contains(getShortName()))
                getAlias().add(getShortName());
        }};

        RelationCore relationCore = new RelationCore(
                (String) jObj.get("Location"),
                (String) jObj.get("DefaultLocation"),
                (String) jObj.get("OwnerId")
        ){{
            if (getDefaultLocationId() == null)         //If there's no specified defaultLocation, set current location to default.
                setDefaultLocationId(getLocationId());     //Since the default world is the "start" anyway, this works fine.
        }};

        ActionCore actionCore = new ActionCore(
                getList(jObj, "Attributes"),
                (String) jObj.get("Text"),
                (String) jObj.get("DefaultUse"),
                getMap(jObj, "ComplexUse"),
                getSubMap(jObj, "ResponseScripts")
        );


        if (typeKey.equals("creature")) {
            //Creature-specific
            CreatureCore creatureCore = new CreatureCore(
                    (String) jObj.get("Race"),
                    (String) jObj.get("DefaultRace"),
                    (String) jObj.get("Gender")
            );

            SpeechCore speechCore = new SpeechCore(
                    getList(jObj, "CasualDialog"),
                    getMap(jObj, "AskTopics"),
                    (String) jObj.get("InitialDialog")
            );

            BehaviorCore behaviorCore;
            JSONObject jsBehaviorCore = (JSONObject) jObj.get("BehaviorCore");

            if (jsBehaviorCore != null) {
                String mood = (String) jsBehaviorCore.get("mood");
                String activity = (String) jsBehaviorCore.get("activity");
                String allegiance = (String) jsBehaviorCore.get("allegiance");
                String status = (String) jsBehaviorCore.get("status");
                Map<String, String> personalQuotes = getMap(jsBehaviorCore, "PersonalQuotes");
                behaviorCore = new BehaviorCore(mood, activity, allegiance, status, personalQuotes);
            } else {
                behaviorCore = new BehaviorCore();        //If a creature has no stated BC, it gets a default one.
            }

            JSONArray jsonMerch = (JSONArray) jObj.get("Merchandise");

            if(jsonMerch != null){
                List<String> merchandiseList = new ArrayList() {{
                    addAll(jsonMerch);
                }};
                return new Merchant(identityCore, relationCore, actionCore, creatureCore,
                        speechCore, behaviorCore, merchandiseList);
            }
            return new Creature(identityCore, relationCore, actionCore, creatureCore,
                    speechCore, behaviorCore);
        }

        if (typeKey.equals("item")) {
            return new Item(identityCore, relationCore, actionCore);
        }

        if (typeKey.equals("location")) {

            String defaultEnter = (String) jObj.get("DefaultEnter");
            String defaultExit = (String) jObj.get("DefaultExit");

                return new Location(identityCore, relationCore, actionCore, defaultEnter, defaultExit);
        }

        if (typeKey.equals("stationaryobject")) {

            JSONArray jsDestinations = (JSONArray) jObj.get("Destinations");

            if(jsDestinations != null){
                //If it has destinations, it's a vehicle
                List<String> destinations = new ArrayList() {{
                    addAll(jsDestinations);
                }};

                return new Vehicle(identityCore, relationCore, actionCore, destinations);
            }
            return new StationaryObject(identityCore, relationCore, actionCore);
        }
        return null;
    }

    //----------------

    public static <T> List<T> loadList(String filepath, String key) {

        List<T> list = new ArrayList<>();

        try (FileReader reader = new FileReader(filepath)) {

            JSONArray array = (JSONArray) new JSONParser().parse(reader);
            Lambda.processList(array, c -> list.add((T) loadGenericFromJson((JSONObject) c, key)));

        } catch (IOException | ParseException e) {
            System.out.println("File error during the reading.");
            e.printStackTrace();
        }

        return list;
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



                    String key = (String) obj;
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

