package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.blackListManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.setupManager;

public class startActivity extends AppCompatActivity {

    setupManager setupM;
    timer screenTimer = new timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setupM = new setupManager(getApplicationContext());

        setupM.setup();

        screenTimer.start();

        /*
        CountDownTimer wait = new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                chooseScreen();
            }
        }.start();*/


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
            chosenScreen = setupM.getMainScreenIntent();//new Intent(getApplicationContext(), MainActivity.class);
        }

        startActivity(chosenScreen);
    }

    private class timer extends Thread {

        public void run()
        {
            try {
                Thread.sleep(5000);
                chooseScreen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
