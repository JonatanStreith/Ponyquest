package jonst.Models;

import jonst.Models.Objects.GenericObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Spell extends GenericObject {


    public Spell(String name, String type, String id, String locationId, List<String> alias, List<String> attributes, String defaultEnterId, String defaultExitId, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName) {
        super(name, type, id, locationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerName);
    }
}
