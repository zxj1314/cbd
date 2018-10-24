package com.test.cbd.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName      TimeFormatUtils.java
 * @Description    时间格式化工具类
 * @author         huangjunjie
 * @Date           2017/7/24
 */
public class TimeFormatUtils {

    private TimeFormatUtils(){}

    /**
     * @param         time
     * @Description  将时间字符串格式化为"yyyy-MM-dd'T'HH:mm:ss"
     * @author       huangjunjie
     * @date         2017/7/19
     */
    public static String formatString(String time) throws ParseException {
        if(time == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(time);
        return sdf.format(d);
    }

    public static String formatToString(String time) throws ParseException {
        if(time == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date d = sdf.parse(time);
        return sdf.format(d);
    }


    /**
     * @Description 获取当前日期字符串，格式为"yyyyMMdd"
     * @author       huangjunjie
     * @date         2017/7/19
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }


    public static String formatDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public static String getTimeStr(String startTime) {
        Date date = new Date(Long.parseLong(startTime));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    public static String getTime(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date(date);
        return sdf.format(time);
    }
}
