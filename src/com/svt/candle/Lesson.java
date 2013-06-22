package com.svt.candle;

/**
 * zakladne udaje hodiny, ktore sa budu zobrazovat
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

	
}