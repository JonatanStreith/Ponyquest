package jonst.Models.Objects;


import jonst.App;
import jonst.Data.Lambda;
import jonst.JsonBuilder;
import jonst.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item extends GenericObject {


    private GenericObject holder;


    public Item(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes,     text, defaultUse, descriptions, complexUse, responseScripts, ownerId);
    }

    public Item(Item original){
        super(original.getName(), original.getShortName(), original.getType(), original.getId(), original.getLocationId(), original.getDefaultLocationId(), original.getAlias(), original.getAttributes(), original.getText(), original.getDefaultUse(), original.getDescriptions(), original.getComplexUse(), original.getResponseScripts(), original.getOwnerId());
    }

    public GenericObject getHolder() {
        return holder;
    }

    public boolean setHolder(GenericObject holder){
        this.holder = holder;
        return true;
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

    public static Item create(String Id){

        World world = App.getWorld();

        Item template = (Item) Lambda.getFirst(world.getTemplateList(), t -> t.getId().equals(Id) && t instanceof Item);

        Item newItem = new Item(template);

        App.getWorld().addNewToList(newItem);

        return newItem;
    }
}
