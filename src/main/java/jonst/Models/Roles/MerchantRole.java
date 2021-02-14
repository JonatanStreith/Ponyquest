package jonst.Models.Roles;

import jonst.Models.Objects.Item;

import java.util.ArrayList;
import java.util.List;

public class MerchantRole extends GenericRole {

    private List<String> merchandiseIds;

    private List<Item> merchandiseList;

    public MerchantRole(List<String> merchandiseIds) {
        super();
        this.merchandiseIds = merchandiseIds;

        merchandiseList = new ArrayList<>();
    }

    public List<Item> getMerchandiseList() {
        return this.merchandiseList;
    }

    public List<String> getMerchandiseIds() {
        return this.merchandiseIds;
    }

    public void setMerchandiseIds(final List<String> merchandiseIds) {
        this.merchandiseIds = merchandiseIds;
    }

    public void addMerchandise(Item merch){
        if(!merchandiseList.contains(merch)) {
            merchandiseList.add(merch);
        }
    }

    public void removeMerchandise(Item merch){
        if(merchandiseList.contains(merch)) {
            merchandiseList.remove(merch);
        }
    }

}
