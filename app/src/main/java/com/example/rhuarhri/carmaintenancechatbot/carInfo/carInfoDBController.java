package com.example.rhuarhri.carmaintenancechatbot.carInfo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {carInfoTable.class}, version = 1, exportSchema = false)
public abstract class carInfoDBController extends RoomDatabase {
    public abstract carInfoDBInterface dbInterface();
}
