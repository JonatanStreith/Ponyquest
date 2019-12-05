package jonst;

import jonst.Models.Creature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HelpfulMethodsTest {


    @Test
    public void name() {

        List<String> testcase = new ArrayList<>();

        testcase.add("Adam");
        testcase.add("Bertil");
        testcase.add("Douglas");
        testcase.add("Zanzibar");
        testcase.add("Gweep");
        testcase.add("Gwen");
        testcase.add("Clive");
        //testcase.add("Eight");

        for (String str: testcase
             ) {
            System.out.print(str+" ");
        }

        System.out.println("");

        HelpfulMethods.reverseSortStringList(testcase);

        for (String str: testcase
        ) {
            System.out.print(str+" ");
        }

    }


    @Test
    public void turnCreatureListIntoStringTest() {

        List<Creature> list = new ArrayList() {{
            //add(new Creature("Pinkie Pie", "", "", "", "", new ArrayList<>() ));
            //add(new Creature("Applejack", "", "", "", "", new ArrayList<>() ));
            //add(new Creature("Rarity", "", "", "", "", new ArrayList<>() ));
            //add(new Creature("Applejack", "", "", "", "", new ArrayList<>() ));
            add(new Creature("Rarity", "", "", "", "", new ArrayList<>() ));
        }};


        String result = HelpfulMethods.turnCreatureListIntoString(list);

        System.out.println(result);

    }
}
