package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatHistoryManager;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.chatResponse;
import com.example.rhuarhri.carmaintenancechatbot.chathistory.documentMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class unKnownResponseHandler {

    private Context context;
    private int userAttempts = 0;
    private chatHistoryManager historyManager;
    private documentMap documentHistory;
    private RecyclerView chatRV;
    private suggestionDisplay suggestionDis;
    private List<String> suggestedResponses;

    private String firstResponse =
            "Sorry I did not understand that. I have a few suggestions listed below. "+
                    "Do they match what you wanted to say. If not could you repeat the question?";

    private String secondResponse =
            "So, still don't understand. ";

    private String thirdResponse = "I was unable to fully understand the question. " +
            "So I am going restart this chat and hopefully a fresh start will fix this.";

    private String response = "";

    public unKnownResponseHandler(RecyclerView ChatRV, Context appContext, suggestionDisplay SuggestionDis)
    {
        context = appContext;
        chatRV = ChatRV;
        suggestionDis = SuggestionDis;
    }

    public void reset()
    {
        userAttempts = 0;
    }

    public boolean unKnownResponse(documentMap DocumentHistory, chatHistoryManager HistoryManager, List<String> SuggestedResponses)
    {
        //no response found

        boolean restartChat = false;
        documentHistory = DocumentHistory;
        historyManager = HistoryManager;
        suggestedResponses = SuggestedResponses;

        //Log.d("SIZE", ""+ documentHistory.getDocumentHistory().size());

        userAttempts++;

        if (userAttempts == 1)
        {

            displayResponse(firstResponse);
            suggestionDis.getNewSpinnerAdapter(suggestedResponses);
        }
        else if (userAttempts == 2)
        {
            getFallBackResponse();
            /*displayResponse(response);
            suggestionDis.getNewSpinnerAdapter(suggestedResponses);*/
        }
        else if (userAttempts >= 3)
        {
            documentHistory = new documentMap();

            userAttempts = 0;
            displayResponse(thirdResponse);
            restartChat = true;
        }

        return restartChat;

    }

    public chatHistoryManager returnChatHistory()
    {
        return historyManager;
    }

    public String returnResponse(){

        if (userAttempts != 0)
        {
            return response;
        }
        else
        {
            //no error found
            return "";
        }

    }


    private void getFallBackResponse()
    {

        //Log.d("SIZE", ""+ documentHistory.getDocumentHistory().size());


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            QueryCreator getDatabasePath = new QueryCreator(db);
            DocumentReference FallbackQuery = getDatabasePath.createFallbackQuery(documentHistory);

            if (FallbackQuery != null) {
                FallbackQuery.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String fallBack;
                            try {

                                fallBack = document.get("fallback").toString();
                            } catch (Exception e) {
                                fallBack = "Could you try again.";
                            }
                            secondResponse = secondResponse + fallBack;
                        } else {
                            secondResponse = secondResponse + "Could you try again.";
                        }

                        displayResponse(secondResponse);
                        suggestionDis.getNewSpinnerAdapter(suggestedResponses);

                    }
                });
            }
            else
            {
                secondResponse = secondResponse + "Could you try again.";

                displayResponse(secondResponse);
                suggestionDis.getNewSpinnerAdapter(suggestedResponses);
            }


    }

    private void displayResponse(String Response)
    {
        response = Response;

        historyManager.addBotResponse(response, "sad", "", "");

        List<chatResponse> chatHistory = historyManager.getHistory();

        chatRVAdapter chatDisplayAdapter = new chatRVAdapter(context, chatHistory);
        chatRV.setAdapter(chatDisplayAdapter);
    }

}
