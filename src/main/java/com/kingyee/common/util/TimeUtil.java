/*
 * Created on 2005-7-13
 * 
 * 时间工具类.
 */
package com.kingyee.common.util;

import com.kingyee.common.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 时间工具
 * 
 * @author 马劼
 */
public class TimeUtil {


    /** 日期格式 yyyy-MM-dd 2013-06-16 */
    public static String FORMAT_DATE = "yyyy-MM-dd";
    /** 日期格式 yyyy-MM-dd HH:mm:ss 2013-06-16 09:54:02 */
    public static String FORMAT_DATETIME_FULL = "yyyy-MM-dd HH:mm:ss";
    /** 日期格式 yyyy-MM-dd HH:mm:ss 2013-06-16 09:54:02 */
    public static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";
    /** 日期格式 MM-dd 06-16 */
    public static String FORMAT_DATE_ONLY_DAY = "MM-dd";
    /** 日期格式 HH:mm 09:54 */
    public static String FORMAT_DATE_ONLY_HOUR = "HH:mm";

	/**
	 * 得到当前时间
	 * 
	 * @return nowstr
	 */
	public static Date getNowTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * SimpleDateFormat到格式的日期
	 * 
	 * @param f 格式
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getDateFormatter(String f) {
		SimpleDateFormat formatter = new SimpleDateFormat(f);
		// workaround for bug
		formatter.setTimeZone(java.util.TimeZone.getDefault());
		// don't alllow things like 1/35/99
		formatter.setLenient(false);
		return formatter;
	}

