package jonst.Models.Objects;

import jonst.Data.Lambda;
import jonst.Models.Cores.BehaviorCore;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jonst.HelpfulMethods.*;


public class Creature extends GenericObject {

    private String race;
    private String defaultRace;
    private String gender;
    private BehaviorCore behaviorCore;

    private List<String> casualDialog;
    private Map<String, String> askTopics;
    private String initialDialog;

    public Creature(IdentityCore identityCore, RelationCore relationCore, List<String> attributes, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, BehaviorCore bc, String initialDialog) {
        super(identityCore, relationCore, attributes, text, defaultUse, complexUse, responseScripts);
        setRace(race);
        this.defaultRace = defaultRace;
        setGender(gender);
        setCasualDialog(casualDialog);
        setAskTopics(askTopics);
        setBehaviorCore(bc);
        setInitialDialog(initialDialog);

    }

    public Creature(Creature template) {
        this(template.getIdentityCore(), template.getRelationCore(), template.getAttributes(), template.getRace(), template.getDefaultRace(), template.getGender(), template.getCasualDialog(), template.getAskTopics(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts(), template.getBehaviorCore(), template.getInitialDialog());
    }

        //TODO: Can I move the major burden of this method to GenericObject?
    public void transformInto(GenericObject template) {
        setName(template.getName());
        setShortName(template.getShortName());
        setType(template.getType());
        setId(template.getId());
        setAlias(template.getAlias());
        setAttributes(template.getAttributes());

        setRace(((Creature) template).getRace());
        setDefaultRace(((Creature) template).getDefaultRace());
        setGender(template.getGender());
        setCasualDialog(((Creature) template).getCasualDialog());
        setAskTopics(((Creature) template).getAskTopics());
        setDescriptions(template.getDescriptions());
        setText(template.getText());
        setDefaultUse(template.getDefaultUse());
        setComplexUse(template.getComplexUse());
        setResponseScripts(template.getResponseScripts());
        setBehaviorCore(((Creature) template).getBehaviorCore());
        setInitialDialog(((Creature) template).getInitialDialog());
    }

    // -------- Getters --------

    public BehaviorCore getBehaviorCore() {
        return behaviorCore;
    }

    public String getInitialDialog() {
        return initialDialog;
    }

    public String getRace() {
        return race;
    }

    public String getDefaultRace() {
        return defaultRace;
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

    public String getMood() {
        return behaviorCore.getMood();
    }

    public String getActivity() {
        return behaviorCore.getActivity();
    }

    public String getAllegiance() {
        return behaviorCore.getAllegiance();
    }

    public String getStatus() {
        return behaviorCore.getStatus();
    }

    // -------- Setters --------

    public void setBehaviorCore(BehaviorCore behaviorCore) {
        this.behaviorCore = behaviorCore;
    }

    public void setInitialDialog(String initialDialog) {
        this.initialDialog = initialDialog;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setDefaultRace(String defaultRace) {
        this.defaultRace = defaultRace;
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

    public boolean setMood(String newMood) {
        behaviorCore.setMood(newMood);
        return true;
    }

    public boolean setActivity(String newActivity) {
        behaviorCore.setActivity(newActivity);
        return true;
    }

    public boolean setAllegiance(String newAllegiance) {
        behaviorCore.setAllegiance(newAllegiance);
        return true;
    }

    public boolean setStatus(String newStatus) {
        behaviorCore.setStatus(newStatus);
        return true;
    }




    public String getRandomCasualDialog() {
        if (casualDialog != null && casualDialog.size() > 0) {
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



    public boolean hasAllegiance(String allegiance) {
        return behaviorCore.getAllegiance().equalsIgnoreCase(allegiance);
    }

    public boolean hasStatus(String status) {
        return behaviorCore.getStatus().equalsIgnoreCase(status);
    }


    public String getPersonalQuote(String key) {
        String response = behaviorCore.getPersonalQuotes().get(key);
        if (response != null) {
            return response;
        }
        return "...";
    }

    public boolean isAgreeable() {       //Returns whether the creature is in a mood to follow instructions.
        if (getAllegiance().equalsIgnoreCase("hostile") || getMood().equalsIgnoreCase("annoyed"))
            return false;
        else
            return true;

    }

    public boolean isWearing(String clothingType) {

        //Checks: does the creature wear an object of this type?
        return Lambda.exists(getItemList(), i -> i.hasAttribute("worn") && i.getType().equalsIgnoreCase(clothingType));

    }

}
