package jonst.Models.Roles;

import jonst.Models.Objects.Location;

import java.util.ArrayList;
import java.util.List;

public class VehicleRole extends GenericRole {
    List<String> destinationIds;

    List<Location> destinations;

    public VehicleRole(List<String> destinationIds) {
        super();
        this.destinationIds = destinationIds;

        destinations = new ArrayList<>();
    }

    public List<String> getDestinationIds() {
        return this.destinationIds;
    }

    public void setDestinationIds(final List<String> destinationIds) {
        this.destinationIds = destinationIds;
    }

    public List<Location> getDestinations() {
        return this.destinations;
    }

    public void setDestinations(final List<Location> destinations) {
        this.destinations = destinations;
    }

    public void addDestination(Location dest){
        if(!destinations.contains(dest)) {
            destinations.add(dest);
        }
    }

    public void removeDestination(Location dest){
        if(destinations.contains(dest)) {
            destinations.remove(dest);
        }
    }
}
