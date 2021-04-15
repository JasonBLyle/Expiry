package com.adeemm.expiry.Models;

public class ListItem {

    private String name;
    private boolean isSection;
    private Food food;

    public ListItem(String name, boolean isSection) {
        this.name = name;
        this.isSection = isSection;
    }

    public ListItem(Food food) {
        this.name = food.getName();
        this.isSection = false;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public boolean isSection() {
        return isSection;
    }

    public Food getFood() {
        return food;
    }
}
