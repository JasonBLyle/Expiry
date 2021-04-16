package com.adeemm.expiry.Models;

import java.util.Calendar;
import java.util.Date;

public class Food {

    private String name;
    private String category;
    private Date expiration;
    private int pictureID;
    private Calendar calendar;

    public Food(String name, Date expiration) {
        this.name = name;
        this.category = category;
        this.expiration = expiration;
        this.pictureID = 0;

        this.calendar = Calendar.getInstance();
        this.calendar.setTime(expiration);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Date getExpiration() {
        return expiration;
    }

    public int getYear(){ return this.calendar.get(Calendar.YEAR); }

    public int getPictureID() { return pictureID; }

    public void setPictureID(int pictureID) { this.pictureID = pictureID; }

    public int getMonth(){ return this.calendar.get(Calendar.MONTH); }

    public int getDay(){ return this.calendar.get(Calendar.DAY_OF_MONTH); }

    public void setName(String name) { this.name = name; }

    public void setCategory(String category) { this.category = category; }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
        this.calendar.setTime(expiration);
    }
}
