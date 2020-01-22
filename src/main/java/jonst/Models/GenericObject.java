package jonst.Models;

import jonst.App;

import java.util.ArrayList;
import java.util.List;

public class GenericObject {
    private String name;
    private String id;
    private String description;
    private String locationName;
    private Location location;
    private List<String> alias;
    private List<Item> itemList;
    private List<String> attributes;        //Contains all attributes that can affect how interactions work!


    public GenericObject(String name, String id, String description, String locationName, List<String> alias, List<String> attributes) {
        setName(name);
        setId(id);
        setDescription(description);
        setLocationName(locationName);
        setAlias(alias);
        setAttributes(attributes);

        itemList = new ArrayList<>();
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    protected void setAlias(List<String> alias) {
        this.alias = alias;
    }

    protected void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setLocation(Location location) {
        this.location = location;
        if (location != null)
            locationName = location.getLocationName();
        else
            locationName = "Carried, not at a location";
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationName() {
        return locationName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<String> getAlias() {
        return alias;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAtLocation(Location location) {
        return getLocation() == location;
    }

    public boolean addItem(Item item) {
        if (!itemList.contains(item)) {
            itemList.add(item);
            return true;
        } else
            return false;
    }

    public boolean removeItem(Item item) {
        if (itemList.contains(item)) {
            itemList.remove(item);
            return true;
        } else
            return false;
    }

    public boolean hasItem(Item item) {

        if (itemList.contains(item)) {
            return true;
        } else
            return false;
    }

    public void getFeedback() {
        String feedback = name + ": " + description;
        System.out.println(feedback);
    }

    public boolean addAlias(String specificAlias) {
        alias.add(specificAlias);
        App.getWorld().getParser().addToNouns(specificAlias);
        return true;
    }

    public boolean removeAlias(String specificAlias) {
        if (alias.contains(specificAlias)) {
            alias.remove(specificAlias);
        }
        App.getWorld().getParser().removeFromNouns(specificAlias);
        return true;
    }
}
