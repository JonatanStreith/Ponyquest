package jonst.Models;

import jonst.Data.StationaryObjectData;

import java.io.Console;

public class StationaryObject extends GenericObject {

    public StationaryObject(String inputName)
    {
        name = inputName;

        if (StationaryObjectData.StationaryObjectShortNames.ContainsKey(name))
        { shortName = StationaryObjectData.StationaryObjectShortNames[name]; }
        else
        { shortName = name; }



        if (StationaryObjectData.stationaryObjectDescriptions.ContainsKey(name))
        {                description = StationaryObjectData.stationaryObjectDescriptions[name];            }
        else
        {
            Console.WriteLine($"{name} lacks description");
            description = "[description missing]";
        }

    }
}
