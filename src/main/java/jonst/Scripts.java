package jonst;

import jonst.Models.Creature;

public class Scripts {

    public static void setMood(String[] script, World world){

        Creature subject = world.getCreature(script[1]);

        subject.setMood(script[2]);
    }

}
