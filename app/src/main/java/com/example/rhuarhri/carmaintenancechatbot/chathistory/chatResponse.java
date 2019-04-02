package com.example.rhuarhri.carmaintenancechatbot.chathistory;

public class chatResponse {

    private String message;
    private String from;

    private String emotion;
    private String image;
    private String imageDescription;

    public void addUserMessage (String Message)
    {
        message = Message;
        from = "user";

    }

    public void addBotMessage (String Message, String Emotion, String Image, String ImageDescription)
    {
        message = Message;
        from = "bot";

        if (!Emotion.equals("") || Emotion.equals(null))
        {
            emotion = Emotion;
        }
        else
        {
            emotion = "";
        }

        if (!Image.equals("") || Image.equals(null))
        {
            image = Image;
        }
        else
        {
            image = "";
        }

        if (!ImageDescription.equals("") || ImageDescription.equals(null))
        {
            imageDescription = ImageDescription;
        }
        else
        {
            imageDescription = "";
        }
    }

    //getters and setters
    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getImage() {
        return image;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
