package com.bs.wt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDate {
	
	protected static String YYYYMMDDHHMMSS = "yyyy-MM-dd hh:mm:ss";
	protected static String YYYYMMDD = "yyyy-MM-dd";
	protected static String START = " 15:00:01";
	protected static String END = " 15:04:59";
	
	public static void main(String[] args) {
		Date randomDate = randomDate();
		System.out.println(randomDate.toString());
	}

	public static Date randomDate() {
		String begin_ = simpleDateFormat(YYYYMMDD) + START;
		String end_ = simpleDateFormat(YYYYMMDD) + END;;
		try {
			Date start = simpleDateFormat(begin_, YYYYMMDDHHMMSS);// 开始日期
			Date end = simpleDateFormat(end_, YYYYMMDDHHMMSS);// 结束日期
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Date simpleDateFormat(String timeStr , String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	
	private static String simpleDateFormat(String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		if (rtnn == begin || rtnn == end) {
			return random(begin, end);
		}
		return rtnn;
	}
}
