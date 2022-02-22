package com.dicoding.moviecatalogue.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.databinding.ActivityFavoriteBinding
import com.dicoding.moviecatalogue.ui.SectionPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionPageAdapter = SectionPageAdapter(this, "favorite")
        val viewPager: ViewPager2 = activityFavoriteBinding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = activityFavoriteBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(SectionPageAdapter.TAB_TITLES[position])
        }.attach()

        val toolbar = activityFavoriteBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}