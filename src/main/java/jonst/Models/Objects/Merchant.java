package jonst.Models.Objects;

import jonst.Models.BehaviorCore;
import jonst.Models.Merchandise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Merchant extends Creature {

    private List<String> merchandiseIds;

    private List<Item> merchandiseList;

    public Merchant(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId, BehaviorCore bc, String initialDialog, List<String> merchandiseIds) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, race, defaultRace, gender, casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, ownerId, bc, initialDialog);
        setMerchandiseIds(merchandiseIds);

        merchandiseList = new ArrayList<>();
    }

    public Merchant(Merchant template) {
        super(template.getName(), template.getShortName(), template.getType(), template.getId(), template.getLocationId(), template.getDefaultLocationId(), template.getAlias(), template.getAttributes(), template.getRace(), template.getDefaultRace(), template.getGender(), template.getCasualDialog(), template.getAskTopics(), template.getDescriptions(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts(), template.getOwnerId(), template.getBehavior(), template.getInitialDialog());
        setMerchandiseIds(template.getMerchandiseIds());

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
