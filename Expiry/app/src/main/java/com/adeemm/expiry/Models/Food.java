package com.adeemm.expiry.Models;

import java.util.Calendar;
import java.util.Date;

/**
 * This is the model for the food in the activity
 */
public class Food {

    private String name;
    private String category;
    private Date expiration;
    private int pictureID;
    private Calendar calendar;
    private boolean frozen;
    private int rDays;

    /**
     * Pre: name is a string
     * expirtation is a Date
     * @param name is the name of the food
     * @param expiration is the date the being added to the food item
     */
    public Food(String name, Date expiration) {
        this.name = name;
        this.category = "";
        this.expiration = expiration;
        this.pictureID = 0;
        this.frozen =false;
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(expiration);
        this.rDays = 0;
    }

    /**
     * Pre: rdays is an int
     * Post: this.rDays = rDays;
     */
    public void setrDays(int rDays) { this.rDays = rDays; }

    /**
     * Pre:none
     * post: return = rDays
     */
    public int getrDays() { return rDays; }

    /**
     * Pre:none
     * post: return = frozen
     */
    public boolean isFrozen() { return frozen; }

    /**
     * Pre: frozen is a boolean
     * Post:this.frozen = frozen;
     */
    public void setFrozen(boolean frozen) { this.frozen = frozen; }

    /**
     * Pre:none
     * post: return = name
     */
    public String getName() { return name; }

    /**
     * Pre:none
     * post: return = category
     */
    public String getCategory() { return category; }

    /**
     * Pre:none
     * post: return = expiration
     */
    public Date getExpiration() { return expiration; }

    /**
     * Pre:none
     * post: return = this.calendar.get(Calendar.YEAR)
     */
    public int getYear(){ return this.calendar.get(Calendar.YEAR); }

    /**
     * Pre:none
     * post: return = rDays
     */
    public int getPictureID() { return pictureID; }

    /**
     * Pre:pictureID is an int
     * Post:this.pictureID = pictureID
     */
    public void setPictureID(int pictureID) { this.pictureID = pictureID; }

    /**
     * Pre:none
     * post: return = this.calendar.get(Calendar.MONTH)
     */
    public int getMonth(){ return this.calendar.get(Calendar.MONTH); }

    /**
     * Pre:none
     * post: return = this.calendar.get(Calendar.DAY_OF_MONTH)
     */
    public int getDay(){ return this.calendar.get(Calendar.DAY_OF_MONTH); }

    /**
     * Pre:name is a String
     * Post:this.name = name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Pre:category is a String
     * Post: this.category = category
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * Pre:none
     * post: return = expiration
     */
    public Date getDate(){return this.expiration;}

    /**
     * Pre:expiration is a Date
     * Post:this.expiration = expiration;
     *   this.calendar.setTime(expiration);
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
        this.calendar.setTime(expiration);
    }
}
