package com.adeemm.expiry.Models;

import java.util.Date;

public class Food {

    private String name;
    private String category;
    private Date expiration;

    public Food(String name, String category, Date expiration) {
        this.name = name;
        this.category = category;
        this.expiration = expiration;
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
}
