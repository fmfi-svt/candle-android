package com.svt.candle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.widget.Toast;

public class SortedTimeTable {

	TimeTable basicTimeTable = null;
	
	ArrayList<ArrayList<Lesson>> days = new ArrayList<ArrayList<Lesson>>();
	
	
	
	public SortedTimeTable(TimeTable basicTimeTable) {
		this.basicTimeTable = basicTimeTable;
		for (int i = 0; i < 5; i++) {
			days.add(new ArrayList<Lesson>());
		}
		divisionToDays();
		for (int i = 0; i < 5; i++) {
			sortLessonsAndAddEmpty(days.get(i));
		}
		
	}
	
	private void divisionToDays() {
		for (int i = 0; i < basicTimeTable.tLessons.size(); i++) {
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("pon")){
				days.get(0).add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("uto")){
				days.get(1).add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("str")){
				days.get(2).add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("stv")){
				days.get(3).add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("pia")){
				days.get(4).add(basicTimeTable.tLessons.get(i));
			}
		}
	}
	
	public static class LessonComparator implements Comparator<Lesson> {
	    @Override
	    public int compare(Lesson l1, Lesson l2) {
	    	return Integer.parseInt(l1.from) - Integer.parseInt(l2.from);
	    }
	}
	private void sortLessonsAndAddEmpty(ArrayList<Lesson> list) {
		ArrayList<Lesson> listEmptyLessons = new ArrayList<Lesson>();
		Collections.sort(list, new LessonComparator());
		//zaciatocnu pridat
		for (int i = 0; i < list.size()-1; i++) {
			int r = Integer.parseInt(list.get(i+1).from) - Integer.parseInt(list.get(i).to);
			if(r > 0){
				listEmptyLessons.add(new Lesson("", list.get(i).to, list.get(i+1).from, r, "", "", "empty", ""));
			}
		}
		list.addAll(listEmptyLessons);
		Collections.sort(list, new LessonComparator());
	}
	
	public String timeTableToString(Context context) {
		if (this == null) {
			final Toast toast = Toast.makeText(context,
					"Nemate nastaveny defaultny rozvrh!", Toast.LENGTH_SHORT);
			toast.show();
			return "";
		}

		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < days.get(i).size(); j++) {
				sb.append(days.get(i).get(j).day);
				sb.append(" ");
				sb.append(days.get(i).get(j).duration);
				sb.append(" ");
				sb.append(days.get(i).get(j).from);
				sb.append(" ");
				sb.append(days.get(i).get(j).to);
				sb.append(" ");
				sb.append(days.get(i).get(j).room);
				sb.append(" ");
				sb.append(days.get(i).get(j).typeOfSubject);
				sb.append(" ");
				sb.append(days.get(i).get(j).subjectName);
				sb.append(" ");
				sb.append(days.get(i).get(j).teachers);
				sb.append("\n\n");
			}
		}
		return sb.toString();
	}
	
}
