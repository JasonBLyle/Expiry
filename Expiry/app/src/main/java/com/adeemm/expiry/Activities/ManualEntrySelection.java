package com.adeemm.expiry.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.adeemm.expiry.Fragments.ManualEntrySelectionFragment;
import com.adeemm.expiry.R;

/**
 * This is the activity that select the way the user wants to add an item to the database
 */

public class ManualEntrySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry_selection);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ManualEntrySelectionFragment newFragment = new ManualEntrySelectionFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

        newFragment.setCallbackResult(new ManualEntrySelectionFragment.CallbackResult() {
            @Override
            public void sendResult(int requestCode) {
                Class<?> cls;

                switch (requestCode) {
                    case 0:
                        cls = Search.class;
                        break;
                    case 1:
                        cls = PresetActivity.class;
                        break;
                    case 2:
                        cls = ItemEntry.class;
                        break;
                    default:
                        cls = MainActivity.class;
                        break;
                }

                try {
                    Intent intent = new Intent(getApplicationContext(), cls);
                    startActivity(intent);
                }
                catch (Exception ignored) { }
            }
        });
    }
}