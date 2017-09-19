package app.dft.appbanhang.navmenu;

/**
 * Created by ductv on 6/24/2016.
 */
public class DrawerItem {
    String ItemName;
    Boolean active = false;
    Boolean hasSub = false;

    public DrawerItem(String itemName) {
        super();
        ItemName = itemName;
    }

    public DrawerItem(String itemName, Boolean hasSub) {
        super();
        ItemName = itemName;
        this.hasSub = hasSub;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHasSub() {
        return hasSub;
    }

    public void setHasSub(Boolean hasSub) {
        this.hasSub = hasSub;
    }
}
