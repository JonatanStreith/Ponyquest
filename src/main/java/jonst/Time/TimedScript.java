package jonst.Time;


import jonst.App;
import jonst.Models.Objects.GenericObject;
import jonst.World;

public class TimedScript {

    String script;

    int initialTime;
    int remainingTime;
    boolean recurring;
    GenericObject subject;
    String message;

    boolean active;

    TimeKeeper timeKeeper;

    public TimedScript(final String script, final GenericObject subject, final int initialTime, final boolean recurring, final String message, final TimeKeeper timeKeeper) {
        this.subject = subject;
        this.script = script;
        this.recurring = recurring;
        this.initialTime = initialTime;
        remainingTime = initialTime;
        this.message = message;
        this.timeKeeper = timeKeeper;

        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate(){
        active = false;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(final String script) {
        this.script = script;
    }



    public void tick(){
        remainingTime--;
    }

    public void decreaseTime(int decrease){
        remainingTime-= decrease;
    }

    public void activate(){

        World world = App.getWorld();
        world.getParser().runScriptCommand(subject, script, world);
        System.out.println(message);

        if(recurring){
            remainingTime = initialTime;
        } else {
            deactivate();
        }

    }

    public void activateIfComplete(){
        if(remainingTime<1)
            activate();
    }


}
