package com.svt.candle;

import java.util.ArrayList;
/**
 * Class represent timetable.
 */
public class TimeTable {
	ArrayList<Lesson> tLessons = new ArrayList<Lesson>();

	//konstruktor pre DataStorageDatabase
	public TimeTable(ArrayList<Lesson> timeTable) {
		this.tLessons = timeTable;
	}
	
	public Boolean isEmpty() {
		if(this.tLessons.size() == 0){
			return true;
		} else {
			return false;
		}
	}
}
