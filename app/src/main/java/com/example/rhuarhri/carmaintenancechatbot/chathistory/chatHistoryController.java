
package com.example.rhuarhri.carmaintenancechatbot.chathistory;


import android.arch.persistence.room.Room;
import android.content.Context;


import java.util.List;

/*
public class chatHistoryController {



    historyDBController dbController;

    public chatHistoryController(Context context)
    {
        dbController = Room.databaseBuilder(context, historyDBController.class, "messageHistoryDataBase").build();

    }

    public void addMessage(String message, String createdBy)
    {

        messageHistoryTable newData = new messageHistoryTable();

        newData.setMessage(message);
        newData.setSentBy(createdBy);

        dbController.dbInterface().addMessage(newData);

        manageHistory();

    }


    public List<messageHistoryTable> getHistory()
    {
        return dbController.dbInterface().getHistory();
    }

    public void newChatSession()
    {
        dbController.dbInterface().startNewChatSession();
    }

    private void manageHistory()
    {
        //after a hundred messages have been recorded the oldest message will be deleted
        //why as all can be resent by the app at any time so messages can be deleted

        if (dbController.dbInterface().getHistorySize() > 100)
        {
            dbController.dbInterface().deleteOldestValue();
        }
        else
        {

        }
    }



}
*/