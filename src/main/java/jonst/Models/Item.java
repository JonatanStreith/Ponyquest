package jonst.Models;

import jonst.Data.ItemData;

import java.io.Console;

public class Item extends GenericObject {


    public Item(String inputName) //Constructor
    {
        name = inputName;

        if (ItemData.hasItemShortName(name))
        { shortName = ItemData.getItemShortName(name); }
        else
        { shortName = name; }






        if (ItemData.hasItemDescription(name))
        {                description = ItemData.getItemDescription(name);            }
        else
        {
            System.out.println(name + " lacks description");
            description = "[description missing]";
        }

    }

}
