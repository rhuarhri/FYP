package com.example.rhuarhri.carmaintenancechatbot.chathistory;

import java.util.ArrayList;
import java.util.List;

public class chatHistoryManager {

    chatResponse currentResponse;
    List<chatResponse> history = new ArrayList<>();

    public void addResponse(String message, String from)
    {
        currentResponse = new chatResponse();

        currentResponse.addMessage(message, from);

        history.add(currentResponse);

        while(history.size() >= 100) {
            history.remove(0);
        }


    }

    public List<chatResponse> getHistory()
    {

        return history;
    }


}
