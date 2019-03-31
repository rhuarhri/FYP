package com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class fuelConsumptionTable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int fuelAmountID;

    @ColumnInfo(name = "amount")
    double fuelAmount;

    @ColumnInfo(name = "date")
    long fuelPurchasedOn;

    @ColumnInfo(name = "mileage")
    int mileageWhenFuelAdded;

    public int getFuelAmountID() {
        return fuelAmountID;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public long getFuelPurchasedOn() {
        return fuelPurchasedOn;
    }

    public int getMileageWhenFuelAdded() {
        return mileageWhenFuelAdded;
    }

    public void setFuelAmountID(int fuelAmountID) {
        this.fuelAmountID = fuelAmountID;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public void setFuelPurchasedOn(long fuelPurchasedOn) {
        this.fuelPurchasedOn = fuelPurchasedOn;
    }

    public void setMileageWhenFuelAdded(int mileageWhenFuelAdded) {
        this.mileageWhenFuelAdded = mileageWhenFuelAdded;
    }
}
