package jonst.Models;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    @Test
    public void BuildCheck() {


            String filepath = SystemData.getDefaultWorld();

            List<Creature> creatureList = new ArrayList<>();


            try (FileReader reader = new FileReader(filepath + "/creatures.json")) {

                JSONArray creatureJSON = (JSONArray) new JSONParser().parse(reader);

                for (Object obj : creatureJSON) {
                    JSONObject jObj = (JSONObject) obj;
                    String fullName = (String) jObj.get("FullName");
                    String id = (String) jObj.get("Id");
                    //String description = (String) jObj.get("Description");
                    String race = (String) jObj.get("Race");
                    String gender = (String) jObj.get("Gender");
                    String location = (String) jObj.get("Location");
                    String text = (String) jObj.get("Text");
                    String defaultUse = (String) jObj.get("DefaultUse");

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
                    if (jsRS != null){
                        for (Object xObj : jsRS.keySet()) {
                            String key = (String) xObj;
                            JSONArray jsScripts = (JSONArray) jsRS.get(key);

                            System.out.println(jsScripts);

                            ArrayList<String> scripts = new ArrayList<>();
                            scripts.addAll(jsScripts);
                            responseScripts.put(key.toLowerCase(), scripts);
                        }
                    }


                    Creature creature = new Creature(fullName, id, location.toLowerCase(), alias, attributes, race.toLowerCase(), gender.toLowerCase(), casualDialog, askTopics);
                    creature.setDescriptions(descriptions);
                    creature.setText(text);
                    creature.setDefaultUse(defaultUse);

                    creature.setComplexUse(complexUse);
                    creature.setResponseScripts(responseScripts);

                    creature.setBehaviorCore(bc);

                    creatureList.add(creature);
                }


                System.out.println("Stop here.");

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

       }



}
