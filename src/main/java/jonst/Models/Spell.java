package jonst.Models;

import jonst.Models.GenericObject;

import java.util.List;

public class Spell extends GenericObject {


    public Spell(String name, String id, String description, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, description, locationName, alias, attributes);
    }
}
