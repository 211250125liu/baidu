package com.baidu.app.news

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NewsDBHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        // Book表的建表语句，id是整型主键自增长
        // text表示文本类型
        val createNews = "create table News (" +
                " id integer primary key autoincrement, " +
                " title text,pictureUrl text, summary text, body text )"
        db.execSQL(createNews)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "News.db"
    }
}