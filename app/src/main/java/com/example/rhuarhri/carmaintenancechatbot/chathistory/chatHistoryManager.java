package com.example.rhuarhri.carmaintenancechatbot.chathistory;

import java.util.ArrayList;
import java.util.List;

public class chatHistoryManager {

    chatResponse currentResponse;
    List<chatResponse> history = new ArrayList<>();

    public void addBotResponse(String message, String emotion, String image, String imageDescription)
    {
        currentResponse = new chatResponse();
        currentResponse.addBotMessage(message, emotion, image, imageDescription);
        checkHistorySize();
        history.add(currentResponse);

    }

    public void addUserResponse(String message)
    {
        currentResponse = new chatResponse();
        currentResponse.addUserMessage(message);
        checkHistorySize();
        history.add(currentResponse);
    }

    private void checkHistorySize()
    {
        while(history.size() >= 50) {
            history.remove(0);
        }
    }

    public List<chatResponse> getHistory()
    {

        return history;
    }


}
