package com.example.rhuarhri.carmaintenancechatbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rhuarhri.carmaintenancechatbot.carmileage.carMileageTable;

import java.util.Calendar;

public class carHistoryActivity extends AppCompatActivity {

    EditText mileageET;
    EditText fuelET;

    Button saveBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_history);

        mileageET = (EditText) findViewById(R.id.mileageET);
        fuelET = (EditText) findViewById(R.id.fuelET);

        saveBTN = (Button) findViewById(R.id.saveBTN);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(mileageET.getText().toString(), fuelET.getText().toString());
            }
        });
    }

    public void saveData(String newMileage, String newFuelAmount)
    {
        int mileage = Integer.parseInt(newMileage);
        int fuel = Integer.parseInt(newFuelAmount);

        carMileageTable CurrentMileage = new carMileageTable();

        CurrentMileage.setMileage(mileage);
        CurrentMileage.setDateRecorded(Calendar.getInstance().getTime().getTime());
        CurrentMileage.setWasServiced(false);


    }
}
