//package jonst.Models.Objects;
//
//import jonst.Models.Cores.ActionCore;
//import jonst.Models.Cores.IdentityCore;
//import jonst.Models.Cores.RelationCore;
//import jonst.Models.Mods.GenericMod;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class Vehicle extends StationaryObject {
//
//    List<String> destinationIds;
//
//    List<Location> destinations;
//
//    public Vehicle(IdentityCore identityCore, RelationCore relationCore, ActionCore actionCore, Map<String, GenericMod> roleMods, List<String> destinationIds) {
//        super(identityCore, relationCore, actionCore, roleMods);
//        setDestinationIds(destinationIds);
//
//        destinations = new ArrayList<>();
//    }
//
//    public Vehicle(Vehicle template) {
//        this(template.getIdentityCore(), template.getRelationCore(), template.getActionCore(), template.getRoleMods(), template.getDestinationIds());
//
//        destinations = template.getDestinations();
//    }
//
//
//    public List<String> getDestinationIds() {
//        return this.destinationIds;
//    }
//
//    public void setDestinationIds(final List<String> destinationIds) {
//        this.destinationIds = destinationIds;
//    }
//
//    public List<Location> getDestinations() {
//        return this.destinations;
//    }
//
//    public void setDestinations(final List<Location> destinations) {
//        this.destinations = destinations;
//    }
//
//    public void addDestination(Location dest){
//        if(!destinations.contains(dest)) {
//            destinations.add(dest);
//        }
//    }
//
//    public void removeDestination(Location dest){
//        if(destinations.contains(dest)) {
//            destinations.remove(dest);
//        }
//    }
//}
