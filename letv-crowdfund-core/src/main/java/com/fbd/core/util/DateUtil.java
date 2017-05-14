package com.fbd.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fbd.core.exception.ApplicationException;

/**
 * 日期工具类
 * 
 * 
 * 
 */
public class DateUtil {

    @SuppressWarnings("unused")
    private Log log = LogFactory.getLog(DateUtil.class);

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String SE_DATE_FORMAT="yyyy/MM/dd";

    /**
     * 按指定格式将字符串转换为日期
     * 
     * @param dateStr
     *            日期串
     * @param pattern
     *            格式
     * @return 日期
     * @throws Exception
     *             异常
     */
    public static Date str2Date(String dateStr, String pattern){
        if (null == dateStr) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("转换日期格式时出错");
        }
    }

    public static Date addDate(Date d, long day) {
        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }
    
       
    /**
     * 按指定格式将字符串转换为日期时间
     * 
     * @param dateStr
     *            日期串
     * @param pattern
     *            格式
     * @return 日期时间
     * @throws ParseException
     *             解析异常
     */
    public static Date str2DateTime(String dateStr, String pattern){
        if (null == dateStr) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_TIME_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApplicationException("转换日期格式时出错");
        }
    }

    /**
     * 将日期格式化为字符串
     * 
     * @param date
     *            日期
     * @param pattern
     *            格式
     * @return 格式化后的日期串
     */
    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 将日期时间格式化为字符串
     * 
     * @param date
     *            日期
     * @param pattern
     *            格式
     * @return 格式化后的日期时间串
     */
    public static String dateTime2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_TIME_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 取得当前时间戳
     * 
     * @return 当前时间戳
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * 取得当前日期
     * 
     * @return 当前日期
     */
    public static String thisDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar
                .getTime());
    }

    /**
     * 对当前日期延后一年
     * 
     * @return 当前日期
     */
    public static String nextYearDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar
                .getTime());
    }

    /**
     * 取得当前时间
     * 
     * @return 当前时间
     */
    public static String thisTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(calendar
                .getTime());
    }

    /**
     * 取得当前完整日期时间
     * 
     * @return 当前完整日期时间
     */
    public static String thisDateTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(calendar
                .getTime());
    }

    /**
     * 获取某月最后一天的日期数字
     * 
     * @param firstDate
     *            日期
     * @return 某月最后一天的日期数字
     * @throws Exception
     *             异常
     */
    @SuppressWarnings("deprecation")
    public static int getLastDayOfMonth(Date firstDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, firstDate.getYear());
        cal.set(Calendar.MONTH, firstDate.getMonth());
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 
     * Description: 获得传入日期中的最小时间
     * 
     * @param
     * @return Date
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 上午11:50:29
     */
    public static Date getDayMin(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 
     * Description: 获得传入日期中的最大时间
     * 
     * @param
     * @return Date
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 上午11:50:29
     */
    public static Date getDayMax(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 取得今天的最小时间
     * 
     * @return 今天的最小时间
     */
    public static Date getTodayMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 取得明天的最小时间
     * 
     * @return 明天的最小时间
     */
    public static Date getTomorrowMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);

        return cal.getTime();
    }

    /**
     * 由指定时间、时间域、数额，计算时间值
     * 
     * @param standard
     *            指定时间
     * @param type
     *            时间域
     * @param amount
     *            数额
     * @return 时间值
     */
    public static Date genDiffDate(Date standard, int type, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(standard);
        cal.add(type, amount);

        return cal.getTime();
    }

    /**
     * 生成指定时间所在的小时段（清空：分钟、秒、毫秒）
     * 
     * @param standard
     *            指定时间
     * @return 指定时间所在的小时段
     */
    public static Date genHourStart(Date standard) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(standard);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 取得当前天后，减去指定天数后的最小时间
     * 
     * @param date
     *            当前日期
     * @param beforeDay
     *            天数
     * @return 当前天后，减去指定天数后的最小时间
     */
    public static Date getBeforeDayMin(Date date, int beforeDay) {

        return getDayMin(date, -beforeDay);
    }

    /**
     * 取得当前天后，减去指定天数后的最大时间
     * 
     * @param date
     *            当前日期
     * @param beforeDay
     *            天数
     * @return 当前天后，减去指定天数后的最大时间
     */
    public static Date getBeforeDayMax(Date date, int beforeDay) {

        return getDayMax(date, -beforeDay);
    }

    /**
     * 取得当前天后，加上指定天数后的最小时间
     * 
     * @param date
     *            当前日期
     * @param afterDay
     *            天数
     * @return 当前天后，加上指定天数后的最小时间
     */
    public static Date getAfterDayMin(Date date, int afterDay) {

        return getDayMin(date, afterDay);
    }

    /**
     * 取得当前天后，加上指定天数后的最大时间
     * 
     * @param date
     *            当前日期
     * @param afterDay
     *            天数
     * @return 当前天后，加上指定天数后的最大时间
     */
    public static Date getAfterDayMax(Date date, int afterDay) {

        return getDayMax(date, afterDay);
    }

    /**
     * 取得当前天后，加上指定天数后的最小时间
     * 
     * @param date
     *            当前日期
     * @param addDay
     *            天数
     * @return 当前天后，加上指定天数后的最小时间
     */
    public static Date getDayMin(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, addDay);

        return cal.getTime();
    }

    /**
     * 取得当前天 ,加上指定天数后的最大时间
     * 
     * @param date
     *            当前日期
     * @param addDay
     *            天数
     * @return 当前天 ,加上指定天数后的最大时间
     */
    public static Date getDayMax(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.add(Calendar.DATE, addDay);

        return cal.getTime();
    }

    /**
     * 
     * Description:获取 未来的日期
     *
     * @param 
     * @return Date
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-20 上午10:48:30
     */
    public static Date getDate(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addDay);
        return cal.getTime();
    }
    /**
     * 
     * Description:获取 未来的日期
     *
     * @param 
     * @return Date
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-20 上午10:48:30
     */
    public static Date subDate(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -addDay);
        return cal.getTime();
    }
    /**
     * 取得当前天 ,加上指定月份数后的时间
     * 
     * @param date
     *            当前日期
     * @param months
     *            月份数
     * @return 当前天 ,加上指定月份数后的时间
     */
    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得当前天 ,加上指定周数后的时间
     * 
     * @param date
     *            当前日期
     * @param weeks
     *            周数
     * @return 当前天 ,加上指定周数后的时间
     */
    public static Date addWeeks(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, weeks);
        return cal.getTime();
    }

    /**
     * 取得当前天 ,加上指定年数后的时间
     * 
     * @param date
     *            当前日期
     * @param years
     *            年数
     * @return 当前天 ,加上指定年数后的时间
     */
    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 日期差天数(按照时间比较,如果不足一天会自动补足)
     * 
     * @param date1
     *            开始日期
     * @param date2
     *            结束日期
     * @return 两日期差天数
     * @throws Exception
     */
    public static int diff(Date date1, Date date2){
        long day = 24L * 60L * 60L * 1000L;
        String str1 = DateUtil.date2Str(date1, "yyyy-MM-dd");
        date1 = DateUtil.str2Date(str1, "yyyy-MM-dd");
        String str2 = DateUtil.date2Str(date2, "yyyy-MM-dd");
        date2 = DateUtil.str2Date(str2, "yyyy-MM-dd");
        return (int) (((date2.getTime() - date1.getTime()) / day));
        // return (int) Math.ceil((((date2.getTime() - date1.getTime()) / (24 *
        // 60 * 60 * 1000d))));
    }

    public static double getMonthSpace(Date date1, Date date2) throws Exception {
        double result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2) {// 同一年
            if (month1 >= month2) {
                if (day1 >= day2) {
                    result = (month1 - month2) + Math.abs((day1 - day2)) / 30.0;
                } else {
                    result = getDifferMonths(c1, c2);
                }
            } else {
                if (day1 <= day2) {
                    result = (month2 - month1) + Math.abs((day2 - day1)) / 30.0;
                } else {
                    result = getDifferMonths(c2, c1);
                }
            }
        } else if (year1 > year2) {
            if (month1 >= month2) {
                int years = year1 - year2;
                if (day1 >= day2) {
                    result = years * 12 + (month1 - month2)
                            + Math.abs((day1 - day2)) / 30.0;
                } else {
                    result = getDifferYearMonths(c1, c2, years);
                }
            } else {
                int years = year1 - year2 - 1;
                if (day1 <= day2) {
                    result = years * 12 + (month2 - month1)
                            + Math.abs((day2 - day1)) / 30.0;
                } else {
                    result = getDifferYearMonths(c2, c1, years);
                }
            }
        } else if (year1 < year2) {
            if (month1 <= month2) {
                int years = year2 - year1;
                if (day1 <= day2) {
                    result = years * 12 + (month2 - month1)
                            + Math.abs((day2 - day1)) / 30.0;
                } else {
                    result = getDifferYearMonths(c2, c1, years);
                }
            } else {
                int years = year2 - year1 - 1;
                if (day1 >= day2) {
                    result = years * 12 + (month1 - month2)
                            + Math.abs((day1 - day2)) / 30.0;
                } else {
                    result = getDifferYearMonths(c2, c1, years);
                }
            }
        }

        return Arith.round(result, 1);
    }

    private static double getDifferMonths(Calendar c1, Calendar c2)
            throws Exception {
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int differM = month1 - month2 - 1;
        c2.set(Calendar.MONTH, month2 + differM);
        int differD = DateUtil.diff(c2.getTime(), c1.getTime());
        if (month1 == month2) {
            differM = 0;
        }
        return differM + differD / 30.0;
    }

    private static double getDifferYearMonths(Calendar c1, Calendar c2,
            int years) throws Exception {
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int differM = month1 - month2 - 1;
        c2.set(Calendar.YEAR, year2 + years);
        c2.set(Calendar.MONTH, month2 + differM);
        int differD = Math.abs(DateUtil.diff(c2.getTime(), c1.getTime()));
        return years * 12 + differM + differD / 30.0;
    }

    /**
     * 日期差天数(和当前时间比)
     * 
     * @param date
     *            比较日期
     * @return 和当前日期差天数
     * @throws Exception
     */
    public static int diff(Date date) throws Exception {
        return diff(new Date(), date);
    }
    
    /**
     * 日期差毫秒数(和当前时间比)
     * 
     * @param date
     *            比较日期
     * @return 和当前日期差天数
     * @throws Exception
     */
    public static long diffMilli(Date date) {
    	return diffMilli(new Date(), date);
    }
    

    /**
     * 日期差天毫秒数(按照两个时间比较)
     * 
     * @param date1
     *            开始日期
     * @param date2
     *            结束日期
     * @return 两日期差毫秒数
     * @throws Exception
     */
    public static long diffMilli(Date date1, Date date2){
        return date1.getTime() - date2.getTime();
    }

    /**
     * 按固定格式比较两个日期
     * 
     * @param date1
     *            日期
     * @param date2
     *            日期2
     * @param pattern
     *            格式 默认：yyyy-MM-dd
     * @return 比较结果
     */
    public static int compareDate(Date date1, Date date2, String pattern) {
        String d1 = date2Str(date1, pattern);
        String d2 = date2Str(date2, pattern);
        return d1.compareTo(d2);
    }

    /**
     * 按固定格式比较两个日期+时间
     * 
     * @param time1
     *            日期时间
     * @param time2
     *            日期时间2
     * @param pattern
     *            格式 默认： yyyy-MM-dd HH:mm:ss
     * @return 比较结果
     */
    public static int compareDateTime(Date time1, Date time2, String pattern) {
        String d1 = dateTime2Str(time1, pattern);
        String d2 = dateTime2Str(time2, pattern);
        return d1.compareTo(d2);
    }

    /**
     * 将Date类转换为XMLGregorianCalendar
     * 
     * @param date
     * @throws DatatypeConfigurationException
     *             转换异常
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date)
            throws DatatypeConfigurationException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        // 由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH) + 1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }

    /**
     * 将XMLGregorianCalendar转换为Date
     * 
     * @param cal
     * @return date
     */
    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    /**
     * 判断是否是闰年
     * 
     * @param date
     *            日期
     * @return boolean
     */
    public static boolean isLeapyear(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar.isLeapYear(gregorianCalendar
                .get(Calendar.YEAR));
    }
    
    /**
     * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
     * 
     * @param date
     *            传入日期
     * @return date 月末日期
     * @author sy
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

    /**
     * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
     * 
     * @param date
     *            传入日期
     * @return date 月末日期
     * @author sy
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getLastDateOfMonth(c);
    }

    /**
     * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
     * 
     * @param calendar
     *            传入日期
     * @return date 月末日期
     * @author sy
     */
    public static Date getLastDateOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
     * 
     * @param date
     *            传入日期
     * @return boolean true为是
     * @author sy
     */
    public static boolean isLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isLastDateOfMonth(c);
    }

    /**
     * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
     * 
     * @param date
     *            传入日期
     * @return boolean false为不是
     * @author sy
     */
    public static boolean isLastDateOfMonth(Calendar date) {
        if (date.getActualMaximum(Calendar.DAY_OF_MONTH) == date
                .get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * 根据日期得到年份
     * 
     * @param date
     *            日期
     * @return 年份
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据日期得到月份
     * 
     * @param date
     *            日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据日期得到日
     * 
     * @param date
     *            日期
     * @return 日
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 
     * Description: 判断两个日期是否同月
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return boolean true:同月,false:不同月
     * @Author yaoxin Create Date: 2013-12-9 下午6:14:37
     */
    public static boolean sameMonth(Date beginDate, Date endDate) {
        boolean result = false;
        int calBegin = getCalendarMonth(beginDate);
        int calEnd = getCalendarMonth(endDate);
        if (calBegin == calEnd) {
            result = true;
        }
        return result;
    }

    /**
     * 
     * Description: 判断两个日期是否同年
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return boolean true:同月,false:不同月
     * @Author yaoxin Create Date: 2013-12-9 下午6:14:37
     */
    public static boolean sameYear(Date beginDate, Date endDate) {
        boolean result = false;
        int calBegin = getCalendarYear(beginDate);
        int calEnd = getCalendarYear(endDate);
        if (calBegin == calEnd) {
            result = true;
        }
        return result;
    }

    /**
     * 
     * Description: 获得日期的月份
     * 
     * @param date
     *            日期
     * @return int 月份
     * @Author yaoxin Create Date: 2013-12-9 下午6:14:02
     */
    public static int getCalendarMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 
     * Description: 获得日期的月份
     * 
     * @param date
     *            日期
     * @return int 月份
     * @Author yaoxin Create Date: 2013-12-9 下午6:14:02
     */
    public static int getCalendarYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 
     * Description: 参数paramDate的上月最后一天 思路：月加1，日设为1，日再减1，即使跨年也可以。
     * 
     * @param paramDate
     *            日期参数
     * @return Date 上月最后一天
     * @Author yaoxin Create Date: 2013-12-10 上午10:22:30
     */
    public static Date getLastDateOfLastMonth(Date paramDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static Date setDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 
     * Description: 获得下个还款日
     * 
     * @param
     * @return Date
     * @throws
     * @Author haolingfeng Create Date: 2015-1-4 上午11:41:25
     */
    public static Date getNextRepayDate(Date thisRepayDate, int repayDay) {
        Date nextRepayDate = null;
        if (repayDay == 31) {// 每月最后一天
            Date newDate = DateUtil.addMonths(thisRepayDate, 1);
            nextRepayDate = DateUtil.getLastDateOfMonth(newDate);
        } else if (repayDay == 30) {
            nextRepayDate = DateUtil.addMonths(thisRepayDate, 1);
            if (DateUtil.getMonth(nextRepayDate) != 2
                    && DateUtil.getDay(nextRepayDate) < repayDay) {
                nextRepayDate = DateUtil.setDate(nextRepayDate, repayDay);
            }
        } else {
            nextRepayDate = DateUtil.addMonths(thisRepayDate, 1);
        }
        return nextRepayDate;
    }
    
    public static Date getRepayDate(Date thisRepayDate, int repayDay,int n) {
        Date nextRepayDate = null;
        if (repayDay == 31) {// 每月最后一天
            Date newDate = DateUtil.addMonths(thisRepayDate, n);
            nextRepayDate = DateUtil.getLastDateOfMonth(newDate);
        } else if (repayDay == 30) {
            nextRepayDate = DateUtil.addMonths(thisRepayDate, n);
            if (DateUtil.getMonth(nextRepayDate) != 2
                    && DateUtil.getDay(nextRepayDate) < repayDay) {
                nextRepayDate = DateUtil.setDate(nextRepayDate, repayDay);
            }
        } else {
            nextRepayDate = DateUtil.addMonths(thisRepayDate, n);
        }
        return nextRepayDate;
    }
    
    
    /**
     * 
     * Description: 返回任务的时间表达式
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-20 下午4:15:01
     */
    public static String getSchedulerCronExpression(Date sdate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        int syear = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String str = second+" "+minute+" "+hour+" "+ date+" "+(month+1)+" "+"? "+syear;
        return str;
    }
    
    public static long getTimestamp(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+8);
        return cal.getTime().getTime();
    }

    public static void main(String[] args) throws ParseException, Exception {
        // int aa = DateUtil.diff(
        // DateUtil.str2DateTime("2015-01-15 02:12:12", null),
         //DateUtil.str2DateTime("2015-03-10 03:42:12", null));
        // Date aa = DateUtil.getNextRepayDate(
        // DateUtil.str2Date("2015-03-01", null), 1);
        // Date aa = DateUtil.getDayMax(DateUtil.str2Date("2017-01-01", null));
         //System.out.println(aa);
         //double i = 0.2/365*aa*1000;

        String dd = DateUtil.getSchedulerCronExpression(new Date());
        String aa = DateUtil.dateTime2Str(new Date(), "yyyyMMddhhmmss");
        
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("inputCharset","1");
        params.put("pickupUrl","4444");
        params.put("receiveUrl","444444");
        
        
        System.out.println(params);
    }
}