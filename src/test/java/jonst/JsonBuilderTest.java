package jonst;

import jonst.Data.SystemData;
import jonst.Models.Creature;
import jonst.Models.Item;
import jonst.Models.Location;
import jonst.Models.StationaryObject;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class JsonBuilderTest {


    @Test
    public void buildCreatureListTest() {


        List<Creature> test = JsonBuilder.loadCreatureList(SystemData.defaultWorld);

        for (Creature cre : test) {
            System.out.println(cre.getName());
        }

    }

    @Test
    public void buildItemListTest() {


        List<Item> test = JsonBuilder.loadItemList(SystemData.defaultWorld);

        for (Item ite : test) {
            System.out.println(ite.getName());
        }

    }

    @Test
    public void buildLocationListTest() {


        List<Location> test = JsonBuilder.loadLocationList(SystemData.defaultWorld);

        for (Location loc : test) {
            System.out.println(loc.getName());
        }

    }

    @Test
    public void buildObjectListTest() {


        List<StationaryObject> test = JsonBuilder.loadStationaryObjectList(SystemData.defaultWorld);

        for (StationaryObject sta : test) {
            System.out.println(sta.getName());
        }

    }

    @Test
    public void saveCreatureListTest() {

        List<Creature> testlist = JsonBuilder.loadCreatureList(SystemData.defaultWorld);

        boolean test = JsonBuilder.saveCreatureList(SystemData.testpath, testlist);

        assertTrue(test);
    }


    @Test
    public void saveItemListTest() {

        List<Item> testlist = JsonBuilder.loadItemList(SystemData.defaultWorld);

        boolean test = JsonBuilder.saveItemList(SystemData.testpath, testlist);

        assertTrue(test);
    }
    @Test
    public void saveLocationListTest() {

        List<Location> testlist = JsonBuilder.loadLocationList(SystemData.defaultWorld);

        boolean test = JsonBuilder.saveLocationList(SystemData.testpath, testlist);

        assertTrue(test);
    }
    @Test
    public void saveStationaryObjectListTest() {

        List<StationaryObject> testlist = JsonBuilder.loadStationaryObjectList(SystemData.defaultWorld);

        boolean test = JsonBuilder.saveStationaryObjectList(SystemData.testpath, testlist);

        assertTrue(test);
    }

}
