package com.adeemm.expiry.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adeemm.expiry.ListAdapter;
import com.adeemm.expiry.Models.ExpirationAPI;
import com.adeemm.expiry.Models.ListItem;
import com.adeemm.expiry.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class SearchResults extends AppCompatActivity {

    private String clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // TODO: fix and add includes
        //Toolbar toolbar = findViewById(R.id.toolbar3);
        //toolbar.setTitle("Search Results");
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpirationAPI api = new ExpirationAPI(this);

        Intent intent = getIntent();
        Map<String, Uri> passedResults = (Map<String, Uri>)intent.getSerializableExtra("SEARCH_RESULTS");

        ArrayList<String> searchResults = new ArrayList<>();
        for (Map.Entry<String,Uri> entry : passedResults.entrySet()) {
            searchResults.add(entry.getKey());
        }

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);

        ListView listView = findViewById(R.id.searchResultsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedItem = searchResults.get(position);
                Uri uri = passedResults.get(clickedItem);
                api.getExpiration(uri, SearchResults.this::apiCallback);
            }
        });
    }

    public void apiCallback(String expiration) {
        Calendar cal = null;

        if (!expiration.contains("after")) {
            cal = Calendar.getInstance();
            int amount = Character.getNumericValue(expiration.charAt(0));

            if (expiration.contains("day")) {
                cal.add(Calendar.DAY_OF_MONTH, amount);
            }
            else if (expiration.contains("week")) {
                cal.add(Calendar.WEEK_OF_MONTH, amount);
            }
            else if (expiration.contains("month")) {
                cal.add(Calendar.MONTH, amount);
            }
            else if (expiration.contains("year")) {
                cal.add(Calendar.YEAR, amount);
            }
        }

        int resID = 0;

        for (String word : clickedItem.split("\\W+") ) {
            int resource = this.getResources().getIdentifier("food_" + word.toLowerCase(), "drawable", this.getPackageName());

            if (resource != 0) {
                resID = resource;
                clickedItem = word;
                break;
            }
        }

        Intent intent = new Intent(this, ItemEntry.class);
        intent.putExtra("FOOD_NAME", clickedItem);

        if (cal != null)
            intent.putExtra("FOOD_EXP", cal.getTimeInMillis());

        if (resID != 0)
            intent.putExtra("FOOD_PIC", resID);

        startActivity(intent);
    }
}