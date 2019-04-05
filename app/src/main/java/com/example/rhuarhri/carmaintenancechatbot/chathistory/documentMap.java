package com.example.rhuarhri.carmaintenancechatbot.chathistory;

import java.util.ArrayList;
import java.util.List;

public class documentMap {

    /*
    In firestore documents store data and also other documents
    in this app each question will be in a document and all questions that relating to that
    question will be in a sub document.
    This class is designed to record the path linking the documents together.
    */
    private List<String> documentHistory;

    public documentMap()
    {
        documentHistory = new ArrayList<String>();
    }

    public void addDocument(String documentID)
    {
        documentHistory.add(documentID);
    }

    public List<String> getDocumentHistory() {
        return documentHistory;
    }

    public boolean mapEmpty()
    {
        if(documentHistory.size() >= 1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
