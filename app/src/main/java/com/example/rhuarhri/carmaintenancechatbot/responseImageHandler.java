package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class responseImageHandler {

    public void setEmotion(ImageView emotionIV, String Emotion)
    {
        if (Emotion.equals("happy"))
        {
            emotionIV.setImageResource(R.drawable.happy);
        }
        else if (Emotion.equals("sad"))
        {
            emotionIV.setImageResource(R.drawable.sad);
        }
        else if (Emotion.equals("think"))
        {
            emotionIV.setImageResource(R.drawable.thinking);
        }
        else
        {
            emotionIV.setImageResource(R.drawable.nuetral);
        }
    }

    public void setInfoImage(Context context, ImageView infoIV, String imageLocation, String imageDescription)
    {
        if (imageLocation.equals("") || imageLocation.equals(null))
        {
            //no image to display

        }
        else {

            Glide.with(context)
                    .load(imageLocation)
                    .into(infoIV);
            infoIV.setContentDescription(imageDescription);
        }
    }
}
