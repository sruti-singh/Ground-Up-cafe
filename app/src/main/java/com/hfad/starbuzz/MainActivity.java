package com.hfad.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

     private SQLiteDatabase db;
     private Cursor favoritesCursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 if(position == 0){
                     Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                             startActivity(intent);
                 }
            }
        };
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
        ListView listFavorites =(ListView)findViewById(R.id.list_favorites);

        try{
            SQLiteOpenHelper sdh= new StarbuzzDatabaseHelper(this);
            db = sdh.getReadableDatabase();
            favoritesCursor = db.query("DRINK", new String[] {"id", "NAME"}, "FAVORITE = 1",
                    null, null, null, null );

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,favoritesCursor,
                    new String[] {"NAME"}, new int[] {android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavialable", Toast.LENGTH_SHORT);
            toast.show();
     }
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                   Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                   intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                   startActivity(intent);
            }
        });
    }

    public  void onDestroy(View view){
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

    public void onRestart(){
        super.onRestart();
        try{
            SQLiteOpenHelper sdh= new StarbuzzDatabaseHelper(this);
            db = sdh.getReadableDatabase();
            Cursor newCursor = db.query("DRINK", new String[] {"id", "NAME"}, "FAVORITE = 1",
                    null, null, null, null );
            ListView listFavorites =(ListView)findViewById(R.id.list_favorites);
            CursorAdapter adapter = (CursorAdapter)listFavorites.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;

        }
        catch (SQLiteException e){
           Toast toast = Toast.makeText(this, "Database Unavialable", Toast.LENGTH_SHORT);
          toast.show();

        }

    }
}