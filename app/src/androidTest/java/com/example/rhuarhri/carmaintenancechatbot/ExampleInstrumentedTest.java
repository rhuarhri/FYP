package com.example.rhuarhri.carmaintenancechatbot;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import static org.junit.Assert.*;

/**
 * Instrumented Test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /*
    List importantWords  = new ArrayList<String>();

    StringBuilder fields = new StringBuilder("");*/

    boolean resultsFound;


    @Test
    public void useAppContext() {
        // Context of the app under Test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.rhuarhri.carmaintenancechatbot", appContext.getPackageName());
    }



    @Test
    public void whiteListTest()
    {
        resultsFound = false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List importantWords  = new ArrayList<String>();

        db.collection("white list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                importantWords.add(document.get("word").toString());
                            }
                            resultsFound = true;

                        } else {

                        }
                    }
                });

        while (resultsFound == false)
        {

        }

        assertEquals(false, importantWords.isEmpty());

    }

    @Test
    public void blackListTest()
    {
        resultsFound = false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List importantWords  = new ArrayList<String>();

        db.collection("black list").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                importantWords.add(document.get("word").toString());
                            }
                            resultsFound = true;

                        } else {

                        }
                    }
                });

        while (resultsFound == false)
        {

        }

        assertEquals(false, importantWords.isEmpty());

    }

    @Test
    public void characterListTest()
    {
        resultsFound = false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List importantWords  = new ArrayList<String>();

        db.collection("characters").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                importantWords.add(document.get("character").toString());
                            }
                            resultsFound = true;

                        } else {

                        }
                    }
                });

        while (resultsFound == false)
        {

        }

        assertEquals(false, importantWords.isEmpty());

    }

    @Test
    public void recordMileageTest()
    {

        /*
        Context appContext = InstrumentationRegistry.getTargetContext();

        carMileageController mileageController = new carMileageController(appContext);

        mileageController.addMileage(100);

        double recordedResult = mileageController.getMileage();

        assertEquals(100, recordedResult);

        mileageController.addMileage(200);

        recordedResult = mileageController.getMileage();

        assertEquals(200, recordedResult);

        boolean updateRequired = mileageController.doesMileageNeedUpdating();

        assertEquals(false, updateRequired);
        */

    }

}
