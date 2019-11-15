package jonst.Models;

import jonst.Data.CreatureData;

import java.io.Console;
import java.util.List;

public class Creature extends GenericObject {

    private String race;

    private List<String> inventory = new List<String>();

    public Creature(String inputName, String inputRace)
    {
        name = inputName;
        race = inputRace;

        if (CreatureData.CreatureShortNames.ContainsKey(name))
        { shortName = CreatureData.CreatureShortNames[name]; }
        else
        { shortName = name; }





        if (CreatureData.creatureDescriptions.ContainsKey(name))
        { description = CreatureData.creatureDescriptions[name]; }
        else
        {
            Console.WriteLine($"{name} lacks description");
            description = "[description missing]";
        }

    }



    public String GetRace()
    { return race; }

}
