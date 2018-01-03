package com.cloud.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:acerge@163.com">gebiao(acerge)</a>
 * @since 2007-9-28 02:05:17
 */
public class DateUtil implements Util4Script {
    /**
     *
     */
    public final static int TIME_DAY_MILLISECOND = 86400000;
    public static final DateUtil instance = new DateUtil();
    public static final long M_SECOND = 1000;
    public static final long M_MINUTE = M_SECOND * 60;
    public static final long M_HOUR = M_MINUTE * 60;
    public static final long M_DAY = M_HOUR * 24;
    /**
     *
     */
    // change by bbq
    public static final String DT_SIMPLE = "yyyy-MM-dd";
    /**
     *
     */
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    /**
     *
     */
    private final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
    /**
     *
     */
    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     *
     */
    private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    /**
     *
     */
    private final static String MONTH_FORMAT = "yyyy-MM";
    /**
     *
     */
    private final static String DAY_FORMAT = "yyyyMMdd";
    private static final Object[] Date = null;
    /***
     * @param date
     * @return 1, 2, 3, 4, 5, 6, 7
     */
    private static int[] chweek = new int[]{0, 7, 1, 2, 3, 4, 5, 6};
    private static String[] cnweek = new String[]{"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static String[] cnSimpleweek = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};

    /**
     * <p>
     * DateUtil instances should NOT be constructed in standard programming.
     * </p>
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance
     * to operate.
     * </p>
     */
    public DateUtil() {
    }

    /**
     * 获取系统时间戳，毫秒级
     *
     * @return
     */
    public static final long timeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 当前日期字符串，yyyy-MM-dd
     *
     * @return
     */
    public static final String currentDateStr() {
        return formatDate(currentTime());
    }

    /**
     * 当前日期字符串，yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeStr() {
        return formatTimestamp(currentTime());
    }

    /**
     * 当前日期字符串，yyyy-MM-dd HH
     *
     * @return
     */
    public static String currentTimeHourStr() {
        return org.apache.commons.lang3.StringUtils.substring(formatTimestamp(currentTime()), 0, 13);
    }

    /**
     * 获取当前日期 <br>
     * 参见{@link #timeMillis()}
     *
     * @return
     */
    public static final Date currentTime() {
        return new Date();
    }

    /**
     * 当前timestamp字符串，yyyy-MM-dd HH:mm:ss <br>
     * 参见{@link #format(Date, String)}
     *
     * @return
     */
    public static final String getCurFullTimestampStr() {
        return formatTimestamp(getCurFullTimestamp());
    }

    /**
     * 当前timestamp <br>
     * 字符串类型返回，参见{@link #}
     *
     * @return
     */
    public static final Timestamp getCurFullTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 当前月份的下一个月 <br>
     * 1月份的下一个月为 2，12月份的下一个月为1
     *
     * @return
     */
    public static final int nextMonth() {
        String next = format(new Date(), "M");
        int nextMonth = Integer.parseInt(next) + 1;
        if (nextMonth == 13) {
            return 1;
        }
        return nextMonth;
    }

    /**
     * 指定时间下一天
     *
     * @param specifiedDay
     * @param i
     * @return
     */
    public static String getSpecifiedDayBefor(String specifiedDay, int i) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - i);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 取得当前系统时间，返回java.util.Date类型
     *
     * @return java.util.Date 返回服务器当前系统时间
     * @see java.util.Date
     */
    public static java.util.Date getCurrDate() {
        return new java.util.Date();
    }

