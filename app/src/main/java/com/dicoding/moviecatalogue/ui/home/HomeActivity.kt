package com.dicoding.moviecatalogue.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.databinding.ActivityHomeBinding
import com.dicoding.moviecatalogue.ui.SectionPageAdapter
import com.dicoding.moviecatalogue.ui.favorite.FavoriteActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val sectionPageAdapter = SectionPageAdapter(this, "home")
        val viewPager: ViewPager2 = activityHomeBinding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = activityHomeBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(SectionPageAdapter.TAB_TITLES[position])
        }.attach()


        val toolbar = activityHomeBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}