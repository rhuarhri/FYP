package com.example.rhuarhri.carmaintenancechatbot;

public class dataConverter {

    //used to convert one kind of measurement into another

    public int convertToKilometers(int distance)
    {
        int inKilometers = (int) Math.round(distance * 1.60934);

        return inKilometers;
    }

    public long convertToDays(long date)
    {
        long days = (long) Math.round((date / (1000*60*60*24)));

        return days;
    }

    public int convertToMiles(int distance)
    {
        int imMiles = (int) Math.round(distance *0.621371);
        return imMiles;
    }

}
