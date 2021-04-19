package com.adeemm.expiry.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adeemm.expiry.Models.Food;
import com.adeemm.expiry.Models.PresetDatabase;
import com.adeemm.expiry.PresetAdapter;
import com.adeemm.expiry.R;

import java.util.List;

/**
 * This is the activity that displays all the preset items for user
 */
public class PresetActivity extends AppCompatActivity {

    List<Food> foods;
    RecyclerView presetView;

    /**
     * This function loads all the items into the recycler view for the user to pick from
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_list_view);

        presetView = findViewById(R.id.presetView);
        PresetDatabase presetDatabase = new PresetDatabase(PresetActivity.this);
        presetDatabase.checkEmpty();

        foods = presetDatabase.getAll();

        PresetAdapter pAdapter = new PresetAdapter(this, foods);

        presetView.setAdapter(pAdapter);
        presetView.setLayoutManager(new LinearLayoutManager(this));
    }

}
