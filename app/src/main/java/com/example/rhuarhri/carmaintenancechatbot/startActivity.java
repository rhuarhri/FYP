package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        CountDownTimer wait = new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                chooseScreen();
            }
        }.start();
    }

    private void chooseScreen()
    {
        carServicing serviceHistory = new carServicing(getApplicationContext());
        Intent chosenScreen;

        if(serviceHistory.setUpRequired())
        {
            chosenScreen = new Intent(getApplicationContext(), setupActivity.class);
        }
        else
        {
            chosenScreen = new Intent(getApplicationContext(), MainActivity.class);
        }

        startActivity(chosenScreen);
    }
}
