package jonst;

import jonst.Data.Lambda;
import jonst.Data.SystemData;
import jonst.Models.Objects.*;
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


    @Test
    public void dualProcessTest() {

        World world = new World(SystemData.getDefaultWorld());

        List<Location> locationList = JsonBuilder.loadLocationList(SystemData.getDefaultWorld());
        List<Creature> creatureList = JsonBuilder.loadCreatureList(SystemData.getDefaultWorld());

        System.out.println("Pretest");

        Lambda.processLists(locationList, creatureList, (l, c) -> c.getLocationId().equalsIgnoreCase(l.getId()), (l, c) -> {l.add(c); c.setLocation(l);});

        System.out.println("Test");

//        for (Location location : locationList) {
//
//            for (Creature creature : creatureList) {
//                if (creature.getLocationId().equalsIgnoreCase(location.getId())) {
//                    location.add(creature); //Adds creature to the location's list of creatures
//                    creature.setLocation(location); //Sets creature's location reference
//                }
//            }
//
//            for (StationaryObject object : stationaryObjectList) {
//                if (object.getLocationId().equalsIgnoreCase(location.getId())) {
//                    location.add(object);     //Adds object to the location's list of objects
//                    object.setLocation(location);   //Sets object's location reference
//                }
//            }
//
//            for (Location loc : locationList) {
//                if (location.getDefaultEnterId() != null && location.getDefaultEnterId().equals(loc.getId())) {
//                    location.setDefaultEnter(loc);
//                } else if (location.getDefaultExitId() != null && location.getDefaultExitId().equals(loc.getId())) {
//                    location.setDefaultExit(loc);
//                }
//                if (location.getDefaultEnter() != null && location.getDefaultExit() != null) {
//                    break;
//                }
//            }
//        }
//
//        for (Item item : itemList) {
//            for (GenericObject gen : genericList) {
//
//                if (item.getLocationId().equalsIgnoreCase(gen.getId())) {
//
//                    gen.addItem(item);
//                    item.setHolder(gen);
//
//                    if (gen instanceof Location) {
//                        item.setLocation((Location) gen);
//                    } else {
//                        item.setLocation(null);
//                    }
//                }
//            }
//        }
//
//        for (
//                Item item : itemList) {
//            if (item.getOwnerName() != null) {
//                for (Creature creature : creatureList) {
//                    if (item.getOwnerName().equalsIgnoreCase(creature.getName())) {
//                        item.setOwner(creature);
//                    }
//                }
//            }
//        }
//
//        for (
//                StationaryObject obj : stationaryObjectList) {
//            if (obj.getOwnerName() != null) {
//                for (Creature creature : creatureList) {
//                    if (obj.getOwnerName().equalsIgnoreCase(creature.getName())) {
//                        obj.setOwner(creature);
//                    }
//                }
//            }
//        }
//    }


}
}

