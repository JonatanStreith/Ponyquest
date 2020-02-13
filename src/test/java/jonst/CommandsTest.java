package jonst;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandsTest {


    World world;

    @Before
    public void setUp() throws Exception {
        world = new World(SystemData.getDefaultWorld());
    }

    @Test
    public void saveGameTest() {

    }

    @Test
    public void loadGameTest() {
    }


    @Test
    public void quitTest() {
    }


    @Test
    public void helpTest() {
    }


    @Test
    public void ListCommandsTest() {
    }


    @Test
    public void listNounsTest() {
    }

//    @Test
//    public void pickUpTest() {
//
//        Item plow = world.getItem("a heavy plow");
//
//        assertTrue(world.getPlayerLocation().itemIsAtLocation(plow));
//        assertFalse(world.isInInventory(plow));
//
//
//        Commands.pickUp("a heavy plow", world);
//
//        assertFalse(world.getPlayerLocation().itemIsAtLocation(plow));
//        assertTrue(world.isInInventory(plow));
//
//
//    }


//    @Test
//    public void dropTest() {
//
//        Item hat = world.getItem("Trixie's hat");
//
//        assertFalse(world.getPlayerLocation().itemIsAtLocation(hat));
//        assertTrue(world.isInInventory(hat));
//
//        Commands.drop("hat", world);
//
//        assertTrue(world.getPlayerLocation().itemIsAtLocation(hat));
//        assertFalse(world.isInInventory(hat));
//
//
//    }


    @Test
    public void showInventoryTest() {

        Commands.showInventory(world);
    }


    @Test
    public void lookAtTest() {

        Commands.lookAt("Applejack", world);
        Commands.lookAt("maud", world);
        Commands.lookAt("acres", world);
        Commands.lookAt("twilight", world);

    }


    @Test
    public void goToTest() {

        Commands.goTo("castle", world);

        assertEquals(world.getLocation("Castle of Friendship"), world.getPlayerLocation());

    }


    @Test
    public void talkToTest() {

        Commands.talkTo("tree", world);
        Commands.talkTo("Twilight", world);
        Commands.talkTo("Applejack", world);
        Commands.talkTo("maud", world);
        Commands.talkTo("Ember", world);

Commands.talkTo("earth pony", world);


    }


    @Test
    public void getExitsTest() {


        Commands.getExits(world);
    }


    @Test
    public void teleportOtherTest() {
    }

    @Test
    public void teleportSelfTest() {
    }


    @Test
    public void askTest() {

        Creature maud = world.getCreature("Maud Pie");

        String[] commands = new String[] {"ask", "maud", "about", "key"};

        Commands.ask(commands, world);

    }




    @Test
    public void listStationaryObjectsTest() {

        Commands.listStationaryObjects(world);
    }


    @Test
    public void listCreaturesTest() {

        Commands.listCreatures(world);
    }
}


