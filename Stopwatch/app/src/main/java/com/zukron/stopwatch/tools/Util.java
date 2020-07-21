package com.zukron.stopwatch.tools;

import java.util.Locale;

/**
 * Project name is Stopwatch
 * Created by Zukron Alviandy R on 7/20/2020
 */
public class Util {
    public static String formatToDuration(int minute, int second, int milli) {
        return String.format(Locale.US, "%02d:%02d:%02d", minute, second, milli);
    }

    public static int convertToMinute(int i) {
        return (i / 90) / 60;
    }

    public static int convertToSecond(int i) {
        return (i / 90) % 60;
    }

    public static int convertToMilli(int i) {
        return (i % 90);
    }
}
