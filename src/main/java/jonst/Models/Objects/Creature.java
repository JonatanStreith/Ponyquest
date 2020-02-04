package jonst.Models.Objects;

import jonst.HelpfulMethods;
import jonst.Models.BehaviorCore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jonst.HelpfulMethods.*;


public class Creature extends GenericObject {

    private String race;
    private List<String> casualDialog;
    private String gender;

    private Map<String, String> askTopics = new HashMap<>();

    private BehaviorCore behavior;


    public Creature(String name, String id, String locationName, List<String> alias, List<String> attributes, String race, String gender, List<String> casualDialog, Map<String, String> askTopics) {
        super(name, id, locationName, alias, attributes);

        setRace(race);
        setGender(gender);
        setCasualDialog(casualDialog);
        setAskTopics(askTopics);


    }

    public void setBehaviorCore(BehaviorCore behavior) {
        this.behavior = behavior;
    }

    public void setRace(String race) {
        this.race = race;
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
            return capitalize(heOrShe(getGender())) + " doesn't have anything to say to you.";
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

        return behavior.getPersonalQuotes().get(key);
    }


}
