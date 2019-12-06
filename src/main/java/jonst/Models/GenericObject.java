package jonst.Models;

public class GenericObject {
    private String name;
    private String shortName;

    private String description;

    private String locationName;

    private Location location;


    public GenericObject(String name, String shortName, String description, String locationName)
{
    setName(name);

    setShortName(shortName);
    setDescription(description);
    setLocationName(locationName);

}
    protected void setName(String name) {
        this.name = name;
    }

    protected void setShortName(String shortName) {
        this.shortName = shortName;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationName() {
        return locationName;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        locationName = location.getLocationName();
    }




    public void getFeedback(){
    String feedback = name + "(" + shortName + "): " + description;
        System.out.println(feedback);
    }

    public boolean isAtLocation(Location location){
        return getLocation() == location;
    }

}
