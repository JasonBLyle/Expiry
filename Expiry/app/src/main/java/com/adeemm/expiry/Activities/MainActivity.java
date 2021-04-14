package com.adeemm.expiry.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.adeemm.expiry.AnimationHelper;
import com.adeemm.expiry.ListAdapter;
import com.adeemm.expiry.Models.ExpirationAPI;
import com.adeemm.expiry.Models.Food;
import com.adeemm.expiry.Models.ListItem;
import com.adeemm.expiry.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    private boolean fabToggled = false;

    private View overlay;

    private long scannedLong;
    private String scannedText;

    private View fab_cam_view;
    private View fab_mic_view;
    private View fab_manual_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expiry");

        FloatingActionButton mainButton = (FloatingActionButton) findViewById(R.id.fab_main_button);

        fab_cam_view = findViewById(R.id.scan_fab_layout);
        fab_mic_view = findViewById(R.id.mic_fab_layout);
        fab_manual_view = findViewById(R.id.manual_fab_layout);

        AnimationHelper.setInvisible(fab_cam_view);
        AnimationHelper.setInvisible(fab_mic_view);
        AnimationHelper.setInvisible(fab_manual_view);

        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.GONE);

        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFAB(mainButton);
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFAB(view);
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_cam_button)).setOnClickListener(new View.OnClickListener() {//Todo add the camera here
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Camera clicked", Toast.LENGTH_SHORT).show();
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_mic_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Mic clicked", Toast.LENGTH_SHORT).show();

                // *** TODO ***
                ExpirationAPI api = new ExpirationAPI(MainActivity.this::testAPI, MainActivity.this);
                //api.getSearchResults("apples");
                api.getExpiration(Uri.parse("https://stilltasty.com/fooditems/index/17993"));
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_manual_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManualEntrySelection.class);
                startActivity(intent);
            }
        });

        initListItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents() != null){
            Toast.makeText(getApplicationContext(),"Scanned: "+intentResult.getContents(),Toast.LENGTH_SHORT).show();
            scannedText=intentResult.getContents();
            scannedLong = Long.parseLong(scannedText);
        }
        else{
            Toast.makeText(getApplicationContext(),"nothing was scanned",Toast.LENGTH_SHORT).show();
        }
    }

    // *** TODO ***
    public void testAPI(Map<String, Uri> results) {
        for (Map.Entry<String,Uri> entry : results.entrySet()) {
            Log.e(entry.getKey(), entry.getValue().toString());
        }
    }

    private void toggleFAB(View view) {
        fabToggled = AnimationHelper.rotateFAB(view, !fabToggled);

        if (fabToggled) {
            AnimationHelper.animateAppear(fab_cam_view);
            AnimationHelper.animateAppear(fab_mic_view);
            AnimationHelper.animateAppear(fab_manual_view);
            overlay.setVisibility(View.VISIBLE);
        }

        else {
            AnimationHelper.animateDisappear(fab_cam_view);
            AnimationHelper.animateDisappear(fab_mic_view);
            AnimationHelper.animateDisappear(fab_manual_view);
            overlay.setVisibility(View.GONE);
        }
    }

    private void initListItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Food f1 = new Food("Apple", "Produce", new Date());
        Food f2 = new Food("Orange", "Produce", new Date());
        Food f3 = new Food("Grapes", "Produce", new Date());

        List<ListItem> items = new ArrayList<>();
        items.add(0, new ListItem("Section 1", true));
        items.add(1, new ListItem(f1));
        items.add(2, new ListItem(f2));
        items.add(3, new ListItem("Section 2", true));
        items.add(4, new ListItem(f3));

        ListAdapter adapter = new ListAdapter(this, items);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ListItem obj, int position) {
                // TODO: Handle click?
            }
        });
    }
}
