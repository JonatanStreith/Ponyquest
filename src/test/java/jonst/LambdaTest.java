package jonst;

import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Objects.*;
import jonst.Models.World;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class LambdaTest {

    World world = new World(SystemData.getDefaultWorld());

    @Test
    public void doubleTest() {
        //Test that we can extract a list of objects matching criteria involving two lists
        List<Item> itemList = world.getItemList();
        List<Creature> creatureList = world.getCreatureList();

        List<Item> ownedItems = Lambda.subList(itemList, creatureList, (i, c) -> ((c.getName().equalsIgnoreCase("Trixie")) && (i.getOwner() == c)));

        assertThat(ownedItems, hasItem(hasProperty("name", is("Trixie's hat"))));
    }


}

