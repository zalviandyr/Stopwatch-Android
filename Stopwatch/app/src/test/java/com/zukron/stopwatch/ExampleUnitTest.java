package com.zukron.stopwatch;

import com.zukron.stopwatch.model.Lap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() {
        ArrayList<Lap> laps = new ArrayList<>();
        Lap lap = new Lap();
        lap.setIndex(0);
        laps.add(lap);

        Lap lap1 = new Lap();
        lap1.setIndex(1);
        laps.add(lap1);

        Collections.sort(laps, new Comparator<Lap>() {
            @Override
            public int compare(Lap lap, Lap t1) {
                return t1.getIndex().compareTo(lap.getIndex());
            }
        });

        for (Lap lap2 : laps) {
            System.out.println(lap2.getIndex());
        }
    }
}