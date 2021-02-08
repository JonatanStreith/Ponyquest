package jonst.Models.Objects;


import jonst.App;
import jonst.Data.Lambda;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;
import jonst.Models.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item extends GenericObject {


    private GenericObject holder;


    public Item(IdentityCore identityCore, RelationCore relationCore, List<String> attributes, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts) {
        super(identityCore, relationCore, attributes, text, defaultUse, complexUse, responseScripts);
    }

    public Item(Item template){
        this(template.getIdentityCore(), template.getRelationCore(), template.getAttributes(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts());
    }

    public GenericObject getHolder() {
        return holder;
    }

    public boolean setHolder(GenericObject holder){
        this.holder = holder;
        if(holder != null) {
            setLocationId(holder.getId());
        }
        else {
            setLocationId(null);
        }
        return true;
    }

    public void transformInto(GenericObject template){
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
