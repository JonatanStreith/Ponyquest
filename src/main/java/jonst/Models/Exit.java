package jonst.Models;

import jonst.Models.Objects.Location;

public class Exit {
    private Location[] connectsLocations;
    private boolean open;

    public Exit(Location[] locations, boolean open) {
        this.connectsLocations = locations;
        this.open = open;
    }

    public Location[] getConnectsLocations() {
        return this.connectsLocations;
    }

    public void setConnectsLocations(Location[] connectsLocations) {
        this.connectsLocations = connectsLocations;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void open(){
        open = true;
    }

    public void close(){
        open = false;
    }

    public Location getConnectingLocation(Location current){
        if(connectsLocations[0] == current){
            return connectsLocations[1];
        } else if (connectsLocations[1] == current) {
            return connectsLocations[0];
        } else
            return null;
    }

    public boolean connectionExists(Location loc0, Location loc1){
        //does this exit connect these two locations? (potential for reverse)
        if((connectsLocations[0] == loc0 && connectsLocations[1] == loc1) || (connectsLocations[0] == loc1 && connectsLocations[1] == loc0)){
            return true;
        }
        return false;
    }

    public boolean containsLocation(Location loc){
        //does this exit connect to this one location?
        if(connectsLocations[0] == loc ||  connectsLocations[1] == loc){
            return true;
        }
        return false;
    }

}
