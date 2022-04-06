package com.nojiko.tanoshi.animequiz_v2

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nojiko.tanoshi.animequiz_v2.databinding.ActivityGameBinding
import com.nojiko.tanoshi.animequiz_v2.viewpager.MenuViewPagerAdapter


class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MenuViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        val textMenu = listOf(
            getString(R.string.characters),
            getString(R.string.mangas),
            getString(R.string.culture)
        )
        val iconMenu = listOf(R.drawable.avatar, R.drawable.anime, R.drawable.porte_torii)

        binding.gameMenu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(applicationContext, R.color.teal_700)
                tab?.icon?.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        tabIconColor,
                        BlendModeCompat.SRC_ATOP
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(applicationContext, R.color.black)
                tab?.icon?.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        tabIconColor,
                        BlendModeCompat.SRC_ATOP
                    )
            }
        })

        TabLayoutMediator(binding.gameMenu, binding.viewPager) { tab, position ->
            tab.text = textMenu[position]
            tab.setIcon(iconMenu[position])
        }.attach()
    }
}