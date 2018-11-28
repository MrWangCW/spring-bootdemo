package com.wang.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/10/13.
 */
public class DateUtils {
    /** 缺省的DateFormat对象，可以将一个java.util.Date格式化成 yyyy-mm-dd HH:SS:MM 输出 */
    private static DateFormat datetimeDF = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    public static Date getSetoffDay(int days) {
        return getSetoffDay(new Date(), days);
    }

    public static Date getSetoffDayTime(int days, int mins) {
        return getSetoffDayTime(new Date(), days, mins);
    }


    /**
     * 返回一个当前日期和时间，并按格式转换为字符串 例：2004-4-30 17:27:03
     *
     * @return String
     */
    public static String getDateTime() {
        GregorianCalendar gcNow = new GregorianCalendar();
        Date dNow = gcNow.getTime();
        return datetimeDF.format(dNow);
    }
    public static Date getSetoffDay(Date aDate, int days) {
        return new Date(aDate.getTime() + days * 24 * 60 * 60 * 1000l);
    }

    public static Date getSetoffDayTime(Date date, int days, int mins) {
        return new Date(date.getTime() + days * 24 * 60 * 60 * 1000l + mins * 60 * 1000l);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat dateFormater1 = new SimpleDateFormat(formatStr);
        return dateFormater1.format(date);
    }

    public static Date getByOffset(int timeType, int timeValue) {
        switch (timeType) {
            case 0: //x天前
                return getSetoffDay(-timeValue);
            case 1: //x时前
                return getSetoffDayTime(new Date(), 0, -60 * timeValue);
            case 2: //xmin前
                return getSetoffDayTime(0, -timeValue);
            case 4: //x 秒前
                return new Date(new Date().getTime() - timeValue);
            default:
                throw new RuntimeException("无此时间类型");
        }
    }

    /**
     * 将字串转成日期和时间，字串格式: yyyy-MM-dd
     * @param string
     * @return
     */
    public static Date toDate(String string) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将字串转成日期和时间，字串格式: yyyy
     * @param string
     * @return
     */
    public static Date toYear(String string) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy");
            return formatter.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将字串转成日期和时间，字串格式: yyyyMMddHHmmss
     * @param string
     * @return
     */
    public static Date toDateTime(String string) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            return formatter.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将毫秒转成日期和时间
     * @param string
     * @return
     */
    public static Date millToDateTime(String string) {
        try {
            Date date = new Date(Long.valueOf(string));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return toDateTime(sdf.format(date),"yyyy-MM-dd HH:mm:ss");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将字串转成日期和时间，字串格式: yyyyMMddHHmmss
     * @param string
     * @return
     */
    public static Date toDateTime(String string, String format) {
        try {
            DateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取昨日 日期的 string 格式  yyyyMMdd
     *
     * @return
     */
    public static String getYesterDayDateString() {
        return DateUtils.formatDate(DateUtils.getSetoffDay(-1));
    }

    /**
     * 获取今日 日期的 string 格式  yyyyMMdd
     *
     * @return
     */
    public static String getTodayDateString() {
        return DateUtils.formatDate(new Date());
    }

    /**
     * 获取指定 （yyyyMMdd/yyyy-MM-dd)格式日期 的 当天开始 时间string yyyMMddHHmmssSSS
     *
     * @param date
     * @return
     */
    public static Date getBeginDateTime(String date) {
        if(date.contains("-"))//如果是yyyy-MM-dd
        {
            date=date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
        }
        return DateUtils.toDateTime(date + "000000000", "yyyyMMddHHmmssSSS");
    }

    /**
     * 获取指定 （yyyyMMdd/yyyy-MM-dd)格式日期 的 当天结束 时间string yyyMMddHHmmssSSS
     *
     * @param date
     * @return
     */
    public static Date getEndDateTime(String date) {
        if(date.contains("-"))//如果是yyyy-MM-dd
        {
            date=date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
        }
        return DateUtils.toDateTime(date + "235959999", "yyyyMMddHHmmssSSS");
    }

    /**
     * 获取指定NG个月后的date
     */
    public static Date getSetoffMonth(Date aDate,int months)
    {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(aDate);
        rightNow.add(Calendar.MONTH,months);
        return rightNow.getTime();
    }
    /**
     * 获取指定NG个月后的date
     * type=Calendar.MONTH,
     */
    public static Date getSetoff(Date aDate,int count ,int type)
    {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(aDate);
        rightNow.add(type,count);
        return rightNow.getTime();
    }

    /**
     * 将字串转成日期和时间，字串格式: yyyy-MM-dd
     * @param string
     * @return
     */
    public static Date yyyyMMddToDate(String string) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            return formatter.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取月初
     */
    public static String getMonthBegin(String yyyyMM)
    {
        return yyyyMM+"01";

    }
    /**
     * 获取月末
     */
    public static String getMonthEnd(String yyyyMM)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(yyyyMM+"01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return DateUtils.formatDate(calendar.getTime(), "yyyyMMdd");
    }
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
          //  day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
           // sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    public static long getDistanceTime( Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        //hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        return   min ;
    }

    /**
     * 获取时间毫秒差
     * @param one
     * @param two
     * @return
     */
    public static long getApartTime( Date one, Date two) {
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff = time1 - time2;
        return diff ;
    }

    /**
     * 相隔天数
     * @param d1
     * @param d2
     * @return
     */
    public static int daysApart(Date d1, Date d2){
        String pushStr = DateUtils.formatDate(d1,"yyyy-MM-dd");
        String dateStr = DateUtils.formatDate(d2,"yyyy-MM-dd");
        Date date1 = DateUtils.toDate(pushStr);
        Date date2 = DateUtils.toDate(dateStr);
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    
    public static Date getWeekBegin(Date date)
    {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);;
    	//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return getBeginDateTime(sdf.format(cal.getTime()));
    }
    public static Date getWeekEnd(Date date)
    {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);;
    	//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        return getEndDateTime(sdf.format(cal.getTime()));
    }

    public static List<String> findDates(String start, String end) {
        List<String> lDate = new ArrayList();
        try {
            Date dBegin = toDate(start);
            Date dEnd = toDate(end);
            Date now = toDate(formatDate(new Date(),"yyyy-MM-dd"));
            if(StringUtils.isBlank(start)){
                start = "2016-11-01";
                dBegin = toDate(start);
            }else{
                if(dBegin.after(now)){
                    start = formatDate(getSetoffDay(-1), "yyyy-MM-dd");
                    dBegin = toDate(start);
                }
            }

            if(StringUtils.isBlank(end) || dEnd.after(now)){
                end = formatDate(getSetoffDay(-1), "yyyy-MM-dd");
                dEnd = toDate(end);
            }
            lDate.add(start);
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(formatDate(calBegin.getTime(),"yyyy-MM-dd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lDate;
    }


    public static void main(String[] args) throws ParseException {
        List<String> dates = findDates("2018-03-05", "2018-04-08");
        for (String str:dates) {
            System.out.println(str);
        }

    }
}
