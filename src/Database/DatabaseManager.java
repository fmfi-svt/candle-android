package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {
	private SQLiteDatabase database;
	private SQLiteOpenHelper dbHelper;
	private Context context;

	private static final String DATABASE_NAME = "rozvrh3";
	private static final int DATABASE_VERSION = 1;
	// tabulka pre typy hodin
	private static final String TB_TYPYH_NAME = "typy";
	private static final String COLUMN_TYPYH_ID = "id";
	private static final String COLUMN_TYPYH_POPIS = "popis";
	// tabulka pre typy miestnosti
	private static final String TB_TYPYM_NAME = "typyMiestnosti";
	private static final String COLUMN_TYPYM_ID = "id";
	private static final String COLUMN_TYPYM_POPIS = "popis";
	// tabulka pre ucitelov
	private static final String TB_UCITELIA_NAME = "ucitelia";
	private static final String COLUMN_UCITELIA_ID = "id";
	private static final String COLUMN_UCITELIA_PRIEZVISKO = "priezvisko";
	private static final String COLUMN_UCITELIA_MENO = "meno";
	private static final String COLUMN_UCITELIA_INICIALA = "iniciala";
	private static final String COLUMN_UCITELIA_KATEDRA = "katedra";
	private static final String COLUMN_UCITELIA_ODDELENIE = "oddelenie";
	private static final String COLUMN_UCITELIA_LOGIN = "login";
	// tabulka pre miestnosti
	private static final String TB_MIESTNOSTI_NAME = "miestnosti";
	private static final String COLUMN_MIESTNOSTI_NAZOV = "nazov";
	private static final String COLUMN_MIESTNOSTI_KAPACITA = "kapacita";
	private static final String COLUMN_MIESTNOSTI_TYP = "typ";
	private static final String COLUMN_MIESTNOSTI_AISKOD = "aiskod";
	// tabulka pre predmety
	private static final String TB_PREDMETY_NAME = "predmety";
	private static final String COLUMN_PREDMETY_ID = "id";
	private static final String COLUMN_PREDMETY_NAZOV = "nazov";
	private static final String COLUMN_PREDMETY_KOD = "kod";
	private static final String COLUMN_PREDMETY_KRATKY_KOD = "kratkykod";
	private static final String COLUMN_PREDMETY_KREDITY = "kredity";
	private static final String COLUMN_PREDMETY_ROZSAH = "rozsah";
	// tabulka pre hodiny
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
	private static final String COLUMN_HODINY_OLDID = "oldid";
	private static final String COLUMN_HODINY_POZNAMKA = "poznamka";
	// tabulka pre id ucitelov, ktori ucia danu hodinu
	private static final String TB_HODUCITEL = "hoducitel";
	private static final String COLUMN_HODUCITEL_IDHODINY = "idHodiny";
	private static final String COLUMN_HODUCITEL_IDUCITELA = "idUcitela";
	// tabulka pre id ucitelov, ktori ucia danu hodinu
	private static final String TB_HODKRUZOK = "hodkruzok";
	private static final String COLUMN_HODKRUZOK_IDHODINY = "idHodiny";
	private static final String COLUMN_HODKRUZOK_IDKRUZKU = "idKruzku";

	public DatabaseManager(Context context) {
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
			// vytvorenie tabulky typy hodin
			createQuery = "CREATE TABLE " + TB_TYPYH_NAME + "("
					+ COLUMN_TYPYH_ID + " TEXT," + COLUMN_TYPYH_POPIS
					+ " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky typy miestnosti
			createQuery = "CREATE TABLE " + TB_TYPYM_NAME + "("
					+ COLUMN_TYPYM_ID + " TEXT," + COLUMN_TYPYM_POPIS
					+ " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky ucitelia
			createQuery = "CREATE TABLE " + TB_UCITELIA_NAME + "("
					+ COLUMN_UCITELIA_ID + " TEXT,"
					+ COLUMN_UCITELIA_PRIEZVISKO + " TEXT,"
					+ COLUMN_UCITELIA_MENO + " TEXT,"
					+ COLUMN_UCITELIA_INICIALA + " TEXT,"
					+ COLUMN_UCITELIA_KATEDRA + " TEXT,"
					+ COLUMN_UCITELIA_ODDELENIE + " TEXT,"
					+ COLUMN_UCITELIA_LOGIN + " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky miestnosti
			createQuery = "CREATE TABLE " + TB_MIESTNOSTI_NAME + "("
					+ COLUMN_MIESTNOSTI_NAZOV + " TEXT,"
					+ COLUMN_MIESTNOSTI_KAPACITA + " TEXT,"
					+ COLUMN_MIESTNOSTI_TYP + " TEXT,"
					+ COLUMN_MIESTNOSTI_AISKOD + " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky predmety
			createQuery = "CREATE TABLE " + TB_PREDMETY_NAME + "("
					+ COLUMN_PREDMETY_ID + " TEXT," + COLUMN_PREDMETY_NAZOV
					+ " TEXT," + COLUMN_PREDMETY_KOD + " TEXT,"
					+ COLUMN_PREDMETY_KRATKY_KOD + " TEXT,"
					+ COLUMN_PREDMETY_KREDITY + " TEXT,"
					+ COLUMN_PREDMETY_ROZSAH + " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky predmety
			createQuery = "CREATE TABLE " + TB_HODINY_NAME + "("
					+ COLUMN_HODINY_ID + " TEXT," + COLUMN_HODINY_DEN
					+ " TEXT," + COLUMN_HODINY_ZACIATOK + " TEXT,"
					+ COLUMN_HODINY_KONIEC + " TEXT," + COLUMN_HODINY_MIESTNOST
					+ " TEXT," + COLUMN_HODINY_TRVANIE + " TEXT,"
					+ COLUMN_HODINY_PREDMET + " TEXT," + COLUMN_HODINY_UCITELIA
					+ " TEXT," + COLUMN_HODINY_KRUZKY + " TEXT,"
					+ COLUMN_HODINY_TYP + " TEXT," + COLUMN_HODINY_OLDID
					+ " TEXT," + COLUMN_HODINY_POZNAMKA + " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky hoducitel
			createQuery = "CREATE TABLE " + TB_HODUCITEL + "("
					+ COLUMN_HODUCITEL_IDHODINY + " TEXT,"
					+ COLUMN_HODUCITEL_IDUCITELA + " TEXT);";
			db.execSQL(createQuery);
			// vytvorenie tablulky hoducitel
			createQuery = "CREATE TABLE " + TB_HODKRUZOK + "("
					+ COLUMN_HODKRUZOK_IDHODINY + " TEXT,"
					+ COLUMN_HODKRUZOK_IDKRUZKU + " TEXT);";
			db.execSQL(createQuery);
		}

		// nebudeme upravovat databazu
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	// metoda vlozi do tabulky typy hodin jeden riadok
	public void insertTypHodin(String id, String popis) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_TYPYH_ID, id);
		newCon.put(COLUMN_TYPYH_POPIS, popis);
		database.insert(TB_TYPYH_NAME, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky HODUCITEL jeden riadok
	public void insertHodUcitel(String idHodiny, String idUcitela) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_HODUCITEL_IDHODINY, idHodiny);
		newCon.put(COLUMN_HODUCITEL_IDUCITELA, idUcitela);
		database.insert(TB_HODUCITEL, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky HODKRUZOK jeden riadok
	public void insertHodKruzok(String idHodiny, String idKruzku) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_HODKRUZOK_IDHODINY, idHodiny);
		newCon.put(COLUMN_HODKRUZOK_IDKRUZKU, idKruzku);
		database.insert(TB_HODKRUZOK, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky typy miestnosti jeden riadok
	public void insertTypMiestnosti(String id, String popis) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_TYPYM_ID, id);
		newCon.put(COLUMN_TYPYM_POPIS, popis);
		database.insert(TB_TYPYM_NAME, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky ucitelia jeden riadok
	public void insertUcitel(String id, String priezvisko, String meno,
			String iniciala, String katedra, String oddelenie, String login) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_UCITELIA_ID, id);
		newCon.put(COLUMN_UCITELIA_PRIEZVISKO, priezvisko);
		newCon.put(COLUMN_UCITELIA_MENO, meno);
		newCon.put(COLUMN_UCITELIA_INICIALA, iniciala);
		newCon.put(COLUMN_UCITELIA_KATEDRA, katedra);
		newCon.put(COLUMN_UCITELIA_ODDELENIE, oddelenie);
		newCon.put(COLUMN_UCITELIA_LOGIN, login);
		database.insert(TB_UCITELIA_NAME, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky miestnosti jeden riadok
	public void insertMiestnost(String nazov, String kapacita, String typ,
			String aiskod) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_MIESTNOSTI_NAZOV, nazov);
		newCon.put(COLUMN_MIESTNOSTI_TYP, typ);
		newCon.put(COLUMN_MIESTNOSTI_KAPACITA, kapacita);
		newCon.put(COLUMN_MIESTNOSTI_AISKOD, aiskod);
		database.insert(TB_MIESTNOSTI_NAME, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky predmetov jeden riadok
	public void insertPredmety(String id, String nazov, String kod,
			String kratkyKod, String kredity, String rozsah) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_PREDMETY_ID, id);
		newCon.put(COLUMN_PREDMETY_NAZOV, nazov);
		newCon.put(COLUMN_PREDMETY_KOD, kod);
		newCon.put(COLUMN_PREDMETY_KRATKY_KOD, kratkyKod);
		newCon.put(COLUMN_PREDMETY_KREDITY, kredity);
		newCon.put(COLUMN_PREDMETY_ROZSAH, rozsah);
		database.insert(TB_PREDMETY_NAME, null, newCon);
		database.close();
	}

	// metoda vlozi do tabulky hodin jeden riadok
	public void insertHodina(String id, String den, String zaciatok,
			String koniec, String miestnost, String trvanie, String predmet,
			String ucietelia, String kruzky, String typ, String oldId,
			String poznamka) {
		database = dbHelper.getWritableDatabase();
		ContentValues newCon = new ContentValues();
		newCon.put(COLUMN_HODINY_ID, id);
		newCon.put(COLUMN_HODINY_DEN, den);
		newCon.put(COLUMN_HODINY_ZACIATOK, zaciatok);
		newCon.put(COLUMN_HODINY_KONIEC, koniec);
		newCon.put(COLUMN_HODINY_MIESTNOST, miestnost);
		newCon.put(COLUMN_HODINY_TRVANIE, trvanie);
		newCon.put(COLUMN_HODINY_PREDMET, predmet);
		newCon.put(COLUMN_HODINY_UCITELIA, ucietelia);
		newCon.put(COLUMN_HODINY_KRUZKY, kruzky);
		newCon.put(COLUMN_HODINY_PREDMET, predmet);
		newCon.put(COLUMN_HODINY_TYP, typ);
		newCon.put(COLUMN_HODINY_OLDID, oldId);
		newCon.put(COLUMN_HODINY_POZNAMKA, poznamka);
		database.insert(TB_HODINY_NAME, null, newCon);
		database.close();
	}

	// na test = vrati z databazy vsetky hodiny v tvare objektu lesson
	public Cursor getLesson() {
		database = dbHelper.getReadableDatabase();
		 final String MY_QUERY = "SELECT h." + COLUMN_HODINY_DEN + ", h."
		 + COLUMN_HODINY_ZACIATOK + ", h." + COLUMN_HODINY_KONIEC
		 + ", h." + COLUMN_HODINY_TRVANIE + ", h."
		 + COLUMN_HODINY_MIESTNOST + ", m." + COLUMN_TYPYH_POPIS
		 + ", p." + COLUMN_PREDMETY_NAZOV + ", h."
		 + COLUMN_HODINY_UCITELIA + " FROM " + TB_HODINY_NAME + " h , "
		 + TB_TYPYH_NAME + " m , " + TB_PREDMETY_NAME + " p WHERE "
		 + "h." + COLUMN_HODINY_TYP + " = m." + COLUMN_TYPYH_ID
		 + " AND " + "p." + COLUMN_PREDMETY_ID + " = h."
		 + COLUMN_HODINY_PREDMET + " AND h." + COLUMN_HODINY_MIESTNOST
		 + " =\"A\" ";

		Cursor cursor = database.rawQuery(MY_QUERY, null);
		Log.d("cursor DBM", Integer.toString(cursor.getCount()));
		database.close();
		return cursor;
	}

	// zmaze vsetky riadky tabuliek, ale zachova sa struktura = nevola sa onCreate!
	public void vymazRiadkyDatabazy() {
		database = dbHelper.getWritableDatabase();
		database.delete(TB_TYPYH_NAME, null, null);
		database.delete(TB_TYPYM_NAME, null, null);
		database.delete(TB_UCITELIA_NAME, null, null);
		database.delete(TB_MIESTNOSTI_NAME, null, null);
		database.delete(TB_PREDMETY_NAME, null, null);
		database.delete(TB_HODINY_NAME, null, null);
		database.delete(TB_HODKRUZOK, null, null);
		database.delete(TB_HODUCITEL, null, null);
		database.close();
	}
	public void zmamDatabazu() {
		context.deleteDatabase(DATABASE_NAME);
	}
}