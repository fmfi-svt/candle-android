package com.svt.candle;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;
/**
 * Class represent timetable.
 */
public class TimeTable {
	
	private String idTimetable = "";
	ArrayList<Lesson> tLessons = new ArrayList<Lesson>();

	public void setId(String id) {
		idTimetable = id;
	}
	
	public String getId() {
		return idTimetable;
	}
	
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
	
	/**
	 * Return string of timetable.
	 */
	public String timeTableToString(Context context) {
		if (this == null) {
			final Toast toast = Toast.makeText(context,
					"Nemate nastaveny defaultny rozvrh!", Toast.LENGTH_SHORT);
			toast.show();
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tLessons.size(); i++) {
			sb.append(tLessons.get(i).day);
			sb.append(" ");
			sb.append(tLessons.get(i).duration);
			sb.append(" ");
			sb.append(tLessons.get(i).from);
			sb.append(" ");
			sb.append(tLessons.get(i).to);
			sb.append(" ");
			sb.append(tLessons.get(i).room);
			sb.append(" ");
			sb.append(tLessons.get(i).typeOfSubject);
			sb.append(" ");
			sb.append(tLessons.get(i).subjectName);
			sb.append(" ");
			sb.append(tLessons.get(i).teachers);
			sb.append("\n\n");
		}
		return sb.toString();
	}
}