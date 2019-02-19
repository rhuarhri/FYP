package com.example.rhuarhri.carmaintenancechatbot;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


/*
public class responseManager {


    boolean lookingForData = true;
    String response = "no data found";

    public responseManager()
    {

    }

    /*
    public String findResponse(String cleanResponse, List<String> importantWords)
    {




        CollectionReference questionSearch = db.collection("questions");

        //response = "" + cleanResponse.get(0) + " ";

        Query responseBasedOn = questionSearch.whereEqualTo("work", "");

/*
        for (int i = 1; i < cleanResponse.size(); i++)
        {
            //responseBasedOn += questionSearch.whereArrayContains("input", cleanResponse.get(i));
            responseBasedOn.whereEqualTo(cleanResponse.get(i), "");
        }*

        responseBasedOn.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                response = document.get("response").toString();

                            }

                        }
                        else
                        {
                            response = "failed";
                        }
                        lookingForData = false;


                        //DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        //response = document.get("response").toString();
                    }
                });

        while (lookingForData == true)
        {
            //response = "working";
        }


        return response;
    }*


    public String findResponse(String cleanResponse, List<String> importantWords)
    {



        String key = "work";

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        CollectionReference questionSearch = db.collection("questions");

        //response = "" + cleanResponse.get(0) + " ";

        Query responseBasedOn = questionSearch.whereEqualTo("input." + "work", "work");

/*
        for (int i = 1; i < cleanResponse.size(); i++)
        {
            //responseBasedOn += questionSearch.whereArrayContains("input", cleanResponse.get(i));
            responseBasedOn.whereEqualTo(cleanResponse.get(i), "");
        }*

        responseBasedOn.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                response = document.get("response").toString();

                            }

                        }
                        else
                        {
                            response = "failed";
                        }
                        lookingForData = false;


                        //DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        //response = document.get("response").toString();
                    }
                });

        while (lookingForData == true)
        {
            //response = "working";
        }


        return response;
    }

}
*/