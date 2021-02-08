package jonst.Models.Cores;

import jonst.Models.Objects.Creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jonst.HelpfulMethods.capitalize;
import static jonst.HelpfulMethods.heOrShe;

public class SpeechCore {
    private List<String> casualDialog;
    private Map<String, String> askTopics;
    private String initialDialog;


    public SpeechCore(List<String> casualDialog, Map<String, String> askTopics, String initialDialog) {
        this.casualDialog = casualDialog;
        this.askTopics = askTopics;
        this.initialDialog = initialDialog;
    }

    public SpeechCore() {
        this.casualDialog = new ArrayList<>();
        this.askTopics = new HashMap<>();
        this.initialDialog = "Blank";
    }

    public List<String> getCasualDialog() {
        return casualDialog;
    }

    public void setCasualDialog(List<String> casualDialog) {
        this.casualDialog = casualDialog;
    }

    public Map<String, String> getAskTopics() {
        return askTopics;
    }

    public void setAskTopics(Map<String, String> askTopics) {
        this.askTopics = askTopics;
    }

    public String getInitialDialog() {
        return initialDialog;
    }

    public void setInitialDialog(String initialDialog) {
        this.initialDialog = initialDialog;
    }


    public String getRandomCasualDialog(Creature subject) {
        if (casualDialog != null && casualDialog.size() > 0) {
            return casualDialog.get((int) Math.floor(Math.random() * casualDialog.size()));
        } else
            return capitalize(heOrShe(subject)) + " doesn't have anything to say to you.";
    }

    public String askAbout(String topic, Creature subject) {
        if (askTopics.containsKey(topic))
            return askTopics.get(topic);
        else if (topic.equalsIgnoreCase(subject.getName())) {
            return askTopics.get("self");
        } else
            return askTopics.get("default");
    }

}
