package jonst.Models;

import jonst.Models.Objects.GenericObject;

import java.util.List;

public class Spell extends GenericObject {


    public Spell(String name, String id, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, locationName, alias, attributes);
    }
}
