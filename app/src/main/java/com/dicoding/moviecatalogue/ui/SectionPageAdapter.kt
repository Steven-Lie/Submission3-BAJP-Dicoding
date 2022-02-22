package com.dicoding.moviecatalogue.ui

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.ui.movie.MovieFragment
import com.dicoding.moviecatalogue.ui.tvshow.TvFragment

class SectionPageAdapter(activity: AppCompatActivity, private val from: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment(from)
            1 -> TvFragment(from)
            else -> Fragment()
        }
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv)
    }
}