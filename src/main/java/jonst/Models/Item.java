package jonst.Models;

import jonst.Data.ItemData;

import java.io.Console;

public class Item extends GenericObject {


    public Item(String inputName) //Constructor
    {
        name = inputName;

        if (ItemData.ItemShortNames.ContainsKey(name))
        { shortName = ItemData.ItemShortNames[name]; }
        else
        { shortName = name; }






        if (ItemData.itemDescriptions.ContainsKey(name))
        {                description = ItemData.itemDescriptions[name];            }
        else
        {
            Console.WriteLine($"{name} lacks description");
            description = "[description missing]";
        }

    }

}
