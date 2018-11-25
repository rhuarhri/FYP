package com.example.rhuarhri.carmaintenancechatbot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/*
import com.crashlytics.android.answers.FirebaseAnalyticsEventMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;*/

public class MainActivity extends AppCompatActivity {

    EditText userResponseET;
    Button sendUserResponseBTN;
    TextView outputTXT;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTXT = (TextView) findViewById(R.id.outputTXT);
        /* Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/

        //fire store
        db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                outputTXT.setText("Data added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                outputTXT.setText("unable to send");
            }
        });




        userResponseET = (EditText) findViewById(R.id.userResponseET);
        sendUserResponseBTN = (Button) findViewById(R.id.askBTN);

        sendUserResponseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //CollectionReference citiesRef = db.collection("users");

                db.collection("users").whereEqualTo("born", 1815).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        outputTXT.setText("Data " + document.get("first"));
                                    }
                                } else {
                                    outputTXT.setText("No data found");
                                }
                            }
                        });

                /*db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        outputTXT.setText("Data " + document.getId() + " " + document.getData());
                                    }
                                } else {
                                    outputTXT.setText("No data found");
                                }
                            }
                        });*/


            }
        });
    }
}
