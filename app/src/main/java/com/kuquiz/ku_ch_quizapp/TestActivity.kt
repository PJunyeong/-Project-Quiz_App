package com.kuquiz.ku_ch_quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val sharedPreference = getSharedPreferences("tag", 0)
        val name = sharedPreference.getString("nickname", "")
        after_login_hello.text = "${name}"

        val fragmentList = listOf(FragmentType(), FragmentTest(), FragmentReview(), FragmentInfo())
        val adapter = FragmentAdapter(supportFragmentManager, 1)
        adapter.fragmentList = fragmentList
        after_login_viewpager.adapter = adapter
        after_login_tablayout.setupWithViewPager(after_login_viewpager)



    }
}