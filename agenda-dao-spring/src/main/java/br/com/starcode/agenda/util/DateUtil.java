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

}
