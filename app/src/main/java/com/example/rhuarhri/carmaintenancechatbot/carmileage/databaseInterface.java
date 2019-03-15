
package com.example.rhuarhri.carmaintenancechatbot.carmileage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;


@Dao
public interface databaseInterface {

    /*
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMileage(carMileageTable newCarMileage);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMileage(carMileageTable newCarMileage);

    @Query("SELECT COUNT (*) FROM carMileageTable")
    int rowsInDataBase();

    @Query("SELECT MAX(recordedOn) FROM carMileageTable WHERE serviced = 1")
    long getServiceHistoryDate();

    @Query("SELECT MAX(mileage) FROM carMileageTable WHERE serviced = 1")
    int getServiceHistoryMileage();

    @Query("SELECT MAX(mileage) FROM carMileageTable WHERE serviced = 0")
    int getLatestMileage();

}
