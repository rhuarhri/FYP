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

        history.add(currentResponse);

    }

    public void addUserResponse(String message)
    {
        currentResponse = new chatResponse();
        currentResponse.addUserMessage(message);
        checkHistorySize();
    }

    private void checkHistorySize()
    {
        while(history.size() >= 100) {
            history.remove(0);
        }
    }

    public List<chatResponse> getHistory()
    {

        return history;
    }


}