    /**
     * 取得当前系统时间戳
     *
     * @return java.sql.Timestamp 系统时间戳
     * @see java.sql.Timestamp
     */
    public static java.sql.Timestamp getCurrTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 将2007-12-1变成2007-12-01。将2007-9-1变为2007-09-01。
     *
     * @param date
     * @return
     */
    public static String getFormatDateV2(String date) {
        if (date == null) {
            return null;
        }

        String[] datearr = org.apache.commons.lang3.StringUtils.split(date, "-");
        if (datearr == null || datearr.length != 3) {
            return date;
        }

        StringBuffer ret = new StringBuffer();
        ret.append(datearr[0]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[1]) < 10 ? "0" + Integer.parseInt(datearr[1]) : datearr[1]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[2]) < 10 ? "0" + Integer.parseInt(datearr[2]) : datearr[2]);
        return ret.toString();
    }

    /**
     * 从时间串中获取小时数。
     *
     * @param timestr "2007-10-12 13:25:00"
     * @return
     */
    public static int getHourFromTimeString(String timestr) {
        if (org.apache.commons.lang3.StringUtils.isBlank(timestr)) {
            return 0;
        }

        return Integer.parseInt(timestr.substring(timestr.length() - 8, timestr.length() - 6));
    }

    /**
     * 返回当前时间是上午还是下午
     * <p>
     * Calendar.AM 0
     * Calendar.PM 1
     *
     * @return
     * @author lenghao
     * @createTime 2008-8-2 下午04:22:07
     */
    public static Integer getCurrDateAMorPM() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.AM_PM);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFormatDate(java.util.Date currDate) {
        return getFormatDate(currDate, DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(java.util.Date)
     */
    public static Date getFormatDateToDate(java.util.Date currDate) {
        return getFormatDate(getFormatDate(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFormatDate_CN(java.util.Date currDate) {
        return getFormatDate(currDate, DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate_CN(String)
     */
    public static Date getFormatDateToDate_CN(java.util.Date currDate) {
        return getFormatDate_CN(getFormatDate_CN(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return Date 返回格式化后的日期，默认格式为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(String, String)
     */
    public static Date getFormatDate(String currDate) {
        return getFormatDate(currDate, DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate 要格式化的日期
     * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(String, String)
     */
    public static Date getFormatDate_CN(String currDate) {
        return getFormatDate(currDate, DATE_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     */
    public static String getFormatDate(java.util.Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     * 15:23:45
     * @see #getFormatDateTime(java.util.Date, String)
     */
    public static String getFormatDateTime(java.util.Date currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式环的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     * @see #getFormatDateTime(String)
     */
    public static Date getFormatDateTimeToTime(java.util.Date currDate) {
        return getFormatDateTime(getFormatDateTime(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     * @see #getFormatDateTime(String, String)
     */
    public static Date getFormatDateTime(String currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(java.util.Date, String)
     */
    public static String getFormatDateTime_CN(java.util.Date currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT_CN);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime_CN(String)
     */
    public static Date getFormatDateTimeToTime_CN(java.util.Date currDate) {
        return getFormatDateTime_CN(getFormatDateTime_CN(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(String, String)
     */
    public static Date getFormatDateTime_CN(String currDate) {
        return getFormatDateTime(currDate, TIME_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
     * HH:mm:ss
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     */
    public static String getFormatDateTime(java.util.Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return "";
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return Date 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see java.text.SimpleDateFormat#parse(java.lang.String)
     */
    public static Date getFormatDate(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
     * HH:mm:ss
     * @see java.text.SimpleDateFormat#parse(java.lang.String)
     */
    public static Date getFormatDateTime(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到本日的上月时间 如果当日为2007-9-1,那么获得2007-8-1
     */
    public static String getDateBeforeMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到本日的前几个月时间 如果number=2当日为2007-9-1,那么获得2007-7-1
     */
    public static String getDateBeforeMonth(int number) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -number);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    public static long getDaysOfDates(Date first, Date second) {
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

        long mils = d1.getTime() - d2.getTime();

        return mils / (TIME_DAY_MILLISECOND);
    }

    /**
     * 获得两个Date型日期之间相差的天数（第2个减第1个）
     *
     * @param first, Date second
     * @return int 相差的天数
     */
    public static int getDaysBetweenDates(Date first, Date second) {
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

        Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

        return mils.intValue();
    }

    /**
     * 获得两个String型日期之间相差的天数（第2个减第1个）
     *
     * @param first, String second
     * @return int 相差的天数
     */
    public static int getDaysBetweenDates(String first, String second) {
        Date d1 = getFormatDateTime(first, DATE_FORMAT);
        Date d2 = getFormatDateTime(second, DATE_FORMAT);

        Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

        return mils.intValue();
    }

    /**
     * @param first
     * @param second
     * @return 获取两个Date之间的天数的列表
     * @author lenghao
     * @createTime 2008-8-5 下午01:57:09
     */
    public static List<Date> getDaysListBetweenDates(Date first, Date second) {
        List<Date> dateList = new ArrayList<Date>();
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);
        if (d1.compareTo(d2) > 0) {
            return dateList;
        }
        do {
            dateList.add(d1);
            d1 = getDateBeforeOrAfter(d1, 1);
        } while (d1.compareTo(d2) <= 0);
        return dateList;
    }

    /**
     *
     *
     */
    public static String getDateBeforeDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(java.util.Date)
     */
    public static String getCurrDateStr() {
        return getFormatDate(getCurrDate());
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     * 15:23:45
     * @see #getFormatDateTime(java.util.Date)
     */
    public static String getCurrDateTimeStr() {
        return getFormatDateTime(getCurrDate());
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getCurrDateStr_CN() {
        return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(java.util.Date, String)
     */
    public static String getCurrDateTimeStr_CN() {
        return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
    }

    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回系统当前日期的前或者后几天
     * @see java.util.Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
     * @see java.util.Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到格式化后的月份，格式为yyyy-MM，如2006-02
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFormatMonth(java.util.Date currDate) {
        return getFormatDate(currDate, MONTH_FORMAT);
    }

    /**
     * 得到格式化后的日，格式为yyyyMMdd，如20060210
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFormatDay(java.util.Date currDate) {
        return getFormatDate(currDate, DAY_FORMAT);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01要格式化的日期
     *
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see java.util.Calendar#getMinimum(int)
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
     * <p>
     * 要格式化的日期
     *
     * @return String 返回格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see java.util.Calendar#getMinimum(int)
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFirstDayOfNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     * @see java.util.Calendar#getMinimum(int)
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFirstDayOfMonth(Date currDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     * @see java.util.Calendar#getMinimum(int)
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getLastDayOfMonth(Date currDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     * <p>
     * 要格式化的日期
     *
     * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
     * @see java.util.Calendar#getMinimum(int)
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到日期的前或者后几小时
     *
     * @param iHour 如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
     * @see java.util.Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.HOUR_OF_DAY, iHour);
        return cal.getTime();
    }

    /**
     * 判断日期是否在当前周内
     *
     * @param curDate
     * @param compareDate
     * @return
     */
    public static boolean isSameWeek(Date curDate, Date compareDate) {
        if (curDate == null || compareDate == null) {
            return false;
        }

        Calendar calSun = Calendar.getInstance();
        calSun.setTime(getFormatDateToDate(curDate));
        calSun.set(Calendar.DAY_OF_WEEK, 1);

        Calendar calNext = Calendar.getInstance();
        calNext.setTime(calSun.getTime());
        calNext.add(Calendar.DATE, 7);

        Calendar calComp = Calendar.getInstance();
        calComp.setTime(compareDate);
        if (calComp.after(calSun) && calComp.before(calNext)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 时间查询时,结束时间的 23:59:59
     */
    public static String addDateEndfix(String datestring) {
        if ((datestring == null) || "".equals(datestring)) {
            return null;
        }
        return datestring + " 23:59:59";
    }

    /**
     * 返回格式化的日期
     *
     * @param "yyyy-MM-dd 23:59:59";
     * @return
     */
    public static Date getFormatDateEndfix(String dateStr) {
        dateStr = addDateEndfix(dateStr);
        return getFormatDateTime(dateStr);
    }

    /**
     * 返回格式化的日期
     *
     * @param datePre 格式"yyyy-MM-dd HH:mm:ss";
     * @return
     */
    public static Date formatEndTime(String datePre) {
        if (datePre == null) {
            return null;
        }
        String dateStr = addDateEndfix(datePre);
        return getFormatDateTime(dateStr);
    }

    /**
     * @param date1
     * @param compday
     * @return
     */
    // date1加上compday天数以后的日期与当前时间比较，如果大于当前时间返回true，否则false
    public static Boolean compareDay(Date date1, int compday) {
        if (date1 == null) {
            return false;
        }
        Date dateComp = getDateBeforeOrAfter(date1, compday);
        Date nowdate = new Date();
        if (dateComp.after(nowdate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>
     * 1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li> <li>
     * 2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个8位的16进制串。</li>
     * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
     *
     * @param timespan 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     * @return 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
     */
    public static String convertBinaryTime2Hex(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String ret = "";
        String tmp = "";
        for (int i = 0; i < timespan.length(); i++) {
            tmp += timespan.charAt(i);
            tmp += timespan.charAt(i);
            // tmp += i;
            if ((i + 1) % 16 == 0) {
                if (!"".equals(ret)) {
                    sb.append(ret).append(",");
                }
                Long t = Long.parseLong(tmp, 2);
                String hexStr = Long.toHexString(t);
                if (hexStr.length() < 8) {
                    int length = hexStr.length();
                    for (int n = 0; n < 8 - length; n++) {
                        sb.append("0").append(hexStr);
                    }
                }
                tmp = "";
            }
        }

        return sb.toString();
    }

    /**
     * 进行时段格式转换，将输入的26位的2进制串转换成48位的二进制串。
     *
     * @param timespan 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
     * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     */
    public static String convertHexTime2Binary(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String tmp = "";
        String ret = "";
        String[] strArr = timespan.split(",");
        StringBuilder sb = new StringBuilder(tmp);
        for (int i = 0; i < strArr.length; i++) {
            String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 16));
            if (binStr.length() < 32) {
                int length = binStr.length();
                for (int n = 0; n < 32 - length; n++) {
                    sb.append("0").append(binStr);
                }
            }
            tmp += binStr;
        }

        for (int i = 0; i < 48; i++) {
            ret += sb.toString().charAt(i * 2);
        }

        return ret;
    }

    /**
     * 进行时段格式转换，将输入的32位的10进制串转换成48位的二进制串。
     *
     * @param timespan 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890c"
     * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     */
    public static String convertDecTime2Binary(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String tmp = "";
        String ret = "";
        String[] strArr = timespan.split(",");
        for (int i = 0; i < strArr.length; i++) {
            String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 10));
            if (binStr.length() < 32) {
                int length = binStr.length();
                for (int n = 0; n < 32 - length; n++) {
                    binStr = "0" + binStr;
                }
            }
            tmp += binStr;
        }

        for (int i = 0; i < 48; i++) {
            ret += tmp.charAt(i * 2);
        }

        return ret;
    }

    /**
     * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>
     * 1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。</li> <li>
     * 2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个10位的10进制串。</li>
     * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
     *
     * @param timespan 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
     * @return 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890"
     */
    public static String convertBinaryTime2Dec(String timespan) {
        if (timespan == null || "".equals(timespan)) {
            return "";
        }

        String ret = "";
        String tmp = "";
        for (int i = 0; i < timespan.length(); i++) {
            tmp += timespan.charAt(i);
            tmp += timespan.charAt(i);
            // tmp += i;
            if ((i + 1) % 16 == 0) {
                if (!"".equals(ret)) {
                    ret += ",";
                }
                Long t = Long.parseLong(tmp, 2);
                String decStr = Long.toString(t);
                if (decStr.length() < 10) {
                    int length = decStr.length();
                    for (int n = 0; n < 10 - length; n++) {
                        decStr = "0" + decStr;
                    }
                }

                ret += decStr;
                tmp = "";
            }
        }

        return ret;
    }

    /**
     * 计算指定日期+addMonth月+15号 返回格式"2008-02-15"
     *
     * @param date
     * @param addMonth
     * @param monthDay
     * @return
     */
    public static String genericSpecdate(Date date, int addMonth, int monthDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, addMonth);
        cal.set(Calendar.DAY_OF_MONTH, monthDay);
        return getFormatDate(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 获得以今天为单位若干天以前或以后的日期的标准格式"Wed Feb 20 00:00:00 CST 2008"，是0点0分0秒。
     *
     * @param idx
     * @return
     */
    public static Date getDateBeforeOrAfterV2(int idx) {
        return getDateBeforeOrAfter(getFormatDateToDate(getCurrDate()), idx);
    }

    /**
     * 获得给定时间若干秒以前或以后的日期的标准格式。
     *
     * @param curDate
     * @param seconds
     * @return curDate
     */
    public static Date getSpecifiedDateTimeBySeconds(Date curDate, int seconds) {
        long time = (curDate.getTime() / 1000) + seconds;
        curDate.setTime(time * 1000);
        return curDate;
    }

    /**
     * 获得给定日期当天23点59分59秒的标准格式。
     *
     * @param curDate
     * @return curDate
     */
    public static Date getSpecifiedDateTime_235959(Date curDate) {
        return getSpecifiedDateTimeBySeconds(getFormatDateToDate(curDate), 24 * 60 * 60 - 1);
    }

    public static String getSpecifiedDateTime_month(Date curDate) {
        return getFormatDateTime(curDate, "MM.dd");
    }

    /**
     * alahan add 20050825 获取传入时间相差的日期
     *
     * @param dt   传入日期，可以为空
     * @param diff 需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
     * @return
     */
    public static String getDiffStringDate(Date dt, int diff) {
        Calendar ca = Calendar.getInstance();

        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }

        ca.add(Calendar.DATE, diff);
        return dtSimpleFormat(ca.getTime());
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(DT_SIMPLE).format(date);
    }

    /**
     * @param format
     * @return
     */
    // SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 取得多个日期中间隔的最大天数
     *
     * @param startDateAndEndDate
     * @return
     * @author Alvise
     */
    public static int maxContinuousDays(Date[][] startDateAndEndDate) {
        // 冒泡排序
        for (int i = 0; i < startDateAndEndDate.length - 1; i++) {
            for (int j = 0; j < startDateAndEndDate.length - i - 1; j++) {
                if (DateUtil.getDaysBetweenDates(startDateAndEndDate[j + 1][0],
                        startDateAndEndDate[j][0]) > 0) {
                    Date[] tempDate = startDateAndEndDate[j];
                    startDateAndEndDate[j] = startDateAndEndDate[j + 1];
                    startDateAndEndDate[j + 1] = tempDate;
                }
            }
        }

//         for (int i = 0; i < startDateAndEndDate.length; i++) {
//         if (startDateAndEndDate[i][0] == null)
//         break;
//         System.out.println(DateUtil.getFormatDate(
//         startDateAndEndDate[i][0]) + ","
//         + DateUtil.getFormatDate(startDateAndEndDate[i][1]));
//         }
//
//         System.out.println(
//         "===========================================");

        // 合并连续的时间段
        int j = 0;
        Date[][] startDateAndEndDateNew = new Date[startDateAndEndDate.length][2];
        for (int i = 0; i < startDateAndEndDateNew.length; i++) {
            if (j >= startDateAndEndDate.length) {
                break;
            }

            startDateAndEndDateNew[i] = startDateAndEndDate[j];
            j++;
            while (j < startDateAndEndDate.length) {
                if (DateUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][0]) > 0) {
                    break;
                } else if (DateUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][1]) > 0) {
                    startDateAndEndDateNew[i][1] = startDateAndEndDate[j][1];
                    j++;
                } else if (DateUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1],
                        startDateAndEndDate[j][1]) <= 0) {
                    j++;
                }

            }
        }

//         for (int i = 0; i < startDateAndEndDateNew.length; i++) {
//            if (startDateAndEndDateNew[i][0] == null)
//                break;
//            System.out.println(DateUtil.getFormatDate(startDateAndEndDateNew[i][0]) + ","
//                    + DateUtil.getFormatDate(startDateAndEndDateNew[i][1]));
//        }

        // 选择法排序
        int maxDays = 0;
        for (int i = 0; i < startDateAndEndDateNew.length - 1; i++) {
            Date curEndDate = startDateAndEndDateNew[i][1];
            Date nextStartDate = startDateAndEndDateNew[i + 1][0];
            if (curEndDate == null || nextStartDate == null) {
                break;
            }

            int temDays = DateUtil.getDaysBetweenDates(curEndDate, nextStartDate);
            if (temDays > maxDays) {
                maxDays = temDays;
            }
        }
        return maxDays;
    }

    /**
     * 取得多个日期中间隔的最大天数,这里的参数是用 ","和";"分割的字符字符串例如 "2008-08-03,2008-08-04;"
     *
     * @param dateStr
     * @return
     * @author Alvise
     */
    public static int maxContinuousDays(String dateStr) {
        String[] seDate = dateStr.split(";");
        Date[][] startDateAndEndDate = new Date[seDate.length][2];

        for (int i = 0; i < seDate.length; i++) {
            String[] tempDate = seDate[i].split(",");
            startDateAndEndDate[i][0] = DateUtil.getFormatDate(tempDate[0]);
            startDateAndEndDate[i][1] = DateUtil.getFormatDate(tempDate[1]);
        }

        return maxContinuousDays(startDateAndEndDate);

    }


    /**
     * 判断时间段1和时间段2是否有交集
     *
     * @param begintimeOne
     * @param endtimeOne
     * @param begintimeTwo
     * @param endtimeTwo
     * @return true:有交集,false:没有交集
     */
    public static boolean isConfilct(String begintimeOne, String endtimeOne, String begintimeTwo,
                                     String endtimeTwo) {
        Date beginOne = getFormatDate(begintimeOne);
        Date endOne = getFormatDate(endtimeOne);
        Date beginTwo = getFormatDate(begintimeTwo);
        Date endTwo = getFormatDate(endtimeTwo);
        if ((beginOne.compareTo(beginTwo) <= 0 && endOne.compareTo(beginTwo) >= 0)
                || (beginOne.compareTo(endTwo) <= 0 && endOne.compareTo(endTwo) >= 0)
                || (beginTwo.compareTo(beginOne) <= 0 && endTwo.compareTo(beginOne) >= 0)
                || (beginTwo.compareTo(endOne) <= 0 && endTwo.compareTo(endOne) >= 0)) {
            return true;
        }
        return false;
    }

    /**
     * 取得最早可购买时间
     *
     * @param busytimes 被购买时间,格式为2008-08-06,2008-08-06;2008-08-9,2008-08-12;2008-08-14,2008-08-22;2008-09-04,2008-09-04
     * @param days      购买时长
     * @return 最高可购买时间
     */
    public static String getCansellTime(String busytimes, int days) {
        Map<String, Integer> dayMap = Maps.newHashMap();
        String[] busytimeArr = StringUtils.split(busytimes, ";");
        for (int i = 0; i < busytimeArr.length; i++) {
            String[] time = StringUtils.split(busytimeArr[i], ",");
            Date d1 = getFormatDateTime(time[0], DATE_FORMAT);
            Date d2 = getFormatDateTime(time[1], DATE_FORMAT);
            while (d1.compareTo(d2) <= 0) {
                dayMap.put(getFormatDate(d1), null);
                d1 = getDateBeforeOrAfter(d1, 1);
            }
        }

        Date lastDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(29)), DATE_FORMAT);
        Date beginDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(2)), DATE_FORMAT);
        Date endDate = getDateBeforeOrAfter(beginDate, days - 1);

        while (beginDate.compareTo(lastDate) <= 0) {
            boolean conflict = false;
            List<Date> daysList = getDaysListBetweenDates(beginDate, endDate);
            for (Date d : daysList) {
                if (dayMap.containsKey(getFormatDate(d))) {
                    conflict = true;
                    break;
                }
            }
            if (!conflict) {
                break;
            }
            beginDate = getDateBeforeOrAfter(beginDate, 1);
            endDate = getDateBeforeOrAfter(beginDate, days - 1);
        }
        return getFormatDate(beginDate);
    }

    /**
     * parse date using default pattern yyyy-MM-dd
     *
     * @param strDate
     * @return 失败返回null
     */
    public static final Date parseDate(String strDate) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(strDate);

            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        DateUtil.getSpecifiedDayBefor("2017-12-06", -1);
        System.out.println(DateUtil.getSpecifiedDayBefor("2017-12-06", -1));
    }

    public static int getWeekOfYear(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDateFromTimestamp(time));
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 中国传统意义的周，周一做为开始
     *
     * @param time
     * @return
     */
    public static int getCnWeekOfYear(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDateFromTimestamp(time));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 根据date字符串，获取timestamp
     *
     * @param strDate 必须为 yyyy-MM-dd hh:mm:ss[.fffffffff]格式
     * @return 失败返回null
     */
    public static final Timestamp parseTimestamp(String strDate) {
        try {
            // Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
            Timestamp result = Timestamp.valueOf(strDate);
            return result;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     */
    public static final Timestamp parseTimestamp(String strDate, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     */
    public static final Date parseDate(String strDate, String pattern) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(pattern);
        try {
            date = df.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param date
     * @return formated date by yyyy-MM-dd
     */
    public static final <T extends Date> String formatDate(T date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * @param
     * @return formated time by HH:mm:ss
     */
    public static final <T extends Date> String formatTime(T date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }

    /**
     * @param
     * @return formated time by yyyy-MM-dd HH:mm:ss
     */
    public static final <T extends Date> String formatTimestamp(T date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timestampFormat.format(date);
    }

    public static final String formatTimestamp(Long mills) {
        return formatTimestamp(new Date(mills));
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final <T extends Date> String format(T date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            String result = df.format(date);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param original
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     * @param
     * @return original+day+hour+minutes+seconds+millseconds
     */
    public static final <T extends Date> T addTime(T original, int days, int hours, int minutes, int seconds) {
        if (original == null) {
            return null;
        }
        long newTime = original.getTime() + M_DAY * days + M_HOUR * hours + M_MINUTE * minutes + M_SECOND * seconds;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    public static final <T extends Date> T addDay(T original, int days) {
        if (original == null) {
            return null;
        }
        long newTime = original.getTime() + M_DAY * days;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    public static final <T extends Date> T addHour(T original, int hours) {
        if (original == null) {
            return null;
        }
        long newTime = original.getTime() + M_HOUR * hours;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    public static final <T extends Date> T addMinute(T original, int minutes) {
        if (original == null) {
            return null;
        }
        long newTime = original.getTime() + M_MINUTE * minutes;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    public static final <T extends Date> T addSecond(T original, int second) {
        if (original == null) {
            return null;
        }
        long newTime = original.getTime() + M_SECOND * second;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    /**
     * @param day
     * @return for example ,1997/01/02 22:03:00,return 1997/01/02 00:00:00.0
     */
    public static final <T extends Date> T getBeginningTimeOfDay(T day) {
        if (day == null) {
            return null;
        }
        // new Date(0)=Thu Jan 01 08:00:00 CST 1970
        String strDate = formatDate(day);
        Long mill = parseDate(strDate).getTime();
        T another = (T) day.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * @param day
     * @return for example ,1997/01/02 22:03:00,return 1997/01/02 23:59:59.999
     */
    public static final <T extends Date> T getLastTimeOfDay(T day) {
        if (day == null) {
            return null;
        }
        Long mill = getBeginningTimeOfDay(day).getTime() + M_DAY - 1;
        T another = (T) day.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 09:00:00,09:07:00 ---> 9:00,9:7:00
     *
     * @param time
     * @return
     */
    public static final String formatTime(String time) {
        if (time == null) {
            return null;
        }
        time = StringUtils.trim(time);
        if (StringUtils.isBlank(time)) {
            throw new IllegalArgumentException("时间格式有错误！");
        }
        // time = time.replace('：',':');
        time = time.replace("：", ":"); // TODO 原代码上面注释
        String[] times = time.split(":");
        String result = "";
        if (times[0].length() < 2) {
            result += "0" + times[0] + ":";
        } else {
            result += times[0] + ":";
        }
        if (times.length > 1) {
            if (times[1].length() < 2) {
                result += "0" + times[1];
            } else {
                result += times[1];
            }
        } else {
            result += "00";
        }
        Timestamp.valueOf("2001-01-01 " + result + ":00");
        return result;
    }

    public static boolean isTomorrow(Date date) {
        if (date == null) {
            return false;
        }
        if (formatDate(addTime(new Date(), 1, 0, 0, 0)).equals(formatDate(date))) {
            return true;
        }
        return false;
    }

    /**
     * @param date
     * @return 1, 2, 3, 4, 5, 6, 7
     */
    public static Integer getWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return chweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    public static Date getCurDateByWeek(Integer week) {
        if (week == null || week < 0 || week > 7) {
            return DateUtil.currentTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, week);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    public static String getCnWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return cnweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * @param date
     * @return "日", "一", "二", "三", "四", "五", "六"
     */
    public static String getCnSimpleWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return cnSimpleweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    public static Integer getCurrentDay() {
        return getDay(new Date());
    }

    public static Integer getCurrentMonth() {
        return getMonth(new Date());
    }

    public static Integer getCurrentYear() {
        return getYear(new Date());
    }

    public static Integer getYear(Date date) {
        if (date == null) {
            return null;
        }
        String year = DateUtil.format(date, "yyyy");
        return Integer.parseInt(year);
    }

    public static Integer getDay(Date date) {
        if (date == null) {
            return null;
        }
        String year = DateUtil.format(date, "d");
        return Integer.parseInt(year);
    }

    /**
     * @param date
     * @return 日期所在月份
     */
    public static Integer getMonth(Date date) {
        if (date == null) {
            return null;
        }
        String month = format(date, "M");
        return Integer.parseInt(month);
    }

    public static Integer getCurrentHour(Date date) {
        if (date == null) {
            return null;
        }
        String hour = DateUtil.format(date, "H");
        return Integer.parseInt(hour);
    }

    public static Integer getCurrentMin(Date date) {
        if (date == null) {
            return null;
        }
        String hour = DateUtil.format(date, "m");
        return Integer.parseInt(hour);
    }

    public static String getCurDateStr() {
        return DateUtil.formatDate(new Date());
    }

    public static String getCurTimeStr() {
        return DateUtil.formatTimestamp(new Date());
    }

    public static boolean isAfter(Date date) {
        if (date == null) {
            return false;
        }
        if (date.after(new Date())) {
            return true;
        }
        return false;
    }

    /**
     * 获取date所在月份的星期为weektype且日期在date之后（或等于）的所有日期
     *
     * @param weektype
     * @return
     */
    public static List<Date> getWeekDateList(Date date, String weektype) {
        int curMonth = getMonth(date);
        int week = Integer.parseInt(weektype);
        int curWeek = getWeek(date);
        int sub = (7 + week - curWeek) % 7;
        Date next = addDay(date, sub);
        List<Date> result = new ArrayList<Date>();
        while (getMonth(next) == curMonth) {
            result.add(next);
            next = addDay(next, 7);
        }
        return result;
    }

    /**
     * 获取date之后(包括date)的num个星期为weektype日期（不限制月份）
     *
     * @param weektype
     * @return
     */
    public static List<Date> getWeekDateList(Date date, String weektype, int num) {
        int week = Integer.parseInt(weektype);
        int curWeek = getWeek(date);
        List<Date> result = new ArrayList<Date>();
        int sub = (7 + week - curWeek) % 7;
        Date next = addDay(date, sub);
        for (int i = 0; i < num; i++) {
            result.add(next);
            next = addDay(next, 7);
        }
        return result;
    }

    /**
     * 获取date所在星期的周一至周日的日期
     *
     * @param date
     * @return
     */
    public static List<Date> getCurWeekDateList(Date date) {
        int curWeek = getWeek(date);
        List<Date> dateList = new ArrayList<Date>();
        for (int i = 1; i <= 7; i++) {
            dateList.add(DateUtil.addDay(date, -curWeek + i));
        }
        return dateList;
    }

    public static Date getWeekLastDay(Date date) {
        int curWeek = getWeek(date);
        return DateUtil.addDay(date, 7 - curWeek);
    }

    public static Date getCurDate() {
        return getBeginningTimeOfDay(new Date());
    }

    /**
     * 获取日期所在月份的第一天
     *
     * @param date
     * @return
     */
    public static <T extends Date> T getMonthFirstDay(T date) {
        if (date == null) {
            return null;
        }
        String dateStr = format(date, "yyyy-MM") + "-01";
        Long mill = parseDate(dateStr).getTime();
        T another = (T) date.clone();
        another.setTime(mill);
        return another;
    }

    public static <T extends Date> T getNextMonthFirstDay(T day) {
        if (day == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String datefor = format(calendar.getTime(), "yyyy-MM-dd");
        Long mill = parseDate(datefor).getTime();
        T another = (T) day.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 获取日期所在月份的最后一天
     *
     * @param
     * @return
     */
    public static <T extends Date> T getMonthLastDay(T date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dateStr = format(date, "yyyy-MM") + "-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Long mill = parseDate(dateStr).getTime();
        T another = (T) date.clone();
        another.setTime(mill);
        return another;
    }

    public static String formatDate(int days) {
        return formatDate(addDay(new Date(), days));
    }

    /**
     * 截取时分秒后的时间
     *
     * @return
     */
    public static Timestamp getCurTruncTimestamp() {
        return getBeginningTimeOfDay(new Timestamp(System.currentTimeMillis()));
    }

    public static Integer getHour(Date date) {
        if (date == null) {
            return null;
        }
        String hour = format(date, "H");
        return Integer.parseInt(hour);
    }

    public static Integer getMinute(Date date) {
        if (date == null) {
            return null;
        }
        String m = format(date, "m");
        return Integer.parseInt(m);
    }

    public static String getTimeDesc(Timestamp time) {
        if (time == null) {
            return "";
        }
        String timeContent;
        Long ss = System.currentTimeMillis() - time.getTime();
        Long minute = ss / 60000;
        if (minute < 1) {
            Long second = ss / 1000;
            timeContent = second + "秒前";
        } else if (minute >= 60) {
            Long hour = minute / 60;
            if (hour >= 24) {
                if (hour > 720) {
                    timeContent = "1月前";
                } else if (hour > 168 && hour <= 720) {
                    timeContent = (hour / 168) + "周前";
                } else {
                    timeContent = (hour / 24) + "天前";
                }
            } else {
                timeContent = hour + "小时前";
            }
        } else {
            timeContent = minute + "分钟前";
        }
        return timeContent;
    }

    public static String getDateDesc(Date time) {
        if (time == null) {
            return "";
        }
        String timeContent;
        Long ss = System.currentTimeMillis() - time.getTime();
        Long minute = ss / 60000;
        if (minute < 1) {
            Long second = ss / 1000;
            timeContent = second + "秒前";
        } else if (minute >= 60) {
            Long hour = minute / 60;
            if (hour >= 24) {
                if (hour > 720) {
                    timeContent = "1月前";
                } else if (hour > 168 && hour <= 720) {
                    timeContent = (hour / 168) + "周前";
                } else {
                    timeContent = (hour / 24) + "天前";
                }
            } else {
                timeContent = hour + "小时前";
            }
        } else {
            timeContent = minute + "分钟前";
        }
        return timeContent;
    }

    /**
     * author: bob date: 20100729 截取日期, 去掉年份 param: date1 eg. 传入"1986-07-28", 返回
     * 07-28
     */
    public static String getMonthAndDay(Date date) {
        return formatDate(date).substring(5);
    }

    public static Date getMillDate() {
        return new Date();
    }

    public static Timestamp getMillTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 时间差：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> String getDiffDayStr(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return "---";
        }
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        if (sign < 0) {
            return "已经过期";
        }
        diff = Math.abs(diff) / 1000;
        long day = diff / 3600 / 24;
        long hour = (diff - (day * 3600 * 24)) / 3600;
        long minu = diff % 3600 / 60;
        return (day == 0 ? "" : day + "天") + (hour == 0 ? "" : hour + "小时") + (minu == 0 ? "" : minu + "分");
    }

    /**
     * 时间差：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> String getDiffStr(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return "---";
        }
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        long hour = diff / 3600;
        long minu = diff % 3600 / 60;
        long second = diff % 60;
        return (sign < 0 ? "-" : "+") + (hour == 0 ? "" : hour + "小时") + (minu == 0 ? "" : minu + "分")
                + (second == 0 ? "" : second + "秒");
    }

    /**
     * 时间差（秒）：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> long getDiffSecond(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return 0;
        }
        long diff = day1.getTime() - day2.getTime();
        if (diff == 0) {
            return 0;
        }
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return sign * diff;
    }

    /**
     * 时间差（分钟）：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> double getDiffMinu(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return 0;
        }
        long diff = day1.getTime() - day2.getTime();
        if (diff == 0) {
            return 0;
        }
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d * 10 / 6.0) / 100.0 * sign;// 两位小数
    }

    /**
     * 时间差（分）：time1 - time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static final double getMillDiffMinu(long time1, long time2) {
        long diff = time1 - time2;
        if (diff == 0) {
            return 0;
        }
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d * 10 / 6.0) / 100.0 * sign;// 两位小数
    }

    /**
     * 时间差（小时）：day1 - day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> double getDiffHour(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return 0;
        }
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d / 3.6) / 1000.0 * sign;// 三位小数
    }

    /**
     * @param day1
     * @param day2
     * @return 日期相差整数round(abs（day1-day2))
     */
    public static final <T extends Date> int getDiffDay(T day1, T day2) {
        if (day1 == null || day2 == null) {
            return 0;
        }
        long diff = day1.getTime() - day2.getTime();
        diff = Math.abs(diff) / 1000;
        return Math.round(diff / (3600 * 24));
    }

    public static boolean isAfterOneHour(Date date, String time) {
        String datetime = formatDate(date) + " " + time + ":00";
        if (addHour(parseTimestamp(datetime), -1).after(getMillTimestamp())) {
            return true;
        }
        return false;
    }

    public static boolean isValidDate(String fyrq) {
        return DateUtil.parseDate(fyrq) != null;
    }

    public static <T extends Date> long getCurDateMills(T date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * eg. 1997/01/02 22:03:00,return 1997/01/02 00:00:00.0
     **/
    public static Timestamp getBeginTimestamp(Date date) {
        return new Timestamp(getBeginningTimeOfDay(date).getTime());
    }

    public static Timestamp getEndTimestamp(Date date) {
        return new Timestamp(getLastTimeOfDay(date).getTime());
    }

    /**
     * @param timestamp
     * @return
     */
    public static Date getDateFromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    public static int after(Date date1, Date date2) {
        date1 = getBeginningTimeOfDay(date1);
        date2 = getBeginningTimeOfDay(date2);
        return date1.compareTo(date2);
    }

    public static Timestamp mill2Timestamp(Long mill) {
        if (mill == null) {
            return null;
        }
        return new Timestamp(mill);
    }

    public static int subCurTimeSend() {
        Timestamp curtime = DateUtil.getCurFullTimestamp();
        Timestamp endtime = DateUtil.getLastTimeOfDay(curtime);
        Long scopeSecond = DateUtil.getDiffSecond(endtime, curtime);
        return scopeSecond.intValue();
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final <T extends Date> String formatEn(T date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
            String result = df.format(date);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCurrAddHour(int hour) {
        return formatTimestamp(System.currentTimeMillis() + M_HOUR * hour);
    }

    public static String getCurrAddDay(int day) {
        return formatTimestamp(System.currentTimeMillis() + M_DAY * day);
    }

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYestoryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }
}
