package com.adeemm.expiry.Activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adeemm.expiry.Models.ExpirationDatabase;
import com.adeemm.expiry.Models.Food;
import com.adeemm.expiry.R;
import com.adeemm.expiry.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Item entry is the class that allows the user to add items to the database
 */
public class ItemEntry extends AppCompatActivity {

    private boolean submitted = false;

    private Food f;
    private ExpirationDatabase database;

    private String[] foodIcons;
    private ArrayList<String> iconList;

    private ImageView foodFormImageView;
    private TextView foodFormName;
    private TextView foodFormExpiration;
    private Date selectedExpirationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_entry);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Item");
        setSupportActionBar(toolbar);

        // loads all the view items
        foodFormImageView = findViewById(R.id.foodImageView);
        foodFormName = findViewById(R.id.foodNameTextView);
        foodFormExpiration = findViewById(R.id.expirationDateTextView);

        foodFormImageView.setTag(R.drawable.food_misc);

        setupEventListeners(foodFormExpiration);

        database = new ExpirationDatabase(this);

        // Check if auto-fill flow
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String productName = intent.getStringExtra("FOOD_NAME");
            int imageID = intent.getIntExtra("FOOD_PIC", 0);
            long expirationDateTime = intent.getLongExtra("FOOD_EXP", -1);
            if (!productName.equals("")) {
                foodFormName.setText(productName);
            }
            if (imageID != 0) {
                foodFormImageView.setImageResource(imageID);
                foodFormImageView.setTag(imageID);
            }
            if (expirationDateTime != -1) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(expirationDateTime);
                selectedExpirationDate = cal.getTime();
                foodFormExpiration.setText(Utils.getFormattedDate(expirationDateTime));
            }
        }

        // Populate food icon choices
        Field[] drawablesFields = com.adeemm.expiry.R.drawable.class.getFields();
        iconList = new ArrayList<>();
        for (Field field : drawablesFields) {
            try {
                if (field.getName().contains("food_")) {
                    iconList.add(field.getName().replace("food_", ""));
                }
            }
            catch (Exception ignored) {}
        }
        foodIcons = iconList.toArray(new String[0]);
    }

    public void onSubmitClick(View view) {
        if (!foodFormName.getText().toString().trim().equals("") && !foodFormExpiration.getText().toString().trim().equals("")) {
            if (!submitted) {
                submitted = true;
                ((ProgressBar)findViewById(R.id.searchProgressBar)).setVisibility(View.VISIBLE);
                ((View)findViewById(R.id.item_entry_form)).setVisibility(View.GONE);

                f = new Food(foodFormName.getText().toString(), selectedExpirationDate);
                f.setPictureID((int)foodFormImageView.getTag());

                database.addFood(f);
                f = null;

                // Return to home screen
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Please enter all required fields!", Toast.LENGTH_LONG).show();
        }
    }

    public void launchDatePicker(View view) {
        hideKeyboard();
        Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        long time = calendar.getTimeInMillis();
                        selectedExpirationDate = calendar.getTime();
                        foodFormExpiration.setText(Utils.getFormattedDate(time));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );

        datePicker.setThemeDark(false);
        datePicker.setAccentColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "DatePicker");
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupEventListeners(TextView textview) {
        textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    launchDatePicker(view);
                }
            }
        });
    }

    public void showImagePicker(View view) {
        String currDrawableName = getResources().getResourceEntryName((int)foodFormImageView.getTag());

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(foodIcons, iconList.indexOf(currDrawableName.replace("food_", "")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                int drawableID = getResources().getIdentifier("food_" + foodIcons[which], "drawable", getPackageName());
                foodFormImageView.setImageDrawable(ContextCompat.getDrawable(ItemEntry.this, drawableID));
                foodFormImageView.setTag(drawableID);
            }
        });
        builder.show();
    }
}