package jonst.Models.Objects;


import jonst.Models.Cores.ActionCore;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;
import jonst.Models.Roles.GenericRole;

import java.util.Map;

public class StationaryObject extends GenericObject {


    public StationaryObject(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, Map<String, GenericRole> roleMods) {
        super(identityCore, relationCore, actionCore, roleMods);
    }

    public StationaryObject(StationaryObject template) {
        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore(), template.getRoles());
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
