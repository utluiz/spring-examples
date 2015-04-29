package br.com.starcode.agenda.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(removeMilis(date).getTime());
	}
	
	public static Date removeMilis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static Date mergeWithHour(Date date, String hour) {
		if (hour == null || hour.isEmpty()) {
			throw new RuntimeException("Hora inválida!");
		}
		
		String[] parts = hour.split(":");
		if (parts.length != 2) {
			throw new RuntimeException("Hora inválida!");
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, Integer.parseInt(parts[0]));
		c.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
		
		return c.getTime();
	}

}
