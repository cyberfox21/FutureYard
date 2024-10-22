package com.tsib.futureyard.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tsib.futureyard.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(Constants.TAG, "MainActivity: onCreate()")

        setContentView(R.layout.activity_main)

        val adapter =
            ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(CameraFragment())
        adapter.addFragment(DashBoardFragment())
        viewPager.adapter = adapter
        viewPager.currentItem = 1
        spring_dots_indicator.setViewPager(viewPager)
    }
}