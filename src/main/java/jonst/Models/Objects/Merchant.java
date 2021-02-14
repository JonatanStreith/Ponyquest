//package jonst.Models.Objects;
//
//import jonst.Models.Cores.*;
//import jonst.Models.Mods.GenericMod;
////import jonst.Models.Merchandise;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//
//public class Merchant extends Creature {
//
//    private List<String> merchandiseIds;
//
//    private List<Item> merchandiseList;
//
//    //TODO: Can a merchant have a combo of unique and generic items? If you sell an item to them, will they remember that specific one?
//
//    public Merchant(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, Map<String, GenericMod> roleMods,
//                    CreatureCore creatureCore, SpeechCore speechCore, BehaviorCore behaviorCore,
//                    List<String> merchandiseIds) {
//        super(identityCore, relationCore, actionCore, roleMods, creatureCore, speechCore, behaviorCore);
//        setMerchandiseIds(merchandiseIds);
//
//        merchandiseList = new ArrayList<>();
//    }
//
//    public Merchant(Merchant template) {
//        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore(), template.getRoleMods(),
//                template.getCreatureCore(), template.getSpeechCore(), template.getBehaviorCore(),
//                template.getMerchandiseIds());
//
//        merchandiseList = template.getMerchandiseList();
//    }
//
//    public List<Item> getMerchandiseList() {
//        return this.merchandiseList;
//    }
//
//    public List<String> getMerchandiseIds() {
//        return this.merchandiseIds;
//    }
//
//    public void setMerchandiseIds(final List<String> merchandiseIds) {
//        this.merchandiseIds = merchandiseIds;
//    }
//
//    public void addMerchandise(Item merch){
//        if(!merchandiseList.contains(merch)) {
//            merchandiseList.add(merch);
//        }
//    }
//
//    public void removeMerchandise(Item merch){
//        if(merchandiseList.contains(merch)) {
//            merchandiseList.remove(merch);
//        }
//    }
//}
