package jonst.CommandSheets;

import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Item;
import jonst.Models.Objects.Location;
import jonst.Models.Objects.StationaryObject;
import jonst.Models.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ScriptsTest {

    private World world;
    private Creature Applejack;
    private Item plow;
    private StationaryObject tree;

    @BeforeEach
    void initialize() {
        world = new World(SystemData.getDefaultWorld());
        Applejack = world.getCreature("Applejack");
        plow = world.getItem("plow");
        tree = world.getStationaryObject("tree");
    }

    @Test
    void setMoodTest() {
        // When
        Scripts.setMood(Applejack, new String[]{"setMood", "confused"}, world);

        // Then
        assertThat(Applejack.getMood(), is("confused"));
    }

    @Test
    void fleeRandomTest() {
        // Given
        Location start = Applejack.getLocation();

        // When
        Scripts.fleeToRandomLocation(Applejack, world);
        Location current = Applejack.getLocation();

        // Then
        assertFalse(start == current);
    }

    @Test
    void fleeTest() {
        // Given
        Location start = Applejack.getLocation();

        // When
        Scripts.fleeTo(Applejack, "Location_Sugarcube_Interior", world);
        Location current = Applejack.getLocation();

        // Then
        assertFalse(start == current);
    }

    @Test
    void cantFleeToIllegalLocation() {
        // Given
        Location start = Applejack.getLocation();

        // When
        Scripts.fleeTo(Applejack, "illegal", world);
        Location current = Applejack.getLocation();

        // Then
        assertTrue(start == current);
    }

    @Test
    void deleteThisItemTest() {
        // When
        boolean result = Scripts.deleteThisItem(plow, world);

        // Then
        assertTrue(result);
        assertNull(world.getItem("plow"));
    }

    @Test
    void cantDeleteNonItem() {
        // When
        boolean result = Scripts.deleteThisItem(Applejack, world);

        // Then
        assertFalse(result);
        assertNotNull(world.getCreature("Applejack"));
    }

    @Test
    void destroyItemTest() {
        // When
        boolean result = Scripts.destroyItem(plow, world);

        // Then
        assertTrue(result);
        assertNull(world.getItem("plow"));
    }

    @Test
    void cantDestroyIndestructible() {
        // Given
        plow.addAttribute("indestructible");

        // When
        boolean result = Scripts.destroyItem(plow, world);

        // Then
        assertFalse(result);
        assertNotNull(world.getItem("plow"));
    }

    @Test
    void cantDestroyInvalidClass() {
        // When
        boolean result = Scripts.destroyItem(Applejack, world);

        // Then
        assertFalse(result);
        assertNotNull(world.getCreature("Applejack"));
    }

    @Test
    void dropContents() {
        Item crate = world.getItem("crate");

        crate.dropContents();

        assertEquals(crate.getLocation(), world.getItem("turnips").getHolder());
    }

    //    @Test
//    void addAttributeTest() {
//        // When
//        Scripts.addAttribute(Applejack, "sleepy");
//    }
}