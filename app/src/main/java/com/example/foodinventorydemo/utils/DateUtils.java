package com.example.foodinventorydemo.utils;

import android.content.Context;
import android.util.TypedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
    public static final String PATTERN = "MMM dd, yyyy";


    private static SimpleDateFormat getFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.ENGLISH);
    }

    public static long getTimeInMillis() {
        return getTimeInMillis(null);
    }

    public static long getTimeInMillis(String roundToUnit) {
        long millis = Calendar.getInstance().getTimeInMillis();

        if (roundToUnit != null) {
            long divisor = 0;
            if (roundToUnit.equals("DAY")) divisor = android.text.format.DateUtils.DAY_IN_MILLIS;
            if (divisor != 0) millis = Math.round((float)millis/(float)divisor);
        }

        return millis;
    }

    public static String getTime() {
        return getTime(PATTERN);
    }
    public static String getTime(String pattern) {
        return format(Calendar.getInstance().getTimeInMillis(), pattern);
    }

    public static String format(long ts) {
        return format(ts, PATTERN);
    }
    public static String format(long ts, String pattern) {
        return getFormat(pattern).format(ts);
    }


    public static String swapFormat(String ts, String oldPattern, String newPattern) throws ParseException {
        return format(parse(ts, oldPattern), newPattern);
    }

    public static long parse(String ts, String pattern) throws ParseException {
        return getFormat(pattern).parse(ts).getTime();
    }

}
