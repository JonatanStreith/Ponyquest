package jonst.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Creature extends GenericObject {

    private String race;
    private List<String> casualDialog;
    private String gender;


    private String disposition;     //TODO: Implement this!


    private Map<String, String> askTopics;



    public Creature(String name, String id, String description, String locationName, List<String> alias, List<String> attributes, String race, String gender, List<String> casualDialog, Map<String, String> askTopics) {
        super(name, id, description, locationName, alias, attributes);

        setRace(race);
        setGender(gender);
        setCasualDialog(casualDialog);
        setAskTopics(askTopics);

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



    public String getRace()
    { return race; }

    public String getGender() {
        return gender;
    }

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
            return askTopics.get("default");
    }


}
