package com.example.rhuarhri.carmaintenancechatbot;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class responseManager {

    FirebaseFirestore db;
    boolean lookingForData = true;
    String response = "no data found";

    public responseManager()
    {
        db = FirebaseFirestore.getInstance();
    }

    public String findResponse(List<String> cleanResponse, List<String> importantWords)
    {
        db = FirebaseFirestore.getInstance();



        CollectionReference questionSearch = db.collection("questions");

        //response = "" + cleanResponse.get(0) + " ";

        Query responseBasedOn = questionSearch.whereEqualTo("work", "");

/*
        for (int i = 1; i < cleanResponse.size(); i++)
        {
            //responseBasedOn += questionSearch.whereArrayContains("input", cleanResponse.get(i));
            responseBasedOn.whereEqualTo(cleanResponse.get(i), "");
        }*/

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
