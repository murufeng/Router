package luyou;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateuntil {
	public static long getdate(String time) throws ParseException {
		/*
		 * String time1 ="2018-07-05 00:00:00"; String time2
		 * ="2018-07-05 23:59:59";
		 * 
		 * long t1 = getLongByDate(time1); long t2 = getLongByDate(time2);
		 * System.out.println(t1+"-"+t2);
		 */
		long t = getLongByDate(time);
		return t;

	}

	// String典型的时间转换或时间戳
	public static long getLongByDate(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse(time);
		long ts = date.getTime() / 1000;
		return ts;
	}

}
