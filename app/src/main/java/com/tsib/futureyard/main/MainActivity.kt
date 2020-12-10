package com.tsib.futureyard.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsib.futureyard.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter =
            ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(CameraFragment())
        adapter.addFragment(DashBoardFragment())
        viewPager.adapter = adapter
        viewPager.currentItem = 1
    }
}