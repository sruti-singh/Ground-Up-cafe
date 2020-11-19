package com.hfad.starbuzz;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;


public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ListView listDrinks = getListView();

        try {
            SQLiteOpenHelper sdh = new StarbuzzDatabaseHelper(this);
            db = sdh.getReadableDatabase();

            cursor = db.query("Drink", new String[]{"id", "NAME"},
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);

            listDrinks.setAdapter(listAdapter);
        } catch (SQLiteException e) {
         Toast toast = Toast.makeText(this, "Database Unavialable", Toast.LENGTH_SHORT);
          toast.show();

        }
    }

      public void onDestroy(){
          super.onDestroy();
          cursor.close();
          db.close();
        }

    public void onListItemClick(ListView listView, View itemView, int position, long id){
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
        startActivity(intent);

    }
}