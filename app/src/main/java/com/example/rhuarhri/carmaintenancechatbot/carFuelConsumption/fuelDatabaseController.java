package com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {fuelConsumptionTable.class}, version = 1, exportSchema = false)
public abstract class fuelDatabaseController extends RoomDatabase {
    public abstract fuelDataBaseInterface dbInterface();
}