package com.adeemm.expiry.Models;

import java.util.Date;

public class Food {

    private String name;
    private String category;
    private Date expiration;
    private int pictureID;
    public Food(String name, String category, Date expiration) {
        this.name = name;
        this.category = category;
        this.expiration = expiration;
        this.pictureID = 0;
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

    public int getYear(){return expiration.getYear();}

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public int getMonth(){return expiration.getMonth();}

    public int getDay(){return expiration.getDay();}

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
