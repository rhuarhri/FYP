package com.example.rhuarhri.carmaintenancechatbot;


import android.content.Context;
import android.content.Intent;
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
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.blackListManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.characterListManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.setupManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.whiteListManager;
import com.example.rhuarhri.carmaintenancechatbot.externalDatabase.wordMeaningListManager;
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

    private FirebaseFirestore db;

    private blackListManager blackListM;
    private characterListManager characterListM;
    private whiteListManager whiteListM;
    private wordMeaningListManager WMLManager;
    private List cleanResponse = new ArrayList<String>();
    private List wordsFound = new ArrayList<String>();
    private List unsimplifiedResponse = new ArrayList<String>();


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

    public UserResponseManager(Context appContext, RecyclerView ChatRV, Spinner SuggestionSP, EditText QuestionET, Intent sentData)
    {
        db = FirebaseFirestore.getInstance();

        context = appContext;
        chatRV = ChatRV;
        suggestionDis = new suggestionDisplay(appContext, SuggestionSP, QuestionET);
        unknownInput = new unKnownResponseHandler(chatRV, context, suggestionDis);
        reader.setupSpeaker(appContext);
        setUpDataFromFireStore(sentData, appContext);

    }

    private void setUpDataFromFireStore(Intent sentData, Context appContext)
    {
        setupManager setupM = new setupManager(appContext);
        setupM.getObjectsFromIntent(sentData);
        blackListM = setupM.getBlackListManager();
        characterListM = setupM.getCharacterListMananager();
        whiteListM = setupM.getWhiteListManager();
        WMLManager = setupM.getWordMeaningListManager();
    }


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

        /*
        Step 1 convert all the response to lower case
         */
        String inputString = userResponse.toLowerCase();

        /*
        Step 2 split the response into single word chunks
        */
        String[] responseArray = inputString.split("\\ ", -1);


        wordsFound = new ArrayList<String>();
        unsimplifiedResponse = new ArrayList<String>();

        for (int i = 0; responseArray.length > i; i++) {

            /*
            Step 3 check that characters are related to grammar such as ! ?
            and removes them
             */
            String currentWord = removeUseless(responseArray[i], characterListM.getCharacterList());

            wordsFound.add(currentWord);
        }

        for (int i = 0; responseArray.length > i; i++) {

            /*
            Step 4 check that found words are useless or linking words linking like and or the
            and removes them
             */
            if (blackListM.getBlackList().contains(wordsFound.get(i).toString())) {

            } else {

                unsimplifiedResponse.add(wordsFound.get(i));
            }
        }

        List<String> responseWithMeaning = new ArrayList<String>();


        for (int i = 0; i < unsimplifiedResponse.size(); i++) {
            String meaning = "";

            /*
            Step 5 check if word is in white list and if so jumps step 6
             */
            if(!whiteListM.getWhiteList().contains(unsimplifiedResponse.get(i)))
            {
                for (int it = 0; it < WMLManager.getSimplifications().size(); it++) {
                    for (int iter = 0; iter < WMLManager.getWordList(it).size(); iter++) {

                        /*
                        Step 6 find if the current word can be simplified for example vam and be simplified to car
                         */
                        if (unsimplifiedResponse.get(i).toString().equals(WMLManager.getWordList(it).get(iter).toString())) {
                            meaning = WMLManager.getMeaning(it);
                            break;
                        } else { }
                }

            }
            }

            if (meaning.equals(""))
            {
                meaning = unsimplifiedResponse.get(i).toString();
            }

            responseWithMeaning.add(meaning);
        }

        String editedResponse = "";
        for (int test = 0; test < responseWithMeaning.size(); test++)
        {

            editedResponse = editedResponse + " " + responseWithMeaning.get(test);
        }




        findResponse(responseWithMeaning);

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


        QueryCreator newQuery = new QueryCreator(db);
        Query responseBasedOn = newQuery.create(cleanResponse, documentHistory);

        responseBasedOn.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                                if(task.getResult().size() < 1)
                                {


                                    boolean reset = unknownInput.unKnownResponse(documentHistory, historyManager, suggestedResponses);

                                    if (reset)
                                    {

                                        restChat();
                                    }
                                    else
                                    {

                                        historyManager = unknownInput.returnChatHistory();
                                    }

                                }
                                else {
                                    unknownInput.reset();



                                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                        String response = document.get("response").toString();
                                        reader.setResponse(response);
                                        String emotion = document.get("emotion").toString();
                                        String image = document.get("image").toString();
                                        String imageDescription = document.get("description").toString();

                                        //welcome auto generated message and should be ignored
                                        if (UserResponse != "welcome") {
                                            documentHistory.addDocument(document.getId());
                                        }



                                        suggestedResponses = (List<String>) document.get("suggestions");
                                        suggestionDis.getNewSpinnerAdapter(suggestedResponses);
                                        historyManager.addBotResponse(response, emotion, image, imageDescription);


                                        chatHistory = historyManager.getHistory();

                                        chatDisplayAdapter = new chatRVAdapter(context, chatHistory);
                                        chatRV.setAdapter(chatDisplayAdapter);

                                        chatRV.smoothScrollToPosition(chatRV.getAdapter().getItemCount() +2);


                                        //go back to welcome message
                                        chatEnd(response);


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
