package jonst.Models;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {


    World world;

    @Before
    public void setUp() throws Exception {
        world = new World(SystemData.getDefaultWorld());
    }


    @Test
    public void ObjectsTest() {

        List<Creature> creatureList = world.getCreatureList();

//        for (Creature cre : creatureList) {
//            System.out.println(cre.getName());
//        }


        System.out.println(world.getCreature("Applejack").isAtLocation(world.getLocationByName("Sweet Apple Acres")));
        System.out.println(world.getCreature("appleJack").isAtLocation(world.getLocationByName("sweEt ApplE acRes")));
        System.out.println(world.getCreature("Twilight Sparkle").isAtLocation(world.getLocationByName("Sweet Apple Acres")));
        System.out.println(world.getLocationByName("Sweet Apple Acres").isAtLocation(world.getCreature("Applejack")));

        List<String> aliases = world.getPlayer().getAlias();

        for (String ali: aliases
             ) {
            System.out.println(ali);
        }
    }


}
