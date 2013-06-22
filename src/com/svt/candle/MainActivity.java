package com.svt.candle;


import java.io.Serializable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

/**
 * Main activity
 */

public class MainActivity extends Activity {
	TextView printTimeTable = null;
	Boolean hladalSom = false;
	DataStorageDatabase dataStorage = null;
	// rozvrh zobrazujuci sa vzdy na zaciatku
	public static TimeTable current = null;
	
	
	/**
	 * Create timetable.
	 */
	public void initializeDataStorage() {
		try {
			if(dataStorage == null) {
				dataStorage = DataStorageDatabase.getDataStorageDatabaseInstance(this);
			}
			//provizorna nastavanie zatial
			current = dataStorage.getTimeTableAccordingTOString("A");
		} catch (Exception e) {
			Log.w("Debug", e.getMessage());
		}
	}

	public void printTimeTable(TimeTable timeTable) {
		if (dataStorage != null) {
			printTimeTable.setText(timeTable.timeTableToString(this));
		}
	}
	/**
	 * Set views
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		printTimeTable = (TextView) findViewById(R.id.printCandle);
		printTimeTable.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		MenuItem addFavoriteMenuItem = menu.add("Pridaj do oblubenych");
		addFavoriteMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.d("addFavorite", "funguje tlacitko");
				dataStorage.addFavoriteTimeTable(current.getId());
				return false;
			}
		});

		MenuItem FavoritesMenuItem = menu.add("Oblubene");
		FavoritesMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.d("Favorites", "funguje tlacitko");
				Intent myIntent = new Intent(MainActivity.this, FavoritesActivity.class);
				startActivity(myIntent);
				finish();
				return false;
			}
		});
		
		MenuItem searchMenuItem = menu.add("Search");
		searchMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.d("search", "funguje tlacitko");
				Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(myIntent);
				finish();
				return false;
			}
		});
		
		return true;
	}
	/**
	 * Create timetable.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		initializeDataStorage();
		printTimeTable(current);
		
	}
	/**
	 * Display timetable.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// kontrola, ci sme vyhladali, nejaky iny rozvrh, ak ano zobrazujeme ten
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String searchedString = extras.getString("searchedString");
		    TimeTable searched = dataStorage.getTimeTableAccordingTOString(searchedString);
			current = searched;
		}
		
//		Log.d("resume", current.timeTableToString(this));
		Log.d("resume", "vykonal sa resume");
		printTimeTable(current);
		Log.d("resume","vytlacil sa rozvr");
	}
	
}