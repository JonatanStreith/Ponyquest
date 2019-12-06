package jonst;

import jonst.Data.SystemData;
import jonst.Models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WorldTest {

    World world;

    @Before
    public void setUp() throws Exception {
        world = new World(SystemData.getDefaultWorld());
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

    }


    @Test
    public void transferCreatureToLocationTest() {

        Location acres = world.getLocation("Sweet Apple Acres");
        Location castle = world.getLocation("Castle of Friendship");
        Creature applejack = world.getCreature("Applejack");

        assertTrue(acres.creatureIsAtLocation(applejack));      //Applejack is currently at Sweet Apple Acres
        assertFalse(castle.creatureIsAtLocation(applejack));    //And not at the castle

        world.transferCreatureToLocation("applejack", "sweet apple acres", "castle of friendship");

        assertTrue(castle.creatureIsAtLocation(applejack)); //Applejack is now at the castle
        assertFalse(acres.creatureIsAtLocation(applejack)); //And not at the Acres

        //world.transferCreatureToLocation("donut steel", "the scrapyard", "castle on the hill" );


    }

    @Test
    public void transferItemToLocationTest() {

        Location acres = world.getLocation("Sweet Apple Acres");
        Location castle = world.getLocation("Castle of Friendship");
        Item crate = world.getItem(world.returnFullName("crate"));

        assertTrue(acres.itemIsAtLocation(crate));
        assertFalse(castle.itemIsAtLocation(crate));

        world.transferItemToLocation(world.returnFullName("crate"), "sweet apple acres", "castle of friendship");

        assertTrue(castle.itemIsAtLocation(crate));
        assertFalse(acres.itemIsAtLocation(crate));


    }

    @Test
    public void transferObjectToLocationTest() {

        Location acres = world.getLocation("Sweet Apple Acres");
        Location castle = world.getLocation("Castle of Friendship");
        StationaryObject tree = world.getStationaryObject(world.returnFullName("tree"));

        assertTrue(acres.stationaryObjectIsAtLocation(tree));
        assertFalse(castle.stationaryObjectIsAtLocation(tree));

        world.transferObjectToLocation(world.returnFullName("tree"), "sweet apple acres", "castle of friendship");

        assertTrue(castle.stationaryObjectIsAtLocation(tree));
        assertFalse(acres.stationaryObjectIsAtLocation(tree));
    }


    @Test
    public void addToInventoryTest() {

        List<Item> testinv = world.getPlayerInventory();

        assertNotNull(testinv);
        assertEquals(3, testinv.size());    //Trixie starts with hat, cape and fireworks

        world.addToInventory(world.getItem("a heavy plow"));

        assertEquals(4, testinv.size());    //...and a plow

        for (Item item : testinv) {
            System.out.println(item.getName());
        }

    }


    @Test
    public void removeFromInventoryTest() {

        List<Item> testinv = world.getPlayerInventory();

        assertNotNull(testinv);
        assertEquals(3, testinv.size());    //Trixie starts with hat, cape and fireworks

        world.removeFromInventory(world.getItem(world.returnFullName("hat")));

        assertEquals(2, testinv.size());

        for (Item item : testinv) {
            System.out.println(item.getName());
        }


    }


    @Test
    public void isInInventoryTest() {

        List<Item> testinv = world.getPlayerInventory();

        assertTrue(world.isInInventory(world.getItem("trixie's hat")));

        assertFalse(world.isInInventory(world.getItem("a rock")));

    }



    // --------------- Setters & Getters ------------------------


    @Test
    public void setMainCharacterTest() {

        List<Creature> list = world.getCreatureList();


        boolean success = world.setMainCharacter(world.getCreature("Pinkie Pie"));

        assertEquals("Pinkie", world.getPlayer().getShortName());

    }

    @Test
    public void getPlayerTest() {

        Creature main = world.getPlayer();

        assertEquals("Trixie", main.getName());

    }

    @Test
    public void getPlayerLocationTest() {

        Location loc = world.getPlayerLocation();

        assertEquals("Sweet Apple Acres", loc.getName());

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
    }

    @Test
    public void getCreatureListTest() {

        assertNotNull(world.getCreatureList());
    }

    @Test
    public void getStationaryObjectListTest() {

        assertNotNull(world.getStationaryObjectList());
    }

    @Test
    public void getItemListTest() {

        assertNotNull(world.getItemList());
    }

    @Test
    public void getGenericListTest() {

        assertNotNull(world.getGenericList());
    }

    @Test
    public void getParserTest() {

        assertNotNull(world.getParser());
    }

    // ------------- Methods that returns objects from object lists ------------------

    @Test
    public void getLocationTest() {

        Location castle = world.getLocation("Castle of Friendship");

        Location fail = world.getLocation("Nonexistent place");

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
        assertEquals("crate", box.getShortName());

    }

    @Test
    public void getStationaryObjectTest() {

        StationaryObject oven = world.getStationaryObject("baking oven");
        StationaryObject fail = world.getStationaryObject("genetics lab");

        assertNotNull(oven);
        assertNull(fail);
        assertEquals("oven", oven.getShortName());
    }


    @Test
    public void getGenericObjectTest() {


        GenericObject gen = world.getGenericObject("an apple tree");
        GenericObject fail = world.getGenericObject("something that doesn't exist");

        assertNotNull(gen);
        assertNull(fail);
        assertEquals("tree", gen.getShortName());
    }


    @Test
    public void returnFullNameTest() {

        String fullName1 = world.returnFullName("tree");
        String fullName2 = world.returnFullName("TwilighT");
        String fullName3 = world.returnFullName("Ember");       //Full name

        assertEquals("an apple tree", fullName1);
        assertEquals("Twilight Sparkle", fullName2);
        assertEquals("Ember", fullName3);

    }

    // ------------- Boolean checks ---------------------


    @Test
    public void isObjectPresentTest() {


        assertTrue(world.isObjectPresent("Applejack"));
        assertFalse(world.isObjectPresent("Fluttershy"));

    }


    @Test
    public void doesObjectExistTest() {

        assertTrue(world.doesObjectExist("Applejack"));
        assertFalse(world.doesObjectExist("Donut Steel"));

    }

    // ------------------ The load function! --------------------------


    @Test
    public void saveToFileTest() {

        boolean success = world.saveToFile(SystemData.getTestpath());

        assertTrue(success);

    }


}
