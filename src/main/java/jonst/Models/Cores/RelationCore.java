package jonst.Models.Cores;

import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;
import jonst.Models.Objects.Item;
import jonst.Models.Objects.Location;

import java.util.ArrayList;
import java.util.List;

public class RelationCore {

    private String locationId;
    private String defaultLocationId;
    private String ownerId;



    private Location location;
    private Creature Owner;

    private List<Item> itemList;


    public RelationCore(String locationId, String defaultLocationId, String ownerId) {
        this.locationId = locationId;
        this.defaultLocationId = defaultLocationId;
        this.ownerId = ownerId;

        itemList = new ArrayList<>();
    }

    public RelationCore() {
        this.locationId = "Blank";
        this.defaultLocationId = "Blank";
        this.ownerId = "Blank";

        itemList = new ArrayList<>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean addItem(Item item, GenericObject holder) {
        if (!itemList.contains(item)) {
            itemList.add(item);
            item.setHolder(holder);
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

    public Creature getOwner() {
        return Owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setOwner(Creature owner) {
        Owner = owner;
    }

    public void setLocation(Location location) {
        this.location = location;

        if (location != null) {
            locationId = location.getId();
        }
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDefaultLocationId() {
        return defaultLocationId;
    }

    public void setDefaultLocationId(String defaultLocationId) {
        this.defaultLocationId = defaultLocationId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

