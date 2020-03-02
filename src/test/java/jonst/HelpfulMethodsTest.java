package jonst;

import jonst.Models.Objects.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class HelpfulMethodsTest {




    @Test
    public void removeDuplicatesTTest() {

        List<String> test = new ArrayList() {{ add("Ulf"); add("Ulf"); add("Tor"); add("Stefan"); }};

        List<String> newList = HelpfulMethods.removeDuplicatesT(test);

        System.out.println(newList);


    }

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


        String result1 = HelpfulMethods.turnStringListIntoString(list, "and");

        System.out.println(result1);

        assertNotEquals("", result1);               //Assume the line's not blank
        assertTrue(result1.contains(list.get(0)));    //Assume the line contains the first entry's name


        list.clear();

        String result2 = HelpfulMethods.turnStringListIntoString(list, "and");

        assertEquals("", result2);          //Assume the line is blank now


    }

//    @Test
//    public void turnCreatureListIntoStringTest() {
//
//        List<Creature> list = new ArrayList() {{
//            add(new Creature("Pinkie Pie", "", "", "", new ArrayList<>(),  "","", new ArrayList<>(), new HashMap<>()));
//            add(new Creature("Applejack", "", "", "", new ArrayList<>(), "","", new ArrayList<>(), new HashMap<>() ));
//            add(new Creature("Rarity", "", "", "", new ArrayList<>(), "","", new ArrayList<>(), new HashMap<>() ));
//            add(new Creature("Applejack", "", "", "", new ArrayList<>(), "","", new ArrayList<>(), new HashMap<>() ));
//            add(new Creature("Rarity", "", "", "",  new ArrayList<>(),"", "",  new ArrayList<>(), new HashMap<>() ));
//        }};
//
//
//        String result1 = HelpfulMethods.turnListIntoString(list, "and");
//
//        System.out.println(result1);
//
//        assertNotEquals("", result1);               //Assume the line's not blank
//        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name
//
//
//        list.clear();
//
//        String result2 = HelpfulMethods.turnListIntoString(list, "and");
//
//        assertEquals("", result2);          //Assume the line is blank now
//
//    }


//    @Test
//    public void turnItemListIntoStringTest() {
//
//        List<Item> list = new ArrayList() {{
//            add(new Item("rope", "", "", "", new ArrayList<>() ));
//            add(new Item("apple", "", "", "",  new ArrayList<>() ));
//            add(new Item("box", "", "", "",  new ArrayList<>() ));
//            add(new Item("crowbar", "", "", "",  new ArrayList<>() ));
//            add(new Item("shoe", "", "", "",  new ArrayList<>() ));
//        }};
//
//
//        String result1 = HelpfulMethods.turnListIntoString(list, "and");
//
//        System.out.println(result1);
//
//        assertNotEquals("", result1);               //Assume the line's not blank
//        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name
//
//
//        list.clear();
//
//        String result2 = HelpfulMethods.turnListIntoString(list, "and");
//
//        assertEquals("", result2);          //Assume the line is blank now
//
//
//    }

//    @Test
//    public void turnStationaryObjectListIntoStringTest() {
//
//        List<StationaryObject> list = new ArrayList() {{
//            add(new StationaryObject("Pinkie Pie", "", "", "", new ArrayList<>() ));
//            add(new StationaryObject("Applejack", "", "", "", new ArrayList<>() ));
//            add(new StationaryObject("Rarity", "", "", "", new ArrayList<>() ));
//            add(new StationaryObject("Applejack", "", "", "", new ArrayList<>() ));
//            add(new StationaryObject("Rarity", "", "", "",  new ArrayList<>() ));
//        }};
//
//
//        String result1 = HelpfulMethods.turnListIntoString(list, "and");
//
//        System.out.println(result1);
//
//        assertNotEquals("", result1);               //Assume the line's not blank
//        assertTrue(result1.contains(list.get(0).getName()));    //Assume the line contains the first entry's name
//
//
//        list.clear();
//
//        String result2 = HelpfulMethods.turnListIntoString(list, "and");
//
//        assertEquals("", result2);          //Assume the line is blank now
//
//
//    }

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

//    @Test
//    public void turnListIntoStringTest() {
//
//        List<Item> list = new ArrayList() {{
//            add(new Item("rope", "", "", "", new ArrayList<>() ));
//            add(new Item("apple", "", "", "",  new ArrayList<>() ));
//            add(new Item("box", "", "", "",  new ArrayList<>() ));
//            add(new Item("crowbar", "", "", "",  new ArrayList<>() ));
//            add(new Item("shoe", "", "", "",  new ArrayList<>() ));
//        }};
//
//
//        //String result = HelpfulMethods.turnListIntoString(list);
//    }

    @Test
    public void capitalizeTest() {

    String str = "hello";
    String str2 = HelpfulMethods.capitalize(str);

        System.out.println(str2);

        assertEquals("Hello", str2);

    }

    @Test
    public void time() throws InterruptedException {


        System.out.println("start");

        TimeUnit.MINUTES.sleep(1);
        System.out.println("end");
    }



}
