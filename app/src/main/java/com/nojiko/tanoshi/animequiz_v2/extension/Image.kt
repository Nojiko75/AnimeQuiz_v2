package com.nojiko.tanoshi.animequiz_v2.extension

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.nojiko.tanoshi.animequiz_v2.R

@RequiresApi(Build.VERSION_CODES.M)
fun ShapeableImageView.wrong() {
    this.foreground =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24)
    this.alpha = 0.5F
    this.isClickable = false
}

@RequiresApi(Build.VERSION_CODES.M)
fun ShapeableImageView.isProposal() {
    this.foreground = null
    this.alpha = 1F
    this.isClickable = true
}