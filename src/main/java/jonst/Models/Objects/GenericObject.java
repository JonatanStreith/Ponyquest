package jonst.Models.Objects;

import jonst.App;
import jonst.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericObject implements Comparable<GenericObject> {
    private String name;
    private String shortName;
    private String type;
    private String id;
    private String locationId;
    private String defaultLocationId;
    private Location location;
    private List<String> alias;
    private List<Item> itemList;
    private List<String> attributes;        //Contains all attributes that can affect how interactions work!
    private String text;
    private String defaultUse;



    private String ownerId;
    private Creature Owner;

    private Map<String, String> descriptions = new HashMap<>();

    private Map<String, String> complexUse = new HashMap<>();

    private Map<String, ArrayList<String>> responseScripts = new HashMap<>();


    public GenericObject(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, String text, String defaultUse, Map<String, String> descriptions, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId) {
        setName(name);
        setShortName(shortName);
        setType(type);
        setId(id);

        setLocationId(locationId);
        setDefaultLocationId(defaultLocationId);
        setOwnerId(ownerId);
        setText(text);
        setDefaultUse(defaultUse);

        setAlias(alias);
        setAttributes(attributes);
        setDescriptions(descriptions);

        setComplexUse(complexUse);
        setResponseScripts(responseScripts);

        itemList = new ArrayList<>();
    }

    //--------- Getters ------------

    public String getDefaultLocationId() {
        return defaultLocationId;
    }

    public String getType() {
        return type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Creature getOwner() {
        return Owner;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getId() {
        return id;
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

    public Map<String, String> getComplexUse() {
        return complexUse;
    }

    public String getText() {
        return text;
    }

    public Map<String, ArrayList<String>> getResponseScripts() {
        return responseScripts;
    }


    //--------- Setters ------------

    public void setDefaultLocationId(String defaultLocationId) {
        this.defaultLocationId = defaultLocationId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwner(Creature owner) {
        Owner = owner;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public void setDefaultUse(String defaultUse) {
        this.defaultUse = defaultUse;
    }

    public void setComplexUse(Map<String, String> complexUse) {
        this.complexUse = complexUse;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setResponseScripts(Map<String, ArrayList<String>> responseScripts) {
        this.responseScripts = responseScripts;
    }

    //--------- Other ------------

    public String toString(){
        return this.getName();
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 5;

        hash = prime * hash + this.getName().hashCode();

        for (String a: getAlias() ) {
            hash += a.hashCode();
        }

        return hash;
    }



    public boolean equals(GenericObject other) {
        if (other.getName().equals(this.getName()) && other.getAlias().equals(this.getAlias()))
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(GenericObject otherObject){
        if(this.equals(otherObject))
            return 0;
        else
            return -1;
    }

    public String getGender() {

        if(this instanceof Creature){
            return ((Creature) this).getGender();
        }
        return "neuter";
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

    public String getSpecficDescription(String key){
        return descriptions.get(key);
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

    public boolean isOwnerPayingAttention(){

        if(getOwner() == null){     //No owner is set
            return false;
        } else if(getOwner().getLocation() != location){        //Owner is not present
            return false;
        } else if(getOwner().getStatus().equalsIgnoreCase("sleeping")){     //Owner is asleep
            return false;
        } else if(getOwner().getAllegiance().equalsIgnoreCase("allied")) {  //Owner is on your side
            return false;
        } else if(getOwner().hasAttribute("charmed")){                                  //Owner has been charmed
            return false;
        }

        return true;
    }

    public void destroy(){

        World world = App.getWorld();

        GenericObject subject = this;

        if(subject instanceof Item) {
            world.removeItemFromGeneric((Item) subject, ((Item) subject).getHolder());
            world.removeFromList(subject);
        }

        else {
            world.removeFromLocation(subject, subject.getLocation());
            world.removeFromList(subject);
        }
    }

    public void read(){
        if(getText() != null){
            System.out.println(getText());
        } else {
            System.out.println("There's nothing to read.");
        }

    }

}
