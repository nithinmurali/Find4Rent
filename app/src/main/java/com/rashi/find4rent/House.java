package com.rashi.find4rent;

/**
 * Created by nithin on 11/4/17.
 */

public class House {
    String name;
    String place;
    Integer rent;
    Integer photoId;

    public House()
    {}

    public House(String name, String place, Integer rent, Integer photoId)
    {
        this.name = name;
        this.place = place;
        this.rent = rent;
        this.photoId = photoId;
    }
}
