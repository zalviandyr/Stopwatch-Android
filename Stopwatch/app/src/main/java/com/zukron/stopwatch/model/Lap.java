package com.zukron.stopwatch.model;

import com.zukron.stopwatch.tools.Util;

/**
 * Project name is Stopwatch
 * Created by Zukron Alviandy R on 7/20/2020
 */
public class Lap {
    private Integer index;
    private String lap;
    private String diff;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getLap() {
        return lap;
    }

    public void setLap(int increment) {
        int minute = Util.convertToMinute(increment);
        int second = Util.convertToSecond(increment);
        int milli = Util.convertToMilli(increment);

        this.lap = Util.formatToDuration(minute, second, milli);
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(int increment) {
        int minute = Util.convertToMinute(increment);
        int second = Util.convertToSecond(increment);
        int milli = Util.convertToMilli(increment);

        this.diff = "+" + Util.formatToDuration(minute, second, milli);
    }
}
