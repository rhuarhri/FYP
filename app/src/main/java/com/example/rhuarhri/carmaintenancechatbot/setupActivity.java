package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rhuarhri.carmaintenancechatbot.carInfo.carInformation;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.setupManager;

public class setupActivity extends AppCompatActivity {

    setupManager setupM;

    Button saveBTN;
    RadioButton InKilometresBTN;
    RadioButton InMilesBTN;
    EditText fuelTankSizeET;
    EditText distanceET;
    carServicing setupServicing;
    carInformation recordedCarInfo;

    private int distance = 0;
    private int fuelTankSize = 0;
    private boolean inMiles = false;

    private dataConverter converter = new dataConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        setupM = new setupManager(getApplicationContext());

        setupM.setup();

        setupServicing = new carServicing(getApplicationContext());
        recordedCarInfo = new carInformation(getApplicationContext());
        saveBTN = (Button) findViewById(R.id.saveBTN);
        InKilometresBTN = (RadioButton) findViewById(R.id.kilometresRB);
        InMilesBTN = (RadioButton) findViewById(R.id.milesRB);
        fuelTankSizeET = (EditText) findViewById(R.id.carFuelTankET);
        distanceET = (EditText) findViewById(R.id.carMileageET);


        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromUI();
                addData();
                nextScreen();
            }
        });
    }

    private void getDataFromUI()
    {
        fuelTankSize = Math.round(Float.parseFloat(fuelTankSizeET.getText().toString()));
        distance = Math.round(Float.parseFloat(distanceET.getText().toString()));

        if(InKilometresBTN.isChecked())
        {
            inMiles = false;
        }

        if (InMilesBTN.isChecked())
        {
            inMiles = true;
        }

        if (InMilesBTN.isChecked() && InKilometresBTN.isChecked())
        {
            inMiles = false;
        }

        if(!InKilometresBTN.isChecked() && !InMilesBTN.isChecked())
        {
            inMiles = false;
        }

    }

    private void addData()
    {
        int distanceInKm = distance;

        if(inMiles) {
            distanceInKm = converter.convertToKilometers(distance);
        }

        setupServicing.runSetUp(distanceInKm);
        recordedCarInfo.addCarInfo(fuelTankSize, inMiles);
    }

    private void nextScreen()
    {
        Intent goToHomeScreen = setupM.getMainScreenIntent();
        startActivity(goToHomeScreen);
    }
}
