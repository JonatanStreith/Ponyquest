package jonst.Models;

import java.util.ArrayList;
import java.util.List;



public class Creature extends GenericObject {

    private String race;
    private List<String> casualDialog = new ArrayList<>();

    private List<String> inventory = new ArrayList<String>();

    public Creature(String name, String shortName, String description, String locationName, String race, List<String> casualDialog) {
        super(name, shortName, description, locationName);

        this.race = race;
        this.casualDialog = casualDialog;

    }




    public String getRace()
    { return race; }

    public List<String> getCasualDialog() {
        return casualDialog;
    }


    public String getRandomCasualDialog(){
        return casualDialog.get((int) Math.ceil(Math.random() * casualDialog.size()));
    }


}
