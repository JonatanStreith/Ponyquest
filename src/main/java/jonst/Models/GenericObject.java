package jonst.Models;

public class GenericObject {
    protected String name;
    protected String shortName;

    protected String description;

    protected String locationName;


    public String GetName()
    { return name; }

    public String GetShortName()
    { return shortName; }

    public String GetDescription()
    { return description; }

    public String GetLocationName()
    { return locationName; }

    public void SetLocation(String loc)
    { locationName = loc; }
}
