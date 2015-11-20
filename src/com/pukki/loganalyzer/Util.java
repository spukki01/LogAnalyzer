package com.pukki.loganalyzer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Util {

    public static String getTimeStamp(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    public static String getTimeStamp() {
        return getTimeStamp("yyyy-MM-dd HH:mm");
    }

}
