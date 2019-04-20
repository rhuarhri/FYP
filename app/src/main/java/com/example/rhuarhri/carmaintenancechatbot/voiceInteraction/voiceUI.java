package com.example.rhuarhri.carmaintenancechatbot.voiceInteraction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.Toast;

public class voiceUI {

    Context context;
    TextToSpeech reader;
    String response;

    PackageManager packageManager;
    Boolean hasFeature = false;
    Intent voiceIntent;


    public void setupSpeaker(Context appContext)
    {
        context = appContext;

        //set up text to to speech functionality
        reader = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR)
                {

                }

            }
        });

    }

    public void setResponse(String Response)
    {
        response = Response;
    }

    public void speaker() {

        reader.speak(response, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void setUpSpeechToText(PackageManager packageM)
    {
        packageManager = packageM;

        //set up speech to text functionality
        voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        if(voiceIntent.resolveActivity(packageManager) != null) {
            hasFeature = true;
        }
        else
        {
            hasFeature = false;
        }

    }

    public Intent speechToText()
    {
        if (hasFeature)
        {
            return voiceIntent;
        }
        else
        {
            return null;
        }
    }
}
