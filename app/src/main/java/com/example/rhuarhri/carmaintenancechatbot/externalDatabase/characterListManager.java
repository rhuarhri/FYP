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

public class characterListManager implements Serializable {

    private List<String> ofCharacters = new ArrayList<String>();

    public void setup()
    {
        if(ofCharacters.isEmpty())
        {
            getFromFireStore();
        }
        else
        {

        }
    }

    public List<String> getCharacterList()
    {
        return ofCharacters;
    }

    private void getFromFireStore()
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("characters").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ofCharacters.add(document.get("character").toString());
                            }
                        }
                    }});
    }
}
