package jonst.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeKeeper {

    List<TimedScript> timedScripts;

    public TimeKeeper() {

        timedScripts = new ArrayList<>();
    }


    public void advance(){
        for (TimedScript scr: timedScripts) {
            scr.tick();
            scr.activateIfComplete();
        }
    }

    public void addScript(String script, int time, boolean recurring){
        timedScripts.add(new TimedScript(script, time, recurring));
    }


}
