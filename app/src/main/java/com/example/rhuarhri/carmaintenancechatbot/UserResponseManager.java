package com.example.rhuarhri.carmaintenancechatbot;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.rhuarhri.carmaintenancechatbot.voiceInteraction.voiceUI;
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

    private String UserResponse = "";
    private List<String> suggestedResponses;

    private List<chatResponse> chatHistory = new ArrayList<>();
    private chatHistoryManager historyManager = new chatHistoryManager();
    private RecyclerView.Adapter chatDisplayAdapter;

    private documentMap documentHistory = new documentMap();
    private unKnownResponseHandler unknownInput;


    private Context context;
    private RecyclerView chatRV;
    private suggestionDisplay suggestionDis;

    private voiceUI reader = new voiceUI();

    public UserResponseManager(Context appContext, RecyclerView ChatRV, Spinner SuggestionSP, EditText QuestionET)
    {
        context = appContext;
        chatRV = ChatRV;
        suggestionDis = new suggestionDisplay(appContext, SuggestionSP, QuestionET);
        unknownInput = new unKnownResponseHandler(chatRV, context, suggestionDis);
        reader.setupSpeaker(appContext);

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
            historyManager.addUserResponse(userResponse);
        }

        chatHistory = historyManager.getHistory();

        chatDisplayAdapter = new chatRVAdapter(context, chatHistory);
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

    public void readResponse()
    {
        if (!unknownInput.returnResponse().equals(""))
        {
            reader.setResponse(unknownInput.returnResponse());
        }

        reader.speaker();

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        QueryCreator newQuery = new QueryCreator(db);
        Query responseBasedOn = newQuery.create(cleanResponse, documentHistory);

        responseBasedOn.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                                if(task.getResult().size() < 1)
                                {

                                    //Log.d("SIZE", ""+ documentHistory.getDocumentHistory().size());

                                    boolean reset = unknownInput.unKnownResponse(documentHistory, historyManager, suggestedResponses);

                                    if (reset)
                                    {
                                        Log.d("RESET", "chat reset");
                                        restChat();
                                    }
                                    else
                                    {

                                        historyManager = unknownInput.returnChatHistory();
                                    }

                                }
                                else {
                                    unknownInput.reset();

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String response = document.get("response").toString();
                                        reader.setResponse(response);
                                        String emotion = document.get("emotion").toString();
                                        String image = document.get("image").toString();
                                        String imageDescription = document.get("description").toString();

                                        //welcome auto generated message and should be ignored
                                        if (UserResponse != "welcome") {
                                            documentHistory.addDocument(document.getId());
                                        }

                                        Log.d("SIZE", "" + documentHistory.getDocumentHistory().size());

                                        suggestedResponses = (List<String>) document.get("suggestions");
                                        suggestionDis.getNewSpinnerAdapter(suggestedResponses);
                                        historyManager.addBotResponse(response, emotion, image, imageDescription);

                                        //unknownInput.addFallBackResponse(document.get("fallback").toString());

                                        chatHistory = historyManager.getHistory();

                                        chatDisplayAdapter = new chatRVAdapter(context, chatHistory);
                                        chatRV.setAdapter(chatDisplayAdapter);

                                        chatRV.smoothScrollToPosition(chatRV.getAdapter().getItemCount() +2);


                                        //go back to welcome message
                                        chatEnd(response);

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
        UserResponse = "welcome";
    }


}