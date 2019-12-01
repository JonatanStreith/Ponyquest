package jonst.Models;

import java.util.ArrayList;

public class GenericObject {
    protected String name;
    protected String shortName;

    protected String description;

    protected String locationName;

public GenericObject(String name, String shortName, String description, String locationName)
{

    this.name = name;
    this.shortName = shortName;
    this.description = description;
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

    public void setLocation(String locationName) {
        this.locationName = locationName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void getFeedback(){
    String feedback = name + "(" + shortName + "): " + description;
        System.out.println(feedback);
    }
}
