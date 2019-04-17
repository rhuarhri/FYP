package com.example.rhuarhri.carmaintenancechatbot.externalDatabase;

import java.io.Serializable;
import java.util.List;

public class baseMeaning implements Serializable {

    List<String> words;
    String meaning;

    public List<String> getWords() {
        return words;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void setMeaning(String sharedMeaning) {
        meaning = sharedMeaning;
    }
}
