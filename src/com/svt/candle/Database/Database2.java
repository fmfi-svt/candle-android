package com.svt.candle.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database2 {
	private SQLiteDatabase database;
	private SQLiteOpenHelper dbHelper;
	private Context context;

	private static final String DATABASE_NAME = "rozvrh3";
	private static final int DATABASE_VERSION = 1;

	// tabulka pre hodiny
	// (`id`, `den`, `zaciatok`, `koniec`, `miestnost`, `trvanie`, `predmet`,
	// `ucitelia`, `kruzky`, `typ`, `kod_predmetu`,`kredity`, `rozsah`,
	// `kapacita_mistnosti`, `typ_miestnosti`) SELECT '328'
	private static final String TB_HODINY_NAME = "hodiny";
	private static final String COLUMN_HODINY_ID = "id";
	private static final String COLUMN_HODINY_DEN = "den";
	private static final String COLUMN_HODINY_ZACIATOK = "zaciatok";
	private static final String COLUMN_HODINY_KONIEC = "koniec";
	private static final String COLUMN_HODINY_MIESTNOST = "miestnost";
	private static final String COLUMN_HODINY_TRVANIE = "trvanie";
	private static final String COLUMN_HODINY_PREDMET = "predmet";
	private static final String COLUMN_HODINY_UCITELIA = "ucitelia";
	private static final String COLUMN_HODINY_KRUZKY = "kruzky";
	private static final String COLUMN_HODINY_TYP = "typ";
	private static final String COLUMN_HODINY_KOD_PREDMETU = "kod_predmetu";
	private static final String COLUMN_HODINY_KREDITY = "kredity";
	private static final String COLUMN_HODINY_ROZSAH = "rozsah";
	private static final String COLUMN_HODINY_KAPACITA_MIESTNOSTI = "kapacita_mistnosti";
	private static final String COLUMN_HODINY_TYP_MIESTRNOSTI = "typ_miestnosti";

	// tabulka pre id ucitelov, ktori ucia danu hodinu
	// (`idHodiny`, `idUcitela`, `katedra`, `meno`, `oddelenie`, `priezvisko`)
	private static final String TB_HODUCITEL = "hoducitel";
	private static final String COLUMN_HODUCITEL_IDHODINY = "idHodiny";
	private static final String COLUMN_HODUCITEL_IDUCITELA = "idUcitela";
	private static final String COLUMN_HODUCITEL_PRIEZVISKO = "priezvisko";
	private static final String COLUMN_HODUCITEL_MENO = "meno";
	private static final String COLUMN_HODUCITEL_KATEDRA = "katedra";
	private static final String COLUMN_HODUCITEL_ODDELENIE = "oddelenie";

	// tabulka pre id ucitelov, ktori ucia danu hodinu
	private static final String TB_HODKRUZOK = "hodkruzok";
	private static final String COLUMN_HODKRUZOK_IDHODINY = "idHodiny";
	private static final String COLUMN_HODKRUZOK_IDKRUZKU = "idKruzku";

	// tabulka pre oblubene rozvry
	private static final String TB_OBLUBENE = "oblubene";
	private static final String COLUMN_OBLUBENE_ID = "id";
	private static final String COLUMN_OBLUBENE_NAME = "name";
	
	// tabulka pre oblubene rozvry
		private static final String TB_HLAVNY = "hlavny";
		private static final String COLUMN_HLAVNY_NAZOV = "name";

	// tabulka pre info o rozvrhu
	private static final String TB_INFO = "info";
	private static final String COLUMN_INFO_VERZIA = "verzia";
	private static final String COLUMN_INFO_SKOLROK = "skolrok";
	private static final String COLUMN_INFO_SEMESTER = "semester";

	public Database2(Context context) {
		this.context = context;
		dbHelper = new MySqliteHelper(context);
	}

	public void close() {
		dbHelper.close();
	}

	public class MySqliteHelper extends SQLiteOpenHelper {

		public MySqliteHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createQuery = null;
			// (`id`, `den`, `zaciatok`, `koniec`, `miestnost`, `trvanie`,
			// `predmet`, `ucitelia`, `kruzky`, `typ`, `kod_predmetu`,`kredity`,
			// `rozsah`,
			// `kapacita_mistnosti`, `typ_miestnosti`) SELECT '328'

			// vytvorenie tablulky hodiny
			createQuery = "CREATE TABLE " + TB_HODINY_NAME + "("
					+ COLUMN_HODINY_ID + " TEXT," + COLUMN_HODINY_DEN
					+ " TEXT," + COLUMN_HODINY_ZACIATOK + " TEXT,"
					+ COLUMN_HODINY_KONIEC + " TEXT," + COLUMN_HODINY_MIESTNOST
					+ " TEXT," + COLUMN_HODINY_TRVANIE + " TEXT,"
					+ COLUMN_HODINY_PREDMET + " TEXT," + COLUMN_HODINY_UCITELIA
					+ " TEXT," + COLUMN_HODINY_KRUZKY + " TEXT,"
					+ COLUMN_HODINY_TYP + " TEXT," + COLUMN_HODINY_KOD_PREDMETU
					+ " TEXT," + COLUMN_HODINY_KREDITY + " TEXT,"
					+ COLUMN_HODINY_ROZSAH + " TEXT,"
					+ COLUMN_HODINY_KAPACITA_MIESTNOSTI + " TEXT,"
					+ COLUMN_HODINY_TYP_MIESTRNOSTI + " TEXT);";
			db.execSQL(createQuery);

			// vytvorenie tablulky hoducitel (`idHodiny`, `idUcitela`,
			// `katedra`, `meno`, `oddelenie`, `priezvisko`)
			createQuery = "CREATE TABLE " + TB_HODUCITEL + "("
					+ COLUMN_HODUCITEL_IDHODINY + " TEXT,"
					+ COLUMN_HODUCITEL_IDUCITELA + " TEXT,"
					+ COLUMN_HODUCITEL_KATEDRA + " TEXT,"
					+ COLUMN_HODUCITEL_MENO + " TEXT,"
					+ COLUMN_HODUCITEL_ODDELENIE + " TEXT,"
					+ COLUMN_HODUCITEL_PRIEZVISKO + " TEXT);";
			db.execSQL(createQuery);

			// vytvorenie tablulky hodkruzok
			createQuery = "CREATE TABLE " + TB_HODKRUZOK + "("
					+ COLUMN_HODKRUZOK_IDHODINY + " TEXT,"
					+ COLUMN_HODKRUZOK_IDKRUZKU + " TEXT);";
			db.execSQL(createQuery);

			// vytvorenie tabulky info, sluzi na uchovanie verzie,skrok a
			// semester
			createQuery = "CREATE TABLE " + TB_INFO + "(" + COLUMN_INFO_VERZIA
					+ " TEXT," + COLUMN_INFO_SKOLROK + " TEXT,"
					+ COLUMN_INFO_SEMESTER + " TEXT);";
			db.execSQL(createQuery);

			// vytvorenie tabulky pre oblubene rozvrhy
			createQuery = "CREATE TABLE " + TB_OBLUBENE + "("
					+ COLUMN_OBLUBENE_ID + " AUTO_INCREMENT,"
					+ COLUMN_OBLUBENE_NAME + " TEXT);";
			db.execSQL(createQuery);
			
			// vytvorenie tabulky pre hlavny rozvrh
						createQuery = "CREATE TABLE " + TB_HLAVNY + "("
								+ COLUMN_OBLUBENE_NAME + " TEXT);";
						db.execSQL(createQuery);

			// indexy na zrychlenie databzy
			createQuery = "CREATE INDEX i1 ON " + TB_HODKRUZOK + " ("
					+ COLUMN_HODKRUZOK_IDKRUZKU + ");";
			db.execSQL(createQuery);
			createQuery = "CREATE INDEX i2 ON " + TB_HODUCITEL + " ("
					+ COLUMN_HODUCITEL_PRIEZVISKO + ");";
			db.execSQL(createQuery);
		}

		// nebudeme upravovat strukturu databazy
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	// vyhladavanie v databaze podla miestnosti
	public Cursor searchLessonsByRoom(String room) {
		database = dbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT h." + COLUMN_HODINY_DEN + ", h."
				+ COLUMN_HODINY_ZACIATOK + ", h." + COLUMN_HODINY_KONIEC
				+ ", h." + COLUMN_HODINY_TRVANIE + ", h."
				+ COLUMN_HODINY_MIESTNOST + ", h." + COLUMN_HODINY_TYP + ", h."
				+ COLUMN_HODINY_PREDMET + ", h." + COLUMN_HODINY_UCITELIA
				+ " FROM " + TB_HODINY_NAME + " h WHERE " + " h."
				+ COLUMN_HODINY_MIESTNOST + " =\"" + room + "\" ";

		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor searchLessonsByRoom", Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	// vyhladavanie v databaze podla kruzkov
	public Cursor searchLessonsByClass(String kruzok) {
		database = dbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT h." + COLUMN_HODINY_DEN + ", h."
				+ COLUMN_HODINY_ZACIATOK + ", h." + COLUMN_HODINY_KONIEC
				+ ", h." + COLUMN_HODINY_TRVANIE + ", h."
				+ COLUMN_HODINY_MIESTNOST + ", h." + COLUMN_HODINY_TYP + ", h."
				+ COLUMN_HODINY_PREDMET + ", h." + COLUMN_HODINY_UCITELIA
				+ " FROM " + TB_HODINY_NAME + " h , " + TB_HODKRUZOK
				+ " k WHERE " + "h." + COLUMN_HODINY_ID + " = k."
				+ COLUMN_HODKRUZOK_IDHODINY + " AND k."
				+ COLUMN_HODKRUZOK_IDKRUZKU + " = \"" + kruzok + "\"";

		Cursor cursor = database.rawQuery(MY_QUERY, null);

		Log.d("cursor searchLessonsByClass",
				Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	// PRIDAT POROVNANIIE AJ PODLA MENA NIE LEN PODLA PRIEZVISKA!!!!!!!!!!
	// vyhladavanie v databaze podla ucitelov
	public Cursor searchLessonsByTeacher(String priezvisko, String meno) {
		database = dbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT h." + COLUMN_HODINY_DEN + ", h."
				+ COLUMN_HODINY_ZACIATOK + ", h." + COLUMN_HODINY_KONIEC
				+ ", h." + COLUMN_HODINY_TRVANIE + ", h."
				+ COLUMN_HODINY_MIESTNOST + ", h." + COLUMN_HODINY_TYP + ", h."
				+ COLUMN_HODINY_PREDMET + ", h." + COLUMN_HODINY_UCITELIA
				+ " FROM " + TB_HODINY_NAME + " h , " + TB_HODUCITEL
				+ " k WHERE " + "k." + COLUMN_HODUCITEL_IDHODINY + " = h."
				+ COLUMN_HODINY_ID + " AND k." + COLUMN_HODUCITEL_PRIEZVISKO
				+ " = \"" + priezvisko + "\" AND k." + COLUMN_HODUCITEL_MENO
				+ " = \"" + meno + "\"";

		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor searchLessonsByTeacher",
				Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	public Cursor dajInfoRozvrhu() {
		database = dbHelper.getWritableDatabase();
		final String MY_QUERY = "SELECT * FROM " + TB_INFO;
		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor DBM", Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	// zmaze vsetky riadky tabuliek, ale zachova sa struktura = nevola sa
	// onCreate!
	public void vymazRiadkyDatabazy() {
		database = dbHelper.getWritableDatabase();
		database.delete(TB_HODINY_NAME, null, null);
		database.delete(TB_OBLUBENE, null, null);
		database.delete(TB_HODKRUZOK, null, null);
		database.delete(TB_HODUCITEL, null, null);
		database.delete(TB_HLAVNY, null, null);
		database.delete(TB_INFO, null, null);
		database.close();
	}

	public void insertTable(String string) {
		database = dbHelper.getWritableDatabase();
		database.execSQL(string);
	}

	// testovanie tabuliek
	public Cursor checkTable(String TB_NAME) {
		database = dbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT * FROM " + TB_NAME;

		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor DBM", Integer.toString(cursor.getCount()));
		database.close();
		// cursor.moveToFirst();
		// for (int i = 0; i < cursor.getCount(); i += 100) {
		//
		// for (int j = 0; j < cursor.getColumnCount(); j++) {
		// Log.d("test",cursor.getColumnName(j) + " " +cursor.getString(j));
		// }
		// Log.d("test", "????????????????????????????");
		// cursor.moveToNext();
		// }
		cursor.close();
		return cursor;
	}
	public Cursor checkTable2(String TB_NAME) {
		database = dbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT * FROM " + TB_NAME;

		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor DBM", Integer.toString(cursor.getCount()));
		database.close();
		 cursor.moveToFirst();
		 for (int i = 0; i < cursor.getCount(); i ++) {
		
		 for (int j = 0; j < cursor.getColumnCount(); j++) {
		 Log.d("test",cursor.getColumnName(j) + " " +cursor.getString(j));
		 }
		 Log.d("test", "????????????????????????????");
		 cursor.moveToNext();
		 }
		cursor.close();
		return cursor;

	}

	// vymaze databazu aj zo strukturou
	public void zmamDatabazu() {
		context.deleteDatabase(DATABASE_NAME);
	}

	public Cursor searchByQuery(String query) {
		database = dbHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		Log.d("cursor DBM", Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	public Cursor getSimilarRooms(String room) {
		final String MY_QUERY = "SELECT DISTINCT " + COLUMN_HODINY_MIESTNOST
				+ " FROM " + TB_HODINY_NAME + " WHERE "
				+ COLUMN_HODINY_MIESTNOST + " LIKE \"" + room + "%\"";
		return searchByQuery(MY_QUERY);
	}

	public Cursor getSimilarTeachers(String teacher) {
		final String MY_QUERY = "SELECT DISTINCT "
				+ COLUMN_HODUCITEL_PRIEZVISKO + " , " + COLUMN_HODUCITEL_MENO
				+ " FROM " + TB_HODUCITEL + " WHERE "
				+ COLUMN_HODUCITEL_PRIEZVISKO + " LIKE \"" + teacher + "%\"";
		return searchByQuery(MY_QUERY);
	}

	public Cursor getSimilarClass(String string) {
		final String MY_QUERY = "SELECT DISTINCT " + COLUMN_HODKRUZOK_IDKRUZKU
				+ " FROM " + TB_HODKRUZOK + " WHERE "
				+ COLUMN_HODKRUZOK_IDKRUZKU + " LIKE \"" + string + "%\"";
		return searchByQuery(MY_QUERY);
	}

	public void addFavoriteTimeTable(String name) {
		database = dbHelper.getWritableDatabase();
		final String MY_QUERY = "INSERT INTO " + TB_OBLUBENE
				+ " VALUES (null, '" + name + "')";
		database.execSQL(MY_QUERY);
		database.close();
	}
	
	public void makeBasicTimeTable(String name) {
		database = dbHelper.getWritableDatabase();
		String MY_QUERY = "DELETE FROM " + TB_HLAVNY;
		database.execSQL(MY_QUERY);
		MY_QUERY = "INSERT INTO " + TB_HLAVNY
				+ " VALUES ('" + name + "')";
		database.execSQL(MY_QUERY);
		database.close();
	}
	
	public void removeFavoriteTimeTable(String name) {
		Log.d("removeFavoriteTimeTable","mazem zaznam");
		database = dbHelper.getWritableDatabase();
//		final String MY_QUERY = "Delete FROM " + TB_OBLUBENE
//				+ " WHERE " + COLUMN_OBLUBENE_NAME + " = ' " + name + " ' ";
//		database.execSQL(MY_QUERY);
		database.delete(TB_OBLUBENE, COLUMN_OBLUBENE_NAME + " = ' " + name + " ' ", null);
		database.close();
	}
	
	public Cursor getNamesFromFavorites() {
		final String MY_QUERY = "SELECT " + COLUMN_OBLUBENE_NAME
				+ " FROM " + TB_OBLUBENE;
		return searchByQuery(MY_QUERY);
	}
	
	public Cursor getBasicTimeTable(){
		final String MY_QUERY = "SELECT " + COLUMN_HLAVNY_NAZOV
				+ " FROM " + TB_HLAVNY;
		return searchByQuery(MY_QUERY);
	}
}