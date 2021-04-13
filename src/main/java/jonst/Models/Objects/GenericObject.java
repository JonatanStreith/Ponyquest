package jonst.Models.Objects;

import jonst.Game;
import jonst.Data.Lambda;
import jonst.Models.Cores.ActionCore;
import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;
import jonst.Models.Roles.GenericRole;
import jonst.Models.World;

import java.util.*;


public abstract class GenericObject implements Comparable<GenericObject> {

    private IdentityCore identityCore;
    private RelationCore relationCore;
    private ActionCore actionCore;

    private Map<String, GenericRole> roles = new HashMap<>();

    public GenericObject(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, Map<String, GenericRole> roles) {
        setIdentityCore(identityCore);
        setRelationCore(relationCore);
        setActionCore(actionCore);
        addRoles(roles);
    }

    //--------- Roles methods --------


    public Map<String, GenericRole> getRoles() {
        return roles;
    }

    public void addRoles(Map<String, GenericRole> roles){

        for (String role: roles.keySet()) {
            assignRole(roles.get(role));
        }

    }

    public boolean hasRole(String key){
        return roles.containsKey(key);
    }

    public GenericRole getRoleByKey(String key){
        if(roles.containsKey(key)){
            return roles.get(key);
        }
        return null;
    }

    public boolean assignRole(GenericRole role){
        if(!roles.containsKey(role.getType())){
            roles.put(role.getType(), role);
            role.setHolder(this);
            return true;
        }
        return false;
    }

    public boolean removeRole(GenericRole role){
        if(roles.containsKey(role.getType())){
            roles.remove(role.getType(), role);
            role.setHolder(null);
            return true;
        }
        return false;
    }




    //--------- Getters ------------

    public ActionCore getActionCore() {
        return actionCore;
    }

    public RelationCore getRelationCore() {
        return relationCore;
    }

    public IdentityCore getIdentityCore() {
        return identityCore;
    }

    public String getDefaultLocationId() {
        return relationCore.getDefaultLocationId();
    }

    public String getType() {
        return identityCore.getType();
    }

    public String getOwnerId() {
        return relationCore.getOwnerId();
    }

    public Creature getOwner() {
        return relationCore.getOwner();
    }

    public String getName() {
        return identityCore.getName();
    }

    public String getShortName() {
        return identityCore.getShortName();
    }

    public String getId() {
        return identityCore.getId();
    }

    public Map<String, String> getDescriptions() {
        return identityCore.getDescriptions();
    }

    public String getLocationId() {
        return relationCore.getLocationId();
    }

    public List<Item> getItemList() {
        return relationCore.getItemList();
    }

    public List<String> getAlias() {
        return identityCore.getAlias();
    }

    public List<String> getAttributes() {
        return actionCore.getAttributes();
    }

    public Location getLocation() {
        return relationCore.getLocation();
    }

    public String getDefaultUse() {
        return actionCore.getDefaultUse();
    }

    public Map<String, String> getComplexUse() {
        return actionCore.getComplexUse();
    }

    public String getText() {
        return actionCore.getText();
    }

    public Map<String, ArrayList<String>> getResponseScripts() {
        return actionCore.getResponseScripts();
    }


    //--------- Setters ------------

    public void setActionCore(ActionCore actionCore) {
        this.actionCore = actionCore;
    }

    public void setRelationCore(RelationCore relationCore) {
        this.relationCore = relationCore;
    }

    public void setIdentityCore(IdentityCore identityCore) {
        this.identityCore = identityCore;
    }

    public void setType(String type) {
        identityCore.setType(type);
    }

    public void setOwnerId(String ownerId) {
        relationCore.setOwnerId(ownerId);
    }

    public void setOwner(Creature owner) {
        relationCore.setOwner(owner);
    }

    protected void setName(String name) {
        identityCore.setName(name);
    }

    public void setShortName(String shortName) {
        identityCore.setShortName(shortName);
    }

    protected void setId(String id) {
        identityCore.setId(id);
    }

    public void setDescriptions(Map<String, String> descriptions) {
        identityCore.setDescriptions(descriptions);
    }

    public void setLocationId(String locationId) {
        relationCore.setLocationId(locationId);
    }

    protected void setAlias(List<String> alias) {
        identityCore.setAlias(alias);
    }

    protected void setAttributes(List<String> attributes) {
        actionCore.setAttributes(attributes);
    }

    public void setLocation(Location location) {
        relationCore.setLocation(location);
    }

    public void setDefaultUse(String defaultUse) {
        actionCore.setDefaultUse(defaultUse);
    }

    public void setComplexUse(Map<String, String> complexUse) {
        actionCore.setComplexUse(complexUse);
    }

    public void setText(String text) {
        actionCore.setText(text);
    }

    public void setResponseScripts(Map<String, ArrayList<String>> responseScripts) {
        actionCore.setResponseScripts(responseScripts);
    }

    //--------- Cool system methods ------------

    public String toString() {
        return this.getName();
    }

    public int hashCode() {
        List<String> hashValues = new ArrayList<>(getAlias());
        hashValues.add(getId());
        return Objects.hash(hashValues);
    }

