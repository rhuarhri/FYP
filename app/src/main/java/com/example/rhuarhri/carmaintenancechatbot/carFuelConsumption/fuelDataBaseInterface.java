package com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface fuelDataBaseInterface {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFuelAmount(fuelConsumptionTable newFuelAmount);


    @Query("SELECT * FROM fuelConsumptionTable WHERE date BETWEEN :StartDate AND :EndDate")
    List<fuelConsumptionTable> getHistoryInTimePeriod(long StartDate, long EndDate);



}
