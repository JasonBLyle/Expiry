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

/**
 * This is the database that holds all the information in the preset classes
 */
public class PresetDatabase  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Presets.db";
    private static final int VERSION = 1;

    /**
     * this function creates a reference to this database
     * @param context is the activity calling the database
     */
    public PresetDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class PresetTable {
        private static final String TABLE = "PRESETS";
        private static final String COL_ID = "_id";
        private static final String NAME = "Food";
        private static final String CATEGORY = "category";
        private static final String DAYS = "Days";
        private static final String PICTURE = "picture";
        private static final String FREEZE_MULTIPLIER = "f_multiplier";
    }

    /**
     *  This function creates the database
     * @param db is this database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + PresetDatabase.PresetTable.TABLE + " (" +
                PresetDatabase.PresetTable.COL_ID + " integer primary key autoincrement, " +
                PresetDatabase.PresetTable.NAME +  " text, " + PresetDatabase.PresetTable.CATEGORY + " text, "+
                PresetDatabase.PresetTable.PICTURE + " integer, "  + PresetDatabase.PresetTable.FREEZE_MULTIPLIER + " integer, "
                + PresetDatabase.PresetTable.DAYS + " integer)";
        db.execSQL(createTableStatement);

    }

    /**
     * This function checks if the databse was just created and it was it loads all the presets.
     * For some reason it wouldn't let me load the presets in onCreate
     */
    public long checkEmpty(){

        String queryString = "SELECT * FROM " + PresetDatabase.PresetTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()) {
            return -1;
        }
        else {
            loadPresets();
            return 1;
        }
    }

    public void addPreset(Food newFood, int days){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PresetDatabase.PresetTable.NAME,newFood.getName());
        values.put(PresetDatabase.PresetTable.CATEGORY,newFood.getCategory());
        values.put(PresetDatabase.PresetTable.DAYS,days);
        values.put(PresetTable.PICTURE,newFood.getPictureID());

        db.insert(PresetDatabase.PresetTable.TABLE,null,values);
        db.close();
    }

    public void addPreset(String name, String catagory, int pictureid, int days,int fm){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PresetDatabase.PresetTable.NAME,name);
        values.put(PresetDatabase.PresetTable.CATEGORY,catagory);
        values.put(PresetDatabase.PresetTable.PICTURE,pictureid);
        values.put(PresetDatabase.PresetTable.DAYS,days);
        values.put(PresetDatabase.PresetTable.FREEZE_MULTIPLIER,fm);

        db.insert(PresetDatabase.PresetTable.TABLE,null,values);
        db.close();
    }

    /**
     * Pre: the database has been initialized
     * Post: return = all the presets in the database
     */
    public List<Food> getAll() {
        List<Food> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PresetDatabase.PresetTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{
                int experiationID = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int pictureID = cursor.getInt(3);
                int fm = cursor.getInt(4);
                int days = cursor.getInt(5);
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DATE,days);
                Date tempDate = c.getTime();
                Food tempFood = new Food(name,tempDate);
                tempFood.setPictureID(pictureID);
                returnList.add(tempFood);
            }while(cursor.moveToNext());
        }
        else{
            //returns an empty list
        }
        return returnList;
    }

    /**
     * This function updgrades the database if the database parameters was changes
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("drop table if exists " + PresetDatabase.PresetTable.TABLE);
        onCreate(db);
    }

    /**
     * This function loads all the presets into the database
     *
     */
    private void loadPresets(){

        String name = "Apple";
        String catagory = "Fruit";
        int picid = R.drawable.food_apple;
        int days = 5;
        int fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Asparagus";
        catagory = "Fruit";
        picid = R.drawable.food_asparagus;
        days = 1;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Aubergine";
        catagory = "Fruit";
        picid = R.drawable.food_aubergine;
        days = 1;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Avocado";
        catagory = "Fruit";
        picid = R.drawable.food_avocado;
        days = 4;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Bacon";
        catagory = "Fruit";
        picid = R.drawable.food_bacon;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Baguette";
        catagory = "Fruit";
        picid = R.drawable.food_baguette;
        days = 2;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Banana";
        catagory = "Fruit";
        picid = R.drawable.food_banana;
        days = 3;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Beans";
        catagory = "Fruit";
        picid = R.drawable.food_bean;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Beat";
        catagory = "Fruit";
        picid = R.drawable.food_beet;
        days = 2;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Biscuit";
        catagory = "Fruit";
        picid = R.drawable.food_biscuit;
        days = 2;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Blueberry";
        catagory = "Fruit";
        picid = R.drawable.food_blueberrie;
        days = 5;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Boiled egg";
        catagory = "Fruit";
        picid = R.drawable.food_boiled;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Bread";
        catagory = "Fruit";
        picid = R.drawable.food_bread;
        days = 2;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Broccoli";
        catagory = "Fruit";
        picid = R.drawable.food_broccoli;
        days = 7;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Butter";
        catagory = "Fruit";
        picid = R.drawable.food_butter;
        days = 7;
        fm = 12;
        addPreset(name,catagory,picid,days,fm);

        name = "Cabbage";
        catagory = "Fruit";
        picid = R.drawable.food_cabbage;
        days = 2;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Cake";
        catagory = "Fruit";
        picid = R.drawable.food_cake;
        days = 1;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Carrot";
        catagory = "Fruit";
        picid = R.drawable.food_carrot;
        days = 7;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Cauliflower";
        catagory = "Fruit";
        picid = R.drawable.food_cauliflower;
        days = 1;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Celery";
        catagory = "Fruit";
        picid = R.drawable.food_celery;
        days = 2;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Cheese";
        catagory = "Fruit";
        picid = R.drawable.food_cheese_1;
        days = 21;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cherry";
        catagory = "Fruit";
        picid = R.drawable.food_cherry;
        days = 4;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Chocolate";
        catagory = "Fruit";
        picid = R.drawable.food_chocolate;
        days = 2;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Cinnamon roll";
        catagory = "Fruit";
        picid = R.drawable.food_cinnamon_roll;
        days = 2;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_citrus;
        days = 6;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Cookie";
        catagory = "Fruit";
        picid = R.drawable.food_cookie;
        days = 7;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Corn";
        catagory = "Fruit";
        picid = R.drawable.food_corn_1;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Crab";
        catagory = "Fruit";
        picid = R.drawable.food_crab;
        days = 1;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Croissant";
        catagory = "Fruit";
        picid = R.drawable.food_croissant;
        days = 2;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Cucumber";
        catagory = "Fruit";
        picid = R.drawable.food_cucumber;
        days = 3;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cupcake";
        catagory = "Fruit";
        picid = R.drawable.food_cupcake;
        days = 2;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_dairy;
        days = 2;
        fm = 10;
        addPreset(name,catagory,picid,days,fm);

        name = "Doughnut";
        catagory = "Fruit";
        picid = R.drawable.food_doughnut;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Cooked Egg";
        catagory = "Fruit";
        picid = R.drawable.food_egg;
        days = 2;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Eggplant";
        catagory = "Fruit";
        picid = R.drawable.food_eggplant;
        days = 1;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Eggs";
        catagory = "Fruit";
        picid = R.drawable.food_eggs;
        days = 3;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Figs";
        catagory = "Fruit";
        picid = R.drawable.food_fig;
        days = 3;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Fish";
        catagory = "Fruit";
        picid = R.drawable.food_fish;
        days = 1;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "French Fries";
        catagory = "Fruit";
        picid = R.drawable.food_french_fry;
        days = 2;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Garlic";
        catagory = "Fruit";
        picid = R.drawable.food_garlic_2;
        days = 3*8;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ginger Bread";
        catagory = "Fruit";
        picid = R.drawable.food_gingerbread;
        days = 2;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Grapes";
        catagory = "Fruit";
        picid = R.drawable.food_grape;
        days = 4;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ham";
        catagory = "Fruit";
        picid = R.drawable.food_ham;
        days = 2;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hamburger";
        catagory = "Fruit";
        picid = R.drawable.food_hamburger;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hazelnut";
        catagory = "Fruit";
        picid = R.drawable.food_hazelnut;
        days = 120;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Hot Dog";
        catagory = "Fruit";
        picid = R.drawable.food_hot_dog;
        days = 7;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Jam";
        catagory = "Fruit";
        picid = R.drawable.food_jam;
        days = 30;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Jelly";
        catagory = "Fruit";
        picid = R.drawable.food_jelly;
        days = 30;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Kebab";
        catagory = "Fruit";
        picid = R.drawable.food_kebab;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ketchup";
        catagory = "Fruit";
        picid = R.drawable.food_ketchup;
        days = 60;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Kiwi";
        catagory = "Fruit";
        picid = R.drawable.food_kiwi;
        days = 4;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lamb";
        catagory = "Fruit";
        picid = R.drawable.food_lamb;
        days = 2;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Leek";
        catagory = "Fruit";
        picid = R.drawable.food_leek;
        days = 2;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_lemon;
        days = 5;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Lettuce";
        catagory = "Fruit";
        picid = R.drawable.food_lettuce;
        days = 3;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Lime";
        catagory = "Fruit";
        picid = R.drawable.food_lime;
        days = 5;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Macaron";
        catagory = "Fruit";
        picid = R.drawable.food_macaron;
        days = 3;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Meat";
        catagory = "Fruit";
        picid = R.drawable.food_meat;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Melon";
        catagory = "Fruit";
        picid = R.drawable.food_melon;
        days = 5;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_milk;
        days = 2;
        fm = 10;
        addPreset(name,catagory,picid,days,fm);

        name = "Mushroom";
        catagory = "Fruit";
        picid = R.drawable.food_mushroom;
        days = 3;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Mustard";
        catagory = "Fruit";
        picid = R.drawable.food_mustard;
        days = 60;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Noodles";
        catagory = "Fruit";
        picid = R.drawable.food_noodle;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Nuts";
        catagory = "Fruit";
        picid = R.drawable.food_nut;
        days = 120;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Onion";
        catagory = "Fruit";
        picid = R.drawable.food_onion;
        days = 20;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Orange";
        catagory = "Fruit";
        picid = R.drawable.food_orange;
        days = 7;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Pancake";
        catagory = "Fruit";
        picid = R.drawable.food_pancake;
        days = 1;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Pasta";
        catagory = "Fruit";
        picid = R.drawable.food_pasta;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peach";
        catagory = "Fruit";
        picid = R.drawable.food_peach;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Peanuts";
        catagory = "Fruit";
        picid = R.drawable.food_peanut;
        days = 180;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pear";
        catagory = "Fruit";
        picid = R.drawable.food_pear;
        days = 3;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peas";
        catagory = "Fruit";
        picid = R.drawable.food_pea;
        days = 3;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peppers";
        catagory = "Fruit";
        picid = R.drawable.food_pepper;
        days = 3;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Pie";
        catagory = "Fruit";
        picid = R.drawable.food_pie;
        days = 1;
        fm = 7;
        addPreset(name,catagory,picid,days,fm);

        name = "Pineapple";
        catagory = "Fruit";
        picid = R.drawable.food_pineapple;
        days = 1;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Pistachio";
        catagory = "Fruit";
        picid = R.drawable.food_pistachio;
        days = 75;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Pizza";
        catagory = "Fruit";
        picid = R.drawable.food_pizza;
        days = 1;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Plum";
        catagory = "Fruit";
        picid = R.drawable.food_plum;
        days = 4;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pomegranate";
        catagory = "Fruit";
        picid = R.drawable.food_pomegranate;
        days = 30;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Potato";
        catagory = "Fruit";
        picid = R.drawable.food_potato;
        days = 21;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Prawn";
        catagory = "Fruit";
        picid = R.drawable.food_prawn;
        days = 1;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Pretzel";
        catagory = "Fruit";
        picid = R.drawable.food_pretzel;
        days = 2;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Radish";
        catagory = "Fruit";
        picid = R.drawable.food_radish;
        days = 1;
        fm = 12;
        addPreset(name,catagory,picid,days,fm);

        name = "Raspberry";
        catagory = "Fruit";
        picid = R.drawable.food_raspberry;
        days = 3;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Salad";
        catagory = "Fruit";
        picid = R.drawable.food_salad;
        days = 2;
        fm = 3;
        addPreset(name,catagory,picid,days,fm);

        name = "Sausage";
        catagory = "Fruit";
        picid = R.drawable.food_sausage;
        days = 2;
        fm = 6;
        addPreset(name,catagory,picid,days,fm);

        name = "Shrimp";
        catagory = "Fruit";
        picid = R.drawable.food_shrimp;
        days = 1;
        fm = 4;
        addPreset(name,catagory,picid,days,fm);

        name = "Spaghetti";
        catagory = "Fruit";
        picid = R.drawable.food_spaghetti;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Steak";
        catagory = "Fruit";
        picid = R.drawable.food_steak;
        days = 1;
        fm = 5;
        addPreset(name,catagory,picid,days,fm);

        name = "Strawberry";
        catagory = "Fruit";
        picid = R.drawable.food_strawberry;
        days = 3;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sushi";
        catagory = "Fruit";
        picid = R.drawable.food_sushi_1;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sweet Potato";
        catagory = "Fruit";
        picid = R.drawable.food_sweet_potato;
        days = 24;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Taco";
        catagory = "Fruit";
        picid = R.drawable.food_taco;
        days = 2;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Tapas";
        catagory = "Fruit";
        picid = R.drawable.food_tapas;
        days = 2;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Toast";
        catagory = "Fruit";
        picid = R.drawable.food_toast;
        days = 5;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Tomato";
        catagory = "Fruit";
        picid = R.drawable.food_tomato;
        days = 7;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Turkey";
        catagory = "Fruit";
        picid = R.drawable.food_turkey;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Watermelon";
        catagory = "Fruit";
        picid = R.drawable.food_watermelon;
        days = 7;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Wrap";
        catagory = "Fruit";
        picid = R.drawable.food_wrap;
        days = 1;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);
    }
}