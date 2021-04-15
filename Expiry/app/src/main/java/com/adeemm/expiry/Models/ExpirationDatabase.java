package com.adeemm.expiry.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class ExpirationDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expirations.db";
    private static final int VERSION = 2;

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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + ExpirationDatabase.FoodTable.TABLE + " (" +
                ExpirationDatabase.FoodTable.COL_ID + " integer primary key autoincrement, " +
                ExpirationDatabase.FoodTable.NAME +  " text, " + ExpirationDatabase.FoodTable.CATEGORY + " text, "+
                ExpirationDatabase.FoodTable.YEAR + " integer, "+ ExpirationDatabase.FoodTable.MONTH + " integer, "+
                ExpirationDatabase.FoodTable.DAY + " integer)";
        db.execSQL(createTableStatement);
    }

    public long addFood(){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        Date today = new Date();
        values.put(ExpirationDatabase.FoodTable.NAME,"Apple");
        values.put(ExpirationDatabase.FoodTable.CATEGORY,"Fruit");
        values.put(ExpirationDatabase.FoodTable.YEAR,2021);
        values.put(ExpirationDatabase.FoodTable.MONTH,10);
        values.put(ExpirationDatabase.FoodTable.DAY,1);
        return db.insert(ExpirationDatabase.FoodTable.TABLE,null,values);
    }
    public long addFood(Food newFood){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        Date today = new Date();
        values.put(ExpirationDatabase.FoodTable.NAME,newFood.getName());
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
}