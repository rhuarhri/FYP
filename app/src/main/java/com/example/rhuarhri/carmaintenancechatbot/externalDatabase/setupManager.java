package com.example.rhuarhri.carmaintenancechatbot.externalDatabase;

import android.content.Context;
import android.content.Intent;

import com.example.rhuarhri.carmaintenancechatbot.MainActivity;

public class setupManager {

    private String blackListKey = "blackList";
    private String characterListKey = "characterList";
    private String whiteListKey = "whiteList";
    private String wordMeaningListKey = "wordMeaningList";

    private Context context;

    private blackListManager blackListM = new blackListManager();
    private characterListManager characterListM = new characterListManager();
    private whiteListManager whiteListM = new whiteListManager();
    private wordMeaningListManager WMLManager = new wordMeaningListManager();

    public setupManager(Context appContext)
    {
        context = appContext;
    }

    public void setup()
    {
        blackListM.setUp();
        characterListM.setup();
        whiteListM.setup();
        WMLManager.setup();
    }

    public Intent getMainScreenIntent ()
    {
        Intent goToMainScreen = new Intent(context, MainActivity.class);
        goToMainScreen.putExtra(blackListKey, blackListM);
        goToMainScreen.putExtra(characterListKey, characterListM);
        goToMainScreen.putExtra(whiteListKey, whiteListM);
        goToMainScreen.putExtra(wordMeaningListKey, WMLManager);
        return goToMainScreen;
    }

    public void getObjectsFromIntent(Intent sentData)
    {
        //This is used in the main screen to easily get data from the intent

        blackListM = (blackListManager) sentData.getSerializableExtra(blackListKey);
        characterListM = (characterListManager) sentData.getSerializableExtra(characterListKey);
        whiteListM = (whiteListManager) sentData.getSerializableExtra(whiteListKey);
        WMLManager = (wordMeaningListManager) sentData.getSerializableExtra(wordMeaningListKey);
    }

    public blackListManager getBlackListManager()
    {
        return blackListM;
    }

    public characterListManager getCharacterListMananager()
    {
        return characterListM;
    }

    public whiteListManager getWhiteListManager()
    {
        return whiteListM;
    }

    public wordMeaningListManager getWordMeaningListManager()
    {
        return WMLManager;
    }
}
