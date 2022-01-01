package com.example.ku_ch_quizapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.annotation.NonNull




class FragmentAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    var fragmentList = listOf<Fragment>()

    private val name: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {

        Log.d("getPageTitle", "getPageTitle!, ${position}")
        return when (position) {
            0 -> "유형별"
            1 -> "기출별"
            2 -> "오답 노트"
            else -> "나의 정보"
        }
    }

}