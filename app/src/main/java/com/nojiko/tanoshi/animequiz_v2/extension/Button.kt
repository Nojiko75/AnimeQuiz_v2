package com.nojiko.tanoshi.animequiz_v2.extension

import android.widget.Button
import androidx.core.content.ContextCompat
import com.nojiko.tanoshi.animequiz_v2.R

fun Button.enable() {
    this.isEnabled = true
    this.isClickable = true
    this.setBackgroundColor(
        ContextCompat.getColor(
            context,
            R.color.button_green
        )
    )
}

fun Button.disable() {
    this.isEnabled = false
    this.isClickable = false
    this.setBackgroundColor(
        ContextCompat.getColor(
            context,
            R.color.button_disabled
        )
    )
}