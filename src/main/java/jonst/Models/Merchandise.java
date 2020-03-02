package jonst.Models;

import java.util.List;

public class Merchandise {
    private List<String> names;
    private String Id;
    private String price;



    public Merchandise(final String id, final String price) {

        setId(id);
        setPrice(price);
    }


    public List<String> getNames() {
        return this.names;
    }

    public void setNames(final List<String> names) {
        this.names = names;
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


