package com.corona.utils;

import java.util.Calendar;

public class DateUtils {
    public static String makeTodayString() {
        // YYYYMMDD
        Calendar c = Calendar.getInstance();
        String today = c.get(Calendar.YEAR)+leadingZero(c.get(Calendar.MONTH)+1)+leadingZero(c.get(Calendar.DATE));
        return today;
    }

    public static String makeAWeekAgeDateString(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        String today = c.get(Calendar.YEAR)+leadingZero(c.get(Calendar.MONTH)+1)+leadingZero(c.get(Calendar.DATE));
        return today;
    }

    public static String leadingZero(int i) {
        return i<10 ? "0"+i:""+i;
    }
}
