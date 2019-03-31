package com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fuelConsumption {

    private fuelDatabaseController fuelHistory;

    public fuelConsumption(Context context)
    {

        fuelHistory = Room.databaseBuilder(context, fuelDatabaseController.class, "fuelConsumptionHistory")
                .allowMainThreadQueries().build();

    }

    public void resetFuelConsumption()
    {
        fuelHistory.dbInterface().reset();
    }

    public void fuelAdded(double amount, int distance)
    {
        fuelConsumptionTable newFuelAmount = new fuelConsumptionTable();

        newFuelAmount.setFuelAmount(amount);

        newFuelAmount.setMileageWhenFuelAdded(distance);

        newFuelAmount.setFuelPurchasedOn(Calendar.getInstance().getTime().getTime());

        fuelHistory.dbInterface().addFuelAmount(newFuelAmount);
    }

    public boolean isFuelConsumptionNormal()
    {
        if (fuelHistory.dbInterface().isDatabaseEmpty() < 2)
        {
            //not enough data to get an accurate result
            return true;
        }

        boolean fuelConsumptionOK = false;

        List<Double> totalFuelConsumption = calculateFuelConsumption();

        double addUpOfFuelConsumption = totalFuelConsumption.get(0);

        double averageFuelConsumption = addUpOfFuelConsumption;

        for (int i = 1; i < totalFuelConsumption.size(); i++)
        {
            double variation = averageFuelConsumption / 10;//10 % of the average fuel consumption

            if (totalFuelConsumption.get(i) > (averageFuelConsumption + variation))
            {
                //car using more fuel than normal could be a hidden problem
                fuelConsumptionOK = false;
                break;
            }
            else
            {
                fuelConsumptionOK = true;
                addUpOfFuelConsumption += addUpOfFuelConsumption + totalFuelConsumption.get(i);
                averageFuelConsumption = addUpOfFuelConsumption / i;
            }
        }

        return fuelConsumptionOK;
    }

    private List<Double> calculateFuelConsumption()
    {

        List<Double> fuelConsumptionOverTime = new ArrayList<Double>();


        long firstDate = fuelHistory.dbInterface().getFirstDate();
        long plus30Days = (1000*60*60*24) * 30;

        long nextDate = firstDate;
        while (firstDate < Calendar.getInstance().getTime().getTime()) {


            List<fuelConsumptionTable> history = fuelHistory.dbInterface().getHistoryInTimePeriod(nextDate, (nextDate + plus30Days));

            double totalFuelAmount = history.get(0).fuelAmount;
            int startMileage = history.get(0).mileageWhenFuelAdded;
            int endMileage = history.get(history.size() - 1).mileageWhenFuelAdded;

            for (int i = 1; i < history.size(); i++) {
                totalFuelAmount += history.get(i).fuelAmount;
            }

            double currentFuelConsumption = (endMileage - startMileage) / totalFuelAmount;

            fuelConsumptionOverTime.add(currentFuelConsumption);

            nextDate = nextDate + plus30Days;

        }

        return fuelConsumptionOverTime;
    }
}
