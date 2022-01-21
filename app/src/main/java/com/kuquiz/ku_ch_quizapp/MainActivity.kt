package com.kuquiz.ku_ch_quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreference = getSharedPreferences("tag", 0)
        super.onCreate(savedInstanceState)
        if (sharedPreference.getString("nickname", "") != ""){
            Log.d("shared", "shared preference exists")
            val nickname: String? = sharedPreference.getString("nickname", "")
            setContentView(R.layout.activity_main)
            nickname_hint.hint = ""
            nickname_sub.text = ""
            nickname_input.isFocusableInTouchMode = false
            nickname_input.setText("${nickname} 님, 반가워요!")
            nickname_input.textAlignment = 4 // 이름을 센터에 정렬한다.
            name_btn.text = "시작"
            name_btn.setOnClickListener{
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
                finish()
            }

        } else {
        setContentView(R.layout.activity_main)
        val db_helper = DBHelper(this)
        db_helper.close()
        name_btn.setOnClickListener {
            val nickname = nickname_input.getText().toString()
            if (nickname.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "닉네임을 입력하세요!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "${nickname}님 안녕하세요!", Toast.LENGTH_LONG).show()
                val editor = sharedPreference.edit()
                editor.putString("nickname", nickname)
                editor.apply()
                Log.d("shared", "shared preference saved")
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        }
    }
}

