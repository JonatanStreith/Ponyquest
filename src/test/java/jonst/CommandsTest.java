package jonst;

import jonst.Data.SystemData;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void pickUpTest() {
    }


    @Test
    public void dropTest() {
    }


    @Test
    public void showInventoryTest() {
    }


    @Test
    public void lookAtTest() {
    }


    @Test
    public void goToTest() {
    }


    @Test
    public void talkToTest() {
    }


    @Test
    public void getExitsTest() {
    }


    @Test
    public void teleportOtherTest() {
    }

    @Test
    public void teleportSelfTest() {
    }


    @Test
    public void askTest() {
    }


    @Test
    public void listItemsTest() {

        Commands.listItems(world);
    }


    @Test
    public void listStationaryObjectsTest() {
        Commands.listStationaryObjects(world);
    }


    @Test
    public void listCreaturesTest() {

        Commands.listCreatures(world);
    }


    @Test
    public void Test() {
    }


}


