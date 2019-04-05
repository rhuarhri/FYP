package com.example.rhuarhri.carmaintenancechatbot;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class suggestionDisplay {

    private Spinner suggestionSP;
    private Context context;
    private String selectedResponse;
    private EditText questionET;

    public suggestionDisplay(Context appContext, Spinner SuggestionSP, EditText QuestionET)
    {
        context = appContext;
        suggestionSP = SuggestionSP;
        questionET = QuestionET;
    }


    public void getNewSpinnerAdapter(List<String> suggestions)
    {
        if (!suggestions.isEmpty()) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, suggestions);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            suggestionSP.setAdapter(adapter);

            suggestionSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    selectedResponse = suggestions.get(i);

                    if (questionET != null) {
                        questionET.setText(selectedResponse);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    public String getSelectedResponse()
    {
        return selectedResponse;
    }
}
