package com.svt.candle;

import java.util.Scanner;
import android.util.Log;

/**
 * Class which get timetable from internet.
 */

public class DataStorageInternet implements DataStorage {
	private TimeTable timeTable;
	private String dataFromDSI = null;
	
	/**
	 * Delete blank(null) characters from string.
	 */
	private String deleteBlankChar(String s) {
		final StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < s.length(); i++) {
			if ((int)dataFromDSI.charAt(i) == 0) {
				break;
			} else {
				sb.append(s.charAt(i));
			}
		}
		
	 return sb.toString();
	}
	
	public DataStorageInternet(String nickUrl) throws Exception{
		
		try {
			ThreadInternet ti = new ThreadInternet(nickUrl);
			ti.run();
			dataFromDSI = ti.getDataFromInternet();
			dataFromDSI = deleteBlankChar(dataFromDSI);
			timeTable = new TimeTable(new Scanner(dataFromDSI));
		} catch (Exception e) {
			Log.w("Debug", e.getMessage());
			Log.w("Debug", "Connection crashed in DataStorageInternet.");
			e.printStackTrace();
		}
	}

	public TimeTable getTimeTable() {
		return timeTable;
	}

}
