package com.example.rhuarhri.carmaintenancechatbot;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rhuarhri.carmaintenancechatbot.carFuelConsumption.fuelConsumption;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryManager;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.documentMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class UserResponseManager {

    String UserResponse = "";

    List<chatResponse> chatHistory = new ArrayList<>();
    chatHistoryManager historyManager = new chatHistoryManager();
    RecyclerView.Adapter chatDisplayAdapter;

    documentMap documentHistory = new documentMap();
    int userAttempts = 0;

    Context context;
    RecyclerView chatRV;
    Spinner suggestionSP;
    EditText questionET;

    public UserResponseManager(Context appContext, RecyclerView ChatRV, Spinner SuggestionSP, EditText QuestionET)
    {
        context = appContext;
        chatRV = ChatRV;
        suggestionSP = SuggestionSP;
        questionET = QuestionET;

    }

    //TODO figure out how the documentHistory is effected by users inputting the wrong data
    //idea
    //set number of times that the user can response before the app
    //starts from scratch

    public void search(String userResponse)
    {

        UserResponse = userResponse;

        //welcome is a auto generated message and should be ignored
        if (UserResponse != "welcome") {
            historyManager.addResponse(userResponse, "user");
        }

        chatHistory = historyManager.getHistory();

        chatDisplayAdapter = new chatRVAdapter(chatHistory);
        chatRV.setAdapter(chatDisplayAdapter);


        class searchThread extends Thread
        {
            public void run()
            {
                getResponse(userResponse);
            }
        }

        searchThread newThread  = new searchThread();
        newThread.run();
    }

    private void getResponse(String userResponse)
    {
        List ofCharacters = new ArrayList<String>();

        List blackList = new ArrayList<String>();

        List whiteList = new ArrayList<String>();

        List importantWords = new ArrayList<String>();

        List cleanResponse = new ArrayList<String>();

        List wordsFound = new ArrayList<String>();

        String inputString = userResponse.toLowerCase();

        String[] responseArray = inputString.split("\\ ", -1);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("characters").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ofCharacters.add(document.get("character").toString());

                            }

                            for (int i = 0; responseArray.length > i; i++) {

                                String currentWord = removeUseless(responseArray[i], ofCharacters);

                                wordsFound.add(currentWord);
                            }

                            db.collection("black list").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    blackList.add(document.get("word").toString());
                                                }

                                                for (int i = 0; responseArray.length > i; i++) {



                                                    if (blackList.contains(wordsFound.get(i)) == true) {

                                                    } else {
                                                        cleanResponse.add(wordsFound.get(i));
                                                    }
                                                }

                                                findResponse(cleanResponse);


                                            }
                                        }
                                    });

                            /*
                            db.collection("white list").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    whiteList.add(document.get("word").toString());
                                                }

                                                for (int i = 0; i < wordsFound.size(); i++) {

                                                    if (whiteList.contains(wordsFound.get(i)) == true) {
                                                        importantWords.add(wordsFound.get(i));
                                                    }
                                                }

                                                findResponse(importantWords, chatRecyclerView);

                                            }
                                        }
                        });*/
                        }
                    }});






    }

    private String removeUseless(String currentWord, List<String> ofCharacters)
    {

        String cleanString;
        String dirtyString = currentWord;

        for (int i = 0; ofCharacters.size() > i; i++)
        {
            dirtyString = dirtyString.replace(""+ofCharacters.get(i), "");
        }

        cleanString = dirtyString;

        return cleanString;
    }

    private void findResponse(List<String> cleanResponse)
    {



        DocumentReference tempColl = null;
        DocumentReference tempColl2 = null;

        List<String> docList = documentHistory.getDocumentHistory();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Query questionSearch = null;

        if (docList.size() > 0) {
            tempColl = db.collection("questions").document(docList.get(0));


            for (int i = 1; i < docList.size(); i++) {
                tempColl2 = tempColl.collection("questions").document(docList.get(i).toString());
                tempColl = tempColl2;
            }

            questionSearch = tempColl.collection("questions").whereEqualTo("input." + cleanResponse.get(0), "");
        }
        else
        {
             questionSearch = db.collection("questions").whereEqualTo("input." + cleanResponse.get(0), "");
        }


        Query tempQuery = questionSearch;
        Query questionSearch2 = questionSearch;



        for (int i = 1; i < cleanResponse.size(); i++)
        {
            questionSearch2 = tempQuery.whereEqualTo("input." + cleanResponse.get(i), "");
            tempQuery = questionSearch2;
        }

        Query responseBasedOn = questionSearch2;



        responseBasedOn.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                                if(task.getResult().size() < 1)
                                {
                                    //no response found
                                    String response = "I did not understand that could you try again";


                                    userAttempts++;

                                    if (userAttempts >= 3)
                                    {
                                        documentHistory = new documentMap();
                                        response = "Sorry I still don't understand could you try once more "
                                                + "I will try really hard this time.";
                                        userAttempts = 0;
                                    }

                                    historyManager.addResponse(response, "bot");

                                    chatHistory = historyManager.getHistory();

                                    chatDisplayAdapter = new chatRVAdapter(chatHistory);
                                    chatRV.setAdapter(chatDisplayAdapter);

                                }
                                else {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String response = document.get("response").toString();

                                        //welcome auto generated message and should be ignored
                                        if (UserResponse != "welcome") {
                                            documentHistory.addDocument(document.getId());
                                        }

                                        List<String> suggestions = (List<String>) document.get("suggestions");
                                        getNewSpinnerAdapter(suggestions);
                                        historyManager.addResponse(response, "bot");

                                        chatHistory = historyManager.getHistory();

                                        chatDisplayAdapter = new chatRVAdapter(chatHistory);
                                        chatRV.setAdapter(chatDisplayAdapter);


                                        //go back to welcome message
                                        chatEnd(response);

                                        userAttempts = 0;
                                    }
                                }


                        }



                    }
                });
        }

    private void chatEnd(String response)
    {
        if (response.equals("fuel null"))
        {
            //reset fuel consumption data
            fuelConsumption rest = new fuelConsumption(context);
            rest.resetFuelConsumption();
            restChat();
        }
        if (response.equals("null"))
        {
            restChat();
        }

    }

    private void restChat()
    {
        documentHistory = new documentMap();
        historyManager = new chatHistoryManager();
        List<String> responseList = new ArrayList<String>();
        responseList.add("welcome");
        findResponse(responseList);
    }


        private void getNewSpinnerAdapter(List<String> suggestions)
        {
            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, suggestions);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            suggestionSP.setAdapter(adapter);

            suggestionSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    questionET.setText(suggestions.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }




}







