package com.example.rhuarhri.carmaintenancechatbot.carInfo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface carInfoDBInterface {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addInformation(carInfoTable carInfo);

    @Query("SELECT COUNT (*) FROM carInfoTable")
    int rowsInDataBase();

    @Query("SELECT * FROM carInfoTable")
    List<carInfoTable> getAll();
}
