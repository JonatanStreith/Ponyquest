package jonst.Models.Objects;

import java.util.ArrayList;
import java.util.Map;

public class Dialog {

    private String key;                 //A key representing this dialog entry

    private String text;                //The actual text displayed

    private ArrayList<String> scripts;           //Scripts that will run when this dialog plays

    private String[][] responses;       //Player's choice of responses



    public Dialog(final String key, final String text, final String[][] responses) {
        this.key = key;
        this.text = text;
        this.responses = responses;
    }

    public ArrayList<String> getScripts() {
        return this.scripts;
    }

    public void setScripts(final ArrayList<String> scripts) {
        this.scripts = scripts;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String[][] getResponses() {
        return this.responses;
    }

    public void setResponses(final String[][] responses) {
        this.responses = responses;
    }
}
