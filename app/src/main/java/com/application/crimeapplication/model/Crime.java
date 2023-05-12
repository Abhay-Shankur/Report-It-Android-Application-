package com.application.crimeapplication.model;

public class Crime {


    String location;
    String Description;
    String image;

    public Crime(String location, String description, String image) {
        this.location = location;
        Description = description;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
