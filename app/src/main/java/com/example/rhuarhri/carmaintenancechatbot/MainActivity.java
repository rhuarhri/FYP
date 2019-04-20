package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumption;
import com.example.rhuarhri.carmaintenancechatbot.carmileage.carServicing;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryManager;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.blackListManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.setupManager;
import com.example.rhuarhri.carmaintenancechatbot.voiceInteraction.voiceUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    boolean lookingForData = true;

    int placeInList = 1;

    EditText userResponseET;

    RecyclerView chatDisplay;

    Spinner autoAnswersDisplay;

    List<chatResponse> chatHistory = new ArrayList<>();
    chatHistoryManager historyManger = new chatHistoryManager();

    carServicing checkServiceHistory;
    fuelConsumption checkFuelConsumption;


    voiceUI listener = new voiceUI();
    UserResponseManager ResponseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatDisplay = (RecyclerView) findViewById(R.id.chatDisplayRV);
        RecyclerView.LayoutManager chatDisplayLM = new LinearLayoutManager(this);
        chatDisplay.setLayoutManager(chatDisplayLM);
        RecyclerView.Adapter chatDisplayAdapter = new chatRVAdapter(getApplicationContext(), chatHistory);
        chatDisplay.setAdapter(chatDisplayAdapter);

        autoAnswersDisplay = (Spinner) findViewById(R.id.autoAnswerSP);

        userResponseET = (EditText) findViewById(R.id.userResponseET);
        Button sendUserResponseBTN = (Button) findViewById(R.id.askBTN);
        Button editStatsBTN = (Button) findViewById(R.id.editStatsBTN);

        Button voiceBTN = (Button) findViewById(R.id.voiceBTN);
        Button readBTN = (Button) findViewById(R.id.readBTN);

        listener.setUpSpeechToText(getPackageManager());
        Intent sentData = getIntent();
        ResponseManager = new UserResponseManager(getApplicationContext(), chatDisplay, autoAnswersDisplay, userResponseET, sentData);

        checkServiceHistory = new carServicing(getApplicationContext());
        checkFuelConsumption = new fuelConsumption(getApplicationContext());

        if(checkServiceHistory.carNeedsServicing() == true)
        {
            ResponseManager.search("servicing");

            checkServiceHistory.carServiced();

        }
        //check fuel consumption every 100 days because fuel consumption may vary from day to day in a normal car
        else if(((Calendar.getInstance().getTime().getTime() / (1000*60*60*24)) % 100) == 0)
        {
            if (checkFuelConsumption.isFuelConsumptionNormal() == false)
            {
                ResponseManager.search("fuel consumption");

            }
            else
            {
                ResponseManager.search("welcome");

            }
        }
        else
        {
            ResponseManager.search("welcome");

        }

        sendUserResponseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userQuestion = userResponseET.getText().toString();

                if(!userQuestion.isEmpty()) {
                    ResponseManager.search(userQuestion);
                }

            }
        });

        editStatsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCarHistoryActivity = new Intent(getApplicationContext(), carHistoryActivity.class);

                startActivity(goToCarHistoryActivity);

            }
        });

        voiceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener.speechToText() != null)
                {
                    startActivityForResult(listener.speechToText(), 10);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "This feature is not supported on this device", Toast.LENGTH_LONG).show();

                }
            }
        });

        readBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResponseManager.readResponse();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                userResponseET.setText(result.get(0));
            }
        }
    }
}
