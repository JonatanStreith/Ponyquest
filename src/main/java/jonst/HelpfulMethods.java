package jonst;

import jonst.Models.Creature;
import jonst.Models.Item;

import java.util.List;

public class HelpfulMethods {

    public static String IsOrAre(int num)
    {
        String output = "is";
        if (num > 1) { output = "are"; };

        return output;
    }











    public static String TurnStringListIntoString(List<String> list)     //Takes a list of strings, pieces them together into one string
    {
        string fullString = "";
        List<string> nameList = new List<string>();

        foreach (string item in list)
        { nameList.Add(item); }

        if (nameList.Count >= 2)
        {
            for (int i = 0; i < nameList.Count - 2; i++)
            { nameList[i] += ", "; }

            nameList[nameList.Count - 2] += " and ";
        }

        for (int i = 0; i < nameList.Count; i++)
        { fullString += nameList[i]; }

        return fullString;
    }


    public static String TurnItemListIntoString(List<Item> list)     //Takes a list of objects, pieces together their names into one string
    {                                                                               //Note: This omits Trixie, as she doesn't need to be mentioned
        string fullString = "";
        List<string> nameList = new List<string>();

        foreach (Item item in list)
        { nameList.Add(item.GetName()); }    //Generates a list of names

        if (nameList.Count >= 2)
        {
            for (int i = 0; i < nameList.Count - 2; i++)
            { nameList[i] += ", "; }

            nameList[nameList.Count - 2] += " and ";
        }

        for (int i = 0; i < nameList.Count; i++)
        { fullString += nameList[i]; }

        return fullString;
    }










    public static String TurnCreatureListIntoString(List<Creature> list)     //Takes a list of objects, pieces together their names into one string
    {                                                                               //Note: This omits Trixie, as she doesn't need to be mentioned
        string fullString = "";
        List<string> nameList = new List<String>();

        foreach (Creature item in list)
        { nameList.Add(item.GetName()); }    //Generates a list of names


        if (nameList.Count >= 3)
        {
            for (int i = 0; i < nameList.Count - 3; i++)
            { nameList[i] += ", "; }

            nameList[nameList.Count - 3] += " and ";
        }

        for (int i = 0; i < nameList.Count - 1; i++)
        { fullString += nameList[i]; }

        return fullString;
    }

}
