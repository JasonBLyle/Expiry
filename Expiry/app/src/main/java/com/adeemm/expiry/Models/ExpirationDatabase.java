package com.adeemm.expiry.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adeemm.expiry.R;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        private static final String REMAINING_DAYS = "r_days";
        private static final String FREEZE_MULTIPIER = "f_mult";
        private static final String FROZEN = "frozen";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + ExpirationDatabase.FoodTable.TABLE + " (" +
                ExpirationDatabase.FoodTable.COL_ID + " integer primary key autoincrement, " +
                ExpirationDatabase.FoodTable.NAME +  " text, " + ExpirationDatabase.FoodTable.CATEGORY + " text, "
                + ExpirationDatabase.FoodTable.PICTURE + " integer, " +ExpirationDatabase.FoodTable.YEAR +
                " integer, "+ ExpirationDatabase.FoodTable.MONTH + " integer, " + ExpirationDatabase.FoodTable.DAY + " integer, "
                + ExpirationDatabase.FoodTable.REMAINING_DAYS + " integer, " + ExpirationDatabase.FoodTable.FREEZE_MULTIPIER + " integer, "
                + ExpirationDatabase.FoodTable.FROZEN + " integer)";
        db.execSQL(createTableStatement);
    }

    public void addFood(Food newFood) {
        SQLiteDatabase db = getWritableDatabase();

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);

        long diffInMillies = Math.abs(today.getTime() - newFood.getDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int temp = (int)diff + 1;

        ContentValues values = new ContentValues();
        values.put(ExpirationDatabase.FoodTable.NAME,newFood.getName());
        values.put(ExpirationDatabase.FoodTable.PICTURE,newFood.getPictureID());
        values.put(ExpirationDatabase.FoodTable.CATEGORY,newFood.getCategory());
        values.put(ExpirationDatabase.FoodTable.YEAR,c.get(Calendar.YEAR)-1900);
        values.put(ExpirationDatabase.FoodTable.MONTH,c.get(Calendar.MONTH));
        values.put(ExpirationDatabase.FoodTable.DAY,c.get(Calendar.DAY_OF_MONTH));
        values.put(ExpirationDatabase.FoodTable.REMAINING_DAYS,temp);
        values.put(ExpirationDatabase.FoodTable.FREEZE_MULTIPIER,2);
        values.put(ExpirationDatabase.FoodTable.FROZEN,0);

        db.insert(ExpirationDatabase.FoodTable.TABLE,null,values);
        db.close();
    }

    public void removeFood (Food newFood) {
        SQLiteDatabase db = getWritableDatabase();

        String name = newFood.getName();
        int year = newFood.getYear() -1900;
        int month = newFood.getMonth();
        int day = newFood.getDay()-newFood.getrDays();

        String table_name = FoodTable.NAME;
        String table_year = FoodTable.YEAR;
        String table_month = FoodTable.MONTH;
        String table_day = FoodTable.DAY;

        String whereClause = String.format("%s = '%s' AND %s = %d AND %s = %d AND %s = %d", table_name, name, table_year, year, table_month, month, table_day, day);

        db.delete(ExpirationDatabase.FoodTable.TABLE, whereClause, null);
        db.close();
    }

    public Food freezeFood(Food food){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FOOD where Food = ?",new String[]{food.getName()});

        if(cursor.moveToFirst()){
            do{
                int experiationID = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int pictureID = cursor.getInt(3);
                int year = cursor.getInt(4);
                int month = cursor.getInt(5);
                int day = cursor.getInt(6);
                int rDays = cursor.getInt(7);
                int freez_M = cursor.getInt(8);
                int frozen = cursor.getInt(9);
                boolean tempbool;
                if(frozen != 0){
                    tempbool=true;
                }
                else {
                    tempbool=false;
                }
                if( food.isFrozen()==tempbool){
                    Date today = new Date(year,month,day);
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);

                    if(!food.isFrozen()){
                        frozen =1;
                        food.setFrozen(true);
                        c.add(Calendar.DATE,rDays*freez_M);
                        food.setExpiration(c.getTime());
                        ContentValues cv = new ContentValues();
                        cv.put(ExpirationDatabase.FoodTable.FROZEN,1);
                        cv.put(ExpirationDatabase.FoodTable.REMAINING_DAYS,rDays*freez_M);
                        cv.put(ExpirationDatabase.FoodTable.FROZEN,frozen);
                        food.setrDays(rDays*freez_M);
                        db.update(ExpirationDatabase.FoodTable.TABLE,cv,"_id = ?",new String[]{String.valueOf(experiationID)});
                    }
                    else{
                        frozen =0;
                        food.setFrozen(false);
                        c.add(Calendar.DATE,rDays/freez_M);
                        food.setExpiration(c.getTime());
                        ContentValues cv = new ContentValues();
                        cv.put(ExpirationDatabase.FoodTable.FROZEN,0);
                        cv.put(ExpirationDatabase.FoodTable.REMAINING_DAYS,rDays/freez_M);
                        cv.put(ExpirationDatabase.FoodTable.FROZEN,frozen);
                        food.setrDays(rDays/freez_M);
                        db.update(ExpirationDatabase.FoodTable.TABLE,cv,"_id = ?",new String[]{String.valueOf(experiationID)});
                    }
                }
                else {

                }
                db.close();
                return food;


            }while(cursor.moveToNext());

        }
        else{

        }
        db.close();
        return food;
    }

    public boolean updateList(){
        String queryString = "SELECT * FROM " + ExpirationDatabase.FoodTable.TABLE;
        SQLiteDatabase db = getWritableDatabase();


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
                int rDays = cursor.getInt(7);
                int freez_M = cursor.getInt(8);
                int frozen = cursor.getInt(9);
                Date tempDate = new Date(year,month,day);
                Calendar c = Calendar.getInstance();
                c.setTime(tempDate);
                Date today = new Date();
                Calendar c2 = Calendar.getInstance();
                c2.setTime(today);
                boolean changed = false;
                if(c.get(Calendar.YEAR) == c2.get(Calendar.YEAR)){
                    if(c.get(Calendar.MONTH)==c2.get(Calendar.MONTH)){
                        if(c.get(Calendar.DAY_OF_MONTH) ==c2.get(Calendar.DAY_OF_MONTH)){
                        }
                        else{
                            changed =true;
                            long diffInMillies = Math.abs(c.compareTo(c2));
                            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                            int temp = (int)diff+1;
                            rDays = rDays-temp;
                            day = c2.get(Calendar.DAY_OF_MONTH);
                        }
                    }
                    else{
                        changed =true;
                        long diffInMillies = Math.abs(c.compareTo(c2));
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        int temp = (int)diff+1;
                        rDays = rDays-temp;
                        month =c2.get(Calendar.MONTH);
                        day = c2.get(Calendar.DAY_OF_MONTH);
                    }
                }
                else{
                    changed =true;
                    long diffInMillies = Math.abs(c.compareTo(c2));
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    int temp = (int)diff+1;
                    rDays = rDays-temp;
                    year =c2.get(Calendar.YEAR);
                    month =c2.get(Calendar.MONTH);
                    day = c2.get(Calendar.DAY_OF_MONTH);
                }
                if (changed) {
                    ContentValues cv = new ContentValues();
                    cv.put(ExpirationDatabase.FoodTable.YEAR,year);
                    cv.put(ExpirationDatabase.FoodTable.MONTH,month);
                    cv.put(ExpirationDatabase.FoodTable.DAY,day);
                    cv.put(ExpirationDatabase.FoodTable.REMAINING_DAYS,rDays);
                    db.update(ExpirationDatabase.FoodTable.TABLE,cv,"_id = ?",new String[]{String.valueOf(experiationID)});
                }

            }while(cursor.moveToNext());
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }


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
                int rDays = cursor.getInt(7);
                int freez_M = cursor.getInt(8);
                int frozen = cursor.getInt(9);
                Date tempDate = new Date(year,month,day);
                Calendar c = Calendar.getInstance();
                c.setTime(tempDate);
                c.add(Calendar.DATE, rDays);
                Date exDate = c.getTime();
                Food tempFood = new Food(name,exDate);
                if(frozen != 0){
                    tempFood.setFrozen(true);
                }
                else {
                    tempFood.setFrozen(false);
                }
                tempFood.setPictureID(pictureID);
                tempFood.setrDays(rDays);
                returnList.add(tempFood);
            }while(cursor.moveToNext());
        }
        else{
            //returns an empty list
        }

        return returnList;
    }
}