package jonst.Models.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Vehicle extends StationaryObject {

    List<String> destinationIds;

    List<Location> destinations;

    public Vehicle(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerName, List<String> destinationIds) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerName);
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
