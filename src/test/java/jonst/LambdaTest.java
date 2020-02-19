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
    public void listCheckTest() {

        ArrayList<Item> itemList = (ArrayList<Item>) JsonBuilder.loadItemList(SystemData.getDefaultWorld());

        List<Item> selectedList = Lambda.subList(itemList, (Item a) -> a.getName().contains("turnip"));

        System.out.println(selectedList);
    }

    @Test
    public void basicTest() {

        World world = new World(SystemData.getDefaultWorld());

        List<Item> itemList = world.getItemList();
        List<Creature> creatureList = world.getCreatureList();


        List<Item> returnList = new ArrayList<>();

        for (Item i : itemList) {
            for (Creature c : creatureList) {



                if (((c.getName().equals("Trixie")) && (i.getOwner()==c))) {
                    returnList.add(i);
                }
            }
        }
        System.out.println(returnList);

    }

    @Test
    public void doubleTest() {

        World world = new World(SystemData.getDefaultWorld());

        List<Item> itemList = world.getItemList();
        List<Creature> creatureList = world.getCreatureList();

        List<Item> ownedItems = Lambda.subList(itemList, creatureList, (Item i, Creature c) ->  ((c.getName().equalsIgnoreCase("Trixie")) && (i.getOwner()==c))   );

        System.out.println(ownedItems);
    }
}
