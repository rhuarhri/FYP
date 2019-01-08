package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UserResponseManager extends Worker {


    FirebaseFirestore db;
    String response = "";

    public UserResponseManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {

        keyWordSearch newSearch = new keyWordSearch();

        newSearch.runSearch("");

        List<String> cleanResponse = newSearch.getCleanResponse();
        List<String> importantWords = newSearch.getImportantWords();


        db = FirebaseFirestore.getInstance();

        CollectionReference questionSearch = db.collection("question");

        Query responseBasedOn = questionSearch.whereArrayContains("input", cleanResponse);

        /*
        for (int i = 1; i < cleanResponse.size(); i++)
        {
            responseBasedOn += questionSearch.whereArrayContains("input", cleanResponse.get(i));
        }*/

        responseBasedOn.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                response = document.get("response").toString();
            }
        });

        Data output = new Data.Builder().putString("response", response).build();

        return Result.success(output);
    }


}
