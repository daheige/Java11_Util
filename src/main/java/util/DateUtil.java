package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date Util
 * @author cv
 */
public class DateUtil {


    /**
     * 日期转Date
     * @param dateString
     * @param format
     * @return java.util.Date
     */
    public static Date parseDate(String dateString, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 时间戳或Date转换成日期格式字符串
     * @param seconds Date或时间戳
     * @return: java.lang.String
     */
    public static String parseString(Object seconds) {
        return parseString(seconds, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     * 时间戳或Date转换成日期格式字符串
     * @param seconds Date或时间戳
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return: java.lang.String
     */
    public static String parseString(Object seconds, String format) {
        if(seconds instanceof Date) {
            Date date = (Date) seconds;
            seconds = date.getTime();
        }
        if (seconds.toString().length() == 10) {
            seconds = seconds+"000";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(seconds.toString())));
    }



    /**
     * 获取指定年月日的开始时间 00:00:00,000
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDayFirstDay(int year, int month, int day) {
        month--;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 将时间戳或者Date的时分秒归零
     * @param seconds Date或时间戳
     * @return
     */
    public static Date compressTime(Object seconds) {
        if(seconds instanceof Date) {
            Date date = (Date) seconds;
            seconds = date.getTime();
        }
        if (seconds.toString().length() == 10) {
            seconds = seconds+"000";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.parseLong(seconds.toString())));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }



    /**
     * 获取指定天前或者天后的开始日期，格式：2016-01-01 00:00:00
     * @param date
     * @param day
     * @return
     */
    public static Date getDateByAddDay(Date date, int day) {
        return getDateByAddDay(date, day, 0 ,0);
    }

    /**
     * 获取指定年月日前或后的开始日期
     * @param date
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static Date getDateByAddDay(Date date, int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.MONTH, month);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
