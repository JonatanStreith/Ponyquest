package jonst.Models.Roles;

import jonst.Models.Objects.GenericObject;

public abstract class GenericRole {
    private String type;

    private GenericObject holder;

    public GenericRole() {
        setType(this.getClass().getSimpleName());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GenericObject getHolder() {
        return holder;
    }

    public void setHolder(GenericObject holder) {
        this.holder = holder;
    }
}
