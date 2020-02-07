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

}
