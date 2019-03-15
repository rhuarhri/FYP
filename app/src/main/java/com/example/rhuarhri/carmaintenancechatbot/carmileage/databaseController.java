
package com.example.rhuarhri.carmaintenancechatbot.carmileage;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {carMileageTable.class}, version = 1, exportSchema = false)
public abstract class databaseController extends RoomDatabase {
    public abstract databaseInterface dbInterface();
}
