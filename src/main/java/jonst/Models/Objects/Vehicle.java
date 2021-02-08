package jonst.Models.Objects;

import jonst.Models.Cores.IdentityCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Vehicle extends StationaryObject {

    List<String> destinationIds;

    List<Location> destinations;

    public Vehicle(IdentityCore identityCore, String locationId, String defaultLocationId, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId, List<String> destinationIds) {
        super(identityCore, locationId, defaultLocationId, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerId);
        setDestinationIds(destinationIds);

        destinations = new ArrayList<>();
    }

    public Vehicle(Vehicle template) {
        this(template.getIdentityCore(), template.getLocationId(), template.getDefaultLocationId(), template.getAttributes(), template.getDescriptions(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts(), template.getOwnerId(), template.getDestinationIds());

        destinations = template.getDestinations();
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
