package jonst.Models.Objects;


import java.util.List;

public class Item extends GenericObject {


    private GenericObject owner;


    public Item(String name, String id, String description, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, description, locationName, alias, attributes);
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
        setDescription(template.getDescription());

        setAlias(template.getAlias());
        setAttributes(template.getAttributes());

        setDefaultUse(template.getDefaultUse());
        setResponseScripts(template.getResponseScripts());
        setComplexUse(template.getComplexUse());


    }


}
