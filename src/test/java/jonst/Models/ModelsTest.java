package jonst.Models;

import jonst.Data.SystemData;
import jonst.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {


    World earth;

    @Before
    public void setUp() throws Exception {
        earth = new World(SystemData.getDefaultWorld());
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
}
