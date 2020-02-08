package com.andronity.moviecatalogue4.DbSql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.DESCRIPTION
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.ID
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.POSTER
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.TABLE_NAME
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.TITLE
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import java.sql.SQLException


class MoviesHelper (context: Context?) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: MovieDatabaseHelper
        private lateinit var database: SQLiteDatabase


        private var INSTANCE: MoviesHelper? = null
        fun getInstance(context: Context?): MoviesHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MoviesHelper(context)
                    }
                }
            }
            return INSTANCE as MoviesHelper
        }
    }

    init {
        dataBaseHelper = MovieDatabaseHelper(context!!)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun  getAllNotes(context: Context?): ArrayList<ResultsItem> {
        val arrayList = ArrayList<ResultsItem>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
            "$ID ASC", null)
        cursor.moveToFirst()
        var movie:ResultsItem
        if (cursor.count > 0) {
            do {
                movie = ResultsItem(posterPath = String())
                movie.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                movie.id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                movie.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                movie.overview= cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))


                arrayList.add(movie)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }
    fun updateMovie(movie:ResultsItem): Int {
        val args = ContentValues()
        args.put(TITLE, movie.title)
        args.put(DESCRIPTION, movie.overview)
        return database.update(DATABASE_TABLE, args, ID + "= '" + movie.id + "'", null)
    }

    fun deleteMovie(idM:String): Int {
        return database.delete(TABLE_NAME, "$ID = '$idM'", null)
    }

    fun insertMovie(movie:ResultsItem): Long {
        val args = ContentValues()
        args.put(POSTER, movie.posterPath)
        args.put(TITLE, movie.title)
        args.put(DESCRIPTION, movie.overview)
        return database.insert(DATABASE_TABLE, null, args)

    }
}