package com.svt.candle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends Activity {
	EditText getNick = null;
	Button search = null;
	DataStorageDatabase dataStorage = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
		search = (Button) findViewById(R.id.buttonSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				
				
				if (amIConnectedToInternet()) {
					try {
						getNick = (EditText) findViewById(R.id.editTextSearch);
						nickUrl = getNick.getText().toString();
						//createTimeTable();
						
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
							"You have not internet access", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
    	
    	
    }
}
