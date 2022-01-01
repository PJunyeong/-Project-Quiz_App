package com.example.ku_ch_quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db_helper = DBHelper(this)
        val questions = db_helper.get_question2(-1, true)
        for (question in questions){
            Log.d("question", "${question}")
        }

        name_btn.setOnClickListener {
            val nickname = nickname_input.getText()

            if (nickname.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "닉네임을 입력하세요!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "${nickname}님 안녕하세요!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
