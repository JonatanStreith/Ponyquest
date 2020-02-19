package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.*;
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






    // --------------- Setters & Getters ------------------------


    @Test
    public void setMainCharacterTest() {

        List<Creature> list = world.getCreatureList();


        boolean success = world.setMainCharacter(world.getCreature("Pinkie Pie"));

        //assertEquals("Pinkie", world.getPlayer().getShortName());

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


//    @Test
//    public void returnFullNameTest() {
//
//        String fullName1 = world.returnFullName("tree");
//        String fullName2 = world.returnFullName("TwilighT");
//        String fullName3 = world.returnFullName("Ember");
//
//        assertEquals("an apple tree", fullName1);       //Gets the full name
//        assertEquals("Twilight Sparkle", fullName2);    //Gets the full name, even though it's not case-correct
//        assertEquals("Ember", fullName3);               //Just gets the same, as it doesn't exist
//
//    }
//
//    @Test
//    public void returnLocalFullNameTest() {
//
//        String fullName1 = world.returnLocalFullName("tree");
//        String fullName2 = world.returnLocalFullName("TwilighT");
//        String fullName3 = world.returnLocalFullName("Ember");
//        String fullName4 = world.returnLocalFullName("acres");
//
//        assertEquals("an apple tree", fullName1);           //Gets the full name
//        assertEquals("TwilighT", fullName2);                //Gets the same as the input, because Twilight isn't present
//        assertEquals("Ember", fullName3);                   //Gets the same, because Ember doesn't exist
//        assertEquals("Sweet Apple Acres", fullName4);
//    }


    // ------------- Boolean checks ---------------------


    @Test
    public void isObjectPresentTest() {


        //assertTrue(world.isObjectPresent("Applejack"));
        //assertFalse(world.isObjectPresent("Fluttershy"));

    }


    @Test
    public void doesObjectExistTest() {

        assertTrue(world.doesObjectExist("Applejack"));
        assertFalse(world.doesObjectExist("Donut Steel"));

    }

    // ------------------ The load function! --------------------------


    @Test
    public void Test() {

        String command = "go to castle";

        String[] parts = command.split(",");

        for (String str: parts
             ) {
            System.out.println(str);
        }


    }

    @Test
    public void getLocationByIdTest() {

        Location location = world.getLocationByID("Canterlot_High_Campus");

        System.out.println(location);

    }

    @Test
    public void matchIdTest() {

        String res = world.matchId("aj");

        System.out.println(res);

    }
}
