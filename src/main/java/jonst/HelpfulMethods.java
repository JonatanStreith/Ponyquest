package jonst;

import jonst.Models.Creature;
import jonst.Models.Item;

import java.util.ArrayList;
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
        String fullString = "";
        /*List<String> nameList = new ArrayList<String>();

        for (String item : list)
        { nameList.add(item); }

        if (nameList.size() >= 2)
        {
            for (int i = 0; i < nameList.size() - 2; i++)
            { nameList[i] += ", "; }

            nameList[nameList.size() - 2] += " and ";
        }

        for (int i = 0; i < nameList.size(); i++)
        { fullString += nameList[i]; }*/

        for (int i = 0; i < list.size(); i++) {

            if(i == list.size() - 1)        //Last entry, just end with the word
                fullString += list.get(i);
            else if(i == list.size() - 2)
                fullString += (list.get(i) + " and ");

            else
                fullString += (list.get(i) + ", ");

        }

        return fullString;
    }


    public static String TurnItemListIntoString(List<Item> list)     //Takes a list of objects, pieces together their names into one string
    {                                                                               //Note: This omits Trixie, as she doesn't need to be mentioned
        String fullString = "";
        List<String> nameList = new ArrayList<String>();

        for (Item item : list)
        { nameList.add(item.GetName()); }    //Generates a list of names

        if (nameList.size() >= 2)
        {
            for (int i = 0; i < nameList.size() - 2; i++)
            { nameList.set(i, ", "); }

            nameList.set(nameList.size() - 2, " and ");
        }

        for (int i = 0; i < nameList.size(); i++)
        { fullString += nameList.get(i); }

        return fullString;
    }










    public static String TurnCreatureListIntoString(List<Creature> list)     //Takes a list of objects, pieces together their names into one string
    {                                                                               //Note: This omits Trixie, as she doesn't need to be mentioned
        String fullString = "";
        List<String> nameList = new ArrayList<String>();

        for(Creature item : list)
        { nameList.add(item.GetName()); }    //Generates a list of names


        if (nameList.size() >= 3)
        {
            for (int i = 0; i < nameList.size() - 3; i++)
            { nameList.set(i, ", "); }

            nameList.set(nameList.size() - 3, " and ");
        }

        for (int i = 0; i < nameList.size() - 1; i++)
        { fullString += nameList.get(i); }

        return fullString;
    }

}
