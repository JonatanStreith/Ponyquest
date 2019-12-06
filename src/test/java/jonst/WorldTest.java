package jonst;

import jonst.Data.SystemData;
import jonst.Models.Creature;
import jonst.Models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WorldTest {

    World earth;

    @Before
    public void setUp() throws Exception {
        earth = new World(SystemData.getDefaultWorld());
    }

    @Test
    public void ConstructorTest() {

        assertNotNull(earth);
        assertNotNull(earth.getStationaryObjectList());
        assertNotNull(earth.getLocationList());
        assertNotNull(earth.getItemList());
        assertNotNull(earth.getCreatureList());
        assertNotNull(earth.getGenericList());

        assertNotEquals(0, earth.getCreatureList().size());
        assertNotEquals(0, earth.getGenericList().size());
        assertNotEquals(0, earth.getItemList().size());
        assertNotEquals(0, earth.getLocationList().size());
        assertNotEquals(0, earth.getStationaryObjectList().size());

        assertNotNull(earth.getPlayer());
        assertEquals("Trixie", earth.getPlayer().getName());

        assertNotNull(earth.getParser());

    }


    @Test
    public void ObjectsTest() {

        List<Creature> creatureList = earth.getCreatureList();

//        for (Creature cre : creatureList) {
//            System.out.println(cre.getName());
//        }


        System.out.println(earth.getCreature("Applejack").isAtLocation(earth.getLocation("Sweet Apple Acres")));
        System.out.println(earth.getCreature("appleJack").isAtLocation(earth.getLocation("sweEt ApplE acRes")));
        System.out.println(earth.getCreature("Twilight Sparkle").isAtLocation(earth.getLocation("Sweet Apple Acres")));
        System.out.println(earth.getLocation("Sweet Apple Acres").creatureIsAtLocation(earth.getCreature("Applejack")));

    }


    @Test
    public void commandsTest() {

        Commands.listItems(earth);
        Commands.listCreatures(earth);




    }
}
