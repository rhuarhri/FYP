package com.example.rhuarhri.carmaintenancechatbot;

import android.util.Log;

import com.example.rhuarhri.carmaintenancechatbot.chathistory.documentMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class QueryCreator {

    private FirebaseFirestore db;

    private List<String> cleanResponse;
    private documentMap documentHistory;

    public QueryCreator(FirebaseFirestore databaseInstance)
    {
        db = databaseInstance;
    }

    public Query create(List<String> CleanResponse, documentMap DocumentHistory)
    {

        cleanResponse = CleanResponse;
        documentHistory = DocumentHistory;

        DocumentReference databasePath = pathThroughDatabase(documentHistory);

        Query returnedQuery = null;

        if (documentHistory.getDocumentHistory().size() > 0)
        {
            Log.d("SIZE", "Not empty");
            returnedQuery = databasePath.collection("questions").whereEqualTo("input." + cleanResponse.get(0), "");
        }
        else
        {
            Log.d("SIZE", "empty");
            returnedQuery = db.collection("questions").whereEqualTo("input." + cleanResponse.get(0), "");
        }

        Query tempQuery = returnedQuery;
        Query questionSearch2 = returnedQuery;

        for (int i = 1; i < cleanResponse.size(); i++)
        {
            questionSearch2 = tempQuery.whereEqualTo("input." + cleanResponse.get(i), "");
            tempQuery = questionSearch2;
        }

        return questionSearch2;
    }

    private DocumentReference pathThroughDatabase(documentMap DocumentHistory)
    {
        documentHistory = DocumentHistory;

        DocumentReference tempColl = null;
        DocumentReference tempColl2 = null;

        List<String> docList = documentHistory.getDocumentHistory();


        if (docList.size() > 0) {
            tempColl = db.collection("questions").document(docList.get(0));


            for (int i = 1; i < docList.size(); i++) {
                tempColl2 = tempColl.collection("questions").document(docList.get(i).toString());
                tempColl = tempColl2;
            }

        }
        else
        {

        }

        return tempColl;
    }

    public DocumentReference createFallbackQuery(documentMap DocumentHistory)
    {
        documentHistory = DocumentHistory;

        DocumentReference databasePath = pathThroughDatabase(documentHistory);

        DocumentReference returnedDoc = databasePath;

        return returnedDoc;

    }


}
