package jonst.Models.Objects;


import java.util.List;

public class Item extends GenericObject {


    private GenericObject owner;


    public Item(String name, String id, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, locationName, alias, attributes);
    }

    public boolean setOwner(GenericObject owner){
        this.owner = owner;
        return true;
    }

    public GenericObject getOwner() {
        return owner;
    }

    public void transformInto(Item template){
        setName(template.getName());
        setId(template.getId());
        setDescriptions(template.getDescriptions());

        setAlias(template.getAlias());
        setAttributes(template.getAttributes());

        setDefaultUse(template.getDefaultUse());
        setResponseScripts(template.getResponseScripts());
        setComplexUse(template.getComplexUse());


    }


}
