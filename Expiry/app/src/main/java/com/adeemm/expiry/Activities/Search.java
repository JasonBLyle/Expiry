package com.adeemm.expiry.Activities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import com.adeemm.expiry.Models.ExpirationAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adeemm.expiry.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * This is the activity that allows the user to search for an item by name
 */
public class Search extends AppCompatActivity {

    private ProgressBar progressBar;
    private FloatingActionButton searchButton;
    private EditText searchText;
    private ImageButton micButton;

    private SpeechRecognizer speechRecognizer;
    private ExpirationAPI api;

    /**
     * This function loads the view for the user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);

        searchText = findViewById(R.id.searchTextBox);
        progressBar = findViewById(R.id.searchProgressBar);
        searchButton = findViewById(R.id.searchFabButton);
        micButton = findViewById(R.id.searchMicButton);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        api = new ExpirationAPI(this);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    onClickSearch(v);
                    return true;
                }
                return false;
            }
        });

        if (getIntent().getBooleanExtra("MIC_FAB", false)) {
            micButton.performClick();
        }
    }

    /**
     * This function launches the api call based on the user's entry
     */
    public void onClickSearch(View view) {
        if (!searchText.getText().toString().trim().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            searchButton.setAlpha(0f);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    api.getSearchResults(searchText.getText().toString(), Search.this::handleSearchResponse);
                }
            });
        }
    }

    /**
     * This is the function that calls the speech recognition api to let the user speak their search request to the phone
     */
    public void onClickMic(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO }, 7);
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                micButton.setColorFilter(Color.argb(255, 255, 0, 0));
                searchText.setText("");
                searchText.setHint("Listening...");
            }

            @Override
            public void onResults(Bundle bundle) {
                micButton.setColorFilter(Color.argb(255, 102, 102, 102));
                searchText.setText(bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
                searchText.setHint("Search for food");
                speechRecognizer.stopListening();
            }

            @Override
            public void onEndOfSpeech() {
                micButton.setColorFilter(Color.argb(255, 102, 102, 102));
                searchText.setHint("Search for food");
                speechRecognizer.stopListening();
            }

            @Override
            public void onError(int error) {
                micButton.setColorFilter(Color.argb(255, 102, 102, 102));
                searchText.setHint("Search for food");
                speechRecognizer.stopListening();
            }

            @Override
            public void onBeginningOfSpeech() { }

            @Override
            public void onRmsChanged(float rmsdB) { }

            @Override
            public void onBufferReceived(byte[] buffer) { }

            @Override
            public void onPartialResults(Bundle partialResults) { }

            @Override
            public void onEvent(int eventType, Bundle params) { }
        });

        speechRecognizer.startListening(intent);
    }

    /**
     * This is the function that handles the result from the search
     */
    public void handleSearchResponse(Map<String, Uri> results) {
        progressBar.setVisibility(View.GONE);
        searchButton.setAlpha(1f);

        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra("SEARCH_RESULTS", (HashMap<String, Uri>)results);
        startActivity(intent);
    }

    /**
     * This function hides the keyboard
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Override the deconstructer to destroy the recognizer object
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }
}