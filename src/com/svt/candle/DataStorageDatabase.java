package com.svt.candle;

import java.io.InputStream;
import java.util.ArrayList;

import com.svt.candle.XMLParsing.ParserXML;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import Database.DatabaseManager;

public class DataStorageDatabase implements DataStorage {
	private Context context;
	private TimeTable timeTable = null;
	private ArrayList<Lesson> lessons = null;
	private InputStream nacitanySubor = null;
	private DatabaseManager dbManager = null;

	@Override
	public TimeTable getTimeTable() {
		if (timeTable == null)
			Log.d("datastoragedatabase", "timetable je null");
		return timeTable;
	}

	public DataStorageDatabase(Context context) {
		this.context = context;
		this.createTimeTable();
	}

	public void createTimeTable() {

		// nacitavanie suboru z res/raw pre parser
		nacitanySubor = context.getResources().openRawResource(R.raw.rozvrh);

		lessons = new ArrayList<Lesson>();
		// na pracu s databazou
		dbManager = new DatabaseManager(context);
		// kym vzdy tvorime databazu odznova - na testovanie, neskor pru
		// aktualizacii
		dbManager.vymazRiadkyDatabazy();
		// ked sa zmeni struktura databazy
		// context.deleteDatabase("rozvrh3");
		// do db vyparsuje rozvrh.txt
		ParserXML parser = new ParserXML(nacitanySubor, dbManager);
		Cursor cursor = dbManager.getLesson();

		Log.d("cursor", "pocet riadkov = " + cursor.getCount());
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Lesson lesson = new Lesson(cursor.getString(0),
					cursor.getString(1), cursor.getString(2),
					Integer.parseInt(cursor.getString(3)), cursor.getString(4),
					cursor.getString(5), cursor.getString(6),
					cursor.getString(7));
			lessons.add(lesson);
			cursor.moveToNext();
		}
		cursor.close();

		timeTable = new TimeTable(lessons);

	}
}
