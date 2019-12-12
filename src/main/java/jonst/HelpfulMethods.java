package jonst;

import jonst.Data.SortIgnoreCase;
import jonst.Models.Creature;
import jonst.Models.GenericObject;
import jonst.Models.Item;
import jonst.Models.StationaryObject;

import java.util.*;

public class HelpfulMethods {


    public static String heOrShe(String gender){
        switch(gender.toLowerCase()){
            case "male":
                return "he";
            case "female":
                return "she";
            default:
                return "it";
        }
    }

    public static String himOrHer(String gender){
        switch(gender.toLowerCase()){
            case "male":
                return "him";
            case "female":
                return "her";
            default:
                return "it";
        }
    }

    public static String isOrAre(int num) {
        String output = " is ";
        if (num > 1) {
            output = " are ";
        }
        ;

        return output;
    }

    public static String turnStringListIntoString(List<String> list, String separator)     //Takes a list of strings, pieces them together into one string
    {
        String fullString = "";

        for (int i = 0; i < list.size(); i++) {

            if (i == list.size() - 1)        //Last entry, just end with the word
                fullString += list.get(i);
            else if (i == list.size() - 2)  //Second to last, put an an "and"
                fullString += (list.get(i) + " " + separator + " ");

            else
                fullString += (list.get(i) + ", "); //Comma separate

        }

        return fullString;
    }


//    public static <T> String turnListIntoString(ArrayList<T> list) {
//        List<String> nameList = new ArrayList<String>();
//
//        if(list.size()>0) {
//            if (list.get(0) instanceof Item) {
//                for (int i = 0; i < list.size(); i++) {
//                    Item it = (Item) list.get(i);
//                    nameList.add(it.getName());
//                }
//
//            } else if (list.get(0) instanceof Creature) {
//                for (int i = 0; i < list.size(); i++) {
//                    Creature cr = (Creature) list.get(i);
//                    nameList.add(cr.getName());
//                }
//
//            } else if (list.get(0) instanceof StationaryObject) {
//                for (int i = 0; i < list.size(); i++) {
//                    StationaryObject st = (StationaryObject) list.get(i);
//                    nameList.add(st.getName());
//                }
//            }
//        }
//        return turnStringListIntoString(nameList, "and");
//    }


    public static <T> String turnListIntoString(List<T> list, String separator) {
        List<String> nameList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            GenericObject obj = (GenericObject) list.get(i);
            nameList.add(obj.getName());
        }

        return turnStringListIntoString(nameList, "and");
    }

    public static void reverseSortStringList(List<String> list) {

        Collections.sort(list, new SortIgnoreCase());

        int size = list.size();

        for (int i = 0; i < size / 2; i++) {
            String temp = list.get(i);      //Set temp var to first position
            list.set(i, list.get(size - i - 1));  //Sets first position to last position
            list.set(size - i - 1, temp);         //Sets last position to temp var
        }
    }


    public static <T> ArrayList<T> removeDuplicatesT(ArrayList<T> list) {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }


    public static List<String> removeDuplicates(ArrayList<String> list) {

        // Create a new ArrayList
        List<String> newList = new ArrayList<>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}
