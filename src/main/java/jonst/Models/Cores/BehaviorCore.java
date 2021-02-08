package jonst.Models.Cores;

import java.util.HashMap;
import java.util.Map;

public class BehaviorCore {
    private String mood;
    private String activity;
    private String allegiance;
    private String status;

    private Map<String, String> personalQuotes;
    //This tracks standard lines like greetings and yes/no replies, personalized. Incorporate in the json later for character-specific.


    public BehaviorCore(String mood, String activity, String allegiance, String status, Map<String, String> personalQuotes) {
        this.mood = mood;
        this.activity = activity;
        this.allegiance = allegiance;
        this.status = status;
        this.personalQuotes = personalQuotes;
    }

    public BehaviorCore() {     //Default bc if none is specified
        this("neutral", "idle", "friendly", "normal", new HashMap<String, String>() {{
                    put("thanks", "\"Thanks!\"");
                    put("yes", "\"Yes.\"");
                    put("no", "\"No.\"");
                    put("angryprotest", "\"Cut that out!\"");
                }}
        );
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAllegiance() {
        return allegiance;
    }

    public void setAllegiance(String allegiance) {
        this.allegiance = allegiance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getPersonalQuotes() {
        return personalQuotes;
    }

    public void setPersonalQuotes(Map<String, String> defaultQuotes) {
        this.personalQuotes = defaultQuotes;
    }
}
