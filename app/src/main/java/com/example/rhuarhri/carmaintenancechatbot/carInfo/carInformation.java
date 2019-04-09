package com.example.rhuarhri.carmaintenancechatbot.carInfo;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class carInformation {

    private carInfoDBController addInfo;
    private int CarFuelTankSize;
    private boolean DistanceInMiles;

    public carInformation(Context context)
    {
        addInfo = Room.databaseBuilder(context, carInfoDBController.class, "recordedCarInformation")
                .allowMainThreadQueries().build();

    }

   public void addCarInfo(int carFuelTankSize, boolean distanceInMiles)
   {
       carInfoTable carInfo = new carInfoTable();
       carInfo.setFueltankSize(carFuelTankSize);
       carInfo.setInMiles(distanceInMiles);

       addInfo.dbInterface().addInformation(carInfo);
   }

   public void getCarInfo()
   {
       List<carInfoTable> inDatabase = addInfo.dbInterface().getAll();

       CarFuelTankSize = inDatabase.get(0).getFueltankSize();
       DistanceInMiles = inDatabase.get(0).isInMiles();
   }

   public int getCarFuelTankSize()
   {
       return CarFuelTankSize;
   }

   public boolean isInMiles()
   {
       return DistanceInMiles;
   }
}
