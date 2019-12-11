package jonst.Models;

import jonst.HelpfulMethods;

import java.util.ArrayList;
import java.util.List;

public class Container extends Item {

    private List<Item> contains;

    public Container(String name, String description, String locationName, List<String> alias) {
        super(name, description, locationName, alias);
    }


    public List<Item> getContains() {
        return contains;
    }

    public String seeContents(){
        return ("It contains " + HelpfulMethods.turnListIntoString(contains, "and"));
    }


    public void addContents(Item item){
        contains.add(item);
    }

    public void removeContent(Item item){
        contains.remove(item);
    }
}
