package com.svt.candle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.R.integer;

public class SortedTimeTable {

	TimeTable basicTimeTable = null;
	
	ArrayList<Lesson> pon = new ArrayList<Lesson>();
	ArrayList<Lesson> uto = new ArrayList<Lesson>();
	ArrayList<Lesson> str = new ArrayList<Lesson>();
	ArrayList<Lesson> stv = new ArrayList<Lesson>();
	ArrayList<Lesson> pia = new ArrayList<Lesson>();
	
	public SortedTimeTable(TimeTable basicTimeTable) {
		this.basicTimeTable = basicTimeTable;
		divisionToDays();
	}
	
	private void divisionToDays() {
		for (int i = 0; i < basicTimeTable.tLessons.size(); i++) {
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("pon")){
				pon.add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("uto")){
				pon.add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("str")){
				pon.add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("stv")){
				pon.add(basicTimeTable.tLessons.get(i));
			}
			if(basicTimeTable.tLessons.get(i).day.equalsIgnoreCase("pia")){
				pon.add(basicTimeTable.tLessons.get(i));
			}
		}
	}
	
	public static class LessonComparator implements Comparator<Lesson> {
	    @Override
	    public int compare(Lesson l1, Lesson l2) {
	    	return Integer.parseInt(l1.from) - Integer.parseInt(l2.from);
	    }
	}
	private ArrayList<Lesson> sortLessonsAndAddEmpty(ArrayList<Lesson> list) {
		Collections.sort(list, new LessonComparator());
		for (int i = 1; i < list.size(); i++) {
			
		}
		
		return list;
	}
	
}
