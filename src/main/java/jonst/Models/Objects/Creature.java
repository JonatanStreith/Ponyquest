package jonst.Models.Objects;

import jonst.Data.Lambda;
import jonst.HelpfulMethods;
import jonst.Models.BehaviorCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jonst.HelpfulMethods.*;


public class Creature extends GenericObject {

    private String race;
    private List<String> casualDialog;
    private String gender;

    private Map<String, String> askTopics;
    private BehaviorCore behavior;
    private final String defaultRace;
    private String initialDialog;

    public Creature(String name, String type, String id, String locationId, List<String> alias, List<String> attributes, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName, BehaviorCore bc, String initialDialog) {
        super(name, type, id, locationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerName);



        setRace(race);
        this.defaultRace = defaultRace;
        setGender(gender);
        setCasualDialog(casualDialog);
        setAskTopics(askTopics);
        setBehaviorCore(bc);
        setInitialDialog(initialDialog);

    }

    public String getInitialDialog() {
        return initialDialog;
    }

    public void setInitialDialog(String initialDialog) {
        this.initialDialog = initialDialog;
    }

    public void setBehaviorCore(BehaviorCore behavior) {
        this.behavior = behavior;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDefaultRace() {
        return defaultRace;
    }



    public void setGender(String gender) {
        this.gender = gender;
    }

    private void setCasualDialog(List<String> casualDialog) {
        this.casualDialog = casualDialog;
    }

    public void setAskTopics(Map<String, String> askTopics) {
        this.askTopics = askTopics;
    }


    public String getRace() {
        return race;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getCasualDialog() {
        return casualDialog;
    }

    public Map<String, String> getAskTopics() {
        return askTopics;
    }


    public String getRandomCasualDialog() {
        if(casualDialog != null && casualDialog.size()>0) {
            return casualDialog.get((int) Math.floor(Math.random() * casualDialog.size()));
        } else
            return capitalize(heOrShe(this)) + " doesn't have anything to say to you.";
    }

    public String askAbout(String topic) {
        if (askTopics.containsKey(topic))
            return askTopics.get(topic);
        else if (topic.equalsIgnoreCase(getName())) {
            return askTopics.get("self");
        } else
            return askTopics.get("default");
    }

    public String getMood() {
        return behavior.getMood();
    }

    public String getActivity() {
        return behavior.getActivity();
    }

    public String getAllegiance() {
        return behavior.getAllegiance();
    }

    public String getStatus() {
        return behavior.getStatus();
    }

    public boolean setMood(String newMood) {
        behavior.setMood(newMood);
        return true;
    }

    public boolean setActivity(String newActivity) {
        behavior.setActivity(newActivity);
        return true;
    }

    public boolean setAllegiance(String newAllegiance) {
        behavior.setAllegiance(newAllegiance);
        return true;
    }

    public boolean setStatus(String newStatus) {
        behavior.setStatus(newStatus);
        return true;
    }

    public String getPersonalQuote(String key){

        String response = behavior.getPersonalQuotes().get(key);
        if(response != null){
            return response;
        }
        return "...";
    }

    public boolean isAgreeable(){       //Returns whether the creature is in a mood to follow instructions.
        if(getAllegiance().equalsIgnoreCase("hostile") || getMood().equalsIgnoreCase("annoyed"))
            return false;
        else
            return true;

    }

    public boolean isWearing(String clothingType){

        //Checks: does the creature wear an object of this type?
        return Lambda.exists(getItemList(), i -> i.hasAttribute("worn") && i.getType().equalsIgnoreCase(clothingType));

    }



}