	/**
	 * 获得当前时间Calendar格式
	 * 
	 * @param d
	 * @return now date
	 */
	public static Calendar getCalendar(Date d) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		return now;
	}

	/**
	 * 得到指定格式的日期字符串
	 * 
	 * @param f 格式
	 * @return nowDate String.
	 */
	public static String getNowTime(String f) {
		return getDateFormatter(f).format(getNowTime());
	}

	/**
	 * 得到当前时间的毫秒部分
	 * 
	 * @return str
	 */
	public static String getSecond() {
		return getNowTime("sss");
	}

	/**
	 * String到Date格式的转换
	 * 
	 * @param d 日期
	 * @param f 格式
	 * @return date Date
	 */
	public static Date stringToDate(String d, String f) {
		// will throw exception if can't parse
		try {
			return getDateFormatter(f).parse(d);
		} catch (ParseException e) {
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 *  string 到 date 的转换
     * @param d 日期
     * @param f 格式
	 * @return
	 */
	public static Long stringToLong(String d, String f) {
		if(d == null){
			return null;
		}
		Date date = stringToDate(d,f);
		if(date == null) {
			return null;
		}
		return date.getTime();
	}

	/**
	 * date到String格式的转换
	 *
     * @param d 日期
     * @param f 格式
	 * @return date String
	 */
	public static String dateToString(Date d, String f) {
		return getDateFormatter(f).format(d);
	}
	
	/**
	 *  Long到String格式的转换
     * @param d 日期
     * @param f 格式
	 * @return date String
	 */
	public static String longToString(Long d, String f) {
		if(d == null){
		    return "";
        }
		return dateToString(longToDate(d),f);
	}

	/**
	 * 当前时间的long格式,除以1000以Integer表示. 主要用于与c++交换日期,缺点是没有毫秒值.
	 * 
	 * @return longDate Integer
	 */
	public static Integer nowDateToInteger() {
		return Integer.valueOf(String.valueOf(getNowTime().getTime() / 1000));
	}

	/**
	 * 转换指定日期到Integer格式
	 * 
	 * @param date
	 * @return date Integer
	 */
	public static Integer dateToInteger(Date date) {
		if (date == null) {
			return new Integer(0);
		} else {
			return Integer.valueOf(String.valueOf(date.getTime() / 1000));
		}
	}

	/**
	 * integer to Date 主要用于从c++中传入的日期类型转换为Date类型.
	 * 
	 * @param date
	 * @return date Date
	 */
	public static Date integerToDate(Integer date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(((long) date.intValue()) * 1000);
		return cal.getTime();
	}

	/**
	 * 转换现在的时间为Long格式 以毫秒为单位计算,从1970年1月1日0时0分0秒0000毫秒算起.
	 * 
	 * @return longDate long
	 */
	public static long dateTolong() {
		return getNowTime().getTime();
	}

	/**
	 * @see this.dateTolong
	 * 
	 * @return longDate Long
	 */
	public static Long dateToLong() {
		return new Long(getNowTime().getTime());
	}

	public static Long dateToLong(String format) {
		String date = getNowTime(format);
		return stringToLong(date, format);
	}

	/**
	 * this.dateTolong
	 * 
	 * @return longDate string
	 */
	public static String dateToLongString() {
		return String.valueOf(getNowTime().getTime());
	}

	/**
	 * long转换为日期
	 *
     * @param d 日期
	 * @return date Date
	 */
	public static Date longToDate(long d) {
		return new Date(d);
	}

	/**
	 * long转换为日期
	 *
     * @param d 日期
	 * @return date Date
	 */
	public static Date longToDate(Long d) {
		return new Date(d.longValue());
	}

	/**
	 * lang or integer型转换成date string
	 * @param str
	 * @param format
	 * @return string date
	 */
	public static String getDate(String str, String format) {
		if (str.length() > 11) {
			return dateToString(longToDate(Long.valueOf(str)), format);
		} else {
			return dateToString(integerToDate(Integer.valueOf(str)), format);
		}
	}

	/**
	 * 取得某月最后一天的日期整数值
	 * @param year		年(yyyy)
	 * @param month	月(mm或m)
	 * @return int			日期整数值
	 */
	public static int getMaxDayOfMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算两个long型的时间之间的差（**天 ** 小时 ** 分钟）
	 * @param starttime   开始时间
	 * @param endtime	结束时间
	 * @return
	 */
	public static String getTimeDiff(long starttime, long endtime) {
        long left = endtime - starttime;
		if (left < 0) {
            left = 0;
		}
		return getTimeDesc(left);
	}

    /**
     * 取得time代表x天x小时x分钟x秒
     * @param time 毫秒
     * @return
     */
	public static String getTimeDesc(long time){
        StringBuffer sb = new StringBuffer();
        long daynum = time / (24 * 60 * 60 * 1000);
        time = time - daynum * (24 * 60 * 60 * 1000);
        long hournum = time / (3600 * 1000);
        time = time - hournum * (3600 * 1000);
        long minnum = time / (60 * 1000);
        time = time - minnum * (60 * 1000);
        long second = time / (1000);
        if (daynum > 0) {
            sb.append(daynum + "天");
        }
        if (hournum > 0) {
            sb.append(hournum + "小时");
        }
        if (minnum > 0) {
            sb.append(minnum + "分钟");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }

        if(sb.length() == 0){
            sb.append("0分钟");
        }

        return sb.toString();
    }

	public static Long getBeforeDate(Date date, int dayCount) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.DATE, dayCount);
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取当前日期之后next天的日期long值
	 * @param next--指定多少天
	 * @return
	 */
	public static Long getNextAssignDate(long next) {
		Long nowTime = getNowDate();
		return nowTime + (24 * 60 * 60 * 1000 * next);
	}
	
	/**
	 * 获得当前日期的零点的long型
	 * 
	 * @return
	 */
	public static long getNowDate(){
		return stringToLong(dateToString(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"); 
	}
	
	
	/**
	 * SimpleDateFormat到格式的日期
	 *
     * @param f 格式
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getDateFormatter(String f, Locale locale) {
		SimpleDateFormat formatter = new SimpleDateFormat(f, locale);
		// workaround for bug
		formatter.setTimeZone(java.util.TimeZone.getDefault());
		// don't alllow things like 1/35/99
		formatter.setLenient(false);
		return formatter;
	}
	
	
	/**
	 *  发动态  刚刚  一分钟前..
	 * @param date
	 * @return
	 */
	public static String getDynTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    int minute = calendar.get(Calendar.MINUTE);
	    
	    long time = date.getTime();
	    long now = System.currentTimeMillis();
	    long s = now-time;
	    
	    calendar.setTime(new Date());
	    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    long now0 = calendar.getTime().getTime();
	    
	    if(s<60*1000){
	    	return "刚刚";
	    }else if(s<60*1000*60){
	    	return s/(1000*60)+"分钟前";
	    }else if(s<60*1000*60*24){
	    	return s/(1000*60*60)+"小时前";
	    }else if(time>now0-1000*60*60*24){
	    	return "昨天"+hour+"点"+minute+"分";
	    }else if(time>now0-1000*60*60*24*2){
	    	return "前天"+hour+"点"+minute+"分";
	    }
	    return year%100+"年"+(month+1)+"月"+day+"日 "+hour +"点"+minute+"分";
	}
	
	
	
	
	/**
	 * date到String格式的转换
	 *
     * @param d 日期
     * @param f 格式
	 * @return date String
	 */
	public static String dateToString(Date d, String f, Locale locale) {
		return getDateFormatter(f, locale).format(d);
	}
	/**
	 * 本周的星期一
	 * @param date
	 * @return
	 */
	public static Long getMonday4Week(Date date){
		date = new Date(stringToLong(dateToString(date, TimeUtil.FORMAT_DATE), TimeUtil.FORMAT_DATE));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		if(weekday == 1){
			return getBeforeDate(date, -6);
		}else if(weekday == 2){
			return stringToLong(dateToString(date, TimeUtil.FORMAT_DATE), TimeUtil.FORMAT_DATE);
		}else {
			return getBeforeDate(date, 2 - weekday);
		}

	}

	// 获取当月第一天和最后一天
	public static Map<String,Object> getThisMonthTime() {
		Map<String,Object> parp = new HashMap<String,Object>();
		Calendar cale = null;
		cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(TimeUtil.FORMAT_DATE);
		Long firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = cale.getTime().getTime();
		parp.put("firstday",firstday);
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
//		lastday = format.format(cale.getTime());
		lastday = cale.getTime().getTime();
		parp.put("lastday",lastday);
//		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
		return parp;
	}


	/**
	 * 由出生日期计算年龄
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday){
		Date birthDay = TimeUtil.stringToDate(birthday, TimeUtil.FORMAT_DATE);

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}
		return age;
	}


	/**
	 * 由出生年份计算年龄
	 * @param birthYear
	 * @return
	 */
	public static int getAgeByYear(String birthYear){
		Date birthDay = TimeUtil.stringToDate(birthYear, "yyyy");

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);

		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int age = yearNow - yearBirth;
		return age;
	}


	/**
	 * 取给定时间的零点值
	 * SQ
	 * 年-月-日 XX:XX:XX  -> 年-月-日 00:00:00
	 * @param time
	 */
	public static Long getZeroTime(Long time){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}

	/**
	 * 取给定时间第二天的毫秒值
	 * SQ
	 * @param time
	 */
	public static Long getNextDay(Long time){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTimeInMillis();
	}

	public static void main(String[] args) {
//		System.out.println(TimeUtil.dateToString(new Date(), "yyyyMMdd-HHmmss"));
		System.out.println(getAge("1989-12-15"));
		System.out.println(TimeUtil.longToString(TimeUtil.getNextDay(System.currentTimeMillis()), TimeUtil.FORMAT_DATETIME_FULL) );
		System.out.println(TimeUtil.stringToLong("2018-01-21", TimeUtil.FORMAT_DATE));
	}


}