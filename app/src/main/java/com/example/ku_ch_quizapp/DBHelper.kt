package com.example.ku_ch_quizapp

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

    fun get_question1(test_num:Int, type:Int, rand:Boolean): MutableList<question1>{
        openDatabase()

        var query:String?

        if (rand == true) {
            query = "SELECT * FROM question1 WHERE question1.type = ${type} ORDER BY RANDOM() LIMIT 10;"
        } else { query = "SELECT * FROM question1 WHERE question1.test_num = ${test_num} ORDER BY question1.number;"}

        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<question1>()
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
            list.add(question1(test_num, number, type, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }

    fun get_question2(test_num:Int, rand:Boolean): MutableList<question2>{
        openDatabase()
        var query:String?
        if (rand==true) {
            query = "SELECT * FROM question2_1 natural join question2_2 ORDER BY RANDOM() LIMIT 10;"
        } else {
            query = "SELECT * FROM question2_1 natural join question2_2 WHERE question2_1.test_num = ${test_num} ORDER BY question2_1.number;"
        }
        val cursor = dataBase?.rawQuery(query,null)
        cursor?.moveToFirst()
        val list = mutableListOf<question2>()
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
            list.add(question2(test_num, type, order, number, question, question_detail, answer, choice1, choice1_detail, choice2, choice2_detail,
                choice3, choice3_detail, choice4, choice4_detail))
        } while (cursor?.moveToNext() == true)
        cursor?.close()
        close()
        return list
    }

}

data class question1(var test_num: Int?, var number: Int?, var type:Int?, var question: String?, var question_detail:String?, var answer:Int?, var choice1:String?, var choice1_detail:String?,
                     var choice2:String?, var choice2_detail:String?, var choice3:String?, var choice3_detail:String?, var choice4:String?, var choice4_detail:String?)
data class question2(var test_num: Int?, var type: Int?, var order:Int?, var number:Int?, var question: String?,
                       var question_detail: String?, var answer: Int?, var choice1: String?, var choice1_detail: String?, var choice2: String?,
                       var choice2_detail: String?, var choice3: String?, var choice3_detail: String?, var choice4: String?, var choice4_detail: String?)
data class review(var date: String?, var test_num: Int?, var number: Int?, var wrong_answer: Int?)
data class score(var test_num: Int?, var type_num:Int?, var score:Int?, var date:String?)