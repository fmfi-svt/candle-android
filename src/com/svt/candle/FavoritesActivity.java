package com.svt.candle;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesActivity extends Activity {
	private ListView mainListView = null;
	private ArrayAdapter<String> listAdapter = null;
	ArrayList<String> vypis = null;
	private DataStorageDatabase dataStorage = null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        dataStorage = DataStorageDatabase.getDataStorageDatabaseInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favorites, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	dataStorage.vypistabulku("oblubene");
    	mainListView = (ListView) findViewById(R.id.favoritesList);
		vypis = dataStorage.getStringsFromFavorites();
		// Create ArrayAdapter using the planet list.
		listAdapter = new ArrayAdapter<String>(this,
				R.layout.row_search_layout, vypis);
			

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);
		mainListView.setClickable(true);
		
		mainListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(
							AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent myIntent = new Intent(FavoritesActivity.this, MainActivity.class);
						myIntent.putExtra("searchedString", vypis.get(arg2));
						startActivity(myIntent);
						finish();
					}
				});
	}
    
    @Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent backIntent = new Intent(FavoritesActivity.this, MainActivity.class);
		startActivity(backIntent);
		finish();
	}
}