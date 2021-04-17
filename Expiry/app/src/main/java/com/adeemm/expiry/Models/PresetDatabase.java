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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + PresetDatabase.PresetTable.TABLE + " (" +
                PresetDatabase.PresetTable.COL_ID + " integer primary key autoincrement, " +
                PresetDatabase.PresetTable.NAME +  " text, " + PresetDatabase.PresetTable.CATEGORY + " text, "+
                PresetDatabase.PresetTable.PICTURE + " integer, " +PresetDatabase.PresetTable.DAYS + " integer)";
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
    public long addPreset(Food newFood,int days){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        Date today = new Date();
        int picid = R.drawable.food_apple;
        values.put(PresetDatabase.PresetTable.NAME,newFood.getName());
        values.put(PresetDatabase.PresetTable.CATEGORY,newFood.getCategory());
        values.put(PresetDatabase.PresetTable.DAYS,days);
        values.put(PresetTable.PICTURE,picid);
        return db.insert(PresetDatabase.PresetTable.TABLE,null,values);
    }
    public long addPreset(String name, String catagory, int pictureid, int days){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PresetDatabase.PresetTable.NAME,name);
        values.put(PresetDatabase.PresetTable.CATEGORY,catagory);
        values.put(PresetTable.PICTURE,pictureid);
        values.put(PresetDatabase.PresetTable.DAYS,days);
        return db.insert(PresetDatabase.PresetTable.TABLE,null,values);
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
                int days = cursor.getInt(4);
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
        addPreset(name,catagory,picid,days);

        name = "Asparagus";
        catagory = "Fruit";
        picid = R.drawable.food_asparagus;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Aubergine";
        catagory = "Fruit";
        picid = R.drawable.food_aubergine;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Avocado";
        catagory = "Fruit";
        picid = R.drawable.food_avocado;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Bacom";
        catagory = "Fruit";
        picid = R.drawable.food_bacon;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Baguette";
        catagory = "Fruit";
        picid = R.drawable.food_baguette;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Banana";
        catagory = "Fruit";
        picid = R.drawable.food_banana;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Beans";
        catagory = "Fruit";
        picid = R.drawable.food_bean;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Beer";
        catagory = "Fruit";
        picid = R.drawable.food_beer;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Beat";
        catagory = "Fruit";
        picid = R.drawable.food_beet;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Biscuit";
        catagory = "Fruit";
        picid = R.drawable.food_biscuit;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Blueberry";
        catagory = "Fruit";
        picid = R.drawable.food_blueberrie;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Boiled egg";
        catagory = "Fruit";
        picid = R.drawable.food_boiled;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Leftover bowl";
        catagory = "Fruit";
        picid = R.drawable.food_bowl;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Bread";
        catagory = "Fruit";
        picid = R.drawable.food_bread;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Bread";
        catagory = "Fruit";
        picid = R.drawable.food_bread_2;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Broccoli";
        catagory = "Fruit";
        picid = R.drawable.food_broccoli;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Butter";
        catagory = "Fruit";
        picid = R.drawable.food_butter;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cabbage";
        catagory = "Fruit";
        picid = R.drawable.food_cabbage;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cafe";
        catagory = "Fruit";
        picid = R.drawable.food_cafe;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cake";
        catagory = "Fruit";
        picid = R.drawable.food_cake;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cake";
        catagory = "Fruit";
        picid = R.drawable.food_cake_2;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Can";
        catagory = "Fruit";
        picid = R.drawable.food_can;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Candy";
        catagory = "Fruit";
        picid = R.drawable.food_candy;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Carrot";
        catagory = "Fruit";
        picid = R.drawable.food_carrot;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cauliflower";
        catagory = "Fruit";
        picid = R.drawable.food_cauliflower;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Celery";
        catagory = "Fruit";
        picid = R.drawable.food_celery;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cereal";
        catagory = "Fruit";
        picid = R.drawable.food_cereal;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cheese";
        catagory = "Fruit";
        picid = R.drawable.food_cheese;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cheese";
        catagory = "Fruit";
        picid = R.drawable.food_cheese_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cherry";
        catagory = "Fruit";
        picid = R.drawable.food_cherry;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Chili pepper";
        catagory = "Fruit";
        picid = R.drawable.food_chili;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Chili pepper";
        catagory = "Fruit";
        picid = R.drawable.food_chili_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Chips";
        catagory = "Fruit";
        picid = R.drawable.food_chips;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Chives";
        catagory = "Fruit";
        picid = R.drawable.food_chives;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Chocolate";
        catagory = "Fruit";
        picid = R.drawable.food_chocolate;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cinnamon roll";
        catagory = "Fruit";
        picid = R.drawable.food_cinnamon_roll;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_citrus;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Coconut";
        catagory = "Fruit";
        picid = R.drawable.food_coconut;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Coffee";
        catagory = "Fruit";
        picid = R.drawable.food_coffee;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cookie";
        catagory = "Fruit";
        picid = R.drawable.food_cookie;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Corn";
        catagory = "Fruit";
        picid = R.drawable.food_corn;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Corn";
        catagory = "Fruit";
        picid = R.drawable.food_corn_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Corndog";
        catagory = "Fruit";
        picid = R.drawable.food_corndog;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cotten Candy";
        catagory = "Fruit";
        picid = R.drawable.food_cotton_candy;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Crab";
        catagory = "Fruit";
        picid = R.drawable.food_crab;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Croissant";
        catagory = "Fruit";
        picid = R.drawable.food_croissant;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cucumber";
        catagory = "Fruit";
        picid = R.drawable.food_cucumber;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cup";
        catagory = "Fruit";
        picid = R.drawable.food_cup;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cupcake";
        catagory = "Fruit";
        picid = R.drawable.food_cupcake;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_dairy;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Dim Sum";
        catagory = "Fruit";
        picid = R.drawable.food_dim_sum;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Dish";
        catagory = "Fruit";
        picid = R.drawable.food_dish;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Dish";
        catagory = "Fruit";
        picid = R.drawable.food_dish_2;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Dolmade";
        catagory = "Fruit";
        picid = R.drawable.food_dolmade;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Doughnut";
        catagory = "Fruit";
        picid = R.drawable.food_doughnut;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Dragon Fruit";
        catagory = "Fruit";
        picid = R.drawable.food_dragon_fruit;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Durian";
        catagory = "Fruit";
        picid = R.drawable.food_durian;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Cooked Egg";
        catagory = "Fruit";
        picid = R.drawable.food_egg;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Eggplant";
        catagory = "Fruit";
        picid = R.drawable.food_eggplant;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Eggs";
        catagory = "Fruit";
        picid = R.drawable.food_eggs;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Figs";
        catagory = "Fruit";
        picid = R.drawable.food_fig;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Fish";
        catagory = "Fruit";
        picid = R.drawable.food_fish;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Flour";
        catagory = "Fruit";
        picid = R.drawable.food_flour;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Flour";
        catagory = "Fruit";
        picid = R.drawable.food_flour_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "French Fries";
        catagory = "Fruit";
        picid = R.drawable.food_french_fry;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Garlic";
        catagory = "Fruit";
        picid = R.drawable.food_garlic;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Garlic";
        catagory = "Fruit";
        picid = R.drawable.food_garlic_2;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "GingerBread";
        catagory = "Fruit";
        picid = R.drawable.food_gingerbread;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Drink";
        catagory = "Fruit";
        picid = R.drawable.food_glass;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Grain";
        catagory = "Fruit";
        picid = R.drawable.food_grain;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Grapes";
        catagory = "Fruit";
        picid = R.drawable.food_grape;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Ham";
        catagory = "Fruit";
        picid = R.drawable.food_ham;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Hamburger";
        catagory = "Fruit";
        picid = R.drawable.food_hamburger;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Hazelnut";
        catagory = "Fruit";
        picid = R.drawable.food_hazelnut;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Honey";
        catagory = "Fruit";
        picid = R.drawable.food_honey;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Honey";
        catagory = "Fruit";
        picid = R.drawable.food_honey_2;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Hops";
        catagory = "Fruit";
        picid = R.drawable.food_hops;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Hot Chocolate";
        catagory = "Fruit";
        picid = R.drawable.food_hot_chocolate;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Hot Dog";
        catagory = "Fruit";
        picid = R.drawable.food_hot_dog;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Ice Cream";
        catagory = "Fruit";
        picid = R.drawable.food_ice_cream;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Jam";
        catagory = "Fruit";
        picid = R.drawable.food_jam;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Jelly";
        catagory = "Fruit";
        picid = R.drawable.food_jelly;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Kebab";
        catagory = "Fruit";
        picid = R.drawable.food_kebab;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Ketchup";
        catagory = "Fruit";
        picid = R.drawable.food_ketchup;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Kiwi";
        catagory = "Fruit";
        picid = R.drawable.food_kiwi;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Kohlrabi";
        catagory = "Fruit";
        picid = R.drawable.food_kohlrabi;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Lamb";
        catagory = "Fruit";
        picid = R.drawable.food_lamb;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Leek";
        catagory = "Fruit";
        picid = R.drawable.food_leek;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Lemon";
        catagory = "Fruit";
        picid = R.drawable.food_lemon;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Lettuce";
        catagory = "Fruit";
        picid = R.drawable.food_lettuce;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Lime";
        catagory = "Fruit";
        picid = R.drawable.food_lime;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Macaron";
        catagory = "Fruit";
        picid = R.drawable.food_macaron;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Meat";
        catagory = "Fruit";
        picid = R.drawable.food_meat;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Melon";
        catagory = "Fruit";
        picid = R.drawable.food_melon;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Milk";
        catagory = "Fruit";
        picid = R.drawable.food_milk;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Misc";
        catagory = "Fruit";
        picid = R.drawable.food_misc;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Mushroom";
        catagory = "Fruit";
        picid = R.drawable.food_mushroom;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Mustard";
        catagory = "Fruit";
        picid = R.drawable.food_mustard;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Nachos";
        catagory = "Fruit";
        picid = R.drawable.food_nacho;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Noodles";
        catagory = "Fruit";
        picid = R.drawable.food_noodle;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Nuts";
        catagory = "Fruit";
        picid = R.drawable.food_nut;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Oats";
        catagory = "Fruit";
        picid = R.drawable.food_oat;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Octopus";
        catagory = "Fruit";
        picid = R.drawable.food_octopus;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Oil";
        catagory = "Fruit";
        picid = R.drawable.food_oil;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Olive";
        catagory = "Fruit";
        picid = R.drawable.food_olive;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Olive oil";
        catagory = "Fruit";
        picid = R.drawable.food_olive_oil;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Olives";
        catagory = "Fruit";
        picid = R.drawable.food_olives;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Onion";
        catagory = "Fruit";
        picid = R.drawable.food_onion;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Orange";
        catagory = "Fruit";
        picid = R.drawable.food_orange;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Organic";
        catagory = "Fruit";
        picid = R.drawable.food_organic;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pancake";
        catagory = "Fruit";
        picid = R.drawable.food_pancake;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Paprika";
        catagory = "Fruit";
        picid = R.drawable.food_paprika;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pasta";
        catagory = "Fruit";
        picid = R.drawable.food_pasta;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Peach";
        catagory = "Fruit";
        picid = R.drawable.food_peach;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Peanuts";
        catagory = "Fruit";
        picid = R.drawable.food_peanut;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pear";
        catagory = "Fruit";
        picid = R.drawable.food_pear;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Peas";
        catagory = "Fruit";
        picid = R.drawable.food_pea;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pepper";
        catagory = "Fruit";
        picid = R.drawable.food_pepper;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pickles";
        catagory = "Fruit";
        picid = R.drawable.food_pickle;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pie";
        catagory = "Fruit";
        picid = R.drawable.food_pie;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pineapple";
        catagory = "Fruit";
        picid = R.drawable.food_pineapple;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pistachio";
        catagory = "Fruit";
        picid = R.drawable.food_pistachio;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pizza";
        catagory = "Fruit";
        picid = R.drawable.food_pizza;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Plum";
        catagory = "Fruit";
        picid = R.drawable.food_plum;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pomegranate";
        catagory = "Fruit";
        picid = R.drawable.food_pomegranate;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Popsicle";
        catagory = "Fruit";
        picid = R.drawable.food_popsicle;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Porridge";
        catagory = "Fruit";
        picid = R.drawable.food_porridge;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Potato";
        catagory = "Fruit";
        picid = R.drawable.food_potato;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Prawn";
        catagory = "Fruit";
        picid = R.drawable.food_prawn;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pretzel";
        catagory = "Fruit";
        picid = R.drawable.food_pretzel;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pudding";
        catagory = "Fruit";
        picid = R.drawable.food_pudding;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Pumpkin";
        catagory = "Fruit";
        picid = R.drawable.food_pumpkin;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Quesadilla";
        catagory = "Fruit";
        picid = R.drawable.food_quesadilla;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Radish";
        catagory = "Fruit";
        picid = R.drawable.food_radish;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Raspberry";
        catagory = "Fruit";
        picid = R.drawable.food_raspberry;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Rice";
        catagory = "Fruit";
        picid = R.drawable.food_rice;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Rice bowl";
        catagory = "Fruit";
        picid = R.drawable.food_rice_bowl;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Risotto";
        catagory = "Fruit";
        picid = R.drawable.food_risotto;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Salad";
        catagory = "Fruit";
        picid = R.drawable.food_salad;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Salami";
        catagory = "Fruit";
        picid = R.drawable.food_salami;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Salmon";
        catagory = "Fruit";
        picid = R.drawable.food_salmon;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sandwich";
        catagory = "Fruit";
        picid = R.drawable.food_sandwich;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sandwich";
        catagory = "Fruit";
        picid = R.drawable.food_sandwich_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sauce";
        catagory = "Fruit";
        picid = R.drawable.food_sauce;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sausage";
        catagory = "Fruit";
        picid = R.drawable.food_sausage;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "seeds";
        catagory = "Fruit";
        picid = R.drawable.food_seeds;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "sesame";
        catagory = "Fruit";
        picid = R.drawable.food_sesame;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Shrimp";
        catagory = "Fruit";
        picid = R.drawable.food_shrimp;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sorbet";
        catagory = "Fruit";
        picid = R.drawable.food_sorbet;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Spaghetti";
        catagory = "Fruit";
        picid = R.drawable.food_spaghetti;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Spices";
        catagory = "Fruit";
        picid = R.drawable.food_spices;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Steak";
        catagory = "Fruit";
        picid = R.drawable.food_steak;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Strawberry";
        catagory = "Fruit";
        picid = R.drawable.food_strawberry;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sugar";
        catagory = "Fruit";
        picid = R.drawable.food_sugar;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sugar Cubes";
        catagory = "Fruit";
        picid = R.drawable.food_sugar_cube;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sushi";
        catagory = "Fruit";
        picid = R.drawable.food_sushi;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sushi";
        catagory = "Fruit";
        picid = R.drawable.food_sushi_1;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Sweet Potato";
        catagory = "Fruit";
        picid = R.drawable.food_sweet_potato;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Taco";
        catagory = "Fruit";
        picid = R.drawable.food_taco;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Tapas";
        catagory = "Fruit";
        picid = R.drawable.food_tapas;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Can";
        catagory = "Fruit";
        picid = R.drawable.food_tin_can;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Toast";
        catagory = "Fruit";
        picid = R.drawable.food_toast;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Tomato";
        catagory = "Fruit";
        picid = R.drawable.food_tomato;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Turkey";
        catagory = "Fruit";
        picid = R.drawable.food_turkey;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Vegan";
        catagory = "Fruit";
        picid = R.drawable.food_vegan;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Water";
        catagory = "Fruit";
        picid = R.drawable.food_water;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Watermelon";
        catagory = "Fruit";
        picid = R.drawable.food_watermelon;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Wrap";
        catagory = "Fruit";
        picid = R.drawable.food_wrap;
        days = 60;
        addPreset(name,catagory,picid,days);

        name = "Wrap";
        catagory = "Fruit";
        picid = R.drawable.food_wrap_2;
        days = 60;
        addPreset(name,catagory,picid,days);

    }
}