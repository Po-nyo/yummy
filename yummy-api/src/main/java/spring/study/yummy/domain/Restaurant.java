package spring.study.yummy.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private long id;
    private String name;
    private String address;
    private List<MenuItem> menuItems;

    public Restaurant() {}

    public Restaurant(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menuItems = new ArrayList<>();
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems)
            addMenuItem(menuItem);
    }
}
