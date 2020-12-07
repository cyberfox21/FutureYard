package com.tsib.futureyard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(ArFragment())
        adapter.addFragment(DashBoardFragment())
        viewPager.adapter = adapter
    }
}