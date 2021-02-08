package jonst.Models.Cores;

public class CreatureCore {
    private String race;
    private String defaultRace;
    private String gender;

    public CreatureCore(String race, String defaultRace, String gender) {
        this.race = race;
        this.defaultRace = defaultRace;
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDefaultRace() {
        return defaultRace;
    }

    public void setDefaultRace(String defaultRace) {
        this.defaultRace = defaultRace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
