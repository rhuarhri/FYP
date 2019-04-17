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

public class wordMeaningListManager implements Serializable {

    List<baseMeaning> simplifications = new ArrayList<baseMeaning>();

    public void setup()
    {
        if (simplifications.isEmpty())
        {
            getFromFireStore();
        }
        else
        {

        }
    }

    public List<baseMeaning> getSimplifications()
    {
        return simplifications;
    }

    public List<String> getWordList(int location)
    {
        return simplifications.get(location).getWords();
    }

    public String getMeaning(int location)
    {
        return simplifications.get(location).getMeaning();
    }

    private void getFromFireStore()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meanings").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                baseMeaning wordSimplifiesTo = document.toObject(baseMeaning.class);
                                simplifications.add(wordSimplifiesTo);

                            }
                        }
                    }
                });
        }
}
