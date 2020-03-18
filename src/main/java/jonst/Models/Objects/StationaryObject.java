package jonst.Models.Objects;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationaryObject extends GenericObject {


    public StationaryObject(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerId);
    }

    public StationaryObject(StationaryObject original) {
        this(original.getName(), original.getShortName(), original.getType(), original.getId(), original.getLocationId(), original.getDefaultLocationId(), original.getAlias(), original.getAttributes(), original.getDescriptions(), original.getText(), original.getDefaultUse(), original.getComplexUse(), original.getResponseScripts(), original.getOwnerId());
    }

    public void transformInto(GenericObject template){
        setName(template.getName());
        setShortName(template.getShortName());
        setType(template.getType());
        setId(template.getId());
        setAlias(template.getAlias());
        setAttributes(template.getAttributes());
        setDescriptions(template.getDescriptions());
        setText(template.getText());
        setDefaultUse(template.getDefaultUse());
        setComplexUse(template.getComplexUse());
        setResponseScripts(template.getResponseScripts());


        //Todo: Must drop contents if not a container
    }
}
