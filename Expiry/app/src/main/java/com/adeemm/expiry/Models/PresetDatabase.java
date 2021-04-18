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

public class PresetDatabase  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Presets.db";
    private static final int VERSION = 1;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + PresetDatabase.PresetTable.TABLE + " (" +
                PresetDatabase.PresetTable.COL_ID + " integer primary key autoincrement, " +
                PresetDatabase.PresetTable.NAME +  " text, " + PresetDatabase.PresetTable.CATEGORY + " text, "+
                PresetDatabase.PresetTable.PICTURE + " integer, "  + PresetDatabase.PresetTable.FREEZE_MULTIPLIER + " integer, "
                + PresetDatabase.PresetTable.DAYS + " integer)";
        db.execSQL(createTableStatement);

    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("drop table if exists " + PresetDatabase.PresetTable.TABLE);
        onCreate(db);
    }

    private void loadPresets(){

        String name = "Apple";
        String catagory = "Fruit";
        int picid = R.drawable.food_apple;
        int days = 60;
        int fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Asparagus";
        catagory = "Fruit";
        picid = R.drawable.food_asparagus;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Aubergine";
        catagory = "Fruit";
        picid = R.drawable.food_aubergine;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Avocado";
        catagory = "Fruit";
        picid = R.drawable.food_avocado;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Bacon";
        catagory = "Fruit";
        picid = R.drawable.food_bacon;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Baguette";
        catagory = "Fruit";
        picid = R.drawable.food_baguette;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Banana";
        catagory = "Fruit";
        picid = R.drawable.food_banana;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Beans";
        catagory = "Fruit";
        picid = R.drawable.food_bean;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Beer";
        catagory = "Fruit";
        picid = R.drawable.food_beer;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Beat";
        catagory = "Fruit";
        picid = R.drawable.food_beet;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Biscuit";
        catagory = "Fruit";
        picid = R.drawable.food_biscuit;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Blueberry";
        catagory = "Fruit";
        picid = R.drawable.food_blueberrie;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Boiled egg";
        catagory = "Fruit";
        picid = R.drawable.food_boiled;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Leftovers";
        catagory = "Fruit";
        picid = R.drawable.food_bowl;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Bread";
        catagory = "Fruit";
        picid = R.drawable.food_bread;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Broccoli";
        catagory = "Fruit";
        picid = R.drawable.food_broccoli;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Butter";
        catagory = "Fruit";
        picid = R.drawable.food_butter;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cabbage";
        catagory = "Fruit";
        picid = R.drawable.food_cabbage;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cafe";
        catagory = "Fruit";
        picid = R.drawable.food_cafe;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cake";
        catagory = "Fruit";
        picid = R.drawable.food_cake;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Can";
        catagory = "Fruit";
        picid = R.drawable.food_can;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Candy";
        catagory = "Fruit";
        picid = R.drawable.food_candy;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Carrot";
        catagory = "Fruit";
        picid = R.drawable.food_carrot;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cauliflower";
        catagory = "Fruit";
        picid = R.drawable.food_cauliflower;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Celery";
        catagory = "Fruit";
        picid = R.drawable.food_celery;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cereal";
        catagory = "Fruit";
        picid = R.drawable.food_cereal;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cheese";
        catagory = "Fruit";
        picid = R.drawable.food_cheese_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cherry";
        catagory = "Fruit";
        picid = R.drawable.food_cherry;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Chili pepper";
        catagory = "Fruit";
        picid = R.drawable.food_chili_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Chips";
        catagory = "Fruit";
        picid = R.drawable.food_chips;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Chives";
        catagory = "Fruit";
        picid = R.drawable.food_chives;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Chocolate";
        catagory = "Fruit";
        picid = R.drawable.food_chocolate;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cinnamon roll";
        catagory = "Fruit";
        picid = R.drawable.food_cinnamon_roll;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_citrus;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Coconut";
        catagory = "Fruit";
        picid = R.drawable.food_coconut;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Coffee";
        catagory = "Fruit";
        picid = R.drawable.food_coffee;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cookie";
        catagory = "Fruit";
        picid = R.drawable.food_cookie;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Corn";
        catagory = "Fruit";
        picid = R.drawable.food_corn_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Corndog";
        catagory = "Fruit";
        picid = R.drawable.food_corndog;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cotten Candy";
        catagory = "Fruit";
        picid = R.drawable.food_cotton_candy;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Crab";
        catagory = "Fruit";
        picid = R.drawable.food_crab;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Croissant";
        catagory = "Fruit";
        picid = R.drawable.food_croissant;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cucumber";
        catagory = "Fruit";
        picid = R.drawable.food_cucumber;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cupcake";
        catagory = "Fruit";
        picid = R.drawable.food_cupcake;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_dairy;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Dim Sum";
        catagory = "Fruit";
        picid = R.drawable.food_dim_sum;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Dolmade";
        catagory = "Fruit";
        picid = R.drawable.food_dolmade;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Doughnut";
        catagory = "Fruit";
        picid = R.drawable.food_doughnut;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Dragon Fruit";
        catagory = "Fruit";
        picid = R.drawable.food_dragon_fruit;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Durian";
        catagory = "Fruit";
        picid = R.drawable.food_durian;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Cooked Egg";
        catagory = "Fruit";
        picid = R.drawable.food_egg;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Eggplant";
        catagory = "Fruit";
        picid = R.drawable.food_eggplant;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Eggs";
        catagory = "Fruit";
        picid = R.drawable.food_eggs;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Figs";
        catagory = "Fruit";
        picid = R.drawable.food_fig;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Fish";
        catagory = "Fruit";
        picid = R.drawable.food_fish;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Flour";
        catagory = "Fruit";
        picid = R.drawable.food_flour_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "French Fries";
        catagory = "Fruit";
        picid = R.drawable.food_french_fry;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Garlic";
        catagory = "Fruit";
        picid = R.drawable.food_garlic_2;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ginger Bread";
        catagory = "Fruit";
        picid = R.drawable.food_gingerbread;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Grain";
        catagory = "Fruit";
        picid = R.drawable.food_grain;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Grapes";
        catagory = "Fruit";
        picid = R.drawable.food_grape;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ham";
        catagory = "Fruit";
        picid = R.drawable.food_ham;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hamburger";
        catagory = "Fruit";
        picid = R.drawable.food_hamburger;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hazelnut";
        catagory = "Fruit";
        picid = R.drawable.food_hazelnut;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Honey";
        catagory = "Fruit";
        picid = R.drawable.food_honey;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hops";
        catagory = "Fruit";
        picid = R.drawable.food_hops;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hot Chocolate";
        catagory = "Fruit";
        picid = R.drawable.food_hot_chocolate;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Hot Dog";
        catagory = "Fruit";
        picid = R.drawable.food_hot_dog;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ice Cream";
        catagory = "Fruit";
        picid = R.drawable.food_ice_cream;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Jam";
        catagory = "Fruit";
        picid = R.drawable.food_jam;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Jelly";
        catagory = "Fruit";
        picid = R.drawable.food_jelly;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Kebab";
        catagory = "Fruit";
        picid = R.drawable.food_kebab;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Ketchup";
        catagory = "Fruit";
        picid = R.drawable.food_ketchup;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Kiwi";
        catagory = "Fruit";
        picid = R.drawable.food_kiwi;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Kohlrabi";
        catagory = "Fruit";
        picid = R.drawable.food_kohlrabi;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lamb";
        catagory = "Fruit";
        picid = R.drawable.food_lamb;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Leek";
        catagory = "Fruit";
        picid = R.drawable.food_leek;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_lemon;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lettuce";
        catagory = "Fruit";
        picid = R.drawable.food_lettuce;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Lime";
        catagory = "Fruit";
        picid = R.drawable.food_lime;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Macaron";
        catagory = "Fruit";
        picid = R.drawable.food_macaron;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Meat";
        catagory = "Fruit";
        picid = R.drawable.food_meat;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Melon";
        catagory = "Fruit";
        picid = R.drawable.food_melon;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_milk;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Mushroom";
        catagory = "Fruit";
        picid = R.drawable.food_mushroom;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Mustard";
        catagory = "Fruit";
        picid = R.drawable.food_mustard;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Nachos";
        catagory = "Fruit";
        picid = R.drawable.food_nacho;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Noodles";
        catagory = "Fruit";
        picid = R.drawable.food_noodle;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Nuts";
        catagory = "Fruit";
        picid = R.drawable.food_nut;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Oats";
        catagory = "Fruit";
        picid = R.drawable.food_oat;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Octopus";
        catagory = "Fruit";
        picid = R.drawable.food_octopus;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Oil";
        catagory = "Fruit";
        picid = R.drawable.food_oil;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Olive oil";
        catagory = "Fruit";
        picid = R.drawable.food_olive_oil;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Olives";
        catagory = "Fruit";
        picid = R.drawable.food_olives;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Onion";
        catagory = "Fruit";
        picid = R.drawable.food_onion;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Orange";
        catagory = "Fruit";
        picid = R.drawable.food_orange;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pancake";
        catagory = "Fruit";
        picid = R.drawable.food_pancake;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Paprika";
        catagory = "Fruit";
        picid = R.drawable.food_paprika;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pasta";
        catagory = "Fruit";
        picid = R.drawable.food_pasta;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peach";
        catagory = "Fruit";
        picid = R.drawable.food_peach;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peanuts";
        catagory = "Fruit";
        picid = R.drawable.food_peanut;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pear";
        catagory = "Fruit";
        picid = R.drawable.food_pear;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peas";
        catagory = "Fruit";
        picid = R.drawable.food_pea;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Peppers";
        catagory = "Fruit";
        picid = R.drawable.food_pepper;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pickles";
        catagory = "Fruit";
        picid = R.drawable.food_pickle;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pie";
        catagory = "Fruit";
        picid = R.drawable.food_pie;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pineapple";
        catagory = "Fruit";
        picid = R.drawable.food_pineapple;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pistachio";
        catagory = "Fruit";
        picid = R.drawable.food_pistachio;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pizza";
        catagory = "Fruit";
        picid = R.drawable.food_pizza;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Plum";
        catagory = "Fruit";
        picid = R.drawable.food_plum;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pomegranate";
        catagory = "Fruit";
        picid = R.drawable.food_pomegranate;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Popsicle";
        catagory = "Fruit";
        picid = R.drawable.food_popsicle;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Porridge";
        catagory = "Fruit";
        picid = R.drawable.food_porridge;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Potato";
        catagory = "Fruit";
        picid = R.drawable.food_potato;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Prawn";
        catagory = "Fruit";
        picid = R.drawable.food_prawn;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pretzel";
        catagory = "Fruit";
        picid = R.drawable.food_pretzel;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pudding";
        catagory = "Fruit";
        picid = R.drawable.food_pudding;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Pumpkin";
        catagory = "Fruit";
        picid = R.drawable.food_pumpkin;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Quesadilla";
        catagory = "Fruit";
        picid = R.drawable.food_quesadilla;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Radish";
        catagory = "Fruit";
        picid = R.drawable.food_radish;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Raspberry";
        catagory = "Fruit";
        picid = R.drawable.food_raspberry;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Rice";
        catagory = "Fruit";
        picid = R.drawable.food_rice;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Rice bowl";
        catagory = "Fruit";
        picid = R.drawable.food_rice_bowl;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Risotto";
        catagory = "Fruit";
        picid = R.drawable.food_risotto;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Salad";
        catagory = "Fruit";
        picid = R.drawable.food_salad;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Salami";
        catagory = "Fruit";
        picid = R.drawable.food_salami;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Salmon";
        catagory = "Fruit";
        picid = R.drawable.food_salmon;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sandwich";
        catagory = "Fruit";
        picid = R.drawable.food_sandwich_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sauce";
        catagory = "Fruit";
        picid = R.drawable.food_sauce;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sausage";
        catagory = "Fruit";
        picid = R.drawable.food_sausage;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Seeds";
        catagory = "Fruit";
        picid = R.drawable.food_seeds;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sesame";
        catagory = "Fruit";
        picid = R.drawable.food_sesame;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Shrimp";
        catagory = "Fruit";
        picid = R.drawable.food_shrimp;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sorbet";
        catagory = "Fruit";
        picid = R.drawable.food_sorbet;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Spaghetti";
        catagory = "Fruit";
        picid = R.drawable.food_spaghetti;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Spices";
        catagory = "Fruit";
        picid = R.drawable.food_spices;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Steak";
        catagory = "Fruit";
        picid = R.drawable.food_steak;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Strawberry";
        catagory = "Fruit";
        picid = R.drawable.food_strawberry;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sushi";
        catagory = "Fruit";
        picid = R.drawable.food_sushi_1;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Sweet Potato";
        catagory = "Fruit";
        picid = R.drawable.food_sweet_potato;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Taco";
        catagory = "Fruit";
        picid = R.drawable.food_taco;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Tapas";
        catagory = "Fruit";
        picid = R.drawable.food_tapas;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Toast";
        catagory = "Fruit";
        picid = R.drawable.food_toast;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Tomato";
        catagory = "Fruit";
        picid = R.drawable.food_tomato;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Turkey";
        catagory = "Fruit";
        picid = R.drawable.food_turkey;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Watermelon";
        catagory = "Fruit";
        picid = R.drawable.food_watermelon;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);

        name = "Wrap";
        catagory = "Fruit";
        picid = R.drawable.food_wrap;
        days = 60;
        fm = 2;
        addPreset(name,catagory,picid,days,fm);
    }
}