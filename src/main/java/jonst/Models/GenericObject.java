package jonst.Models;

public class GenericObject {
    protected String name;
    protected String shortName;

    protected String description;

    protected String locationName;


    public String getName()
    { return name; }

    public String getShortName()
    { return shortName; }

    public String getDescription()
    { return description; }

    public String getLocationName()
    { return locationName; }

    public void setLocation(String loc)
    { locationName = loc; }
}
