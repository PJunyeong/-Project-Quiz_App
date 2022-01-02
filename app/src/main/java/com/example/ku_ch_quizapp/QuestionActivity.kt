package com.example.ku_ch_quizapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val db_helper = DBHelper(this)
        var adapter = CustomAdapter()
        var num:Int?
        var questions:MutableList<question>
        var quiz_type = true
        var submited = false
        if (intent.hasExtra("type_num")){
            Log.d("type_num", "유형별 퀴즈를 시작합니다.")
            quiz_type = true
            num = intent.getIntExtra("type_num", 0)
            if (num < 6) {
            questions= db_helper.get_question1(-1, num, true)
            } else {
                questions = db_helper.get_question2(-1,true)
            }
            adapter.listData = questions
            recyclerView.adapter = adapter
            adapter.num = num
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else if (intent.hasExtra("test_num")) {
            Log.d("test_num", "기출별 퀴즈를 시작합니다.")
            quiz_type = false
            num = intent.getIntExtra("test_num", 0)
            var question1s = db_helper.get_question1(num, -1, false)
            var question2s = db_helper.get_question2(num, false)
            questions = (question1s + question2s) as MutableList<question>
            adapter.listData = questions
            adapter.num = num
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        question_submit.setOnClickListener {
            val answers = adapter.listAnswer
            val questions = adapter.listData
            val num = adapter.num
            var total:Int = 0

            Log.d("size_answers", "${answers.size}")
            Log.d("size_questions", "${questions.size}")

            if (!submited && answers.size != (questions.size+1)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("채점").setMessage("답을 모두 고르지 않았습니다. 제출하시겠습니까?")
                .setPositiveButton("예", DialogInterface.OnClickListener{dialog, id ->
                    submited = true
                    for (question in questions) {
                        if (answers[get_key(question)] != null) {
                            if (answers[get_key(question)] == question.answer) {
                                total += 1
                            } else {
                                db_helper.insert_review(question.type!!, question.test_num!!, question.number!!, answers[get_key(question)]!!)
                            }
                        }
                    }
                    if (quiz_type) {
                        db_helper.insert_score(0, num, total)
                    } else {
                        db_helper.insert_score(num, 0, total)
                    }
                    adapter.question_set = false
                    adapter.notifyDataSetChanged()
                    question_submit.text = "돌아가기"
                })
                .setNegativeButton("아니오", DialogInterface.OnClickListener {dialog, id ->

                })
                builder.show() } else if (submited) {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
                finish()
            } else { for (question in questions) {
                if (answers[get_key(question)] != null) {
                    if (answers[get_key(question)] == question.answer) {
                        total += 1
                    } else {
                        db_helper.insert_review(question.type!!, question.test_num!!, question.number!!, answers[get_key(question)]!!)
                    }
                }
            }
            // 기출 문제면 type_num을 0, test_num을 기출 회차 입력한다. 유형 문제면 test_num을 0, type_num을 유형 회차 입력한다.
            if (quiz_type) {
                db_helper.insert_score(0, num, total)
            } else {
                db_helper.insert_score(num, 0, total)
            }
            adapter.question_set = false
            adapter.notifyDataSetChanged()
            question_submit.text = "돌아가기"
            submited = true}
        }
    }
}