    public boolean equals(GenericObject other) {
        if (other.getName().equals(this.getName()) && other.getAlias().equals(this.getAlias()))
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(GenericObject otherObject) {
        if (this.equals(otherObject))
            return 0;
        else
            return -1;
    }

    // -------------------------------------

    public String getGender() {

        if (this instanceof Creature) {
            return this.getGender();
        }
        return "neuter";
    }

    public String getDescription() {

        // This method returns a string combining various relevant descriptions of the object

        StringBuilder fullDescription = new StringBuilder(identityCore.getSpecificDescription("default"));

        // Creatures can have specific descriptions pertaining to various races, moods, and statuses

        if (this instanceof Creature) {
            if (identityCore.hasSpecificDescription(((Creature) this).getRace())) {
                fullDescription.append(" " + identityCore.getSpecificDescription(((Creature) this).getRace()));
            }
            if (identityCore.hasSpecificDescription(((Creature) this).getMood())) {
                fullDescription.append(" " + identityCore.getSpecificDescription(((Creature) this).getMood()));
            }
            if (identityCore.hasSpecificDescription(((Creature) this).getStatus())) {
                fullDescription.append(" " + identityCore.getSpecificDescription(((Creature) this).getStatus()));
            }
        }

        //Options for other classes


        // Descriptions specific to any current attributes

        for (String attr : getAttributes()) {
            if (identityCore.hasSpecificDescription(attr)) {
                fullDescription.append(" " + identityCore.getSpecificDescription(attr));
            }
        }
        return fullDescription.toString();
    }

    public String getSpecficDescription(String key) {
        return identityCore.getSpecificDescription(key);
    }

    public boolean hasAttribute(String attr) {
        return actionCore.hasAttribute(attr);
    }

    public boolean hasAnyOfTheseAttributes(String[] attr) {
        return actionCore.hasAnyOfTheseAttributes(attr);
    }

    public boolean hasAnyAttributes(String[] attributeArray) {
        return actionCore.hasAnyAttributes(attributeArray);
    }

    public boolean addAttribute(String attr) {
        return actionCore.addAttribute(attr);
    }

    public boolean removeAttribute(String attr) {
        return actionCore.removeAttribute(attr);
    }

    public boolean isAtLocation(Location location) {
        return getLocation() == location;
    }

    public boolean addItem(Item item) {
        return relationCore.addItem(item, this);
    }

    public boolean removeItem(Item item) {
        return relationCore.removeItem(item);
    }

    public boolean hasItem(Item item) {
        return relationCore.hasItem(item);
    }

    public Item getOwnedItemByName(String name) {
        return relationCore.getOwnedItemByName(name);
    }

    public boolean addAlias(String specificAlias) {
        if (!identityCore.getAlias().contains(specificAlias)) {
            identityCore.getAlias().add(specificAlias);
        }
        Game.getWorld().getParser().addToNouns(specificAlias);
        return true;
    }

    public boolean removeAlias(String specificAlias) {
        if (identityCore.getAlias().contains(specificAlias)) {
            identityCore.getAlias().remove(specificAlias);
        }
        Game.getWorld().getParser().removeFromNouns(specificAlias);
        return true;
    }

    public String getComplexUseCommand(String key) {
        return actionCore.getComplexUseCommand(key);
    }

    public boolean runResponseScript(String command) {
        return actionCore.runResponseScript(command, this);
    }

    public boolean isOwnerPayingAttention() {

        if (
                (getOwner() == null)                    //No owner is set
                        || (getOwner().getLocation() != getLocation())  //Owner is not present
                        || (getOwner().hasStatus("sleeping"))      //Owner is asleep
                        || (getOwner().hasAllegiance("allied"))    //Owner is on your side
                        || (getOwner().hasAnyAttributes(new String[]{"charmed"}))    //Owner has any relevant attributes
        )
            return false;

        return true;
    }

    public void destroy() {

        World world = Game.getWorld();

        if (this instanceof Item) {
            world.removeItemFromHolder((Item) this, ((Item) this).getHolder());
            world.removeFromList(this);
        } else if (this instanceof Location) {
            System.out.println("Destroying a location is not supported yet.");  //TODO: Destroying locations
        } else {
            world.removeFromLocation(this, this.getLocation());
            world.removeFromList(this);
        }
    }

    public void dropContents() {
        for (Item item : getItemList()
             ) { item.setHolder(getLocation());
        }
    }

    public void read() {
        if (getText() != null) {
            System.out.println(getText());
        } else {
            System.out.println("There's nothing to read.");
        }

    }

    public String getHolderId() {
        if (!(this instanceof Item)) {
            return getLocationId();
        } else {
            return ((Item) this).getHolder().getId();
        }
    }


    //TODO: Move this to World
    public static GenericObject create(String Id) {
        World world = Game.getWorld();

        GenericObject templ = Lambda.getFirst(world.getTemplateList(), t -> t.getId().equals(Id));
        GenericObject gen;

        if (templ == null) {
            return null;
        }

        if (templ instanceof Item) {
            gen = new Item((Item) templ);
        } else if (templ instanceof Creature) {
            gen = new Creature((Creature) templ);
        } else if (templ instanceof StationaryObject) {
            gen = new StationaryObject((StationaryObject) templ);
        } else if (templ instanceof Location) {
            gen = new Location((Location) templ);
        } else {
            gen = null;
        }
        if (gen != null) {
            Game.getWorld().addNewToList(gen);
        }

        return gen;


    }

    public abstract void transformInto(GenericObject template);


}
