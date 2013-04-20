package com.svt.candle;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
}
