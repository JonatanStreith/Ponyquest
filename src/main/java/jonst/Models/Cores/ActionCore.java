package jonst.Models.Cores;

import jonst.App;
import jonst.Models.Objects.GenericObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionCore {
    private List<String> attributes;        //Contains all attributes that can affect how interactions work!
    private String text;
    private String defaultUse;

    private Map<String, String> complexUse = new HashMap<>();
    private Map<String, ArrayList<String>> responseScripts = new HashMap<>();

    public ActionCore(List<String> attributes, String text, String defaultUse, Map<String, String> complexUse, Map<String, ArrayList<String>> responseScripts) {
        this.attributes = attributes;
        this.text = text;
        this.defaultUse = defaultUse;
        this.complexUse = complexUse;
        this.responseScripts = responseScripts;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDefaultUse() {
        return defaultUse;
    }

    public void setDefaultUse(String defaultUse) {
        this.defaultUse = defaultUse;
    }

    public Map<String, String> getComplexUse() {
        return complexUse;
    }

    public void setComplexUse(Map<String, String> complexUse) {
        this.complexUse = complexUse;
    }

    public Map<String, ArrayList<String>> getResponseScripts() {
        return responseScripts;
    }

    public void setResponseScripts(Map<String, ArrayList<String>> responseScripts) {
        this.responseScripts = responseScripts;
    }


    public boolean hasAttribute(String attr) {

        return attributes.contains(attr);
    }

    public boolean hasAnyAttributes(String[] attributeArray) {
        for (String attr : attributeArray) {
            if (attributes.contains(attr))
                return true;
        }
        return false;
    }

    public boolean addAttribute(String attr) {

        if (!attributes.contains(attr)) {
            attributes.add(attr);
            return true;
        }
        return false;
    }

    public boolean removeAttribute(String attr) {

        if (attributes.contains(attr)) {
            attributes.remove(attr);
            return true;
        }
        return false;
    }

    public String getComplexUseCommand(String key) {
        return complexUse.get(key.toLowerCase());
    }

    public boolean runResponseScript(String command, GenericObject target) {

        ArrayList<String> responseCommands = responseScripts.get(command);

        if (responseCommands != null) {
            for (String script : responseCommands) {
                App.getWorld().getParser().runScriptCommand(target, script, App.getWorld());
            }
            return true;
        }
        return false;
    }


}
