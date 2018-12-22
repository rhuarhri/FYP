package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class keyWordSearch extends Worker {

    List ofCharacters = new ArrayList<String>();
    boolean charactersFound = false;
    List blackList = new ArrayList<String>();
    boolean blackListFound = false;
    List whiteList = new ArrayList<String>();
    boolean whiteListFound = false;

    OneTimeWorkRequest whiteListSearch = new OneTimeWorkRequest.Builder(whiteListInterface.class).build();
    OneTimeWorkRequest blackListSearch = new OneTimeWorkRequest.Builder(blackListInterface.class).build();
    OneTimeWorkRequest characterSearch = new OneTimeWorkRequest.Builder(characterListInterface.class).build();


    public keyWordSearch(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);



        WorkManager.getInstance().getWorkInfoByIdLiveData(whiteListSearch.getId())
                .observe((LifecycleOwner) context, info -> {
                    if (info != null && info.getState().isFinished()) {
                        String[] defaultData;
                        defaultData = info.getOutputData().getStringArray("result");
                        // ... do something with the result ...

                        whiteList = Arrays.asList(defaultData);
                        whiteListFound = true;


                    }
                });



        WorkManager.getInstance().getWorkInfoByIdLiveData(blackListSearch.getId())
                .observe((LifecycleOwner) context, info -> {
                    if (info != null && info.getState().isFinished()) {
                        String[] defaultData;
                        defaultData = info.getOutputData().getStringArray("result");
                        // ... do something with the result ...

                        blackList = Arrays.asList(defaultData);
                        blackListFound = true;


                    }
                });



        WorkManager.getInstance().getWorkInfoByIdLiveData(characterSearch.getId())
                .observe((LifecycleOwner) context, info -> {
                    if (info != null && info.getState().isFinished()) {
                        String[] defaultData;
                        defaultData = info.getOutputData().getStringArray("result");
                        // ... do something with the result ...

                        ofCharacters = Arrays.asList(defaultData);
                        charactersFound = true;


                    }
                });


    }

    @NonNull
    @Override
    public Result doWork() {

        WorkManager threadManager = WorkManager.getInstance();

        threadManager.enqueue(whiteListSearch);

        threadManager.enqueue(blackListSearch);

        threadManager.enqueue(whiteListSearch);

        while (whiteListFound == false && blackListFound == false && whiteListFound == false)
        {
            //wait until work is done
        }

        List<String> cleanResponse = search("");

        String[] resultArray =  (String[]) cleanResponse.toArray(new String[cleanResponse.size()]);

        Data output = new Data.Builder().putStringArray("result", resultArray).build();

        return Result.success(output);
    }



    /*
    keyWordSearch()
    {
        ofCharacters.add("?");
        ofCharacters.add("!");
        ofCharacters.add("*");

        blackList.add("a");
        blackList.add("is");
        blackList.add("the");

        whiteList.add("gear");
    }*/




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
