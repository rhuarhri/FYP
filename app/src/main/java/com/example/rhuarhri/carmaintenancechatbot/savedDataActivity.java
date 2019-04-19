package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.setupManager;

public class savedDataActivity extends AppCompatActivity {

    setupManager setupM;
    timer screenTimer = new timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        setupM = new setupManager(getApplicationContext());

        setupM.setup();

        screenTimer.start();
    }

    private class timer extends Thread {

        public void run()
        {
            try {
                Thread.sleep(5000);
                showHomeScreen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showHomeScreen()
    {
        Intent goToHomeScreen = setupM.getMainScreenIntent();

        startActivity(goToHomeScreen);
    }
}
