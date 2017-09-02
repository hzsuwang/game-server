package com.iterror.game.common.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class QpDateUtil {

    private static final String DEFAULT_DATE_FORMAT     = "yyyy/MM/dd/";

    // yyyy-MM-dd HH:mm:ss
    public static final String  DEFAULT_DATE_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";

    // yyyy-MM-dd
    public static final String  DEFAULT_DATE_FORMAT_MIN = "yyyy-MM-dd";

    private static int          SEC_EVERYDAY            = 86400;

    /**
     * 把日期型转换成字符串形式。
     * 
     * @param date 日期
     * @param dateFormat 日期格式，例如"yyyy/MM/dd"、"yyyy年MM月dd"
     * @return 日期字符串
     */
    public static String toLocaleString(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }

        if (StringUtils.isBlank(dateFormat)) {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        }

        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * 把日期型转换成"yyyy/MM/dd/"字符串形式。
     * 
     * @param date
     * @return 日期字符串
     */
    public static String toLocaleString(Date date) {
        return toLocaleString(date, null);
    }

    /**
     * 获得sysdate+hours后的时间
     * 
     * @param hours 提前或者推后的时间
     * @return sysdate+hours后的时间
     */
    public static Date getSysDate(int hours) {
        Calendar time = Calendar.getInstance();

        time.add(Calendar.HOUR, hours);

        return time.getTime();
    }

    /**
     * 方法说明:天数差
     * 
     * @param firstDate
     * @param lastDate
     */
    public static int getTimeIntervalDays(Date firstDate, Date lastDate) {
        long intervals = lastDate.getTime() - firstDate.getTime() + (60 * 1000);

        if (intervals > 0) {
            long daysd = intervals / (24 * 60 * 60 * 1000);

            return new Long(daysd).intValue();
        }

        return 0;
    }

    /**
     * 读取当前时间的开始时间
     * 
     * @param time
     * @return
     */
    public static Date getDateStartTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 读取当前时间的结束时间
     * 
     * @param time
     * @return
     */
    public static Date getDateEndTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 方法说明:小时差
     * 
     * @param firstDate
     * @param lastDate
     */
    public static int getTimeIntervalHours(Date firstDate, Date lastDate) {
        long intervals = lastDate.getTime() - firstDate.getTime() + (60 * 1000);

        if (intervals > 0) {
            long longHours = (intervals / (60 * 60 * 1000)) % 24;

            return new Long(longHours).intValue();
        }

        return 0;
    }

    /**
     * 方法说明:分钟差
     * 
     * @param firstDate
     * @param lastDate
     */
    public static int getTimeIntervalMins(Date firstDate, Date lastDate) {
        long intervals = lastDate.getTime() - firstDate.getTime() + (60 * 1000);

        if (intervals > 0) {
            long longMins = (intervals / (60 * 1000)) % 60;

            return new Long(longMins).intValue();
        }

        return 0;
    }
    
    /**
     * 方法说明:秒钟差
     * 
     * @param firstDate
     * @param lastDate
     */
    public static int getTimeIntervalSeconds(Date firstDate, Date lastDate) {
        long intervals = lastDate.getTime() - firstDate.getTime() + (60 * 1000);
        if (intervals > 0) {
            return (int)intervals/1000;
        }
        return 0;
    }

    /**
     * 秒数差
     * 
     * @param startDate
     * @return
     */
    public static int calLastedTime(Date startDate) {
        long b = new Date().getTime();
        long a = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }

    /**
     * 方法说明:parse date
     * 
     * @param date
     * @param dateformat
     */
    public static Date parseDate(String date, String dateformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 比较日期是否大于当前日期
     */
    public static boolean afterNow(Date date) {
        if (date == null) {
            return false;
        }

        Calendar nowCar = Calendar.getInstance();
        Calendar car = Calendar.getInstance();

        car.setTime(date);

        return car.after(nowCar);
    }

    /*
     * 查看是否早几天
     */
    public static boolean afterDays(Date date, int day) {
        if (date == null) {
            return false;
        }

        Calendar levelDay = Calendar.getInstance();
        Calendar createDay = Calendar.getInstance();

        createDay.setTime(date);
        createDay.add(Calendar.DATE, day);

        if (createDay.after(levelDay)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 查看是否早几小时
     */
    public static boolean afterHours(Date date, int hours) {
        if (date == null) {
            return false;
        }

        Calendar levelDay = Calendar.getInstance();
        Calendar createDay = Calendar.getInstance();

        createDay.setTime(date);
        createDay.add(Calendar.HOUR, hours);

        if (createDay.after(levelDay)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取得系统当前日期
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 返回多少时间的前的时间, seconds 可以是负的
     * 
     * @param when
     * @param seconds
     */
    public static Date addTime(Date when, int seconds) {
        Calendar c = Calendar.getInstance();

        c.setTime(when);
        c.add(Calendar.SECOND, seconds);

        return c.getTime();
    }

    /**
     * @param date
     * @param pattern "yyyy-MM-dd HH:mm:ss.SSS"
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern);

        return ymdhmsFormat.format(date);
    }

    /**
     * @param str
     * @param pattern "yyyy-MM-dd HH:mm:ss.SSS"
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String str, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern);
        try {
            return ymdhmsFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getFilterLoveDate() {
        return QpDateUtil.strToDate("2014-09-13 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getFilterUserInfoDate() {
        return QpDateUtil.strToDate("2014-12-15 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当天日期
     * 
     * @date 2009-2-20
     */
    public static Date getToday() {
        Calendar ca = Calendar.getInstance();
        return ca.getTime();
    }

    /**
     * 获得当天最后的时间
     * 
     * @date 2009-2-20
     */
    public static Date getTodayEndTime() {
        Calendar ca = Calendar.getInstance();
        String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        todayStr = todayStr + " 23:59:59";
        return strToDate(todayStr, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 获得当天最后的时间
     * 
     * @date 2009-2-20
     */
    public static Date getTodayStartTime() {
        Calendar ca = Calendar.getInstance();
        String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        todayStr = todayStr + " 00:00:00";
        return strToDate(todayStr, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 获得当天零点的时间
     */
    public static Date getTodayZero() {
        Calendar ca = Calendar.getInstance();
        Calendar todayDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE), 0, 0, 0);
        return todayDate.getTime();
    }

    /**
     * @date 2009-2-20
     */
    public static Date mkDate(int year, int month, int date) {
        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(ca.getTime());
        return ca.getTime();
    }

    /**
     * 获取18年前的日期
     * 
     * @return
     */
    public static Date getEighthYearsAgo() {
        Calendar ca = Calendar.getInstance();
        Calendar agoDate = new GregorianCalendar(ca.get(Calendar.YEAR) - 18, ca.get(Calendar.MONTH), ca.get(Calendar.DATE), 0, 0, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(agoDate.getTime());
        return agoDate.getTime();
    }

    /**
     * 获取180天后的日期
     * 
     * @return
     */
    public static Date getHalfYearLater() {
        Calendar ca = Calendar.getInstance();
        Calendar agoDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) + 180, 0, 0, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(agoDate.getTime());
        return agoDate.getTime();
    }

    /**
     * 获取365天后的日期
     * 
     * @return
     */
    public static Date getAYearLater() {
        Calendar ca = Calendar.getInstance();
        Calendar agoDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) + 365, 18, 0, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(agoDate.getTime());
        return agoDate.getTime();
    }

    /**
     * <li></li>
     * 
     * @param date1
     * @param date2 <li></li>
     */
    public static boolean compareTwoDate(Date date1, Date date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
        return simpleDateFormat.format(date1).equals(simpleDateFormat.format(date2));
    }

    /**
     * <li></li>
     * 
     * @param date1
     * @param date2 <li></li>
     */
    public static boolean compareTwoDateDay(Date date1, Date date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date1).equals(simpleDateFormat.format(date2));
    }

    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 判断是否在今天
     * 
     * @param a
     * @return
     */
    public static boolean isToday(Date a) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE));
        Date today = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today).equals(format.format(a));
    }

    /**
     * 当前时间是否在区间时间内
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isDateIn(Date nowTime, Date startTime, Date endTime) {
        boolean inDate = false;
        long now = nowTime.getTime();
        if (now > startTime.getTime() && now < endTime.getTime()) {
            inDate = true;
        }
        return inDate;
    }

    public static Date getTomorrowZero() {
        Calendar ca = Calendar.getInstance();
        Calendar todayDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) + 1, 0, 0, 0);
        return todayDate.getTime();
    }

    public static Date getTomorrowEighteen() {
        Calendar ca = Calendar.getInstance();
        Calendar tDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) + 1, 18, 0, 0);
        return tDate.getTime();
    }

    public static Date getYestodayZero() {
        Calendar ca = Calendar.getInstance();
        Calendar todayDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) - 1, 0, 0, 0);
        return todayDate.getTime();
    }

    public static Date getTwoDaysBeforeZero() {
        Calendar ca = Calendar.getInstance();
        Calendar todayDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) - 2, 0, 0, 0);
        return todayDate.getTime();
    }

    // 获取N天前的0点时间
    public static Date getDaysBeforeZero(int day) {
        Calendar ca = Calendar.getInstance();
        Calendar todayDate = new GregorianCalendar(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE) - day, 0, 0, 0);
        return todayDate.getTime();
    }

    public static boolean isYestoday(Date date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        Date today = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today).equals(format.format(date));
    }

    /**
     * 当天剩余的秒数
     * 
     * @return
     */
    public static int getSecondRestOfToday() {
        Calendar c = Calendar.getInstance();
        return SEC_EVERYDAY - c.get(Calendar.HOUR_OF_DAY) * 60 * 60 - c.get(Calendar.MINUTE) * 60 - c.get(Calendar.SECOND);
    }
    
    public static void main(String[] args) {
        System.out.println(QpDateUtil.getSecondRestOfToday());
    }
}
