package com.svt.candle;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	 * Return string of timetable.
	 */
	public static String timeTableToString(TimeTable timeTable, Context context) {
		if (timeTable == null) {
			final Toast toast = Toast.makeText(context,
					"Nemate nastaveny defaultny rozvrh!", Toast.LENGTH_SHORT);
			toast.show();
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < timeTable.tLessons.size(); i++) {
			sb.append(timeTable.tLessons.get(i).day);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).duration);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).from);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).to);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).room);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).typeOfSubject);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).subjectName);
			sb.append(" ");
			sb.append(timeTable.tLessons.get(i).teachers);
			sb.append("\n\n");
		}
		return sb.toString();
	}

	/**
	 * Create timetable.
	 */
	public void initializeDataStorage() {
		try {
			if(dataStorage == null) dataStorage = new DataStorageDatabase(this);
			//provizorna nastavanie zatial
			current = dataStorage.getTTaccTORoom("A");
		} catch (Exception e) {
			Log.w("Debug", e.getMessage());
		}
	}

	public void printTimeTable(TimeTable timeTable) {
		if (dataStorage != null) {
			printTimeTable.setText(timeTableToString(timeTable, this));
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

		search = (Button) findViewById(R.id.buttonSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
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
			}
		});

		printTimeTable(current);
	}
}
