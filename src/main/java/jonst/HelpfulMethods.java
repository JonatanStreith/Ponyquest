package jonst;

import jonst.Data.ReverseSortIgnoreCase;
import jonst.Data.SortIgnoreCase;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;

import java.util.*;
import java.util.stream.Collectors;

public class HelpfulMethods {


    public static String heOrShe(GenericObject subject) {

        if (subject == App.getWorld().getPlayer()) {
            return "you";
        }

        if (!(subject instanceof Creature)) {
            return "it";
        } else {

            switch (subject.getGender().toLowerCase()) {
                case "male":
                    return "he";
                case "female":
                    return "she";
                default:
                    return "it";
            }
        }
    }

    public static String himOrHer(GenericObject subject) {

        if (!(subject instanceof Creature)) {
            return "it";
        } else {
            switch (subject.getGender().toLowerCase()) {
                case "male":
                    return "him";
                case "female":
                    return "her";
                default:
                    return "it";
            }
        }
    }

    public static String hisOrHer(GenericObject subject) {
        if (!(subject instanceof Creature)) {
            return "its";
        } else {
            switch (subject.getGender().toLowerCase()) {
                case "male":
                    return "his";
                case "female":
                    return "her";
                default:
                    return "its";
            }
        }
    }


    public static String capitalize(String str) {
        String cap = str.substring(0, 1).toUpperCase();
        String remainder = str.substring(1);
        return cap + remainder;
    }


    public static String isOrAre(int num) {
        String output = " is ";
        if (num > 1) {
            output = " are ";
        }

        return output;
    }

    public static String turnStringListIntoString(List<String> longList, String separator)     //Takes a list of strings, pieces them together into one string
    {
        List<String> shortList = removeDuplicatesT(longList);    //Shortlist with only single entries

        List<String> enumeratedList = new ArrayList<>();

        for (int i = 0; i < shortList.size(); i++) {        //Check each entry on the shortlist

            int counter = 0;

            for (int j = 0; j < longList.size(); j++) {         //How many times does the entry show up on the longList?
                if (shortList.get(i).equals(longList.get(j))) {
                    counter++;
                }
            }

            if (counter == 1) {
                char ch = shortList.get(i).charAt(0);
                if (Character.getType(ch) == Character.UPPERCASE_LETTER) {   //If it's uppercase, it's probably a name and shouldn't be prefixed with 'a'.
                    enumeratedList.add(shortList.get(i));
                } else if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {     //If it's a wovel
                    enumeratedList.add("an " + shortList.get(i));
                } else {                                                                        //If it's a consonant
                    enumeratedList.add("a " + shortList.get(i));
                }
            } else {
                enumeratedList.add(counter + " " + shortList.get(i) + "s");                     //Amend the multiples with the counter
            }

        }


        String fullString = "";

        for (int i = 0; i < enumeratedList.size(); i++) {

            if (i == enumeratedList.size() - 1)        //Last entry, just end with the word
                fullString += enumeratedList.get(i);
            else if (i == enumeratedList.size() - 2)  //Second to last, put an an "and"
                fullString += (enumeratedList.get(i) + " " + separator + " ");

            else
                fullString += (enumeratedList.get(i) + ", "); //Comma separate

        }

        return fullString;
    }

    public static <T> String turnListIntoString(List<T> list, String separator) {
        List<String> nameList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            GenericObject obj = (GenericObject) list.get(i);
            if(obj.hasAttribute("worn")) {

                nameList.add(obj.getName() + " (worn)");
            } else {
                nameList.add(obj.getName());
            }
        }

        return turnStringListIntoString(nameList, "and");
    }

    public static void reverseSortStringList(List<String> list) {

        Collections.sort(list, new ReverseSortIgnoreCase());



    }


    public static <T> List<T> removeDuplicatesT(List<T> list) {

        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }


}
