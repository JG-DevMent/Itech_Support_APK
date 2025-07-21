package com.example.itechsupport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COL_ID = "id"
        private const val COL_USERNAME = "username"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
        private const val COL_PHONE = "phone"
        private const val COL_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_USERS (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USERNAME TEXT UNIQUE, " +
                "$COL_EMAIL TEXT UNIQUE, " +
                "$COL_PASSWORD TEXT, " +
                "$COL_PHONE TEXT, " +
                "$COL_ROLE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(username: String, email: String, password: String, phone: String, role: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_USERNAME, username)
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password)
            put(COL_PHONE, phone)
            put(COL_ROLE, role)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun validateUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?",
            arrayOf(username, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getUserByEmail(email: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COL_EMAIL = ?",
            arrayOf(email)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun updatePasswordByEmail(email: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PASSWORD, newPassword)
        }
        val result = db.update(TABLE_USERS, values, "$COL_EMAIL = ?", arrayOf(email))
        db.close()
        return result > 0
    }
}