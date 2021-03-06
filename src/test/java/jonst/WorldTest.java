package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.*;
import jonst.Models.World;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WorldTest {

    World world;

    @Before
    public void setUp() {
        world = new World(SystemData.getDefaultWorld());
    }

    @Test
    public void name() {

        GenericObject o1 = world.getTemplateById("Item_Food_apple");
        GenericObject o2 = world.getTemplateById("Item_Teacup");
        GenericObject o3 = world.getTemplateById("Object_Tree");

        System.out.println(o1.getClass()==o2.getClass());
        System.out.println(o1.getClass()==o3.getClass());

    }

    @Test
    public void ConstructorTest() {

        assertNotNull(world);
        assertNotNull(world.getStationaryObjectList());
        assertNotNull(world.getLocationList());
        assertNotNull(world.getItemList());
        assertNotNull(world.getCreatureList());
        assertNotNull(world.getGenericList());

        assertNotEquals(0, world.getCreatureList().size());
        assertNotEquals(0, world.getGenericList().size());
        assertNotEquals(0, world.getItemList().size());
        assertNotEquals(0, world.getLocationList().size());
        assertNotEquals(0, world.getStationaryObjectList().size());

        assertNotNull(world.getPlayer());
        assertEquals("Trixie", world.getPlayer().getName());

        assertNotNull(world.getParser());

        assertEquals("Junkyard", world.getItem("Error item").getLocationId());    //Check that the error item gets sent to the junkyard.


    }


//    @Test
//    public void transferCreatureToLocationTest() {
//
//        Location acres = world.getLocation("Sweet Apple Acres");
//        Location castle = world.getLocation("Castle of Friendship");
//        Creature applejack = world.getCreature("Applejack");
//
//        assertTrue(acres.creatureIsAtLocation(applejack));      //Applejack is currently at Sweet Apple Acres
//        assertFalse(castle.creatureIsAtLocation(applejack));    //And not at the castle
//
//        world.transferCreatureToLocation("applejack", "sweet apple acres", "castle of friendship");
//
//        assertTrue(castle.creatureIsAtLocation(applejack)); //Applejack is now at the castle
//        assertFalse(acres.creatureIsAtLocation(applejack)); //And not at the Acres
//
//        //world.transferCreatureToLocation("donut steel", "the scrapyard", "castle on the hill" );
//
//
//    }

//    @Test
//    public void transferItemToLocationTest() {
//
//        Location acres = world.getLocation("Sweet Apple Acres");
//        Location castle = world.getLocation("Castle of Friendship");
//        Item crate = world.getItem(world.matchLocalName("crate"));
//
//        assertTrue(acres.itemIsAtLocation(crate));
//        assertFalse(castle.itemIsAtLocation(crate));
//
//        world.transferItemToNewOwner(world.matchLocalName("crate"), "sweet apple acres", "castle of friendship");
//
//        assertTrue(castle.itemIsAtLocation(crate));
//        assertFalse(acres.itemIsAtLocation(crate));
//
//
//    }

//    @Test
//    public void transferObjectToLocationTest() {
//
//        Location acres = world.getLocation("Sweet Apple Acres");
//        Location castle = world.getLocation("Castle of Friendship");
//        StationaryObject tree = world.getStationaryObject(world.matchLocalName("tree"));
//
//        assertTrue(acres.stationaryObjectIsAtLocation(tree));
//        assertFalse(castle.stationaryObjectIsAtLocation(tree));
//
//        world.transferObjectToLocation(world.matchLocalName("tree"), "sweet apple acres", "castle of friendship");
//
//        assertTrue(castle.stationaryObjectIsAtLocation(tree));
//        assertFalse(acres.stationaryObjectIsAtLocation(tree));
//    }

    @Test
    public void existsTest() {
        assertTrue(world.objectExists("Trixie"));
        assertFalse(world.objectExists("illegal"));
    }


    // --------------- Setters & Getters ------------------------


    @Test
    public void setMainCharacterTest() {

        boolean success = world.setMainCharacter(world.getCreature("Pinkie Pie"));

        assertEquals("Pinkie Pie", world.getPlayer().getName());
        assertTrue(success);

    }

    @Test
    public void getPlayerTest() {

        Creature main = world.getPlayer();

        assertEquals("Trixie", main.getName());

    }

    @Test
    public void getPlayerLocationTest() {

        Location loc = world.getPlayerLocation();

        assertEquals(world.getPlayer().getLocation().getName(), loc.getName());

    }

    @Test
    public void getPlayerInventoryTest() {
        List<Item> testinv = world.getPlayerInventory();

        assertNotNull(testinv);
        assertEquals(3, testinv.size());

    }

    @Test
    public void getLocationListTest() {

        assertNotNull(world.getLocationList());
        assertFalse(world.getLocationList().isEmpty());
    }

    @Test
    public void getCreatureListTest() {

        assertNotNull(world.getCreatureList());
        assertFalse(world.getCreatureList().isEmpty());
    }

    @Test
    public void getStationaryObjectListTest() {

        assertNotNull(world.getStationaryObjectList());
        assertFalse(world.getStationaryObjectList().isEmpty());
    }

    @Test
    public void getItemListTest() {

        assertNotNull(world.getItemList());
        assertFalse(world.getItemList().isEmpty());
    }

    @Test
    public void getGenericListTest() {

        assertNotNull(world.getGenericList());
        assertFalse(world.getGenericList().isEmpty());
    }

    @Test
    public void getParserTest() {

        assertNotNull(world.getParser());
    }

    // ------------- Methods that returns objects from object lists ------------------

    @Test
    public void getLocationTest() {

        Location castle = world.getLocationByName("Castle of Friendship");

        Location fail = world.getLocationByName("Nonexistent place");

        assertNotNull(castle);
        assertNull(fail);
        assertEquals("Castle of Friendship", castle.getName());

    }

    @Test
    public void getCreatureTest() {

        Creature pinkie = world.getCreature("Pinkie Pie");

        Creature fail = world.getCreature("Chuck Norris");

        assertNotNull(pinkie);
        assertNull(fail);
        assertEquals("Pinkie Pie", pinkie.getName());

    }

    @Test
    public void getItemTest() {

        Item box = world.getItem("a wooden crate");
        Item fail = world.getItem("machine gun");

        assertNotNull(box);
        assertNull(fail);
        //assertEquals("crate", box.getShortName());

    }

    @Test
    public void getStationaryObjectTest() {

        StationaryObject oven = world.getStationaryObject("baking oven");
        StationaryObject fail = world.getStationaryObject("genetics lab");

        assertNotNull(oven);
        assertNull(fail);
       // assertEquals("oven", oven.getShortName());
    }


    @Test
    public void getGenericObjectTest() {


        GenericObject gen = world.getGenericObject("an apple tree");
        GenericObject fail = world.getGenericObject("something that doesn't exist");

        assertNotNull(gen);
        assertNull(fail);
      //  assertEquals("tree", gen.getShortName());
    }


}
