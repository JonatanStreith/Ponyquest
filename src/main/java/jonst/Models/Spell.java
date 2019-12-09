package jonst.Models;

import jonst.Models.GenericObject;

import java.util.List;

public class Spell extends GenericObject {


    public Spell(String name, String shortName, String description, String locationName, List<String> alias) {
        super(name, shortName, description, locationName, alias);
    }
}
