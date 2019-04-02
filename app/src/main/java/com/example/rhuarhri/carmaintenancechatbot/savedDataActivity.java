package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class savedDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        notificationTimer();
    }

    private void notificationTimer()
    {
        CountDownTimer timer = new CountDownTimer(8000, 8000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                showHomeScreen();
            }
        }.start();
    }

    private void showHomeScreen()
    {
        Intent goToHomeScreen = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(goToHomeScreen);
    }
}
