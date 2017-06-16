package com.github.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/14.
 */
public class DateUtils {
    public static final String ymdhm="yyyy-MM-dd HH:mm";
    public static final String ymdhms="yyyy-MM-dd HH:mm:ss";
    //把日期转为字符串
    public static String dateToString(Date date) {
        return dateToString(date,"yyyy-MM-dd");
    }
    public static String dateToString(Date date,String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    //把字符串转为日期
    public static Date stringToDate(String strDate,String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
    public static Date stringToDate(String strDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getLocalDate(){
        return new Timestamp(new Date().getTime())+"";
    }

}
