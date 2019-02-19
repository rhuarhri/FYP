package com.example.rhuarhri.carmaintenancechatbot;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryController;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryManager;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

/*
import com.crashlytics.android.answers.FirebaseAnalyticsEventMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;*/

public class MainActivity extends AppCompatActivity {

    boolean lookingForData = true;

    int placeInList = 1;

    EditText userResponseET;
    Button sendUserResponseBTN;
    TextView outputTXT;
    RecyclerView chatDisplay;
    RecyclerView.Adapter chatDisplayAdapter;
    RecyclerView.LayoutManager chatDisplayLM;
    Spinner autoAnswersDisplay;

    List<chatResponse> chatHistory = new ArrayList<>();
    chatHistoryManager historyManger = new chatHistoryManager();

    OneTimeWorkRequest wordSearch; //= new OneTimeWorkRequest.Builder(UserResponseManager.class).build();
    WorkManager threadManager = WorkManager.getInstance();
    LifecycleOwner lifecycleOwner;

    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();*/

    String response = "";


    //String Testresults = "a";

    //StringBuilder fields = new StringBuilder("");

    UserResponseManager ResponseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTXT = (TextView) findViewById(R.id.outputTXT);

        chatDisplay = (RecyclerView) findViewById(R.id.chatDisplayRV);
        chatDisplayLM = new LinearLayoutManager(this);
        chatDisplay.setLayoutManager(chatDisplayLM);
        chatDisplayAdapter = new chatRVAdapter(chatHistory);
        chatDisplay.setAdapter(chatDisplayAdapter);

        autoAnswersDisplay = (Spinner) findViewById(R.id.autoAnswerSP);


        userResponseET = (EditText) findViewById(R.id.userResponseET);
        sendUserResponseBTN = (Button) findViewById(R.id.askBTN);




        ResponseManager = new UserResponseManager(getApplicationContext(), chatDisplay, autoAnswersDisplay, userResponseET);

        ResponseManager.search("welcome");


        sendUserResponseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //historyManger.addResponse("lights don't work", "user");

                //chatHistory = historyManger.getHistory();

                //chatDisplayAdapter = new chatRVAdapter(chatHistory);
                //chatDisplay.setAdapter(chatDisplayAdapter);


                //ManageUserResponse("lights don't work");
                //ManageUserResponse("welcome");

                //List<String> importantWords = new ArrayList<>();


                ResponseManager.search(userResponseET.getText().toString());

                //findResponse(null, chatDisplay);

