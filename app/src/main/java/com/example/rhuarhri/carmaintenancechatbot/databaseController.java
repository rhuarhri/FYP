package com.example.rhuarhri.carmaintenancechatbot;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {carMileageTable.class}, version = 1)
public abstract class databaseController extends RoomDatabase {
    public abstract databaseInterface dbInterface();
}
