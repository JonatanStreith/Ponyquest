package jonst.Models;

import jonst.Data.CreatureData;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Creature extends GenericObject {

    private String race;

    private List<String> inventory = new ArrayList<String>();

    public Creature(String inputName, String inputRace)
    {
        name = inputName;
        race = inputRace;

        if (CreatureData.hasCreatureShortName(name))
        { shortName = CreatureData.getCreatureShortName(name); }
        else
        { shortName = name; }





        if (CreatureData.hasCreatureDescription(name))
        { description = CreatureData.getCreatureDescription(name); }
        else
        {
            System.out.println(name + " lacks description.");
            description = "[description missing]";
        }

    }



    public String GetRace()
    { return race; }

}
