package jonst.Models.Objects;

import jonst.Data.Lambda;
import jonst.Models.Cores.*;
import jonst.Models.Roles.GenericRole;

import java.util.List;
import java.util.Map;

public class Creature extends GenericObject {


    private CreatureCore creatureCore;
    private SpeechCore speechCore;
    private BehaviorCore behaviorCore;

    public Creature(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, Map<String, GenericRole> roleMods,
                    CreatureCore creatureCore, SpeechCore speechCore, BehaviorCore behaviorCore) {
        super(identityCore, relationCore, actionCore, roleMods);
        setCreatureCore(creatureCore);
        setSpeechCore(speechCore);
        setBehaviorCore(behaviorCore);
    }

    public Creature(Creature template) {
        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore(), template.getRoles(),
                template.getCreatureCore(), template.getSpeechCore(), template.getBehaviorCore());
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


    public SpeechCore getSpeechCore() {
        return speechCore;
    }

    public void setSpeechCore(SpeechCore speechCore) {
        this.speechCore = speechCore;
    }

    public CreatureCore getCreatureCore() {
        return creatureCore;
    }

    public void setCreatureCore(CreatureCore creatureCore) {
        this.creatureCore = creatureCore;
    }

    public BehaviorCore getBehaviorCore() {
        return behaviorCore;
    }

    public String getInitialDialog() {
        return speechCore.getInitialDialog();
    }

    public String getRace() {
        return creatureCore.getRace();
    }

    public String getDefaultRace() {
        return creatureCore.getDefaultRace();
    }

    public String getGender() {
        return creatureCore.getGender();
    }

    public List<String> getCasualDialog() {
        return speechCore.getCasualDialog();
    }

    public Map<String, String> getAskTopics() {
        return speechCore.getAskTopics();
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
        speechCore.setInitialDialog(initialDialog);
    }

    public void setRace(String race) {
        creatureCore.setRace(race);
    }

    public void setDefaultRace(String defaultRace) {
        creatureCore.setDefaultRace(defaultRace);
    }

    public void setGender(String gender) {
        creatureCore.setGender(gender);
    }

    private void setCasualDialog(List<String> casualDialog) {
        speechCore.setCasualDialog(casualDialog);
    }

    public void setAskTopics(Map<String, String> askTopics) {
        speechCore.setAskTopics(askTopics);
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
        return speechCore.getRandomCasualDialog(this);
    }

    public String askAbout(String topic) {
        return speechCore.askAbout(topic, this);
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
