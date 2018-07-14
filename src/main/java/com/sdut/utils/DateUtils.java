package com.sdut.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * @author Administrator
 *
 */
public class DateUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String formatDate(Date date) {
		return sdf.format(date);
	}
}
