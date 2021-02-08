package jonst.Models.Cores;

import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Location;

public class RelationCore {

    private String locationId;
    private String defaultLocationId;
    private String ownerId;

    private Location location;
    private Creature Owner;


    public RelationCore(String locationId, String defaultLocationId, String ownerId) {
        this.locationId = locationId;
        this.defaultLocationId = defaultLocationId;
        this.ownerId = ownerId;
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

