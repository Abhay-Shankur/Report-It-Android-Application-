package com.application.crimeapplication.model;

public class MissingPerson {

    String Image;
    String Name;
    String Contact;
    String City;
    String BirthSpot;

    public MissingPerson() {
    }

    public MissingPerson(String image, String name, String contact, String city, String birthSpot) {
        Image = image;
        Name = name;
        Contact = contact;
        City = city;
        BirthSpot = birthSpot;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getBirthSpot() {
        return BirthSpot;
    }

    public void setBirthSpot(String birthSpot) {
        BirthSpot = birthSpot;
    }
}
