package jonst.Time;

import jonst.Models.Objects.GenericObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeKeeper {

    List<TimedScript> timedScripts;

    public TimeKeeper() {

        timedScripts = new ArrayList<>();
    }


    public void advance() {
        for (TimedScript scr : timedScripts) {
            scr.tick();
            scr.activateIfComplete();
        }
        removeDeactivatedScripts();
    }

    public void addScript(String script, GenericObject subject, int time, boolean recurring, String message) {
        timedScripts.add(new TimedScript(script, subject, time, recurring, message, this));
    }

    public void removeScript(TimedScript script) {
        if (timedScripts.contains(script)) {
            timedScripts.remove(script);
        }
    }

    public void removeDeactivatedScripts(){
        timedScripts = timedScripts.stream()
                .filter(s -> s.isActive() == true)
                .collect(Collectors.toList());
    }


}
