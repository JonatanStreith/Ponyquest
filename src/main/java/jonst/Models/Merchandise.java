package jonst.Models;

public class Merchandise {
    private String name;
    private String Id;
    private String price;

    public Merchandise(final String name, final String id, final String price) {
        setName(name);
        setId(id);
        setPrice(price);
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(final String id) {
        this.Id = id;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }
}


