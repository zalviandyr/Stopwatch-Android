package com.zukron.stopwatch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.triggertrap.seekarc.SeekArc;
import com.zukron.stopwatch.adapter.LapAdapter;
import com.zukron.stopwatch.R;
import com.zukron.stopwatch.model.Lap;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SeekArc saTime;
    private Button btnTime, btnPause, btnLapReset;
    private ListView lvLapResult;
    private static boolean isStart = false;
    private static int increment = 0;
    private static int indexLap = 0;
    private ArrayList<Integer> incrementArray = new ArrayList<>();
    private ArrayList<Lap> laps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saTime = findViewById(R.id.sa_time);
        btnTime = findViewById(R.id.btn_time);
        btnPause = findViewById(R.id.btn_pause);
        btnLapReset = findViewById(R.id.btn_lap_reset);
        lvLapResult = findViewById(R.id.lv_lap_result);

        init();
    }

    private void init() {
        incrementArray.add(0);

        saTime.setProgress(0);
        saTime.setEnabled(false);

        btnPause.setEnabled(false);
        btnLapReset.setEnabled(false);

        btnTime.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnLapReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_time:
                startStopwatch();
                break;
            case R.id.btn_pause:
                pauseStopwatch();
                break;
            case R.id.btn_lap_reset:
                lapResetStopwatch();
                break;
        }
    }

    private void startStopwatchThread() {
        Thread stopwatchThread = new Thread() {
            @Override
            public void run() {
                while (MainActivity.isStart) {
                    try {
                        Thread.sleep(10);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int minute = convertToMinute(MainActivity.increment);
                                int second = convertToSecond(MainActivity.increment);
                                int milli = convertTOMilli(MainActivity.increment);

                                double progress = ((double) second / 60) * 100;
                                saTime.setProgress((int) progress);

                                btnTime.setText(formatToDuration(minute, second, milli));
                            }
                        });

                        MainActivity.increment += 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        stopwatchThread.start();
    }

    private int convertToMinute(int i) {
        return (i / 90) / 60;
    }

    private int convertToSecond(int i) {
        return (i / 90) % 60;
    }

    private int convertTOMilli(int i) {
        return (i % 90);
    }

    private String formatToDuration(int minute, int second, int milli) {
        return String.format(Locale.US, "%02d:%02d:%02d", minute, second, milli);
    }

    private void pauseStopwatch() {
        MainActivity.isStart = false;

        btnLapReset.setText(getResources().getString(R.string.reset));
    }

    private void startStopwatch() {
        btnPause.setEnabled(true);
        btnLapReset.setEnabled(true);
        btnLapReset.setText(getResources().getString(R.string.lap));

        MainActivity.isStart = true;
        startStopwatchThread();
    }

    private void lapResetStopwatch() {
        if (btnLapReset.getText().equals(getResources().getString(R.string.lap))) {
            // increment array untuk current lap
            incrementArray.add(MainActivity.increment);

            int curInc = incrementArray.get(MainActivity.indexLap + 1);
            int prevInc = incrementArray.get(MainActivity.indexLap);

            Lap lap = new Lap();
            lap.setIndex(MainActivity.indexLap);
            lap.setLap(MainActivity.increment);
            lap.setDiff(curInc - prevInc);

            laps.add(lap);

            LapAdapter lapAdapter = new LapAdapter(this, laps);
            lvLapResult.setAdapter(lapAdapter);

            // increment index
            MainActivity.indexLap += 1;
        } else if (btnLapReset.getText().equals(getResources().getString(R.string.reset))) {
            MainActivity.increment = 0;
            MainActivity.indexLap = 0;

            saTime.setProgress(0);
            laps.clear();
            incrementArray.clear();
            incrementArray.add(0);

            lvLapResult.setAdapter(null);

            btnPause.setEnabled(false);
            btnLapReset.setEnabled(false);
            btnTime.setText(getResources().getString(R.string.start));
        }
    }
}