
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
    double mileage;

    @ColumnInfo(name = "recordedOn")
    Date dateRecorded;

    public int getMileageID() {
        return mileageID;
    }

    public double getMileage() {
        return mileage;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setMileageID(int mileageID) {
        this.mileageID = mileageID;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
}
