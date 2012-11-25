package com.svt.candle;

import java.util.ArrayList;
import java.util.Scanner;
import android.util.Log;
/**
 * Class represent timetable.
 */
public class TimeTable {
	ArrayList<Lesson> timeTable = new ArrayList<Lesson>();

	public TimeTable(Scanner scan) {
		try {
			while(scan.hasNext()){
				timeTable.add(new Lesson(scan.nextLine()));
			}
			if(timeTable.size() == 0){
				Log.w("Debug","Rozvrh je prázdny");
			}
		} catch (Exception e) {
			if (scan != null) {
			}
			Log.w("Debug", e.getMessage());
		} finally{
			scan.close();
		}
	}
}
