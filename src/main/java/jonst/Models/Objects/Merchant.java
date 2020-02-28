package jonst.Models.Objects;

import jonst.Models.BehaviorCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Merchant extends Creature {

    private List<String> merchandise;



    public Merchant(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, String race, String defaultRace, String gender, List<String> casualDialog, Map<String, String> askTopics, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName, BehaviorCore bc, String initialDialog, List<String> merchandise) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, race, defaultRace, gender, casualDialog, askTopics, descriptions, text, defaultUse, complexUse, responseScripts, ownerName, bc, initialDialog);

        setMerchandise(merchandise);
    }

    public List<String> getMerchandise() {
        return this.merchandise;
    }

    public void setMerchandise(final List<String> merchandise) {
        this.merchandise = merchandise;
    }

    public void addMerchandise(String item){
        if(!merchandise.contains(item)) {
            merchandise.add(item);
        }
    }

    public void removeMerchandise(String item){
        if(merchandise.contains(item)) {
            merchandise.remove(item);
        }
    }
}
