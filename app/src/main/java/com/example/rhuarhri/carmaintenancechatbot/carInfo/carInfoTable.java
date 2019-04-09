package com.example.rhuarhri.carmaintenancechatbot.carInfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class carInfoTable {

    @PrimaryKey(autoGenerate = true)
    int infoID;

    @ColumnInfo(name = "tank")
    int fueltankSize;

    @ColumnInfo(name = "inMiles")
    boolean inMiles;

    public int getInfoID() {
        return infoID;
    }

    public int getFueltankSize() {
        return fueltankSize;
    }

    public boolean isInMiles() {
        return inMiles;
    }



    public void setInfoID(int infoID) {
        this.infoID = infoID;
    }

    public void setFueltankSize(int fueltankSize) {
        this.fueltankSize = fueltankSize;
    }

    public void setInMiles(boolean inMiles) {
        this.inMiles = inMiles;
    }


}
