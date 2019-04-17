package com.example.rhuarhri.carmaintenancechatbot.carmileage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

//import com.example.rhuarhri.carmaintenancechatbot.carmileage.carMileageTable;
//import com.example.rhuarhri.carmaintenancechatbot.carmileage.databaseController;

import com.example.rhuarhri.carmaintenancechatbot.dataConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class carServicing {

    private int Mileage = 0;

    private long lastService;
    private int mileageSinceLastService;

    private carMileageTable addServiceHistory;
    private databaseController addMileage;

    private dataConverter converter = new dataConverter();

    public carServicing(Context context)
    {
        addMileage = Room.databaseBuilder(context, databaseController.class, "carMileageHistory")
                .allowMainThreadQueries().build();


        Mileage = addMileage.dbInterface().getLatestMileage();
        lastService = addMileage.dbInterface().getServiceHistoryDate();
        mileageSinceLastService = addMileage.dbInterface().getServiceHistoryMileage();

    }

    public int getCurrentMileage()
    {
        return Mileage;
    }

    public boolean setUpRequired()
    {
        if(addMileage.dbInterface().rowsInDataBase() <= 0)
        {
            //no data in data base
            return true;
        }
        else
        {
            return false;
        }
    }

    public void runSetUp(int mileage)
    {
        carNotServiced(mileage);
        Mileage = addMileage.dbInterface().getLatestMileage();
        carServiced();
    }

    public void carNotServiced(int mileage)
    {
        addServiceHistory = new carMileageTable();

        addServiceHistory.setDateRecorded(Calendar.getInstance().getTime().getTime());
        addServiceHistory.setMileage(mileage);
        addServiceHistory.setWasServiced(false);

        addMileage.dbInterface().addMileage(addServiceHistory);

    }

    public void carServiced()
    {

        addServiceHistory = new carMileageTable();

        addServiceHistory.setDateRecorded(Calendar.getInstance().getTime().getTime());
        addServiceHistory.setMileage(Mileage);
        addServiceHistory.setWasServiced(true);

        addMileage.dbInterface().addMileage(addServiceHistory);

        Log.e("MESSAGE", "car serviced");

    }

    public boolean carNeedsServicing()
    {

        boolean needsServicing = false;

        long currentDate = Calendar.getInstance().getTime().getTime();

        long daysDifference = converter.convertToDays((currentDate - lastService));

        //Log.d("DATE", "current Date is " + currentDate);
        //Log.d("MESSAGE", "mileage since last service is " + mileageSinceLastService);
        Log.d("DATE", "Date difference is "+ daysDifference);

        int mileageDifference = Mileage - mileageSinceLastService;
        Log.d("MILEAGE", "current mileage is " + Mileage);
        Log.d("MILEAGE", "mileage since last service is " + mileageSinceLastService);
        Log.d("MILEAGE", "mileage difference is "+ mileageDifference);

        if (addMileage.dbInterface().rowsInDataBase() > 0) {
            if (mileageDifference > 97000) { //which is about 6000 miles
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
