package jonst.Models;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {



    @Test
    public void ConstructorsTest() {

        Location location = new Location("Sugarcube Corner", "SGC", "A place.", new ArrayList<>());
        Creature creature = new Creature("Spike", "Spikey", "A dragon.", "dragon");
        Item item = new Item("Juicy red apple", "apple", "a fruit" );
        StationaryObject obj = new StationaryObject("Tree", "tree", "tree");

        assertNotNull(location);
        assertNotNull(creature);
        assertNotNull(item);
        assertNotNull(obj);

    }
}
