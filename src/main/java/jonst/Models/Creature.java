package jonst.Models;

import jonst.Data.CreatureData;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;



public class Creature extends GenericObject {

    private String race;

    private List<String> inventory = new ArrayList<String>();

    public Creature(String name, String shortName, String description, String race) {
        super(name, shortName, description);

        this.race = race;
    }




    public String getRace()
    { return race; }

}
