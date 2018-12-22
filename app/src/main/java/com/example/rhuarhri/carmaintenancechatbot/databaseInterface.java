package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;


@Dao
public interface databaseInterface {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMileage(carMileageTable newCarMileage);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMileage(carMileageTable newCarMileage);

    @Query("SELECT COUNT (*) FROM carMileageTable")
    int rowsInDataBase();

    @Query("SELECT recordedOn FROM carMileageTable")
    Date mileageRecordedOn();

    @Query("SELECT mileage FROM carMileageTable")
    double currentMileage();



}
