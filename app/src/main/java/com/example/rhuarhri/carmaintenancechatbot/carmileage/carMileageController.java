
package com.example.rhuarhri.carmaintenancechatbot.carmileage;

import android.arch.persistence.room.Room;
import android.content.Context;


import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.round;

/*
public class carMileageController {

    databaseController dbController;

    public carMileageController(Context context)
    {
        dbController = Room.databaseBuilder(context,
                databaseController.class, "CarMileageDataBase").build();
    }

    public void addMileage(double newMileage)
    {

        Date currentDate = Calendar.getInstance().getTime();

        carMileageTable newData = new carMileageTable();
        newData.setMileage(newMileage);
        newData.setDateRecorded(currentDate);


        if(dbController.dbInterface().rowsInDataBase() > 0)
        {
            //update with new mileage
            dbController.dbInterface().updateMileage(newData);
        }
        else
        {
            //add to empty data base
            dbController.dbInterface().addMileage(newData);
        }
    }

    public double getMileage()
    {

        return dbController.dbInterface().currentMileage();


    }

    public boolean doesMileageNeedUpdating()
    {

        Date CurrentDate = Calendar.getInstance().getTime();

        Date recordedDate = dbController.dbInterface().mileageRecordedOn();

        /*
        Why this
        The data value in recorded in milliseconds, however only the amount of days
        that have passed needs to be measured.
        As a result the date values are reduced by dividing them by 10000 and round the
        result to the nearest hole number thus removing some unnecessary data.

        The value 25920 is about 30 days in milliseconds if 10000 was added to it.
         *

        double dateDifference = (CurrentDate.getTime() - recordedDate.getTime()) / 10000;
        if (round(dateDifference) > 25920)
        {
            return true;
        }
        else
        {
            return false;
        }


    }
}
*/