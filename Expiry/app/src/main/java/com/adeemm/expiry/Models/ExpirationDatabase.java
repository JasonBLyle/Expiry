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

    public void addFood (Food newFood) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpirationDatabase.FoodTable.NAME,newFood.getName());
        values.put(ExpirationDatabase.FoodTable.PICTURE,newFood.getPictureID());
        values.put(ExpirationDatabase.FoodTable.CATEGORY,newFood.getCategory());
        values.put(ExpirationDatabase.FoodTable.YEAR,newFood.getYear());
        values.put(ExpirationDatabase.FoodTable.MONTH,newFood.getMonth());
        values.put(ExpirationDatabase.FoodTable.DAY,newFood.getDay());

        db.insert(ExpirationDatabase.FoodTable.TABLE,null,values);
        db.close();
    }

    public void removeFood (Food newFood) {
        SQLiteDatabase db = getWritableDatabase();

        String name = newFood.getName();
        int year = newFood.getYear();
        int month = newFood.getMonth();
        int day = newFood.getDay();

        String table_name = FoodTable.NAME;
        String table_year = FoodTable.YEAR;
        String table_month = FoodTable.MONTH;
        String table_day = FoodTable.DAY;

        String whereClause = String.format("%s = '%s' AND %s = %d AND %s = %d AND %s = %d", table_name, name, table_year, year, table_month, month, table_day, day);

        db.delete(ExpirationDatabase.FoodTable.TABLE, whereClause, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ExpirationDatabase.FoodTable.TABLE);
        onCreate(db);
    }

    public List<Food> getAll() {
        List<Food> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + ExpirationDatabase.FoodTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()) {
            do {
                int experiationID = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int pictureID = cursor.getInt(3);
                int year = cursor.getInt(4);
                int month = cursor.getInt(5);
                int day = cursor.getInt(6);

                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                Food tempFood = new Food(name, cal.getTime());
                tempFood.setPictureID(pictureID);
                returnList.add(tempFood);
            } while(cursor.moveToNext());
        }

        db.close();
        return returnList;
    }
}