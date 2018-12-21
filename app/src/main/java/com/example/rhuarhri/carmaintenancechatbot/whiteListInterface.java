package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class whiteListInterface extends Worker {

    /*
    The reason that this is in it's own thread  is
    because this allows  the app to look at the black list
    and the character list at the same time.
     */

    FirebaseFirestore db;
    List importantWords  = new ArrayList<String>();
    String[] resultArray;
    boolean resultsFound = false;

    public whiteListInterface(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        getList();

        Data output = new Data.Builder().putStringArray("result", resultArray).build();


        if(importantWords.isEmpty() == true)
        {
            return Result.failure();
        }
        else
        {
            return Result.success();
        }

        //return Result.success(output);
    }

    public void getList()
    {
        //start up
        db = FirebaseFirestore.getInstance();

        requestData();

        while(resultsFound == false)
        {
            //wait until requested data is received
        }

        //finish
        db = null;

        resultArray = (String[]) importantWords.toArray(new String[importantWords.size()]);





    }

    private void requestData()
    {


        db.collection("white list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                importantWords.add(document.get("word").toString());
                            }
                            resultsFound = true;

                        } else {

                        }
                    }
                });
    }



}
