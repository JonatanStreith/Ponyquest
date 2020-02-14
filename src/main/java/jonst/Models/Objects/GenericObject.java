package jonst.Models.Objects;

import jonst.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericObject {
    protected String name;
    private String id;
    private String locationId;
    private Location location;
    private List<String> alias;
    private List<Item> itemList;
    private List<String> attributes;        //Contains all attributes that can affect how interactions work!
    private String text;
    private String defaultUse;



    private String ownerName;
    private Creature Owner;

    private Map<String, String> descriptions = new HashMap<>();

    private Map<String, String> complexUse = new HashMap<>();

    private Map<String, ArrayList<String>> responseScripts = new HashMap<>();


    public GenericObject(String name, String id, String locationId, List<String> alias, List<String> attributes, String text, String defaultUse, Map<String, String> descriptions, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName) {
        setName(name);
        setId(id);

        setLocationId(locationId);
        setAlias(alias);
        setAttributes(attributes);

        setText(text);
        setDefaultUse(defaultUse);
        setDescriptions(descriptions);

        setComplexUse(complexUse);
        setResponseScripts(responseScripts);

        setOwnerName(ownerName);

        itemList = new ArrayList<>();

    }

    public String toString(){
        return this.getName();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Creature getOwner() {
        return Owner;
    }

    public void setOwner(Creature owner) {
        Owner = owner;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
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
            locationId = location.getId();
        else
            locationId = "Carried, not at a location";
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSpecficDescription(String key){
        return descriptions.get(key);
    }

    public String getDescription() {

        StringBuilder fullDescription = new StringBuilder(descriptions.get("default"));

        if(this instanceof Creature){
            if(descriptions.keySet().contains( ((Creature) this).getRace() )){
                fullDescription.append(" " + descriptions.get( ((Creature) this).getRace() ));
            }
            if(descriptions.keySet().contains( ((Creature) this).getMood() )){
                fullDescription.append(" " + descriptions.get( ((Creature) this).getMood() ));
            }
            if(descriptions.keySet().contains( ((Creature) this).getStatus() )){
                fullDescription.append(" " + descriptions.get( ((Creature) this).getStatus() ));
            }
        }


        for (String attr: getAttributes()) {
            if(descriptions.keySet().contains(attr)){
                fullDescription.append(" " + descriptions.get(attr));
            }
        }
        return fullDescription.toString();
    }

    public String getGender() {

        if(this instanceof Creature){
            return ((Creature) this).getGender();
        }
        return "neuter";
    }

    public String getDescription(String key) {
        return descriptions.get(key);
    }


    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public String getLocationId() {
        return locationId;
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

    public String getDefaultUse() {
        return defaultUse;
    }

    public void setDefaultUse(String defaultUse) {
        this.defaultUse = defaultUse;
    }

    public Map<String, String> getComplexUse() {
        return complexUse;
    }

    public void setComplexUse(Map<String, String> complexUse) {
        this.complexUse = complexUse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //----------------------------------------

    public boolean equals(GenericObject other) {
        if (other.getName().equals(this.getName()) && other.getAlias().equals(this.getAlias()))
            return true;
        else
            return false;
    }

    public boolean hasAttribute(String attr) {
        return attributes.contains(attr);
    }

    public boolean addAttribute(String attr){

        if(!attributes.contains(attr)) {
            attributes.add(attr);
            return true;
        }
        return false;
    }

    public boolean removeAttribute(String attr){

        if(attributes.contains(attr)) {
            attributes.remove(attr);
            return true;
        }
        return false;
    }

    public boolean isAtLocation(Location location) {
        return getLocation() == location;
    }

    public boolean addItem(Item item) {
        if (!itemList.contains(item)) {
            itemList.add(item);
            item.setHolder(this);
            return true;
        } else
            return false;
    }

    public boolean removeItem(Item item) {
        if (itemList.contains(item)) {
            itemList.remove(item);
            item.setHolder(null);
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

    public Item getOwnedItemByName(String name) {

        for (Item item : itemList) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
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

    public String getComplexUseCommand(String key) {
        return complexUse.get(key.toLowerCase());
    }

    public boolean runResponseScript(String command) {

        ArrayList<String> responseCommands = responseScripts.get(command);

        if (responseCommands != null) {
            for (String script : responseCommands) {
                App.getWorld().getParser().runScriptCommand(this, script, App.getWorld());
            }
            return true;
        }
        return false;
    }

    public Map<String, ArrayList<String>> getResponseScripts() {
        return responseScripts;
    }

    public void setResponseScripts(Map<String, ArrayList<String>> responseScripts) {
        this.responseScripts = responseScripts;
    }

    public boolean isOwnerPayingAttention(){

        if(getOwner() == null){     //No owner is set
            return false;
        } else if(getOwner().getLocation() != location){
            return false;
        } else if(getOwner().getStatus().equalsIgnoreCase("sleeping")){
            return false;
        } else if(getOwner().getAllegiance().equalsIgnoreCase("allied")) {
            return false;
        } else if(getOwner().hasAttribute("charmed")){
            return false;
        }

        return true;
    }
}
