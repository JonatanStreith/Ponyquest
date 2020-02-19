package jonst;

import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {



    @Test
    public void getSubValuesTest() {
        World world = new World(SystemData.getDefaultWorld());

        List<Creature> creatureList = world.getCreatureList();

        List<String> results = Lambda.getSubvalues(creatureList, i -> i.getName()    );

        System.out.println(results);
    }

    @Test
    public void listCheckTest() {

        ArrayList<Item> itemList = (ArrayList<Item>) JsonBuilder.loadItemList(SystemData.getDefaultWorld());

        List<Item> selectedList = Lambda.subList(itemList, a -> a.getName().contains("turnip"));

        System.out.println(selectedList);
    }


    @Test
    public void doubleTest() {

        World world = new World(SystemData.getDefaultWorld());

        List<Item> itemList = world.getItemList();
        List<Creature> creatureList = world.getCreatureList();

        List<Item> ownedItems = Lambda.subList(itemList, creatureList, ( i,  c) ->  ((c.getName().equalsIgnoreCase("Trixie")) && (i.getOwner()==c))   );

        System.out.println(ownedItems);
    }
}
