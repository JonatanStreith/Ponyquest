package jonst;

import jonst.Models.Cores.*;
import jonst.Models.Objects.Creature;
import jonst.Models.Objects.Location;
import jonst.Models.Roles.GenericRole;
import jonst.Models.Roles.VehicleRole;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RolesTest {

    Creature testCreature;
    Location testLocation;
    @Before
    public void setUp(){
        testCreature = new Creature(new IdentityCore(), new RelationCore(), new ActionCore(),
                new HashMap<>(), new CreatureCore(), new SpeechCore(), new BehaviorCore());
        testLocation = new Location(new IdentityCore("Home", "Home.short", "place", "001", new ArrayList<>(), new HashMap<>()),
                new RelationCore(), new ActionCore(), new HashMap<>(), null, null);
    }

    //Creature(identityCore, relationCore, actionCore, roleMods,
    //                    creatureCore, speechCore, behaviorCore)

    @Test
    public void roleIsLegit(){
        VehicleRole vr = new VehicleRole(Arrays.asList("testLocation"));

        testCreature.assignRole(vr);

        Map roles = testCreature.getRoles();

        for (Object key: roles.keySet()             ) {
            System.out.println(key.toString());
        }

        GenericRole gen = testCreature.getRoleByKey("VehicleRole");
        assertNotNull(gen);

        System.out.println("...");
    }

}
