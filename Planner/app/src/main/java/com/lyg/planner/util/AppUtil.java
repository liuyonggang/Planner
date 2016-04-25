package com.lyg.planner.util;

import android.util.Log;

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

    public static String getTotalDays(long startMillis,long endMillis){
        long totalTime = endMillis - startMillis;
        try{
            int day = (int)Math.floor(totalTime/(24*60*60*1000));
            if (totalTime%(24*60*60*1000) == 0){
                return day+"天";
            }
            int hour = (int)Math.floor(totalTime/(60*60*1000)%24);
            if (totalTime%(60*60*1000) == 0){
                return day+"天"+hour+"时";
            }
            int minite = (int)Math.floor((totalTime/(60*1000))%60);
            return day+"天"+hour+"时"+minite+"分";
        }catch (Exception e){
            Log.e("SubPlan-EXP",e.getMessage());
        }

        return null;
    }
}
