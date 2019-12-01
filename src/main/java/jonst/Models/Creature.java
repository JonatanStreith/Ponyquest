package jonst.Models;

import jonst.Data.CreatureData;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;



public class Creature extends GenericObject {

    private String race;
    private List<String> casuaDialog = new ArrayList<>();

    private List<String> inventory = new ArrayList<String>();

    public Creature(String name, String shortName, String description, String locationName, String race, List<String> casuaDialog) {
        super(name, shortName, description, locationName);

        this.race = race;
        this.casuaDialog = casuaDialog;

    }




    public String getRace()
    { return race; }

    public List<String> getCasuaDialog() {
        return casuaDialog;
    }
}
