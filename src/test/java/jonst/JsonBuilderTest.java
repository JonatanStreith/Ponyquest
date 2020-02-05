package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Item;
import jonst.Models.Objects.Location;
import jonst.Models.Objects.StationaryObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonBuilderTest {


    @Test
    public void buildCreatureListTest() {


        List<Creature> test = JsonBuilder.loadCreatureList(SystemData.getDefaultWorld());

        for (Creature cre : test) {
            System.out.println(cre.getName());
        }

    }

    @Test
    public void buildItemListTest() {


        List<Item> test = JsonBuilder.loadItemList(SystemData.getDefaultWorld());

        for (Item ite : test) {
            System.out.println(ite.getName());
        }

    }

    @Test
    public void buildLocationListTest() {


        List<Location> test = JsonBuilder.loadLocationList(SystemData.getDefaultWorld());

        for (Location loc : test) {
            System.out.println(loc.getName());
        }

    }

    @Test
    public void buildObjectListTest() {


        List<StationaryObject> test = JsonBuilder.loadStationaryObjectList(SystemData.getDefaultWorld());

        for (StationaryObject sta : test) {
            System.out.println(sta.getName());
        }

    }

    @Test
    public void saveCreatureListTest() {

        List<Creature> testlist = JsonBuilder.loadCreatureList(SystemData.getDefaultWorld());

        boolean test = JsonBuilder.saveCreatureList(SystemData.getTestpath(), testlist);

        assertTrue(test);
    }


    @Test
    public void saveItemListTest() {

        List<Item> testlist = JsonBuilder.loadItemList(SystemData.getDefaultWorld());

        boolean test = JsonBuilder.saveItemList(SystemData.getTestpath(), testlist);

        assertTrue(test);
    }
    @Test
    public void saveLocationListTest() {

        List<Location> testlist = JsonBuilder.loadLocationList(SystemData.getDefaultWorld());

        boolean test = JsonBuilder.saveLocationList(SystemData.getTestpath(), testlist);

        assertTrue(test);
    }
    @Test
    public void saveStationaryObjectListTest() {

        List<StationaryObject> testlist = JsonBuilder.loadStationaryObjectList(SystemData.getDefaultWorld());

        boolean test = JsonBuilder.saveStationaryObjectList(SystemData.getTestpath(), testlist);

        assertTrue(test);
    }

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
    public void generateDefaultItemTest(){

        Item testItem = JsonBuilder.generateTemplateItem("red Apple");
        assertNull(testItem);

    }

    @Test
    public void test() {
        Map<String, Integer> testcase = new HashMap<>();

        testcase.put("John", 20);
        testcase.put("John", 30);

        System.out.println(testcase.get("John")) ;

    }
}
