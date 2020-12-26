package com.company.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class MyDateUtil {

    // Date转换时间戳
    // long time = new Date().getTime();

    // 时间戳转换Date
    // long time = new Date().getTime();
    // Date date = new Date(time);

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_Z = "EEE MMM dd HH:mm:ss Z yyyy";

    /**
     *  处理时间格式 2019-11-28T06:52:09.724+0000 为 yyyy-MM-dd HH:mm:ss 格式字符串日期
     *
     * @param oldDate  "2020-12-26T05:34:47.000+0000"
     * @return String 2020-12-26 05:34:47
     * */
    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat(FORMAT_T);
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat (FORMAT_Z, Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat(FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }
    /**
     *  处理String格式的时间类型  为 Timestamp mysql识别的日期格式
     *
     * @param object 2020-12-26T05:34:47.000+0000
     * @return Timestamp 2020-12-26 05:34:47.0
     * */
    public static Timestamp returnTimestampForTemp(Object object){
        String oString = dealDateFormat(object.toString());
        Timestamp timestamp = Timestamp.valueOf(oString);
        return  timestamp;
    }


    /**
     * 描述：字符串转换为Date
     * @param dateStr
     **/
    public static Date stringConvertDate(String dateStr) {
        return MyDateUtil.stringConvertDate(dateStr, FORMAT);
    }

    /**
     * 描述：字符串转换为Date
     * @param dateStr
     * @param partten
     **/
    public static Date stringConvertDate(String dateStr, String partten) {
        try {
            return new SimpleDateFormat(partten).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述：字符串转换时间戳
     * @param dateStr
     **/
    public static Long stringConvertTimestamp(String dateStr){
        return MyDateUtil.stringConvertTimestamp(dateStr, FORMAT);
    }

    /**
     * 描述：字符串转换时间戳
     * @param dateStr
     * @param partten
     **/
    public static Long stringConvertTimestamp(String dateStr, String partten){
        try {
            return new SimpleDateFormat(partten).parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述：Date转换字符串
     * @param date
     **/
    public static String dateConvertString(Date date) {
        return MyDateUtil.dateConvertString(date, FORMAT);
    }

    /**
     * 描述：Date转换字符串
     * @param date
     * @param partten
     **/
    public static String dateConvertString(Date date, String partten) {
        return new SimpleDateFormat(partten).format(date);
    }

    /**
     * 描述：时间戳转换字符串
     * @param timestamp
     **/
    public static String timestampConvertString(Long timestamp){
        return MyDateUtil.timestampConvertString(timestamp, FORMAT);
    }

    /**
     * 描述：时间戳转换字符串
     * @param timestamp
     * @param partten
     **/
    public static String timestampConvertString(Long timestamp, String partten){
        return new SimpleDateFormat(partten).format(timestamp);
    }


    /**
     * 描述：计算时间差
     * @param startTimeStr
     * @param endTimeStr
     **/
    public static String getTimeDifference(String startTimeStr, String endTimeStr) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        Date startTime = null;
        Date endTime  = null;
        try {
            startTime = format.parse(startTimeStr);
            endTime = format.parse(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long between = endTime.getTime() - startTime.getTime();

//        long day = between / (24 * 60 * 60 * 1000);
//        long hour = (between / (60 * 60 * 1000) - day * 24);
//        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        String result = day + "天" + hour + "时" + min + "分" + s + "秒";

        long hour = (between / (60 * 60 * 1000));
        long min = ((between / (60 * 1000) - hour * 60));
        long s = (between / 1000 - hour * 60 * 60 - min * 60);
        String result = hour + "时" + min + "分" + s + "秒";
        return result;
    }


    /**
     * Date转LocalTime
     *
     * @param date
     * @return
     */
    private static LocalTime dateConvertLocalTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * LocalTime转Date
     *
     * @param localTime
     * @return
     */
    private static Date localTimeConvertDate(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    private static LocalDate dateConvertLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    private static Date localDateConvertDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    private static LocalDateTime dateConvertLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeConvertDate(LocalDateTime localDateTime){
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }



}
