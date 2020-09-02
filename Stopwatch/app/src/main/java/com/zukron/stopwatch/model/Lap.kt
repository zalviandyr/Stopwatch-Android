package com.zukron.stopwatch.model

import java.util.*

/**
 * Project name is Stopwatch
 * Created by Zukron Alviandy R on 9/2/2020
 * Contact me if any issues on zukronalviandy@gmail.com
 */

data class Lap(
        var index: Int,
        var lap: Int,
        var diff: Int
) {
    companion object {
        fun convertToDuration(increment: Int): String {
            val minute = (increment / 90) / 60
            val second = (increment / 90) % 60
            val millis = (increment % 90)

            return String.format(
                    Locale.US,
                    "%02d:%02d:%02d",
                    minute, second, millis
            )
        }
    }
}