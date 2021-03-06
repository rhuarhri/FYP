package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumption;
import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumptionTable;
import com.example.rhuarhri.carmaintenancechatbot.carInfo.carInformation;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carMileageTable;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;

import java.util.Calendar;

public class carHistoryActivity extends AppCompatActivity {

    carInformation carInfo;
    carServicing addCarServicing;

    EditText mileageET;
    SeekBar fuelAmountSB;
    TextView fuelAmountDisplayTXT;
    double currentFuelAmount;

    Button saveBTN;

    private dataConverter converter = new dataConverter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_history);

        addCarServicing = new carServicing(getApplicationContext());
        carInfo = new carInformation(getApplicationContext());
        carInfo.getCarInfo();

        mileageET = (EditText) findViewById(R.id.mileageET);
        mileageET.setText(""+displayMileage());
        fuelAmountSB = (SeekBar) findViewById(R.id.fuelAmountSB);
        fuelAmountDisplayTXT = (TextView) findViewById(R.id.FuelAmountTXT);

        saveBTN = (Button) findViewById(R.id.saveBTN);


        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(mileageET.getText().toString(), currentFuelAmount);
            }
        });

        fuelAmountSB.setMax(carInfo.getCarFuelTankSize());

        fuelAmountSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                fuelAmountDisplayTXT.setText(""+ i + " litres");
                currentFuelAmount = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private int displayMileage()
    {
        int distance = addCarServicing.getCurrentMileage();
        Log.d("DISTANCE", ""+ distance);

        if (carInfo.isInMiles())
        {
            distance = converter.convertToMiles(distance);
        }

        return distance;
    }


    private void saveData(String newMileage, double newFuelAmount)
    {

        int mileage = 0;
        double fuel = newFuelAmount;

        if (!carInfo.isInMiles())
        {
            mileage = Integer.parseInt(newMileage);
        }
        else
        {
            mileage = converter.convertToKilometers(Integer.parseInt(newMileage));
        }



        addCarServicing.carNotServiced(mileage);

        fuelConsumption addFuelConsumption = new fuelConsumption(getApplicationContext());

        addFuelConsumption.fuelAdded(fuel, mileage);

        Intent goToSavedDataScreen = new Intent(getApplicationContext(), savedDataActivity.class);

        startActivity(goToSavedDataScreen);

    }


}
