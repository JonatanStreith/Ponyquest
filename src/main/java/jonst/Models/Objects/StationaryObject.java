package jonst.Models.Objects;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationaryObject extends GenericObject {


    public StationaryObject(String name, String id, String locationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName) {
        super(name, id, locationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerName);


    }


}
