package com.adeemm.expiry.Models;

/**
 * This is the model for the items in the main activity recycler view
 */
public class ListItem {

    private String name;
    private boolean isSection;
    private Food food;

    /**
     *Pre: name is a string
     * inSection is a boolean
     * Post:this.name = name;
     *   this.isSection = isSection;
     * @param name is the name of the food
     * @param isSection is whether the item is a food or a section
     */
    public ListItem(String name, boolean isSection) {
        this.name = name;
        this.isSection = isSection;
    }

    /**
     *Pre: food is a food
     * Post:this.name = food.getName();
     *         this.isSection = false;
     *         this.food = food;
     * @param food is the name of the food being added to the list
     */
    public ListItem(Food food) {
        this.name = food.getName();
        this.isSection = false;
        this.food = food;
    }

    /**
     * Pre:none
     * Post: return name
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Pre:none
     * Post: return isSection
     * @return the type of item it is
     */
    public boolean isSection() {
        return isSection;
    }

    /**
     * Pre:none
     * Post:return food
     * @return the food object in the item
     */
    public Food getFood() {
        return food;
    }
}
