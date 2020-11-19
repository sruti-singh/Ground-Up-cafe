package com.hfad.starbuzz;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

 StarbuzzDatabaseHelper(Context context){

   super(context, DB_NAME, null, DB_VERSION);
 }

    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
     db.execSQL("create table Drink("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT,"
                +"DESCRIPTION TEXT,"
                +"IMAGE_RESOURCE_ID INTEGER);"
                );
         insertDrink(db,"Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
         insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
         insertDrink(db, "Filter","Highest quality beans roasted and brewed fresh", R.drawable.filter);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
         updateMyDatabase(db, oldVersion, newVersion);
    }
   private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
      if(oldVersion<1)
      {
          db.execSQL("create table Drink("
                  +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                  +"NAME TEXT,"
                  +"DESCRIPTION TEXT,"
                  +"IMAGE_RESOURCE_ID INTEGER);"
          );
          insertDrink(db,"Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
          insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
          insertDrink(db, "Filter","Highest quality beans roasted and brewed fresh", R.drawable.filter);

      }
      if(oldVersion <2){
          db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
      }
   }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }
}

