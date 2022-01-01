package com.example.ku_ch_quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val db_helper = DBHelper(this)
        var num:Int?
        if (intent.hasExtra("type_num")){
            Log.d("type_num", "유형별 퀴즈를 시작합니다.")
            num = intent.getIntExtra("type_num", 0)
            if (num < 6) {
            val questions= db_helper.get_question1(-1, num, true)
            for (question in questions) {
                Log.d("question1", "${question}")
            }

            } else {
                val questions = db_helper.get_question2(-1,true)
                for (question in questions) {
                    Log.d("question2", "${question}")
                }
            }


        } else if (intent.hasExtra("test_num")) {
            Log.d("test_num", "기출별 퀴즈를 시작합니다.")
            val test_num = intent.getIntExtra("test_num", 0)
            val question1s = db_helper.get_question1(test_num, -1, false)
            for (question in question1s) {
                Log.d("question1", "${question}")
            }
            val question2s = db_helper.get_question2(test_num, false)
            for (question in question2s) {
                Log.d("question2", "${question}")
            }
        }



    }
}