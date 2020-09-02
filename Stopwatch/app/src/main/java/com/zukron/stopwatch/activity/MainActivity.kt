package com.zukron.stopwatch.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zukron.stopwatch.R
import com.zukron.stopwatch.adapter.LapAdapter
import com.zukron.stopwatch.model.Lap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.properties.Delegates

/**
 * Project name is Stopwatch
 * Created by Zukron Alviandy R on 9/2/2020
 * Contact me if any issues on zukronalviandy@gmail.com
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var running: Boolean = false
    private var increment = 0
    private var curIncrement = 0
    private var prevIncrement = 0
    private var incrementLap: Int by Delegates.observable(0) { _, oldValue, newValue ->
        curIncrement = newValue
        prevIncrement = oldValue
    }

    private var indexLap = 0
    private var listLap: MutableList<Lap> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        sa_time.progress = 0
        sa_time.isEnabled = false

        btn_pause.isEnabled = false
        btn_lap_reset.isEnabled = false

        btn_time.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        btn_lap_reset.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_time -> startStopwatch()
            R.id.btn_pause -> pauseStopwatch()
            R.id.btn_lap_reset -> lapOrResetStopWatch()
        }
    }

    private fun startStopwatch() {
        btn_pause.isEnabled = true
        btn_lap_reset.isEnabled = true
        btn_lap_reset.text = resources.getString(R.string.lap)

        // jika state kondisi false atau pause, untuk menghindari start yang berulang" jika user menekan tombol start
        if (!running) {
            running = true
            CoroutineScope(IO).launch {
                while (running) {
                    delay(10)

                    val second = (increment / 90) % 60
                    val progress = (second / 60) * 100

                    withContext(Main) {
                        sa_time.progress = progress
                        btn_time.text = Lap.convertToDuration(increment)
                    }

                    increment += 1
                }
            }
        }
    }

    private fun pauseStopwatch() {
        running = false
        btn_lap_reset.text = resources.getString(R.string.reset)
    }

    private fun lapOrResetStopWatch() {
        if (running) {
            incrementLap = increment
            val diff = curIncrement - prevIncrement

            val lap = Lap(indexLap, increment, diff)
            listLap.add(lap)

            val lapAdapter = LapAdapter(this, listLap)
            lv_lap_result.adapter = lapAdapter

            indexLap += 1
        } else {
            increment = 0
            incrementLap = 0
            indexLap = 0
            listLap.clear()

            sa_time.progress = 0
            lv_lap_result.adapter = null

            btn_pause.isEnabled = false
            btn_lap_reset.isEnabled = false
            btn_time.text = resources.getString(R.string.start)
        }
    }
}
