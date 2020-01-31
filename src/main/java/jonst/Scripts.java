package jonst;

import jonst.Models.Objects.Creature;
import jonst.Models.Objects.GenericObject;

public class Scripts {

    public static void setMood(GenericObject subject, String[] script, World world){

        if(subject instanceof Creature) {
            ((Creature) subject).setMood(script[1]);
        }
    }

}
