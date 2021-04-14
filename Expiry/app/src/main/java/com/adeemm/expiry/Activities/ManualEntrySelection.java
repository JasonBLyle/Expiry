package com.adeemm.expiry.Activities;


import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.adeemm.expiry.Fragments.ManualEntrySelectionFragment;
import com.adeemm.expiry.R;


public class ManualEntrySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry_selection);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ManualEntrySelectionFragment newFragment = new ManualEntrySelectionFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setCallbackResult(new ManualEntrySelectionFragment.CallbackResult() {
            @Override
            public void sendResult(int requestCode) {
                Toast.makeText(getApplicationContext(), "Menu clicked: " + String.valueOf(requestCode), Toast.LENGTH_SHORT).show();
            }
        });
    }
}