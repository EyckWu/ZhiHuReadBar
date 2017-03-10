package com.eyckwu.readbar.model.bean;

/**
 * Created by Eyck on 2017/2/26.
 */

public class MenuItem {
    private String name;
    private boolean isOrder;

    public MenuItem() {
    }

    public MenuItem(String name, boolean isOrder) {
        this.name = name;
        this.isOrder = isOrder;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", isOrder=" + isOrder +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }
}
