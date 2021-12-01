package com.kizadev.myapplication.extensions

import androidx.core.text.buildSpannedString
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit


fun Long.mapToMinutes(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(minutes)

    return StringBuilder().append("$minutes:$seconds").toString()
}