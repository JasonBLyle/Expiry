package com.adeemm.expiry.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adeemm.expiry.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpirationDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expirations.db";
    private static final int VERSION = 3;

    public ExpirationDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class FoodTable {
        private static final String TABLE = "FOOD";
        private static final String COL_ID = "_id";
        private static final String NAME = "Food";
        private static final String CATEGORY = "category";
        private static final String YEAR = "year";
        private static final String MONTH = "month";
        private static final String DAY = "day";
        private static final String PICTURE = "picture";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + ExpirationDatabase.FoodTable.TABLE + " (" +
                ExpirationDatabase.FoodTable.COL_ID + " integer primary key autoincrement, " +
                ExpirationDatabase.FoodTable.NAME +  " text, " + ExpirationDatabase.FoodTable.CATEGORY + " text, "
                + ExpirationDatabase.FoodTable.PICTURE + " integer, " +ExpirationDatabase.FoodTable.YEAR +
                " integer, "+ ExpirationDatabase.FoodTable.MONTH + " integer, "+ ExpirationDatabase.FoodTable.DAY + " integer)";
        db.execSQL(createTableStatement);
    }

    public long addFood(){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE,60);
        Date temptest = c.getTime();
        int temp = R.drawable.food_apple;
        values.put(ExpirationDatabase.FoodTable.NAME,"Apple");
        values.put(ExpirationDatabase.FoodTable.PICTURE, temp);
        values.put(ExpirationDatabase.FoodTable.CATEGORY,"Fruit");
        values.put(ExpirationDatabase.FoodTable.YEAR,2021-1900);
        values.put(ExpirationDatabase.FoodTable.MONTH,10);
        values.put(ExpirationDatabase.FoodTable.DAY,1);
        return db.insert(ExpirationDatabase.FoodTable.TABLE,null,values);
    }
    public long addFood(Food newFood,int PictureID){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpirationDatabase.FoodTable.NAME,newFood.getName());
        values.put(ExpirationDatabase.FoodTable.PICTURE,PictureID);
        values.put(ExpirationDatabase.FoodTable.CATEGORY,newFood.getCategory());
        values.put(ExpirationDatabase.FoodTable.YEAR,newFood.getYear());
        values.put(ExpirationDatabase.FoodTable.MONTH,newFood.getMonth());
        values.put(ExpirationDatabase.FoodTable.DAY,newFood.getDay());
        return db.insert(ExpirationDatabase.FoodTable.TABLE,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("drop table if exists " + ExpirationDatabase.FoodTable.TABLE);
        onCreate(db);
    }
    public List<Food> getAll(){
        List<Food> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + ExpirationDatabase.FoodTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{
                int experiationID = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int pictureID = cursor.getInt(3);
                int year = cursor.getInt(4);
                int month = cursor.getInt(5);
                int day = cursor.getInt(6);
                Date tempDate = new Date(year,month,day);
                Food tempFood = new Food(name,category,tempDate);
                tempFood.setPictureID(pictureID);
                returnList.add(tempFood);
            }while(cursor.moveToNext());
        }
        else{
            //returns an empty list
        }

        return returnList;
    }
}