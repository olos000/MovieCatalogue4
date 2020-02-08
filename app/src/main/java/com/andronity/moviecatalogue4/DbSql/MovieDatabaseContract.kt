package com.andronity.moviecatalogue4.DbSql


import android.net.Uri
import android.provider.BaseColumns
import android.service.notification.Condition.SCHEME


class MovieDatabaseContract {

    internal class MovieColum : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            const val ID = "id"
            const val POSTER = "poster_path"
            const val TITLE = "title"
            const val DESCRIPTION = "description"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }

    companion object {
        const val AUTHORITY = "com.andronity.moviecatalogue4"
    }


}