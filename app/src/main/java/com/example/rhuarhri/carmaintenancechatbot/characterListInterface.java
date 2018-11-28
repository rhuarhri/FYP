package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class characterListInterface extends Worker {

    FirebaseFirestore db;
    List characterList  = new ArrayList<String>();

    public characterListInterface(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

    private List getList(){

        db = FirebaseFirestore.getInstance();

        requestData();

        db = null;
        return  characterList;
    }

    private void requestData()
    {


        db.collection("characters").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                characterList.add(document.get("character"));
                            }

                        } else {

                        }
                    }
                });

    }



}
