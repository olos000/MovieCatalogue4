package com.andronity.moviecatalogue4.DbSql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.TABLE_NAME


internal class MovieDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {

        private const val DATABASE_NAME = "db_movie"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_NAME" +
                " (${MovieDatabaseContract.MovieColum.ID} INTEGER PRIMARY KEY," +
                " ${MovieDatabaseContract.MovieColum.POSTER} TEXT," +
                " ${MovieDatabaseContract.MovieColum.TITLE} TEXT," +
                " ${MovieDatabaseContract.MovieColum.DESCRIPTION} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}