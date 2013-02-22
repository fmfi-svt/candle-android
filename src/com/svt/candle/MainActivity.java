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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main activity
 */

public class MainActivity extends Activity {
	TextView printTimeTable = null;
	Button search = null;
	DataStorageDatabase dataStorage = null;
	// prednastavený rozvrh
	String nickUrl = "2i2";
	EditText getNick = null;
	// rozvrh zobrazujuci sa vzdy na zaciatku
	TimeTable current = null;
	
	
	/**
	 * Create timetable.
	 */
	public void initializeDataStorage() {
		try {
			if(dataStorage == null) {
				dataStorage = DataStorageDatabase.getDataStorageDatabaseInstance(this);
			}
			//provizorna nastavanie zatial
			current = dataStorage.getTimeTableAccordingTORoom("A");
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
		MenuItem searchMenuItem = menu.add("Search");
		MenuItem.OnMenuItemClickListener searchClickListener = new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.d("search", "funguje tlacitko");
				Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
				Serializable data = new Serializable() {
					
				};
				
				startActivity(myIntent);
				return false;
			}
		};

		searchMenuItem.setOnMenuItemClickListener(searchClickListener);
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

//		search = (Button) findViewById(R.id.buttonSearch);
//		search.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				if (amIConnectedToInternet()) {
//					try {
//						getNick = (EditText) findViewById(R.id.editTextSearch);
//						nickUrl = getNick.getText().toString();
//						//createTimeTable();
//						
//						if(dataStorage.getTimeTable() != null){
//							if(!dataStorage.getTimeTable().isEmpty()){
//								printTimeTable();
//							} else{
//								final Toast toast = Toast.makeText(getApplicationContext(),
//										"Timetable is empty!", Toast.LENGTH_SHORT);
//								toast.show();
//							}
//						} else {
//							final Toast toast = Toast.makeText(getApplicationContext(),
//									"Timetable does not exist!", Toast.LENGTH_SHORT);
//							toast.show();
//						}
//						
//					} catch (Exception e) {
//						Log.w("Debug", e.getMessage());
//					}
//				} else {
//					final Toast toast = Toast.makeText(getApplicationContext(),
//							"You have not internet access", Toast.LENGTH_SHORT);
//					toast.show();
//				}
//			}
//		});

		printTimeTable(current);
	}
}
