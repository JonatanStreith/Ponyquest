package jonst.Models;

import jonst.App;

import java.util.ArrayList;
import java.util.List;

public class GenericObject {
    private String name;
    //private String shortName;

    private String description;

    private String locationName;

    private Location location;

    private List<String> alias;

    private List<Item> itemList;


    public GenericObject(String name, String description, String locationName, List<String> alias)
{
    setName(name);

    //setShortName(shortName);
    setDescription(description);
    setLocationName(locationName);

    setAlias(alias);

    itemList = new ArrayList<>();


}
    protected void setName(String name) {
        this.name = name;
    }

//    protected void setShortName(String shortName) {
//        this.shortName = shortName;
//    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setLocationName(String locationName) {
        this.locationName = locationName;
    }



    public String getName() {
        return name;
    }

//    public String getShortName() {
//        return shortName;
//    }

    public String getDescription() {
        return description;
    }

    public String getLocationName() {
        return locationName;
    }


    public Location getLocation() {
        return location;
    }

    public List<Item> getItemList() {
        return itemList;
    }


    public void setLocation(Location location) {
        this.location = location;
        locationName = location.getLocationName();
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }


    public void getFeedback(){
    String feedback = name + ": " + description;
        System.out.println(feedback);
    }

    public boolean isAtLocation(Location location){
        return getLocation() == location;
    }

    public boolean addAlias(String specificAlias){

        alias.add(specificAlias);

        App.getWorld().getParser().addToNouns(specificAlias);

        return true;
    }

    public boolean removeAlias(String specificAlias){
        if(alias.contains(specificAlias)) {
            alias.remove(specificAlias);
        }
        App.getWorld().getParser().removeFromNouns(specificAlias);

        return true;
    }

//    public boolean addItem(Item item){
//        if(!itemList.contains(item)) {
//            itemList.add(item);
//            return true;
//        } else
//            return false;
//    }
//
//    public boolean removeItem(Item item){
//        if(itemList.contains(item)) {
//            itemList.remove(item);
//            return true;
//        } else
//            return false;
//    }

}
