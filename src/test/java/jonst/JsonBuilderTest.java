package jonst;

import jonst.Data.JsonBuilder;
import jonst.Data.SystemData;
import jonst.Models.Dialog;
import jonst.Models.Objects.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonBuilderTest {






    @Test
    public void getSavesMenuTest() {

        Map<Long, String> test = JsonBuilder.getSavesMenu();

        assertNotNull(test);
        assertNotEquals(0, test.size());
        assertEquals("backup", test.get((long)1));

        for (long key: test.keySet() ) {
            System.out.println(key + ": " + test.get(key));
        }

    }

    @Test
    public void addToSavesMenuTest() {

        JsonBuilder.addToSavesMenu("Lars");

    }



    @Test
    public void test() {
        Map<String, Integer> testcase = new HashMap<>();

        testcase.put("John", 20);
        testcase.put("John", 30);

        System.out.println(testcase.get("John")) ;

    }

    @Test
    public void dialogBuilderTest() {

        List<Dialog> testList = JsonBuilder.generateDialogList();

        System.out.println("Stop here.");
    }

    @Test
    public void generateTemplateListTest() {
        List<GenericObject> testList = JsonBuilder.generateTemplateList();

        System.out.println(".");
    }

    @Test
    public void generateLists(){

        List<GenericObject> itemList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/items.json", "item");
        List<GenericObject> creatureList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/creatures.json", "creature");
        List<GenericObject> locationList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/locations.json", "location");
        List<GenericObject> objectList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/objects.json", "stationaryobject");

        System.out.println(".");
    }
}
