package jonst.Models.Objects;


import java.util.List;

public class Item extends GenericObject {


    private GenericObject holder;


    public Item(String name, String id, String locationName, List<String> alias, List<String> attributes) {
        super(name, id, locationName, alias, attributes);
    }

    public boolean setHolder(GenericObject holder){
        this.holder = holder;
        return true;
    }

    public String getName() {
        if(hasAttribute("worn")) {
            return name + " (worn)";
        }
        return name;
    }

    public GenericObject getHolder() {
        return holder;
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
