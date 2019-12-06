package jonst;

import jonst.Models.Creature;
import jonst.Models.Item;
import jonst.Models.StationaryObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HelpfulMethodsTest {


    @Test
    public void isOrAreTest() {

        int num0 = 0;
        int num1 = 1;
        int num2 = 2;
        int num3 = 3;

        assertEquals(" is ", HelpfulMethods.isOrAre(num0));
        assertEquals(" is ", HelpfulMethods.isOrAre(num1));
        assertEquals(" are ", HelpfulMethods.isOrAre(num2));
        assertEquals(" are ", HelpfulMethods.isOrAre(num3));

    }

    @Test
    public void turnStringListIntoStringTest() {

        List<String> list = new ArrayList() {{
            add("Pinkie Pie");
            add("Applejack");
            add("Rarity");
            add("Applejack");
            add("Rarity");
        }};


        String result1 = HelpfulMethods.turnStringListIntoString(list);

        System.out.println(result1);

        assertNotEquals("", result1);               //Assume the line's not blank
        assertTrue(result1.contains(list.get(0)));    //Assume the line contains the first entry's name


        list.clear();

        String result2 = HelpfulMethods.turnStringListIntoString(list);

        assertEquals("", result2);          //Assume the line is blank now


    }

    @Test
    public void turnCreatureListIntoStringTest() {

        List<Creature> list = new ArrayList() {{
            add(new Creature("Pinkie Pie", "", "", "", "", new ArrayList<>() ));
            add(new Creature("Applejack", "", "", "", "", new ArrayList<>() ));
            add(new Creature("Rarity", "", "", "", "", new ArrayList<>() ));
            add(new Creature("Applejack", "", "", "", "", new ArrayList<>() ));
            add(new Creature("Rarity", "", "", "", "", new ArrayList<>() ));
        }};


        String result1 = HelpfulMethods.turnCreatureListIntoString(list);

        System.out.println(result1);

        assertNotEquals("", result1);               //Assume the line's not blank
        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name


        list.clear();

        String result2 = HelpfulMethods.turnCreatureListIntoString(list);

        assertEquals("", result2);          //Assume the line is blank now

    }


    @Test
    public void turnItemListIntoStringTest() {

        List<Item> list = new ArrayList() {{
            add(new Item("rope", "", "", "" ));
            add(new Item("apple", "", "", "" ));
            add(new Item("box", "", "", "" ));
            add(new Item("crowbar", "", "", "" ));
            add(new Item("shoe", "", "", "" ));
        }};


        String result1 = HelpfulMethods.turnItemListIntoString(list);

        System.out.println(result1);

        assertNotEquals("", result1);               //Assume the line's not blank
        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name


        list.clear();

        String result2 = HelpfulMethods.turnItemListIntoString(list);

        assertEquals("", result2);          //Assume the line is blank now


    }

    @Test
    public void turnStationaryObjectListIntoStringTest() {

        List<StationaryObject> list = new ArrayList() {{
            add(new StationaryObject("Pinkie Pie", "", "", "" ));
            add(new StationaryObject("Applejack", "", "", "" ));
            add(new StationaryObject("Rarity", "", "", "" ));
            add(new StationaryObject("Applejack", "", "", "" ));
            add(new StationaryObject("Rarity", "", "", "" ));
        }};


        String result1 = HelpfulMethods.turnStationaryObjectListIntoString(list);

        System.out.println(result1);

        assertNotEquals("", result1);               //Assume the line's not blank
        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name


        list.clear();

        String result2 = HelpfulMethods.turnStationaryObjectListIntoString(list);

        assertEquals("", result2);          //Assume the line is blank now


    }

    @Test
    public void reverseSortStringListTest() {

        List<String> test = new ArrayList() {{add("Betty"); add("Adam"); add("Cecil");}};

        for (String line: test) {
            System.out.print(line + ", ");
        }


        HelpfulMethods.reverseSortStringList(test);

        for (String line: test) {
            System.out.print(line + ", ");
        }
        assertEquals("Cecil", test.get(0));
        assertEquals("Adam", test.get(2));

    }
}
