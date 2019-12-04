package jonst;

import jonst.Data.SystemData;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {


    @Test
    public void ConstructorTest() {

        World earth = new World(SystemData.defaultWorld);
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
}
