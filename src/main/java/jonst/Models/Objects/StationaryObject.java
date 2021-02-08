package jonst.Models.Objects;


import jonst.Models.Cores.IdentityCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationaryObject extends GenericObject {


    public StationaryObject(IdentityCore identityCore, String locationId, String defaultLocationId, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId) {
        super(identityCore, locationId, defaultLocationId, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerId);
    }

    public StationaryObject(StationaryObject original) {
        this(original.getIdentityCore(), original.getLocationId(), original.getDefaultLocationId(), original.getAttributes(), original.getDescriptions(), original.getText(), original.getDefaultUse(), original.getComplexUse(), original.getResponseScripts(), original.getOwnerId());
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
