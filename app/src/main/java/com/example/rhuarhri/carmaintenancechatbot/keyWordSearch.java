package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class keyWordSearch {

    List ofCharacters = new ArrayList<String>();
    boolean charactersFound = false;
    List blackList = new ArrayList<String>();
    boolean blackListFound = false;
    List whiteList = new ArrayList<String>();
    boolean whiteListFound = false;

    //OneTimeWorkRequest whiteListSearch = new OneTimeWorkRequest.Builder(whiteListInterface.class).build();
    //OneTimeWorkRequest blackListSearch = new OneTimeWorkRequest.Builder(blackListInterface.class).build();
    //OneTimeWorkRequest characterSearch = new OneTimeWorkRequest.Builder(characterListInterface.class).build();

    FirebaseFirestore db;

    List<String> cleanResponse;
    List<String> importantWords;
    Boolean isQuestion = false;


    public void runSearch(String response)
    {
        db = FirebaseFirestore.getInstance();

        getWhiteList();

        getBlackList();

        getCharacterList();

        while (whiteListFound == false && blackListFound == false && whiteListFound == false)
        {
            //wait until work is done
        }

        cleanResponse = search(response);

        db = null;
    }

    List<String> getCleanResponse()
    {
        return cleanResponse;
    }

    List<String> getImportantWords()
    {
        return importantWords;
    }

    boolean getIsQuestion()
    {
        return isQuestion;
    }


    public List<String> search(String userResponse)
    {

        String inputString = userResponse.toLowerCase();

        List Result = new ArrayList<String>();

        if (isQuestion(inputString) == true)
        {
            //set as question
            isQuestion = true;
        }

        String[] responseArray = inputString.split("\\ ", -1);


        for (int i = 0; responseArray.length > i; i++) {

                String currentWord = removeUseless(responseArray[i]);

                if (blackList.contains(currentWord) == true) {

                } else {
                    Result.add(currentWord);
                    if (whiteList.contains(currentWord) == true) {
                        importantWords.add(currentWord);
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

    private void getWhiteList()
    {

        db.collection("white list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                whiteList.add(document.get("word").toString());
                            }
                            whiteListFound = true;

                        } else {
                            whiteListFound = true;
                        }
                    }
                });
    }

    private void getBlackList()
    {

        db.collection("black list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                blackList.add(document.get("word").toString());
                            }

                            blackListFound = true;

                        } else {
                            blackListFound = true;
                        }
                    }
                });

    }

    private void getCharacterList()
    {

        db.collection("characters").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ofCharacters.add(document.get("character").toString());
                                charactersFound = true;
                            }

                        } else {
                            charactersFound = true;
                        }
                    }
                });
    }
}


//passed code

/*
    public keyWordSearch(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    /*

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

        //WorkManager threadManager = WorkManager.getInstance();

        //threadManager.enqueue(whiteListSearch);

        //threadManager.enqueue(blackListSearch);

        //threadManager.enqueue(whiteListSearch);



        String[] resultArray =  (String[]) cleanResponse.toArray(new String[cleanResponse.size()]);

        Data output = new Data.Builder().putStringArray("result", resultArray).build();

        return Result.success(output);
    }*/

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
