package com.nojiko.tanoshi.animequiz_v2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide action bar for Android 12
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)
    }
}