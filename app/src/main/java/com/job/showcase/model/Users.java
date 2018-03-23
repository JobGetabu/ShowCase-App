package com.job.showcase.model;

/**
 * Created by Job on Friday : 3/23/2018.
 */

public class Users {
    private String name;
    private String imageurl;

    public Users() {
    }

    public Users(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
