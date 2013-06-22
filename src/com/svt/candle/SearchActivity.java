package com.svt.candle;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity{
	private EditText getNick = null;
	private Button search = null;
	private DataStorageDatabase dataStorage = null;
	private Context thisContext = this;
	private ListView mainListView = null;
	private ArrayAdapter<String> listAdapter = null;
	ArrayList<String> vypis = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		dataStorage = DataStorageDatabase.getDataStorageDatabaseInstance(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	
		getNick = (EditText) findViewById(R.id.editTextSearch);
		getNick.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					getNick = (EditText) findViewById(R.id.editTextSearch);
					if (getNick.length() == 0) {
						Toast toastNoText = Toast.makeText(thisContext,
								R.string.no_text, Toast.LENGTH_SHORT);
						toastNoText.show();
					} else {
						// Find the ListView resource.
						mainListView = (ListView) findViewById(R.id.searchList);
						vypis = dataStorage.getSimilarStrings(getNick.getText().toString());
						// Create ArrayAdapter using the planet list.
						listAdapter = new ArrayAdapter<String>(thisContext,
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
										Intent myIntent = new Intent(SearchActivity.this, MainActivity.class);
										myIntent.putExtra("searchedString", vypis.get(arg2));
										startActivity(myIntent);
										finish();
									}
								});
					}
				} catch (Exception e) {
					Log.w("Debug-SearchAct-onClick", e.getMessage());
				}
			}
			@Override
			public void afterTextChanged(Editable s) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent backIntent = new Intent(SearchActivity.this, MainActivity.class);
		startActivity(backIntent);
		finish();
	}
}