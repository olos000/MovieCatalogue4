package com.andronity.moviecatalogue4.DbSql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.DESCRIPTION
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.ID
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.POSTER
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.TABLE_NAME
import com.andronity.moviecatalogue4.DbSql.TvShowDatabaseContract.TvColum.Companion.TITLE
import com.andronity.moviecatalogue4.Model.TvShow.ResultsItem

class TvShowHelper (var context : Context?){
    companion object {
    private const val DATABASE_TABLE = TABLE_NAME
    private lateinit var dataBaseHelper: TvShowDatabaseHelper
    private lateinit var database: SQLiteDatabase


        private var INSTANCE: TvShowHelper? = null
        fun getInstance(context: Context?): TvShowHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TvShowHelper(context)
                    }
                }
            }
            return INSTANCE as TvShowHelper
        }
    }

    init {
        dataBaseHelper = TvShowDatabaseHelper(context!!)
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
//    fun insert(values: ContentValues?): Long {
//        return database.insert(DATABASE_TABLE, null, values)
//    }
//    fun update(id: String, values: ContentValues?): Int {
//        return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
//    }
//    fun deleteById(id: String): Int {
//        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
//    }
   fun getAllNotes(): ArrayList<ResultsItem> {
        val arrayList = ArrayList<ResultsItem>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
            "$ID ASC", null)
        cursor.moveToFirst()
        var tvShow : ResultsItem
        if (cursor.count > 0) {
            do {
                tvShow =ResultsItem(posterPath = String())
                tvShow.id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                tvShow.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                tvShow.name = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                tvShow.overview = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))


                arrayList.add(tvShow)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }
    fun insertTvShow(tvShow: ResultsItem): Long {
        val args = ContentValues()
        args.put(TITLE,tvShow.name)
        args.put(POSTER, tvShow.posterPath)
        args.put(DESCRIPTION, tvShow.overview)
        return database.insert(DATABASE_TABLE, null, args)
    }
    fun updateTvShow(tvShow: ResultsItem): Int {
        val args = ContentValues()
        args.put(POSTER, tvShow.posterPath)
        args.put(TITLE, tvShow.name)
        args.put(DESCRIPTION, tvShow.overview)
        return database.update(DATABASE_TABLE, args, ID + "= '" + tvShow.id + "'", null)
    }

    fun deleteTvShow(id: String): Int {
        return database.delete(TABLE_NAME, "$ID = '$id'", null)
    }


}