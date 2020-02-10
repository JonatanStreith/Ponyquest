package jonst.Models.Objects;

import java.util.Map;

public class Dialog {

    private String key;

    private String text;

    private Map<String, String> responses;

    public Dialog(final String key, final String text, final Map<String, String> responses) {
        this.key = key;
        this.text = text;
        this.responses = responses;
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

    public Map<String, String> getResponses() {
        return this.responses;
    }

    public void setResponses(final Map<String, String> responses) {
        this.responses = responses;
    }
}
