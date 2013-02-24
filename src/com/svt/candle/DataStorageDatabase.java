package com.svt.candle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import com.svt.candle.Database.DatabaseManager;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.CalendarContract.Instances;
import android.util.Log;

/**
 * Trieda starajuca sa o ziskanie a odovzdavanie dat
 * otestovat - verzia check file,internet a parsovanie file,internet
 */
public class DataStorageDatabase {
	private Context context;
	private TimeTable timeTable = null;
	private ArrayList<Lesson> lessons = null;
	private DatabaseManager dbManager = null;
	private static DataStorageDatabase instance = null;
	
	public static DataStorageDatabase getDataStorageDatabaseInstance(Context context){
		if(instance != null){
			return instance;
		} else {
			try {
				instance = new DataStorageDatabase(context);
			} catch (IOException e) {
				Log.d("constructor", "problem v konstruktore");
			}
		}
		return instance;
	}
	
	private DataStorageDatabase(Context context) throws IOException {
		this.context = context;
		dbManager = new DatabaseManager(context);
		Cursor cursorInfoRozvrh = dbManager.dajInfoRozvrhu();
		// aby sa dalo z cursora citat pri kontrole
		cursorInfoRozvrh.moveToFirst();
		// iba pre testovanie
		// dbManager.vymazRiadkyDatabazy();
		//
		/*
		 * kontrola - ak je databaza prazdna, pozrieme ci sme pripojeny na net,
		 * ak nie parsujeme interny subor, ak ano - parsujeme verziu na
		 * internete ak je aktualnejsia.
		 */

		if (cursorInfoRozvrh.getCount() == 0) {
			if (amIConnectedToInternet()) {
				if (checkVersionInternet().equals(checkVersionFile())) {
					Log.d("dsdb", "parse form file");
					parseFromFile();
				} else {
					Log.d("dsdb", "parse form internet");
					parseFromInternet();
				}
			} else {
				Log.d("dsdb", "parse form file2");
				parseFromFile();
			}
			dbManager.checkTable("hodiny");
			dbManager.checkTable("typy");
			dbManager.checkTable("typymiestnosti");
			dbManager.checkTable("ucitelia");
			dbManager.checkTable("predmety");
			dbManager.checkTable("miestnosti");
			dbManager.checkTable("hodkruzok");
			dbManager.checkTable("hoducitel");
			dbManager.checkTable("info");
		}
		/*
		 * kontrola - ak je databaza naplnena a mame pristup k internetu,
		 * skontrolujeme ci je aktualnejsia verzia - porovnavame databaza vs
		 * internet
		 */
		else {
			if (amIConnectedToInternet()) {
				if (!checkVersionInternet().equals(
						cursorInfoRozvrh.getString(0))) {
					parseFromInternet();
					Log.d("dsdb", "parse form internet2");
				}
			}
		}
	}

	/**
	 * Control internet connection.
	 */
	public Boolean amIConnectedToInternet() {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * zo stringu vrati verziu
	 */
	private String getVersionFromString(String input) {
		Log.d("dostal som sa tu", "kontroal");	
		Scanner scan = new Scanner(input);
		String frg = null;
		String delims = "[=]";
		String[] frgs = new String[2];
		while (scan.hasNext()) {
			frg = scan.next();
			frgs = frg.split(delims);
			if (frgs[0].equals("verzia")) {
				return frgs[1].substring(1, frgs[1].length() - 1);
			}
		}
		return null;
	}

	/**
	 * Zistujeme verziu xml v internom subore rozvrh.xml
	 */
	public String checkVersionFile() throws IOException {
		InputStreamReader nacitanySubor = new InputStreamReader(context
				.getResources().openRawResource(R.raw.sqlfile));
		int len = 100;
		char[] buffer = new char[len];
		if (nacitanySubor.read(buffer, 0, len) == -1) {
			Log.d("buffer", "buffer je null");
			return "";
		}
		return getVersionFromString(new String(buffer));
	}

	/**
	 * Zistujeme verziu xml na internete
	 */
	public String checkVersionInternet() throws MalformedURLException {
		
		ThreadInternet ti = new ThreadInternet(100);
		ti.run();
		String data = ti.getDataFromInternet();
		return getVersionFromString(data);
	}

	private void parse(InputStreamReader isr) {
		String string = "";
		BufferedReader br = new BufferedReader(isr);
		try {
			string = br.readLine();// nacitana verzia - tu nechceme
			string = br.readLine();
			while (string != null) {
				dbManager.insertTable(string);
				string = br.readLine();
			}
		} catch (IOException e) {
			Log.d("parse", e.getMessage());
		}

	}

	/**
	 * Rozparuje interny subor a ulozi data do databazy
	 */
	private void parseFromFile() {
		try {
			InputStreamReader isr = new InputStreamReader(context
					.getResources().openRawResource(R.raw.sqlfile));
			parse(isr);
		} catch (Exception e) {
			Log.d("parsovanieSQLfile", e.getMessage());
		}
	}

	/**
	 * Rozparuje xml z xml suboru na internete a ulozi data do databazy Na
	 * xperii x8 cas 2:30 so DataHadlerIf, cize ako pri subore
	 */
	private void parseFromInternet() {
		try {
			dbManager.vymazRiadkyDatabazy();
			ThreadInternet ti = new ThreadInternet();
			ti.run();
			InputStreamReader isr = new InputStreamReader(ti.getIS());
			parse(isr);
		} catch (Exception e) {
			Log.d("parseFromInternet", e.getMessage());
		}
	}

	public ArrayList<String> getSimilarStrings(String string){
		
		ArrayList<String> strings = new ArrayList<String>(); 
		Cursor cursor = dbManager.getSimilarRooms(string);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			strings.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return strings;
	}
	
	/**
	 * Vyhlada data v databaze podla miestnosti a vrati objekt TimeTable
	 * @return {@link TimeTable}
	 * @param string room_name
	 */
	public TimeTable getTimeTableAccordingTORoom(String room) {
		lessons = new ArrayList<Lesson>();

		Cursor cursor = dbManager.searchLessonsByRoom(room);

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
		return timeTable;

	}
}
