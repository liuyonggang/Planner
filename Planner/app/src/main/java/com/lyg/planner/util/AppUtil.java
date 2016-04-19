package com.lyg.planner.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/4/11.
 */
public class AppUtil {

    /* 获取时间戳*/
    public static long getMilliDate(String date){
        long milliDtate = 0;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try{
            milliDtate = format.parse(date).getTime();
        }catch (Exception e){

        }
        return milliDtate;
    }

    public static boolean isEntityString(String s) {
        return s == null || s.equals("null") || s.equals("") || s.trim().length() <= 0 ? false : true;
    }
}
