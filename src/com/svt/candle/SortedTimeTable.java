package com.svt.candle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.R.integer;

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
		Collections.sort(list, new LessonComparator());
		//zaciatocnu pridat
		for (int i = 0; i < list.size()-1; i++) {
			int r = Integer.parseInt(list.get(i+1).from) - Integer.parseInt(list.get(i).from);
			if(r > 0){
				list.add(new Lesson(null, list.get(i).to, list.get(i+1).from, r, null, null, "empty", null));
			}
		}
		Collections.sort(list, new LessonComparator());
	}
	
}
