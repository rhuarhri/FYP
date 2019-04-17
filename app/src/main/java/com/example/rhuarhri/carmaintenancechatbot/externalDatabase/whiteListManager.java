package com.example.rhuarhri.carmaintenancechatbot.externalDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class whiteListManager implements Serializable {

    private List<String> whiteList = new ArrayList<String>();

    public void setup()
    {
        if (whiteList.isEmpty())
        {
            getFromFireStore();
        }
        else
        {

        }
    }


    public List getWhiteList()
    {
        return whiteList;
    }


    private void getFromFireStore()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

                            db.collection("white list").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    whiteList.add(document.get("word").toString());
                                                }


                                            }
                                        }
                        });
    }
}
