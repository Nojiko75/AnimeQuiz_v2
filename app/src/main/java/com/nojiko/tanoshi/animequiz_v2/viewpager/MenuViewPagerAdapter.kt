package com.nojiko.tanoshi.animequiz_v2.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nojiko.tanoshi.animequiz_v2.MenuCharacterFragment
import com.nojiko.tanoshi.animequiz_v2.MenuCultureFragment
import com.nojiko.tanoshi.animequiz_v2.MenuMangaFragment

class MenuViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MenuCharacterFragment()
            1 -> return MenuMangaFragment()
        }
        return MenuCultureFragment()
    }

    companion object {
        private const val NUM_TABS = 3
    }
}