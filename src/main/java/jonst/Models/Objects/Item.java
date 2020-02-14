package jonst.Models.Objects;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item extends GenericObject {


    private GenericObject holder;


    public Item(String name, String id, String locationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName) {
        super(name, id, locationId, alias, attributes, text, defaultUse, descriptions, complexUse, responseScripts, ownerName);





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
