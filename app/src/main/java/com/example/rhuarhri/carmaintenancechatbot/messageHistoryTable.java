package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class messageHistoryTable {

    @PrimaryKey(autoGenerate = true)
    int messageId;

    @ColumnInfo(name = "from")
    String sentBy;

    @ColumnInfo(name = "message")
    String message;

    //TODO add image and audio names in the future


    public int getMessageId() {
        return messageId;
    }

    public String getSentBy() {
        return sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
