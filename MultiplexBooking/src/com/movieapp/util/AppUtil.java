package com.movieapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public enum AppUtil {

	Instance;
	String seatName[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };

	public String generateSeatName(int row, int col) {
		String name = seatName[row % 26] + (col + 1);
		return name;

	}

	public Date getDateFromString(String dateInString) {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date;
		try {
			date = format.parse(dateInString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String getStringFromDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = df.format(date);
		return dateString;
	}
	public String getStringFromDateForUpdation(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}
}
