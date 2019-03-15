package com.example.rhuarhri.carmaintenancechatbot.carmileage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

//import com.example.rhuarhri.carmaintenancechatbot.carmileage.carMileageTable;
//import com.example.rhuarhri.carmaintenancechatbot.carmileage.databaseController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class carServicing {

    private int Mileage = 0;

    private long lastService;
    private int mileageSinceLastService;

    private carMileageTable addServiceHistory;
    private databaseController addMileage;

    public carServicing(Context context)
    {
        addMileage = Room.databaseBuilder(context, databaseController.class, "carMileageHistory")
                .allowMainThreadQueries().build();


        Mileage = addMileage.dbInterface().getLatestMileage();
        lastService = addMileage.dbInterface().getServiceHistoryDate();
        mileageSinceLastService = addMileage.dbInterface().getServiceHistoryMileage();

    }

    public void carServiced()
    {

        addServiceHistory = new carMileageTable();

        addServiceHistory.setDateRecorded(Calendar.getInstance().getTime().getTime());
        addServiceHistory.setMileage(Mileage);
        addServiceHistory.setWasServiced(true);

        addMileage.dbInterface().addMileage(addServiceHistory);

    }

    public boolean carNeedsServicing()
    {

        boolean needsServicing = false;

        Date currentDate = Calendar.getInstance().getTime();

        long difference = currentDate.getTime() - lastService;
        double daysDifference = (difference / (1000*60*60*24));


        int mileageDifference = Mileage - mileageSinceLastService;

        if (addMileage.dbInterface().rowsInDataBase() <= 0) {
            if (mileageDifference > 6000) {
                needsServicing = true;
                Log.e("MESSAGE", "mileage difference greater than 6000");

            } else if (daysDifference > 180) {
                needsServicing = true;
                Log.e("MESSAGE", "Time difference greater than 180 days");
            } else {
                needsServicing = false;
            }
        }
        else{
            needsServicing = false;
            carServiced();
            Log.e("MESSAGE", "Data in data base");
        }


        return needsServicing;
    }
}
