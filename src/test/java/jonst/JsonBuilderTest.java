package jonst;

import jonst.Data.JsonBuilder;
import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Dialog;
import jonst.Models.Objects.GenericObject;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class JsonBuilderTest {


    @Test
    public void getSavesMenuTest() {

        Map<Long, String> test = JsonBuilder.getSavesMenu();

        assertNotNull(test);
        assertNotEquals(0, test.size());

    }

    @Test
    public void addToSavesMenuTest() {

        JsonBuilder.addToSavesMenu("Lars");

        Map<Long, String> test = JsonBuilder.getSavesMenu();

        List<String> values = new ArrayList<>();
        values.addAll(test.values());


        assertNotNull(Lambda.getFirst(values, x -> x.equals("Lars")));

    }

    @Test
    public void dialogBuilderTest() {

        List<Dialog> testList = JsonBuilder.generateDialogList();

        assertTrue(testList.size() > 0);

    }

    @Test
    public void generateTemplateListTest() {
        List<GenericObject> testList = JsonBuilder.generateTemplateList();

        assertTrue(testList.size() > 0);
        assertNotNull(Lambda.getFirst(testList, Lambda.objectByName("apple")));
    }


    @Test
    public void generateLists() {

        List<GenericObject> itemList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/items.json", "item");
        List<GenericObject> creatureList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/creatures.json", "creature");
        List<GenericObject> locationList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/locations.json", "location");
        List<GenericObject> objectList = JsonBuilder.loadList(SystemData.getDefaultWorld() + "/objects.json", "stationaryobject");

        assertNotNull(Lambda.getFirst(itemList, Lambda.objectByName("apple")));
        assertNotNull(Lambda.getFirst(creatureList, Lambda.objectByName("Trixie")));
        assertNotNull(Lambda.getFirst(locationList, Lambda.objectByName("Sugarcube Corner")));
        assertNotNull(Lambda.getFirst(objectList, Lambda.objectByName("mirror")));
    }
}
