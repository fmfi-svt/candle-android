package com.svt.candle;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	DataStorage dataStorage = null;
	// prednastavený rozvrh
	String nickUrl = "2i2";
	EditText getNick = null;

	/**
	 * Control wifi connection.
	 */
	public Boolean amIConnectedToWifi() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * Return string of timetable.
	 */
	public static String timeTableToString(DataStorage data) {
		if (data == null) {
			return "no data";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.getTimeTable().timeTable.size(); i++) {
			sb.append(data.getTimeTable().timeTable.get(i).day);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).duration);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).from);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).to);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).room);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).subjectName);
			sb.append(" ");
			sb.append(data.getTimeTable().timeTable.get(i).teachers);
			sb.append("\n\n");
		}
		return sb.toString();
	}

	/**
	 * Create timetable.
	 */
	public void createTimeTable() {
		try {
			if (amIConnectedToWifi()) {
				dataStorage = new DataStorageInternet(nickUrl);
			} else {
				dataStorage = new DataStorageSolid();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.w("Debug", e.getMessage());
		}
	}

	public void printTimeTable() {
		if (dataStorage != null) {
			printTimeTable.setText(timeTableToString(dataStorage));
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
		createTimeTable();
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
				if (amIConnectedToWifi()) {
					try {
						getNick = (EditText) findViewById(R.id.editTextSearch);
						nickUrl = getNick.getText().toString();
						createTimeTable();
						
						if(dataStorage.getTimeTable() != null){
							if(!dataStorage.getTimeTable().isEmpty()){
								printTimeTable();
							} else{
								final Toast toast = Toast.makeText(getApplicationContext(),
										"Timetable is empty!", Toast.LENGTH_SHORT);
								toast.show();
							}
						} else {
							final Toast toast = Toast.makeText(getApplicationContext(),
									"Timetable does not exist!", Toast.LENGTH_SHORT);
							toast.show();
						}
						
					} catch (Exception e) {
						Log.w("Debug", e.getMessage());
					}
				} else {
					final Toast toast = Toast.makeText(getApplicationContext(),
							"You have not wifi connection", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		printTimeTable();
	}
}
