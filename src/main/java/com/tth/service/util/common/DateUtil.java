package com.tth.service.util.common;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.http.client.utils.DateUtils.formatDate;


public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String MONTH_TIME_SPLIT_PATTERN = "yyyy-MM";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    /**
     * ROMA 格式化时间
     *
     * @param timestamp
     * @return
     */
    public static String longToROMAFormattedString(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);  // 使用 ofEpochMilli 而不是 ofEpochSecond
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        return zonedDateTime.format(formatter);
    }

    public static Long UTCTimestamp(Long timestampMillis) {
        // 设置时区为UTC
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(utcTimeZone);

        String formattedTimestamp = sdf.format(new Date(timestampMillis));

        // 将UTC时间戳作为Long类型返回
        long utcTimestamp = timestampMillis;
        return utcTimestamp;
    }

    public static String UTCTimestampToStr(Long timestampMillis) {
        // 设置时区为UTC
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(utcTimeZone);

        String formattedTimestamp = sdf.format(new Date(timestampMillis));

        // 将UTC时间戳作为String格式类型返回
        return formattedTimestamp;
    }

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    /**
     * 常用的时间格式.
     */
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};


    /**
     * 计算时间差--秒
     *
     * @param startInstant
     * @param endInstant
     * @return
     */
    public static Long InstantTimeDiffToSeconds(Instant startInstant, Instant endInstant) {
        // 计算时间差
        Duration duration = Duration.between(startInstant, endInstant);
        // 可以转换为LocalDateTime
        // LocalDateTime startDateTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);
        // LocalDateTime endDateTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);
        // Duration dateTimeDuration = Duration.between(startDateTime, endDateTime);

        // 计算时分秒合并是这样的
        // long hours = duration.toHours();
        // long minutes = duration.toMinutes() % 60; // Get the remaining minutes
        // long seconds = duration.getSeconds() % 60; // Get the remaining seconds
        // System.out.println("时间间隔：" + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒");

        // 获取日期时间差
        // long hours = duration.toHours();
        // long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();
        return seconds;
    }


    /**
     * 计算时间差--分
     *
     * @param startInstant
     * @param endInstant
     * @return
     */
    public static Long InstantTimeDiffToMinutes(Instant startInstant, Instant endInstant) {
        // 计算时间差
        Duration duration = Duration.between(startInstant, endInstant);
        return duration.toMinutes();
    }


    /**
     * 计算时间差--时
     *
     * @param startInstant
     * @param endInstant
     * @return
     */
    public static Long InstantTimeDiffToHours(Instant startInstant, Instant endInstant) {
        // 计算时间差
        Duration duration = Duration.between(startInstant, endInstant);
        return duration.toHours();
    }


    /**
     * 计算时间差--时分秒
     *
     * @param startInstant
     * @param endInstant
     * @return
     */
    public static Map<String, Long> InstantTimeDiff(Instant startInstant, Instant endInstant) {
        // 计算时间差
        Duration duration = Duration.between(startInstant, endInstant);

        // 获取日期时间差
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Get the remaining minutes
        long seconds = duration.getSeconds() % 60; // Get the remaining seconds
        System.out.println("时间间隔：" + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒");

        Map<String, Long> map = new ConcurrentHashMap<>();
        map.put("hours", hours);
        map.put("minutes", minutes);
        map.put("seconds", seconds);
        return map;
    }

    /**
     * @param startTime
     * @param endTime
     * @param format
     * @param str       d-天,h-小时,m-秒 表示返回相差的时间单位
     * @return
     */
    public static Long dateDiff(String startTime, String endTime, String format, String str) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        String result = "0";
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
                + (min - day * 24 * 60) + "分钟" + sec + "秒。");

        if ("d".equalsIgnoreCase(str)) {
            return day;
        } else if ("h".equalsIgnoreCase(str)) {
            return hour;
        } else if ("m".equalsIgnoreCase(str)) {
            return min;
        } else {
            return 0L;
        }

    }


    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获得当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        return getDateFormat(new Date(), FULL_TIME_SPLIT_PATTERN);
    }

    /**
     * 获得当前月计划
     */
    public static String getCurrentMonth() {
        return getDateFormat(new Date(), MONTH_TIME_SPLIT_PATTERN);
    }

    private static String pattern = "yyyy-MM-dd";

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将日期型字符串转换为日期格式.
     * 支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"
     *
     * @param str
     * @return Date
     * @since 1.0
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param date   时间
     * @param format 时间格式
     * @return String
     * @Description: 时间转string
     */
    public static String dateToString(Date date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 使用 "yyyy-MM-dd HH:mm:ss.SSS" 格式化
     */
    public static String format(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param
     * @return String
     * @Description: 默认采用 yyyy-MM-dd格式
     */
    public static String dateToString(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 得到当前日期和时间字符串.
     *
     * @return String 日期和时间字符串，例如 2015-08-11 09:51:53
     * @since 1.0
     */
    public static String getDateTime() {
        return formatDate(new Date(), DateUtil.FULL_TIME_SPLIT_PATTERN);
    }

    /**
     * @param
     * @return String
     * @Description: 默认采用 yyyy-MM-dd格式
     */
    public static String dateToStringByFormat(Date date, String formart) {

        SimpleDateFormat formatter = new SimpleDateFormat(formart);
        return formatter.format(date);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        return strToDate(strDate, pattern);
    }

    /**
     * 给定字符串和字符串格式转换为时间
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将new Date转换为 yyyy-MM-dd HH:mm:ss 格式的Date
     *
     * @param date
     * @return
     */
    public static Date newDateFormatDate(Date date) {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = dd.parse(dateToString(date, FULL_TIME_SPLIT_PATTERN));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parse;
    }

    /**
     * 两个时间之间的天数
     *
     * @param lastDate
     * @param firstDate
     * @return
     */
    public static long getDays(String lastDate, String firstDate) {
        if (lastDate == null || "".equals(lastDate)) {
            return 0;
        }
        if (firstDate == null || "".equals(firstDate)) {
            return 0;
        }
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(pattern);
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(lastDate);
            mydate = myFormatter.parse(firstDate);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 计算指定时间距离现在多少天
     *
     * @param date
     * @return
     */
    public static long getDaysToCurrentDate(Date date) {
        String dateStr = dateToString(date);
        String currentDateStr = dateToString(getCurrentDate());
        return getDays(dateStr, currentDateStr);
    }

    /**
     * 两个时间之间的分钟数
     *
     * @param @param  lastDate
     * @param @param  firstDate
     * @param @return
     * @return long
     * @throws
     */
    public static long getMinutes(String lastDate, String firstDate) {
        if (lastDate == null || "".equals(lastDate)) {
            return 0;
        }
        if (firstDate == null || "".equals(firstDate)) {
            return 0;
        }
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(lastDate);
            mydate = myFormatter.parse(firstDate);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (60 * 1000);
        return day;
    }

    // 计算当月最后一天,返回字符串
    public static Date getDefaultDay() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        return lastDate.getTime();
    }

    // 上月第一天
    public static Date getPreviousMonthFirst() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号

        return lastDate.getTime();
    }

    // 获取当月第一天
    public static Date getFirstDayOfMonth() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号

        return lastDate.getTime();
    }

    // 获得本周星期日的日期
    public static Date getCurrentWeekday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        return monday;
    }

    // 获取当天时间
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // 获得当前日期与本周日相差的天数
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // 获得本周一的日期
    public static Date getMondayOFWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        return currentDate.getTime();
    }

    // 取得指定日期所在周的第一天
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 取得指定日期所在周的最后一天
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    // 获得相应周的周六的日期
    public static Date getSaturday() {
        int weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        return currentDate.getTime();
    }

    // 获得上周星期日的日期
    public static Date getPreviousWeekSunday() {
        int weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        return currentDate.getTime();
    }

    // 获得上周星期一的日期
    public static Date getPreviousWeekday() {
        int weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        return currentDate.getTime();
    }

    // 获得下周星期一的日期
    public static Date getNextMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        return currentDate.getTime();
    }

    // 获得下周星期日的日期
    public static Date getNextSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        return currentDate.getTime();
    }

    public static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    // 获得上月最后一天的日期
    public static Date getPreviousMonthEnd() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        return lastDate.getTime();
    }

