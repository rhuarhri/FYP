package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumption;
import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumptionTable;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carMileageTable;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;

import java.util.Calendar;

public class carHistoryActivity extends AppCompatActivity {

    EditText mileageET;
    EditText fuelET;

    Button saveBTN;
    ToggleButton distanceTBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_history);

        mileageET = (EditText) findViewById(R.id.mileageET);
        fuelET = (EditText) findViewById(R.id.fuelET);

        saveBTN = (Button) findViewById(R.id.saveBTN);
        distanceTBTN = (ToggleButton) findViewById(R.id.distanceTBTN);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(mileageET.getText().toString(), fuelET.getText().toString());
            }
        });

    }

    public void saveData(String newMileage, String newFuelAmount)
    {

        int mileage = 0;
        double fuel = Double.parseDouble(newFuelAmount);

        if (distanceTBTN.isChecked())
        {
            mileage = Integer.parseInt(newMileage);
        }
        else
        {
            mileage = convertToKilometers(Integer.parseInt(newMileage));
        }

        carServicing addCarServicing = new carServicing(getApplicationContext());

        addCarServicing.carNotServiced(mileage);

        fuelConsumption addFuelConsumption = new fuelConsumption(getApplicationContext());

        addFuelConsumption.fuelAdded(fuel, mileage);

        Intent goToSavedDataScreen = new Intent(getApplicationContext(), savedDataActivity.class);

        startActivity(goToSavedDataScreen);

    }

    private int convertToKilometers(int distance)
    {
        int inKilometers = (int) Math.round(distance * 1.60934);

        return inKilometers;
    }
}
