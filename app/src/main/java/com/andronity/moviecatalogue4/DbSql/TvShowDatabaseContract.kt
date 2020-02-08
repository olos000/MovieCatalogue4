package com.andronity.moviecatalogue4.DbSql

import android.provider.BaseColumns

class TvShowDatabaseContract {
    internal class TvColum : BaseColumns {
        companion object {
            const val TABLE_NAME = "tvshow"
            const val ID = "id"
            const val POSTER = "poster_path"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
        }
    }
}