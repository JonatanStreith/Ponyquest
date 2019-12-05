package jonst;

import jonst.Models.Creature;
import jonst.Models.GenericObject;
import jonst.Models.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelpfulMethods {

    public static String isOrAre(int num)
    {
        String output = " is ";
        if (num > 1) { output = " are "; };

        return output;
    }

    public static String turnStringListIntoString(List<String> list)     //Takes a list of strings, pieces them together into one string
    {
        String fullString = "";

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



    public static String turnItemListIntoString(List<Item> list)     //Takes a list of objects, pieces together their names into one string
    {
        String fullString = "";
        List<String> nameList = new ArrayList<String>();

        for (Item item : list)
        { nameList.add(item.getName()); }    //Generates a list of names

        if (nameList.size() >= 2)
        {
            for (int i = 0; i < nameList.size() - 2; i++)
            { nameList.set(i, nameList.get(i) + ", "); }

            nameList.set(nameList.size() - 2, nameList.get(nameList.size() - 2) +  " and ");
        }

        for (int i = 0; i < nameList.size(); i++)
        { fullString += nameList.get(i); }

        return fullString;
    }


    public static String turnCreatureListIntoString(List<Creature> list)     //Takes a list of objects, pieces together their names into one string
    {
        String fullString = "";
        List<String> nameList = new ArrayList<String>();

        for(Creature item : list)
        { nameList.add(item.getName()); }    //Generates a list of names

        if (nameList.size() >= 2)
        {
            for (int i = 0; i < nameList.size() - 2; i++)
            { nameList.set(i, nameList.get(i) + ", "); }

            nameList.set(nameList.size() - 2, nameList.get(nameList.size() - 2) +  " and ");
        }

        for (int i = 0; i < nameList.size(); i++)
        { fullString += nameList.get(i); }

        return fullString;
    }


    public static void reverseSortStringList(List<String> list){

        Collections.sort(list);

        int size = list.size();

        for (int i = 0; i < size/2 ; i++) {

            String temp = list.get(i);      //Set temp var to first position
            list.set(i, list.get(size-i-1));  //Sets first position to last position
            list.set(size-i-1, temp);         //Sets last position to temp var

        }



        //return theList;
    }



}
