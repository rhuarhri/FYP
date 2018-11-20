package com.example.rhuarhri.carmaintenancechatbot;

import java.util.ArrayList;
import java.util.List;

public class keyWordSearch {

    List ofCharacters = new ArrayList<String>();
    List blackList = new ArrayList<String>();
    List whiteList = new ArrayList<String>();

    keyWordSearch()
    {
        ofCharacters.add("?");
        ofCharacters.add("!");
        ofCharacters.add("*");

        blackList.add("a");
        blackList.add("is");
        blackList.add("the");

        whiteList.add("gear");
    }




    public List<String> search(String userResponse)
    {
        List Result = new ArrayList<String>();

        if (isQuestion(userResponse) == true)
        {
            //set as question
        }

        String[] responseArray = userResponse.split("\\ ", -1);


        for (int i = 0; responseArray.length > i; i++) {

                String currentWord = removeUseless(responseArray[i]);

                if (blackList.contains(currentWord) == true) {

                } else {
                    Result.add(currentWord);
                    if (whiteList.contains(currentWord) == true) {
                        //special words
                    }
                }
            }




        return Result;
    }


    private String removeUseless(String currentWord)
    {

        String cleanString;
        String dirtyString = currentWord;

        for (int i = 0; ofCharacters.size() > i; i++)
        {
            dirtyString = dirtyString.replace(""+ofCharacters.get(i), "");
        }

        cleanString = dirtyString;

        return cleanString;
    }

    private boolean isQuestion(String userResponse)
    {
        return userResponse.endsWith("?");
    }

    private void inBlackList(String currentWord)
    {


    }
}
