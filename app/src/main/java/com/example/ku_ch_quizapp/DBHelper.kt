package com.example.ku_ch_quizapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.LocalTime

// Replace this string with your .sql/.db file name
const val dbName = "KU_CH_QuizApp.db"

// Database version number, you need to change it in case of any schema
// change.
const val dbVersionNumber = 1

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersionNumber) {

    private var dataBase: SQLiteDatabase? = null

    init {
        // Check if the database already copied to the device.
        val dbExist = checkDatabase()
        if (dbExist) {
            // if already copied then don't do anything.
            Log.d("dbCheck", "Database exist")
        } else {
            // else copy the database to the device.
            Log.d("dbCheck", "Database doesn't exist")
            createDatabase()
        }
    }


    // Copy the database
    private fun createDatabase() {
        copyDatabase()
    }

    // Check if the database already copied to the device.
    private fun checkDatabase(): Boolean {
        val dbFile = File(context.getDatabasePath(dbName).path)
        return dbFile.exists()
    }

    // Copy the database
    private fun copyDatabase() {

        val inputStream = context.assets.open("$dbName")

        val outputFile = File(context.getDatabasePath(dbName).path)
        val outputStream = FileOutputStream(outputFile)

        val bytesCopied = inputStream.copyTo(outputStream)
        Log.d("bytesCopied", "$bytesCopied")
        inputStream.close()

        outputStream.flush()
        outputStream.close()
    }

    // Open the database with read and write access mode.
    private fun openDatabase() {
        dataBase = SQLiteDatabase.openDatabase(context.getDatabasePath(dbName).path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    // Close the database.
    override fun close() {
        dataBase?.close()
        super.close()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun get_question1(test_num:Int, type:Int, rand:Boolean): MutableList<question>{
        openDatabase()

        var query:String?

        if (rand == true) {
            query = "SELECT * FROM question1 WHERE question1.type = ${type} ORDER BY RANDOM() LIMIT 10;"
        } else { query = "SELECT * FROM question1 WHERE question1.test_num = ${test_num} ORDER BY question1.number;"}

        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<question>()
        do {
            val test_num = cursor?.getInt(0)
            val number = cursor?.getInt(1)
            val type = cursor?.getInt(2)
            val question = cursor?.getString(3)
            val question_detail = cursor?.getString(4)
            val answer = cursor?.getInt(5)
            val choice1 = cursor?.getString(6)
            val choice1_detail = cursor?.getString(7)
            val choice2 = cursor?.getString(8)
            val choice2_detail = cursor?.getString(9)
            val choice3 = cursor?.getString(10)
            val choice3_detail = cursor?.getString(11)
            val choice4 = cursor?.getString(12)
            val choice4_detail = cursor?.getString(13)
            val order = 0
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }

    fun get_question2(test_num:Int, rand:Boolean): MutableList<question>{
        openDatabase()
        var query:String?
        if (rand==true) {
            query = "SELECT * FROM question2_1 natural join question2_2 ORDER BY RANDOM() LIMIT 10;"
        } else {
            query = "SELECT * FROM question2_1 natural join question2_2 WHERE question2_1.test_num = ${test_num} ORDER BY question2_1.number;"
        }
        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<question>()
        do {
            val test_num = cursor?.getInt(0)
            val type = cursor?.getInt(1)
            val order = cursor?.getInt(2)
            val number = cursor?.getInt(3)
            val question = cursor?.getString(4)
            val question_detail = cursor?.getString(5)
            val answer = cursor?.getInt(6)
            val choice1 = cursor?.getString(7)
            val choice1_detail = cursor?.getString(8)
            val choice2 = cursor?.getString(9)
            val choice2_detail = cursor?.getString(10)
            val choice3 = cursor?.getString(11)
            val choice3_detail = cursor?.getString(12)
            val choice4 = cursor?.getString(13)
            val choice4_detail = cursor?.getString(14)
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }

    fun insert_score(test_num:Int, type_num:Int, score:Int) {
        openDatabase()
        var query:String = "INSERT INTO score(test_num, type_num, score) VALUES (${test_num}, ${type_num}, ${score});"
        dataBase?.execSQL(query)
        Log.d("score", "점수 입력 완료")
        close()
    }

    fun insert_review(type_num:Int, test_num:Int, number:Int, wrong_answer:Int){
        openDatabase()
        if (type_num < 6) {
            dataBase?.execSQL("INSERT INTO review1(test_num, number, wrong_answer) VALUES (${test_num}, ${number}, ${wrong_answer});")
        } else {
            dataBase?.execSQL("INSERT INTO review2(test_num, number, wrong_answer) VALUES (${test_num}, ${number}, ${wrong_answer});")
        }
        Log.d("review", "오답 노트 입력 완료")
        close()
    }

    fun get_review():MutableList<review>{
        openDatabase()
        var query:String?
        query = "SELECT * FROM review1, review2 ORDER BY review_cnt;"
        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<review>()
        do {
            val review_cnt = cursor?.getInt(0)
            val test_num = cursor?.getInt(1)
            val number = cursor?.getInt(2)
            val wrong_answer = cursor?.getInt(3)
            list.add(review(review_cnt, test_num, number, wrong_answer))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }

    fun get_score():MutableList<score>{
        openDatabase()
        var query:String?
        query = "SELECT * FROM score ORDER BY score_cnt;"
        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<score>()
        do {
            val test_num = cursor?.getInt(0)
            val type_num = cursor?.getInt(1)
            val score = cursor?.getInt(2)
            val score_cnt = cursor?.getInt(3)
            list.add(score(test_num, type_num, score, score_cnt))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }
}

data class question(var test_num: Int?, var number: Int?, var type:Int?, var question: String?, var question_detail:String?, var answer:Int?, var choice1:String?, var choice1_detail:String?,
                     var choice2:String?, var choice2_detail:String?, var choice3:String?, var choice3_detail:String?, var choice4:String?, var choice4_detail:String?, var order:Int?)
data class review(var review_cnt:Int?, var test_num:Int?, var number:Int?, var wrong_answer:Int?)
data class score(var test_num: Int?, var type_num:Int?, var score:Int?, var score_cnt:Int?)