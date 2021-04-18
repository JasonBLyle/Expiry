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

public class PresetActivity extends AppCompatActivity {

    List<Food> foods;
    RecyclerView presetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_list_view);

        presetView = findViewById(R.id.presetView);
        PresetDatabase presetDatabase = new PresetDatabase(PresetActivity.this);

        foods = presetDatabase.getAll();

        PresetAdapter pAdapter = new PresetAdapter(this,foods);

        presetView.setAdapter(pAdapter);
        presetView.setLayoutManager(new LinearLayoutManager(this));
    }

}
