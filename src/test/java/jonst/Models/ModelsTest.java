package jonst.Models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {



    @Test
    public void ConstructorsTest() {

        Location location = new Location("Sugarcube Corner");
        Creature creature = new Creature("Spike", "dragon");
        Item item = new Item("Apple");
        StationaryObject obj = new StationaryObject("Tree");

        assertNotNull(location);
        assertNotNull(creature);
        assertNotNull(item);
        assertNotNull(obj);

    }
}
