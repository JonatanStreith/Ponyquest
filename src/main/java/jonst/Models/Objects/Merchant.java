package jonst.Models.Objects;

import jonst.Models.BehaviorCore;
import jonst.Models.Merchandise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Merchant extends Creature {

    private List<Merchandise> merchandiseList;

    public Merchant(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName, BehaviorCore bc, String initialDialog, List<Merchandise> merchandiseList) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, race, defaultRace, gender, casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, ownerName, bc, initialDialog);


        setMerchandiseList(merchandiseList);
    }

    public List<Merchandise> getMerchandiseList() {
        return this.merchandiseList;
    }

    public void setMerchandiseList(final List<Merchandise> merchandiseList) {
        this.merchandiseList = merchandiseList;
    }

}
