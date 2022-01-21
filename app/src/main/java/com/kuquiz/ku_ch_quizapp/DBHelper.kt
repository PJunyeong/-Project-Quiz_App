package com.kuquiz.ku_ch_quizapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream


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
            val cnt = 0
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order, cnt))
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
            val cnt = 0
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order, cnt))
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

    fun get_review():MutableList<question>{
        openDatabase()
        val list = mutableListOf<question>()
        var query:String?

        query = "SELECT COUNT(*) FROM review1"
        var cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        var review1_cnt = cursor?.getInt(0)
        cursor?.close()


        if (review1_cnt != 0) {
        query = "SELECT question1.test_num, question1.number, question1.type, question1.question, question1.question_detail, question1.answer, choice1, choice1_detail, choice2, choice2_detail, choice3, choice3_detail, choice4, choice4_detail, 0, count(question1.number) FROM question1, review1 WHERE question1.test_num = review1.test_num AND question1.number = review1.number GROUP BY question1.number;"
        cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()

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
            val order = cursor?.getInt(14)
            val cnt = cursor?.getInt(15)
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order, cnt))
        } while (cursor?.moveToNext() == true)
        cursor?.close()}

        query = "SELECT COUNT(*) FROM review2"
        cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        var review2_cnt = cursor?.getInt(0)
        Log.d("review2_cnt", "${review2_cnt}")
        cursor?.close()

        if (review2_cnt != 0) {
        query = "SELECT question2_1.test_num, question2_1.number, question2_1.type, question2_1.question, question2_1.question_detail, question2_1.answer, question2_2.choice1, question2_2.choice1_detail, question2_2.choice2,question2_2.choice2_detail, question2_2.choice3, question2_2.choice3_detail, question2_2.choice4, question2_2.choice4_detail, question2_2.'order', count(question2_1.number) FROM question2_1 natural join question2_2, review2 WHERE question2_1.test_num = review2.test_num AND question2_1.number = review2.number GROUP BY question2_1.number;"
        cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()

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
            val order = cursor?.getInt(14)
            val cnt = cursor?.getInt(15)
            list.add(question(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail, order, cnt))
        } while (cursor?.moveToNext() == true)
        cursor?.close()}
        close()
        return list

    }

    fun get_score(test_num:Int, type_num:Int): MutableList<Float> {
        openDatabase()
        var my_score = mutableListOf<Float>()
        var query:String?
        if (test_num == 0) {
            query = "SELECT COUNT(*), SUM(score) FROM score WHERE score.type_num = ${type_num};"

        } else {
            query = "SELECT COUNT(*), SUM(score) FROM score WHERE score.test_num =${test_num}"
        }
        var cursor = dataBase?.rawQuery(query, null)
        cursor?.moveToFirst()
        val cnt:Float? = cursor?.getFloat(0)
        val aver:Float? = cursor?.getFloat(1)
        my_score.add(cnt!!)
        if (aver == null) {
            my_score.add(0F)
        } else { my_score.add(aver/cnt)}
        Log.d("score", "test_num : ${test_num}, type_num : ${type_num}, score : ${cnt}")
        cursor?.close()
        close()
        return my_score
    }

    fun delete_score(type_remove:Boolean){
        openDatabase()
        var query:String?

        if (type_remove){
            // test_num이 0이면 유형별 문제를 모두 삭제한다. 만일 유형별 문제를 푼 점수가 있다면.
            query = "SELECT COUNT(*) FROM score WHERE score.test_num ==0;"
            var cursor = dataBase?.rawQuery(query, null)
            cursor?.moveToFirst()
            var type_score_check = cursor?.getInt(0)
            cursor?.close()

            if (type_score_check == 0) {
                close()
                return
            } else {
                dataBase?.execSQL("DELETE FROM score WHERE score.test_num = 0;")
                close()
                return
            }
        } else {
            // test_num이 0이 아니다. 즉 기출별 문제를 모두 삭제한다. 만일 기출별 문제를 푼 점수가 있다면.
            query = "SELECT COUNT(*) FROM score WHERE score.test_num !=0;"
            var cursor = dataBase?.rawQuery(query, null)
            cursor?.moveToFirst()
            var test_score_check = cursor?.getInt(0)
            cursor?.close()

            if (test_score_check == 0) {
                close()
                return
            } else {
                dataBase?.execSQL("DELETE FROM score WHERE score.type_num = 0;")
                close()
                return
            }
        }

    }

    fun delete_review(type_num:Int, test_num:Int, number:Int) {
        openDatabase()
        var query:String?

        if (type_num < 6) {
            query = "DELETE FROM review1 WHERE review1.test_num = ${test_num} AND review1.number = ${number};"
        } else {
            query = "DELETE FROM review2 WHERE review2.test_num = ${test_num} AND review2.number = ${number};"
        }
        dataBase?.execSQL(query)
        close()
        return
    }


}

data class question(var test_num: Int?, var number: Int?, var type:Int?, var question: String?, var question_detail:String?, var answer:Int?, var choice1:String?, var choice1_detail:String?,
                     var choice2:String?, var choice2_detail:String?, var choice3:String?, var choice3_detail:String?, var choice4:String?, var choice4_detail:String?, var order:Int?, var cnt:Int?)
