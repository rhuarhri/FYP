package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UserResponseManager extends Worker {


    FirebaseFirestore db;
    String response = "no data found";

    String userResponse = "";

    public UserResponseManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }




    @NonNull
    @Override
    public Result doWork() {


         userResponse = getInputData().getString("data");
        userResponse = "Lights don't work";



        keyWordSearch newSearch = new keyWordSearch();

        newSearch.runSearch(userResponse);

        List<String> cleanResponse = newSearch.getCleanResponse();
        List<String> importantWords = newSearch.getImportantWords();



        responseManager RM = new responseManager();



        response = RM.findResponse(cleanResponse, importantWords);

        Data output = new Data.Builder().putString("response", response).build();

        return Result.success(output);
    }




}
