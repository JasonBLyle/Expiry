package com.adeemm.expiry.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.adeemm.expiry.Models.ExpirationDatabase;
import com.adeemm.expiry.Models.Food;
import com.adeemm.expiry.R;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    EditText nameET;
    EditText experationDateET;
    List<Food> foods;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        nameET = findViewById(R.id.nameET);
        imageView = findViewById(R.id.AddImageView);
        experationDateET = findViewById(R.id.dateET);
        ExpirationDatabase dataBaseHelper = new ExpirationDatabase(AddItemActivity.this);
        foods = dataBaseHelper.getAll();
        imageView.setImageResource(foods.get(0).getPictureID());
    }


    public void confirmBT(View view){
        if(validateFood()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }


    public boolean validateFood(){
        return true;
    }
}