                //outputTXT.setText(RM.findResponse(db, "work", importantWords));


            }
        });
    }
}


    //PAST CODE
    /*
    public void findResponse(List<String> cleanResponse, RecyclerView chatRecyclerView)
    {



        String key = "work";

        cleanResponse = new ArrayList<String>();
        cleanResponse.add("lights");
        cleanResponse.add("don't");
        cleanResponse.add("work");
        //cleanResponse.add("on");


        int currentPlaceInList = 1;


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);*

        //while (tooManyResponses == true) {



        //response = "" + cleanResponse.get(0) + " ";

        Query responseBasedOn = questionSearch.whereEqualTo("input." + cleanResponse.get(0), "");

        Query questionSearch = db.collection("questions").whereEqualTo("input." + cleanResponse.get(0), "");

        Query temp = questionSearch;
        Query questionSearch2 = questionSearch;


        for (int i = 1; i < cleanResponse.size(); i++)
        {
          questionSearch2 = temp.whereEqualTo("input." + cleanResponse.get(i), "");
          temp = questionSearch2;
        }


        //Query questionSearch2 = questionSearch.whereEqualTo("input." + cleanResponse.get(2), "");







        Query responseBasedOn = questionSearch2;


        /*
            for (int i = 1; i < cleanResponse.size(); i++) {
                //responseBasedOn.whereEqualTo("input." + cleanResponse.get(i), "");
                questionSearch.whereEqualTo("input." + cleanResponse.get(i), "");
            }*



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
                                if(task.getResult().size() > 1)
                                {
                                    //too many responses
                                    lookingForData = true;

                                }
                                else {
                            lookingForData = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String response = document.get("response").toString();
                                historyManger.addResponse(response, "bot");

                                chatHistory = historyManger.getHistory();

                                chatDisplayAdapter = new chatRVAdapter(chatHistory);
                                chatRecyclerView.setAdapter(chatDisplayAdapter);


                            }
                            }

                        }


                        //DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        //response = document.get("response").toString();
                    }
                });







    }
    private void ManageUserResponse(String response)
    {
        Data myData = new Data.Builder().putString("data", response).build();


        wordSearch = new OneTimeWorkRequest.Builder(UserResponseManager.class)
                .setInputData(myData).build();

        WorkManager.getInstance().getWorkInfoByIdLiveData(wordSearch.getId())
                .observe(lifecycleOwner, info -> {
                    if (info != null && info.getState().isFinished()) {
                        String defaultData;
                        defaultData = info.getOutputData().getString("response");
                        // ... do something with the result ...

                        historyManger.addResponse(defaultData, "bot");

                        chatHistory = historyManger.getHistory();

                        chatDisplayAdapter = new chatRVAdapter(chatHistory);
                        chatDisplay.setAdapter(chatDisplayAdapter);


                    }
                });

        threadManager.enqueue(wordSearch);

    }

}



                /*
                responseManager RM = new responseManager();

                String response = RM.findResponse("Lights don't work");

                outputTXT.setText(response);

                historyManger.addResponse(response, "bot");*/




                /*



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
                                //lookingForData = false;
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //response = "thread"; //document.get("response").toString();
                                        outputTXT.setText(document.get("response").toString());

                                    }

                                }

                                //DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                //response = document.get("response").toString();
                            }
                        });



/*


                CollectionReference questionSearch = db.collection("questions");

                Query responseBasedOn = questionSearch.whereEqualTo("work", "");

                responseBasedOn.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //response = "thread";/*document.get("response").toString();*
                                    }

                                }
                            }});




                List<String> searchFor = new ArrayList<String>();

                searchFor.add("work");
                searchFor.add("lights");
                searchFor.add("don't");




                db.collection("questions").whereEqualTo(searchFor.get(0), "")
                        .whereEqualTo(searchFor.get(1), "").whereEqualTo(searchFor.get(2), "").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        outputTXT.setText(document.get("response").toString());

                                        historyManger.addResponse(document.get("response").toString(), "bot");

                                        chatHistory = historyManger.getHistory();

                                        chatDisplayAdapter = new chatRVAdapter(chatHistory);
                                        chatDisplay.setAdapter(chatDisplayAdapter);


                                    }
                                } else {
                                    outputTXT.setText("No data found");
                                }
                            }
                        });
*/




/*
                WorkManager.getInstance().getWorkInfoByIdLiveData(wordSearch.getId()).getValue()
                        .observe((LifecycleOwner) getApplicationContext(), info -> {
                            if (info != null && info.getState().isFinished()) {
                                String[] defaultData = new String[0];
                                String[] myResult = info.getOutputData().getStringArray("result");                                // ... do something with the result ...
                            }
                        });



                WorkManager.getInstance().getStatusById(wordSearch.getId()).get(getApplicationContext(), info -> {
                            if (info != null && info.getState().isFinished()) {
                                String[] defaultData = new String[0];
                                String[] myResult = info.getOutputData().getStringArray("result", defaultData));
                                // ... do something with the result ...
                            }
                        });*/

    /*
    private void requestData()
    {
        FirebaseFirestore db;

        db = FirebaseFirestore.getInstance();


        outputTXT.setText(
        db.collection("white list")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //importantWords.add(document.getData());
                                        //Testresults =  Testresults + String.valueOf(document.get("word"));
                                        fields.append(document.get("word"));
                                        String dataFound = fields.toString();
                                        //outputTXT.setText(dataFound);


                                    }
                                } else {
                                    outputTXT.setText("No data found");
                                }
                            }
                        }).getResult().getDocuments().size());




        //outputTXT.setText("from list " + database.getImportantWords().get(0));
    }*/




//chatHistoryController newMessage = new chatHistoryController(getApplicationContext());
//newMessage.addMessage("welcome", "bot");
//chatDisplayAdapter.notifyDataSetChanged();

//CollectionReference citiesRef = db.collection("users");
/*
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
                        });*/

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



/*
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
*/



