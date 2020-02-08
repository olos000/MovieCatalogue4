package com.andronity.moviecatalogue4.DbSql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.TABLE_NAME

class TvShowDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "db_tvshow"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_TVSHOW = "CREATE TABLE $TABLE_NAME" +
                " (${TvShowDatabaseContract.TvColum.ID} INTEGER PRIMARY KEY," +
                " ${TvShowDatabaseContract.TvColum.POSTER} TEXT ," +
                " ${TvShowDatabaseContract.TvColum.TITLE} TEXT ," +
                " ${TvShowDatabaseContract.TvColum.DESCRIPTION} TEXT)"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_TVSHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}