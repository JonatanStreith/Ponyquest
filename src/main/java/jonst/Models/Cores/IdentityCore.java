package jonst.Models.Cores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityCore {
    private String name;
    private String shortName;
    private String type;
    private String id;
    private List<String> alias;
    private Map<String, String> descriptions;

    public IdentityCore(String name, String shortName, String type, String id, List<String> alias, Map<String, String> descriptions) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        this.id = id;
        this.alias = alias;
        this.descriptions = descriptions;
    }

    public IdentityCore() {
        this.name = "Blank";
        this.shortName = "Blank";
        this.type = "Blank";
        this.id = "Blank";
        this.alias = new ArrayList<>();
        this.descriptions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getSpecificDescription(String key){
        if(descriptions.containsKey(key))
        return descriptions.get(key);
        else
            return "[Description missing: " + key + "]";
    }

    public boolean hasSpecificDescription(String key){
        return descriptions.containsKey(key);
    }
}
