package com.example.rhuarhri.carmaintenancechatbot.chathistory;

public class chatResponse {

    private String message;
    private String from;

    public void addMessage (String Message, String From)
    {
        message = Message;
        from = From;

    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