// 判断当前日期是星期几

    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    // 获得下个月第一天的日期
    public static Date getNextMonthFirst() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        return lastDate.getTime();
    }

    // 获得下个月最后一天的日期
    public static Date getNextMonthEnd() {

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天

        return lastDate.getTime();
    }

    // 获得明年第一天的日期
    public static Date getNextYearFirst() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一年
        lastDate.set(Calendar.MONTH, 0);// 把日期设置为当年第一月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天

        return lastDate.getTime();
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yy.MM.dd").format(cal.getTime());
    }

    /**
     * 得到某一年的所有月份
     *
     * @param year
     * @return
     */
    public static List<String> getMonthByYear(int year) {
        List<String> months = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            String str = "";
            if (i < 10) {
                str = "0" + i;
            } else {
                str = i + "";
            }
            months.add(year + "-" + str);
        }
        return months;
    }

    /**
     * 得到当前日期的年份
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yy.MM.dd").format(cal.getTime());
    }

    public static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    // 获得本年第一天的日期
    public static Date getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        return currentDate.getTime();
    }

    // 获得本年最后一天的日期 *
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // 获得上年第一天的日期 *
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // 获得上年最后一天的日期
    public static String getPreviousYearEnd() {
        int weeks = 0, MaxYear = 0;
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
                + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    // 获得本季度
    public static String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days
                + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * @param
     * @return String
     * @Description: 得到昨天日期
     */
    public static Date lastDay(Date today) {
        return nextNDay(today, -1);
    }

    /**
     * @param
     * @return String
     * @Description: 得到明天日期
     */
    public static Date nextDay(Date today) {
        return nextNDay(today, 1);
    }

    /**
     * @param period (n 天)
     * @return String
     * @Description: 得到n天后日期
     */
    public static Date nextNDay(Date today, int period) {

        long millSecond = 3600000 * 24;
        long lastDayLong = today.getTime() + period * millSecond;
        Date nextDay = new Date(lastDayLong);

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        String str = formatter.format(nextDay);
        return formatter.parse(str, pos);
    }

    public static Date stringToDate(String timeStr, String pattern) {
        if (timeStr == null || "".equals(timeStr)) {
            return null;
        }
        Date date = new Date();
        SimpleDateFormat apf = new SimpleDateFormat(pattern);
        try {
            date = apf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static boolean isToday(String today, String dateformat) {
        if (today == null) {
            today = "";
        }
        Date date = new Date();
        String str = (new SimpleDateFormat(dateformat)).format(date);
        if (today.equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * @param day 天数
     * @return Date
     * @Description: 得到几天前的时间
     */
    public static Date getDateBefore(int day) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * @param day 天数
     * @return Date
     * @Description: 得到几天后的时间
     */
    public static Date getDateAfter(int day) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        return currDate;
    }

    /**
     * java.util.Date 类型转换为 XMLGregorianCalendar
     *
     * @param date
     * @return XMLGregorianCalendar
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date)
            throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            throw e;
        }
        return gc;
    }

    /**
     * XMLGregorianCalendar 类型转换为 java.util.Date
     *
     * @return Date
     */
    public static Date convertToDate(XMLGregorianCalendar xmlCalendar) {

        if (null != xmlCalendar) {
            Calendar c = xmlCalendar.toGregorianCalendar();
            Date d = c.getTime();
            return d;
        } else {
            return null;
        }

    }

    /**
     * 日期得到年，月
     *
     * @return
     */
    public static String getDateMonth(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

/**
 * 得到某个月所有的星期一
 *
 * @return
 */
/*public static List<Date> getMonthWeek(String month) {
    List<Date> list = new ArrayList<Date>();
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    sdf1.setLenient(false);
    for (int i = 1; i < 32; i++) {
        try {
            Date date = sdf1.parse(month + "-" + i);
            int s = DateUtil.dayForWeek(DateUtil.format(date));
            if (s == 1) {
                list.add(date);
                // System.out.println(DateUtil.format(date) + ":" + s);
            }
        } catch (Exception e) {
            // do nothing
        }
    }
    return list;
}
*/

    /**
     * 得到某个月所有的星期五
     *
     * @return
     */
    public static List<Date> getMonthWeekFiveDay(String month) {
        List<Date> list = new ArrayList<Date>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        for (int i = 1; i < 32; i++) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 5) {
                    list.add(date);
                    // System.out.println(DateUtil.format(date) + ":" + s);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return list;
    }

    /**
     * 得到从当日到某年某月某日的所有符合条件（比如：都是周一和周二）的日期
     */
    public static List<String> getAllDateToDay(List<String> weekList, String start, String end) {
        List<String> dayList = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(strToDate(start));
        Calendar endC = Calendar.getInstance();
        endC.setTime(strToDate(end));
        while (!c.after(endC)) {
            int dayForWeek = 0;
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            if (weekList.contains(String.valueOf(dayForWeek))) {
                dayList.add(format.format(c.getTime()));
            }
            c.add(Calendar.DATE, 1);
        }
        return dayList;
    }


    /**
     * 传入月份，找到这个月的第一个星期五的日期，返回周一的日期
     *
     * @return
     */
    public static String getMonthWeekMonday(String month) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        Date fiveDay = null;
        for (int i = 1; i < 32; i++) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 5) {
                    fiveDay = date;
                    break;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return DateUtil.dateToString(DateUtil.nextNDay(fiveDay, -4));
    }

    /**
     * 传入月份，找到这个月的第一个星期五的日期，返回周天的日期
     *
     * @return
     */
    public static String getMonthWeekLastSunday(String month) {
        List<Date> list = new ArrayList<Date>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        for (int i = 1; i < 32; i++) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 5) {
                    list.add(date);
                    // System.out.println(DateUtil.format(date) + ":" + s);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return DateUtil.dateToString(DateUtil.nextNDay(list.get(list.size() - 1), 2));
    }

    /**
     * 通过月份得到这个月每周的开始日期及结束日期 (用于人员工作量统计)
     *
     * @param month
     * @return
     */
    public static List<String> formart(String month) {
        List<Date> oneWeekList = DateUtil.getMonthWeekFiveDay(month);
        List<String> list = new ArrayList<String>();
        String start = "";   // 当月开始时间
        String end = "";    // 当月结束时间
        for (int i = 0; i < oneWeekList.size(); i++) {
            String str = DateUtil.dateToString(DateUtil.getFirstDayOfWeek(oneWeekList.get(i)), "yy.MM.dd") + "-" + DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i)), "yy.MM.dd");
            if (i == 0) {
                start = DateUtil.dateToString(DateUtil.getFirstDayOfWeek(oneWeekList.get(i)), "yy.MM.dd");
            }
            if (i == oneWeekList.size() - 1) {
                end = DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i)), "yy.MM.dd");
            }
            list.add(str);
        }
        if (oneWeekList.size() == 4) {
            list.add("");
        }
        String s = start + "-" + end;
        list.add(s);
        return list;
    }


    /**
     * 通过月份得到这个月每周的开始日期及结束日期 (用于人员工作量统计)
     * 不包含当月的开始和结束时间
     *
     * @param month
     * @return
     */
    public static List<Map<String, Object>> formartTwo(String month) {
        List<Date> oneWeekList = DateUtil.getMonthWeekFiveDay(month);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oneWeekList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String str = DateUtil.dateToString(DateUtil.getFirstDayOfWeek(oneWeekList.get(i)), "MM.dd") + "-" + DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i)), "MM.dd");
            map.put("name", str);
            map.put("value", DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i))));
            list.add(map);
        }
        return list;
    }

    // 将日期的时分秒转换为 23:59:00
    public static Date getCustomizationTime(Date date) {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd 23:59:00");
        SimpleDateFormat dd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = dd.format(getLastDayOfWeek(date));
        Date date2 = null;
        try {
            date2 = dd2.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }


    /**
     * 得到某个月第一个星期五
     *
     * @param month 月份
     * @return
     */
    public static Date getFirstFiveDay(String month) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        Calendar lastDate = Calendar.getInstance();
        try {
            lastDate.setTime(sdf1.parse(month + "-01"));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        for (int i = 1; i <= lastDate.get(Calendar.DATE); i++) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 5) {
                    return date;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }

    /**
     * 得到某个月最后一个星期五
     *
     * @param month 月份
     * @return
     */
    public static Date getLastFiveDay(String month) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        Calendar lastDate = Calendar.getInstance();
        try {
            lastDate.setTime(sdf1.parse(month + "-01"));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        for (int i = lastDate.get(Calendar.DATE); i > 0; i--) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 5) {
                    return date;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }


    /**
     * 查询指定日期与当前日期之间星期天的个数
     */
    public static int getWeekCount(String startDate) {
        int sunDaySum = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(strToDate(startDate));
        end.setTime(getPreviousWeekSunday());
        while (start.compareTo(end) <= 0) {
            int w = start.get(Calendar.DAY_OF_WEEK);
            if (w == Calendar.SUNDAY) {
                sunDaySum++;
                // System.out.println(format.format(start.getTime()));//打印每天
            }
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);// 循环，每次天数加1
        }
        // System.out.println("星期天总数为:" + sunDaySum);
        return sunDaySum;
    }

    public static List<Date> getMonthWeekSunday(String month) {
        List<Date> list = new ArrayList<Date>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setLenient(false);
        for (int i = 1; i < 32; i++) {
            try {
                Date date = sdf1.parse(month + "-" + i);
                int s = DateUtil.dayForWeek(DateUtil.format(date));
                if (s == 7) {
                    list.add(date);
                    // System.out.println(DateUtil.format(date) + ":" + s);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return list;
    }

    /*根据月获取周，第一周按1日开始计算
     * 第一周：04.01-04.05    #星期三至星期日
     * 第二周：04.06-04.12    #星期一至星期日
     * .......
     * 第五周:04.27-04.28    #星期1至星期二
     */
    public static List<Map<String, Object>> formatWeekDateForMonth(String month) {
        List<Date> oneWeekList = DateUtil.getMonthWeekSunday(month);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String tyear = month.split("-")[0];
        String tmonth = month.split("-")[1];
        for (int i = 0; i <= oneWeekList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (i == 0) {
                String firstday = tmonth + "." + "01";
                String endday = DateUtil.dateToString(oneWeekList.get(i), "MM.dd");
                map.put("name", firstday + "-" + endday);
                map.put("value", DateUtil.dateToString(oneWeekList.get(i)));
                list.add(map);
            } else if (i == oneWeekList.size()) {
                int iyear = Integer.parseInt(tyear);
                int imonth = Integer.parseInt(tmonth);
                int endday = DateUtil.getLastDayOfMonth(iyear, imonth);
                Date enddate = DateUtil.strToDate(month + "-" + endday);
                if (oneWeekList.get(i - 1).compareTo(enddate) < 0) {
                    String start = DateUtil.dateToString(DateUtil.nextNDay(oneWeekList.get(i - 1), 1), "MM.dd");
                    String end = DateUtil.dateToString(enddate, "MM.dd");
                    map.put("name", start + "-" + end);
                    map.put("value", DateUtil.dateToString(enddate));
                    list.add(map);
                }
            } else {
                String str = DateUtil.dateToString(DateUtil.getFirstDayOfWeek(oneWeekList.get(i)), "MM.dd") + "-" + DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i)), "MM.dd");
                map.put("name", str);
                map.put("value", DateUtil.dateToString(DateUtil.getLastDayOfWeek(oneWeekList.get(i))));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 获得某月某周第一天时间（第一周第一天和最后一周第一天是关键点）
     * 某月1日为 第一周起点，
     * 2017.03.01-03.05 第一周  （星期三到星期日）
     */
    public static String getFirstDayForWeek(String submitTime) {
        String startDate = "";
        Date temp = DateUtil.strToDate(submitTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(temp);
        List<Map<String, Object>> list = DateUtil.formatWeekDateForMonth(month);
        String firstWeek = (String) list.get(0).get("value");
        String lastWeek = (String) list.get(list.size() - 1).get("value");
        if (submitTime.equals(firstWeek)) {
            // 第一周第一天处理
            startDate = month + "-01";
        } else if (submitTime.compareTo(lastWeek) > 0) {
            // 最后一周第一天处理
            Date temp1 = DateUtil.nextNDay(DateUtil.stringToDate(lastWeek, "yyyy-MM-dd"), 1);
            startDate = DateUtil.dateToString(temp1);
        } else {
            // 其它完整周处理
            Date temp2 = DateUtil.getFirstDayOfWeek(DateUtil.stringToDate(submitTime, "yyyy-MM-dd"));
            startDate = DateUtil.dateToString(temp2);
        }
        return startDate;
    }

    // 获得一月最后一天
    public static String getLastDayForMonth(String month) {
        String tyear = month.split("-")[0];
        String tmonth = month.split("-")[1];
        String lastday = month + "-" + DateUtil.getLastDayOfMonth(Integer.parseInt(tyear), Integer.parseInt(tmonth));
        return lastday;
    }


    /**
     * 查询指定的两个日期之间星期天的个数   add 2017-06-20 iceHuang
     */
    public static int getWeekCountBetweenTwoDate(String startDate, String endDate) {
        int sunDaySum = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(strToDate(startDate));
        end.setTime(strToDate(endDate));
        while (start.compareTo(end) <= 0) {
            int w = start.get(Calendar.DAY_OF_WEEK);
            if (w == Calendar.SUNDAY) {
                sunDaySum++;
                // System.out.println(format.format(start.getTime()));//打印每天
            }
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);// 循环，每次天数加1
        }
        // System.out.println("星期天总数为:" + sunDaySum);
        return sunDaySum;
    }

    /**
     * 生成16位不重复的随机数，含数字+大小写
     *
     * @return
     */
    public static String getGUID() {
        StringBuilder uid = new StringBuilder();
        // 产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            // 产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    // 0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    // ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    // ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }
}
