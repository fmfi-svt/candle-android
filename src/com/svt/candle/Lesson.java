package com.svt.candle;

import java.util.Scanner;
import android.util.Log;

/**
 * POUZIVANA NA PARSOVANIE TXT V PRVYCH VERZIACH Trieda reprezentujúca jednu
 * vyučovaciu časť, v rozvrhu zobrazované ako jedno okienko, volaná zvyčajne
 * konštruktorom triedy TimeTable. Očakávaný tvar riadku: Po 10:40 - 12:10 (2
 * v.hod.) M-VII Prednáška Algoritmy a dátové štruktúry M. Forišek
 */

public class Lesson {

	String day = "";
	String from = "";
	String to = "";
	Integer duration = 0;
	String room = "";
	String typeOfSubject = "";
	String subjectName = "";
	String teachers = "";

	// konstruktor pre DataStorageDatabase
	public Lesson(String day, String from, String to, Integer duration,
			String room, String typeOfSubject, String subjectName,
			String teachers) {
			this.day = day;
			this.from = from;
			this.to = to;
			this.duration = duration;
			this.room = room;
			this.typeOfSubject = typeOfSubject;
			this.subjectName = subjectName;
			this.teachers = teachers;
	}

	public Lesson(String source) throws WrongFormatException {
		Scanner scan = null;
		try {
			scan = new Scanner(source);

			if (scan.hasNext()) {
				day = scan.next();
			}

			if (!(day.equals((String) ("Po")) || day.equals((String) ("Ut"))
					|| day.equals((String) ("St"))
					|| day.equals((String) ("Št")) || day
						.equals((String) ("Pi")))) {
				Log.w("Debug", "WrongFormatException");
				throw new WrongFormatException(" day, instead of there is:"
						+ day);
			}
			if (scan.hasNext()) {
				from = scan.next();
			}

			// odstranenie pomlcka
			if (scan.hasNext()) {
				to = scan.next();
			}
			if (scan.hasNext()) {
				to = scan.next();
			}

			// zoberie 2 znak a pretypuje na int, prvy znak je zaciatok zatvorky
			if (scan.hasNext()) {
				duration = Character.getNumericValue(scan.next().charAt(1));
			}

			if (duration > 8) {
				throw new WrongFormatException(
						" duration, instead of there is:" + duration);
			}
			// preskocenie retazca viazuceho sa na dlzku hodiny
			if (scan.hasNext()) {
				room = scan.next();
			}
			if (scan.hasNext()) {
				room = scan.next();
			}

			String pom;
			if (scan.hasNext()) {
				typeOfSubject = scan.next();
			}

			while (scan.hasNext()) {
				pom = scan.next();
				if (Character.isUpperCase(pom.charAt(0))) {
					subjectName = pom;
					break;
				}
				typeOfSubject += " " + pom;
			}

			while (scan.hasNext()) {
				pom = scan.next();
				if (Character.isUpperCase(pom.charAt(0))) {
					teachers = pom;
					break;
				}
				subjectName += " " + pom;
			}
			// načítavanie vyučujúcich, ak je reťazec pridlhý pravdepodobne
			// chýba koniec riadku a načítavame ďalšie hodiny.
			if (scan.hasNext()) {
				while (scan.hasNext()) {
					teachers += " " + scan.next();
				}
			}

			if (teachers.length() > 40) {
				throw new WrongFormatException(
						" chýba koniec riadku, alebo zly format učiteľa");
			}
		} catch (WrongFormatException e) {
			Log.w("Debug", e.getMessage());
		} catch (Exception e) {
			if (scan != null) {
				scan.close();
			}
			Log.w("Debug", e.getMessage());
		}
	}
}