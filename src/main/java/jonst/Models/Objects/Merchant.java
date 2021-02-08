package jonst.Models.Objects;

import jonst.Models.Cores.ActionCore;
import jonst.Models.Cores.BehaviorCore;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;
//import jonst.Models.Merchandise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Merchant extends Creature {

    private List<String> merchandiseIds;

    private List<Item> merchandiseList;

    //TODO: Can a merchant have a combo of unique and generic items? If you sell an item to them, will they remember that specific one?

    public Merchant(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, BehaviorCore bc, String initialDialog, List<String> merchandiseIds) {
        super(identityCore, relationCore, actionCore, race, defaultRace, gender, casualDialog, askTopics, bc, initialDialog);
        setMerchandiseIds(merchandiseIds);

        merchandiseList = new ArrayList<>();
    }

    public Merchant(Merchant template) {
        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore(), template.getRace(), template.getDefaultRace(), template.getGender(), template.getCasualDialog(), template.getAskTopics(), template.getBehaviorCore(), template.getInitialDialog(), template.getMerchandiseIds());

        merchandiseList = template.getMerchandiseList();
    }

    public List<Item> getMerchandiseList() {
        return this.merchandiseList;
    }

    public List<String> getMerchandiseIds() {
        return this.merchandiseIds;
    }

    public void setMerchandiseIds(final List<String> merchandiseIds) {
        this.merchandiseIds = merchandiseIds;
    }

    public void addMerchandise(Item merch){
        if(!merchandiseList.contains(merch)) {
            merchandiseList.add(merch);
        }
    }

    public void removeMerchandise(Item merch){
        if(merchandiseList.contains(merch)) {
            merchandiseList.remove(merch);
        }
    }
}
