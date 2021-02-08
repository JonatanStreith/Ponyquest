package jonst.Models.Objects;

import jonst.Models.Cores.IdentityCore;
import jonst.Models.Cores.RelationCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Vehicle extends StationaryObject {

    List<String> destinationIds;

    List<Location> destinations;

    public Vehicle(IdentityCore identityCore, RelationCore relationCore, List<String> attributes, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts, List<String> destinationIds) {
        super(identityCore, relationCore, attributes, text, defaultUse, complexUse, responseScripts);
        setDestinationIds(destinationIds);

        destinations = new ArrayList<>();
    }

    public Vehicle(Vehicle template) {
        this(template.getIdentityCore(), template.getRelationCore(), template.getAttributes(), template.getText(), template.getDefaultUse(), template.getComplexUse(), template.getResponseScripts(), template.getDestinationIds());

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
