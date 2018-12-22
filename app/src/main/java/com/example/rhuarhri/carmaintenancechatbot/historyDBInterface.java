package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface historyDBInterface {


    @Query("SELECT * FROM messageHistoryTable")
    List<messageHistoryTable> getHistory();

    @Query("DELETE FROM messageHistoryTable WHERE MIN(messageId)")
    void deleteOldestValue();

    @Query("SELECT COUNT(*) FROM messageHistoryTable")
    int getHistorySize();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMessage(messageHistoryTable newData);


}
