package jonst.Models.Cores;

import java.util.List;

public class IdentityCore {
    private String name;
    private String shortName;
    private String type;
    private String id;
    private List<String> alias;

    public IdentityCore(String name, String shortName, String type, String id, List<String> alias) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        this.id = id;
        this.alias = alias;
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
}
