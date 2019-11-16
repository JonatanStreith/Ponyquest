package jonst.Models;

import jonst.Data.StationaryObjectData;

import java.io.Console;

public class StationaryObject extends GenericObject {

    public StationaryObject(String inputName)
    {
        name = inputName;

        if (StationaryObjectData.hasStationaryObjectShortName(name))
        { shortName = StationaryObjectData.getStationaryObjectShortName(name); }
        else
        { shortName = name; }



        if (StationaryObjectData.hasStationaryObjectDescription(name))
        {                description = StationaryObjectData.getStationaryObjectDescription(name);            }
        else
        {
            System.out.println(name + " lacks description");
            description = "[description missing]";
        }

    }
}
