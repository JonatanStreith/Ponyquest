package jonst.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Creature extends GenericObject {

    private String race;
    private List<String> casualDialog;

    private Map<String, String> askTopics;


    //private List<String> inventory = new ArrayList<String>();




    public Creature(String name, String shortName, String description, String locationName, List<String> alias, String race, List<String> casualDialog, Map<String, String> askTopics) {
        super(name, shortName, description, locationName, alias);

        setRace(race);
        setCasualDialog(casualDialog);
        setAskTopics(askTopics);

    }

    private void setCasualDialog(List<String> casualDialog) {
        this.casualDialog = casualDialog;
    }

    public void setAskTopics(Map<String, String> askTopics) {
        this.askTopics = askTopics;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRace()
    { return race; }

    public List<String> getCasualDialog() {
        return casualDialog;
    }

    public Map<String, String> getAskTopics() {
        return askTopics;
    }

    public String getRandomCasualDialog(){
        return casualDialog.get((int) Math.floor(Math.random() * casualDialog.size()));
    }

    public String askAbout(String topic){
        if(askTopics.containsKey(topic))
        return askTopics.get(topic);
        else
            return "They don't seem to know anything about that.";
    }


}
