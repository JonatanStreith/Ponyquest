package jonst.Models.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Vehicle extends StationaryObject {

    List<String> destinationIds;

    List<Location> destinations;

    public Vehicle(String name, String shortName, String type, String id, String locationId, String defaultLocationId, List<String> alias, List<String> attributes, Map<String, String> descriptions, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, String ownerId, List<String> destinationIds) {
        super(name, shortName, type, id, locationId, defaultLocationId, alias, attributes, descriptions, text, defaultUse, complexUse, responseScripts, ownerId);
        setDestinationIds(destinationIds);

        destinations = new ArrayList<>();
    }

    public Vehicle(Vehicle template) {
        this(template.getName(), template.getShortName(), template.getType(), template.getId(), template.getLocationId(), template.getDefaultLocationId(), template.getAlias(), template.getAttributes(), template.getDescriptions(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts(), template.getOwnerId(), template.getDestinationIds());

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
