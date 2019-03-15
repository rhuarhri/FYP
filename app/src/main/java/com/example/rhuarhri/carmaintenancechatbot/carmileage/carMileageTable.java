
package com.example.rhuarhri.carmaintenancechatbot.carmileage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class carMileageTable {

    @PrimaryKey(autoGenerate = true)
    int mileageID;

    @ColumnInfo(name = "mileage")
    int mileage;

    @ColumnInfo(name = "serviced")
    boolean wasServiced;

    @ColumnInfo(name = "recordedOn")
    long dateRecorded;

    public int getMileageID() {
        return mileageID;
    }


    public int getMileage() {
        return mileage;
    }

    public boolean isWasServiced() {
        return wasServiced;
    }

    public long getDateRecorded() {
        return dateRecorded;
    }

    public void setMileageID(int mileageID) {
        this.mileageID = mileageID;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setWasServiced(boolean wasServiced) {
        this.wasServiced = wasServiced;
    }

    public void setDateRecorded(long dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
}
