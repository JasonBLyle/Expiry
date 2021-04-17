package com.adeemm.expiry.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.adeemm.expiry.AnimationHelper;
import com.adeemm.expiry.ListAdapter;
import com.adeemm.expiry.Models.BarcodeAPI;
import com.adeemm.expiry.Models.ExpirationDatabase;
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
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private boolean fabToggled = false;

    private View overlay;

    private View fab_cam_view;
    private View fab_mic_view;
    private View fab_manual_view;

    private BarcodeAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Expiry");
        setSupportActionBar(toolbar);

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

        ((FloatingActionButton) findViewById(R.id.fab_cam_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("For flash use the volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_mic_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                intent.putExtra("MIC_FAB", true);
                startActivity(intent);
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_manual_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ExpirationDatabase dataBaseHelper = new ExpirationDatabase(MainActivity.this);
                //Food temp = new Food("Apple", "Produce", new Date());
                //dataBaseHelper.addFood();
                Intent intent = new Intent(view.getContext(), ManualEntrySelection.class);
                startActivity(intent);
            }
        });

        api = new BarcodeAPI(this);
        initListItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult.getContents() != null) {
            String scannedText = intentResult.getContents();

            Uri uri = api.getURL(scannedText);
            api.getData(uri, this::productBarcodeHandler);
        }
        else {
            Toast.makeText(getApplicationContext(),"Nothing was scanned", Toast.LENGTH_SHORT).show();
        }
    }

    public void productBarcodeHandler(JSONObject response) {
        try {
            JSONObject productInfo = response.getJSONObject("product");
            JSONArray keywords = productInfo.getJSONArray("_keywords");

            String name = productInfo.getString("product_name");
            int resID = R.drawable.food_misc;

            for (int i = 0; i < keywords.length(); i++) {
                String keyword = keywords.getString(i);

                if (keyword.contains(" ")) {
                    keyword = keyword.replace(" ", "_");
                }

                int resource = this.getResources().getIdentifier("food_" + keyword, "drawable", this.getPackageName());
                if (resource != 0) {
                    resID = resource;
                    break;
                }
            }

            Intent intent = new Intent(this, ItemEntry.class);
            intent.putExtra("FOOD_NAME", name);
            intent.putExtra("FOOD_PIC", resID);
            startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"ERROR finding product!", Toast.LENGTH_LONG).show();
            Log.e("UPC Scan Error:", e.getMessage());
            e.printStackTrace();
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

        ExpirationDatabase expirationDatabase = new ExpirationDatabase(MainActivity.this);
        List<Food> foods = expirationDatabase.getAll();

//        Date temp = new Date(21+100,4,12);
//        Food f1 = new Food("Apple",  new Date());
//        Food f2 = new Food("Orange", new Date());
//        Food f3 = new Food("Grapes", new Date());
//        Food f4 = new Food("Grapes",  temp);
//
        List<ListItem> items = new ArrayList<>();
//        items.add(0, new ListItem("Section 1", true));
//        items.add(1, new ListItem(f1));
//        items.add(2, new ListItem(f2));
//        items.add(3, new ListItem("Section 2", true));
//        items.add(4, new ListItem(f3));
//        items.add(5, new ListItem(f4));
//
//        for(int i = 0; i < foods.size(); i++) {
//            items.add(i + 6, new ListItem(foods.get(i)));
//        }

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
