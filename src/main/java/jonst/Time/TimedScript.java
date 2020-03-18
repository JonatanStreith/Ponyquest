package jonst.Time;

import com.sun.xml.internal.bind.v2.TODO;

public class TimedScript {

    String script;

    int initialTime;
    int remainingTime;
    boolean recurring;

    public TimedScript(final String script, final int initialTime, final boolean recurring) {
        this.script = script;
        this.recurring = recurring;
        this.initialTime = initialTime;
        remainingTime = initialTime;

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
        //TODO
        System.out.println("Do something here");
    }

    public void activateIfComplete(){
        if(remainingTime<1)
            activate();
    }
}
