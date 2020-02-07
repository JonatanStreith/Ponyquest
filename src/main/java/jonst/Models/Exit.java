package jonst.Models;

import jonst.Models.Objects.Location;

import java.util.ArrayList;

public class Exit {
    private Location[] locations;
    private boolean open;

    public Exit(Location[] locations, boolean open) {
        this.locations = locations;
        this.open = open;
    }

    public Location[] getLocations() {
        return this.locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
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
        if(locations[0] == current){
            return locations[1];
        } else if (locations[1] == current) {
            return locations[0];
        } else
            return null;
    }

    public boolean connectionExists(Location loc0, Location loc1){
        //does this exit connect these two locations? (potential for reverse)
        if((locations[0] == loc0 && locations[1] == loc1) || (locations[0] == loc1 && locations[1] == loc0)){
            return true;
        }
        return false;
    }

    public boolean containsLocation(Location loc){
        //does this exit connect to this one location?
        if(locations[0] == loc ||  locations[1] == loc){
            return true;
        }
        return false;
    }

}
