package jonst.Models;

import jonst.HelpfulMethods;

import java.util.List;

public class Container extends Item {

    private List<Item> contains;

    public Container(String name, String shortName, String description, String locationName, List<String> alias) {
        super(name, shortName, description, locationName, alias);
    }


    public List<Item> getContains() {
        return contains;
    }

    public String seeContents(){
        return ("It contains " + HelpfulMethods.turnItemListIntoString(contains, "and"));
    }


    public void addContents(Item item){
        contains.add(item);
    }

    public void removeContent(Item item){
        contains.remove(item);
    }
}
