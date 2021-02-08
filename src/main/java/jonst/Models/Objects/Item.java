package jonst.Models.Objects;


import jonst.Game;
import jonst.Data.Lambda;
import jonst.Models.Cores.ActionCore;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;
import jonst.Models.World;

public class Item extends GenericObject {


    private GenericObject holder;


    public Item(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore) {
        super(identityCore, relationCore, actionCore);
    }

    public Item(Item template){
        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore());
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

        World world = Game.getWorld();

        Item template = (Item) Lambda.getFirst(world.getTemplateList(), t -> t.getId().equals(Id) && t instanceof Item);

        Item newItem = new Item(template);

        Game.getWorld().addNewToList(newItem);

        return newItem;
    }
}
