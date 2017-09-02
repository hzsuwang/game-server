package com.iterror.game.common.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil extends DateUtils implements Serializable {

    /**
	 * 
	 */
    private static final long      serialVersionUID   = 7605263477725721681L;

    public static final String     dateyyyymmddhhmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String     FULL_PATTERN       = "yyyymmddhhmmss";
    public static final String     dateyyyymm         = "yyyy-MM-dd";
    public static final String     ddateyyyymm        = "yyyyMMdd";

    public static SimpleDateFormat formatter          = new SimpleDateFormat(dateyyyymmddhhmmss);
    public static SimpleDateFormat formatter1         = new SimpleDateFormat(dateyyyymm);

    public static Date formatDate(String dateString, String format) {
        if (StringUtils.isBlank(format)) {
            format = dateyyyymmddhhmmss;
        }
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        try {
            return sformat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDateString(Date date, String format) {
        if (StringUtils.isBlank(format)) {
            format = dateyyyymmddhhmmss;
        }
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        try {
            return sformat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        calendar.set(Calendar.HOUR, 0);
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
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return
     */
    public static Date getStringDate(String date) {
        try {
            return formatter1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String format(Date date, String format) {
        if (StringUtils.isBlank(format)) {
            format = dateyyyymmddhhmmss;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateyyyymmddhhmmss);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算两个时间的时间差(毫秒)
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long differ(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date2.getTime() - date1.getTime();
        }
        return 0;
    }

    public static int getTimeIntervalSeconds(Date startTime, Date now) {
        return (int) (now.getTime() - startTime.getTime() / 1000);
    }
}
