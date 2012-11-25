package com.svt.candle;


import java.util.Scanner;

import android.util.Log;
/**
 * Class creates and returns timetable from solid data without Internet connection.  
 */
public class DataStorageSolid implements DataStorage{

	private TimeTable timeTable;
	private Scanner scan2 = new Scanner(
			"Po 10:40 - 12:10 (2 v.hod.) M-VII Prednáška Algoritmy a dátové štruktúry M. Forišek \n"
					+ "Po 12:20 - 13:50 (2 v.hod.) M-223 Výber/Vol Matematická analýza (3) K. Rostás \n"
					+ "Ut 8:10 - 12:40 (6 v.hod.) KTVS Nezadaný typ Telesná výchova a šport (3) KTVŠ \n "
					+ "Ut 13:10 - 14:40 (2 v.hod.) M-II Prednáška Algoritmy a dátové štruktúry M. Forišek \n");


	public DataStorageSolid() {
		try {
			timeTable = new TimeTable(scan2);
		} catch (Exception e) {
			Log.w("Debug",e.getMessage());
		}
	}

	public TimeTable getTimeTable() {
		return timeTable;
	}
}
