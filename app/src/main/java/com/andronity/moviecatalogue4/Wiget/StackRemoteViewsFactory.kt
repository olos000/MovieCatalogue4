package com.andronity.moviecatalogue4.Wiget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.andronity.moviecatalogue4.DbSql.MovieDatabaseContract.MovieColum.Companion.CONTENT_URI
import com.andronity.moviecatalogue4.DbSql.MoviesHelper
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import com.andronity.moviecatalogue4.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


internal class StackRemoteViewsFactory(private val mContext: Context)  : RemoteViewsService.RemoteViewsFactory {
    private  lateinit var  cursor : Cursor
    private val mWidgetItems = ArrayList<ResultsItem>()



    override fun onCreate() {

    }



    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0


    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()

        // querying ke database
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null)!!

        Binder.restoreCallingIdentity(identityToken)
   }

    override fun hasStableIds():  Boolean = false

    override fun getViewAt(position: Int): RemoteViews {

        val rv = RemoteViews(mContext.packageName, R.layout.wiget_item)
        try {
            var url = "https://image.tmdb.org/t/p/w185/" + mWidgetItems[position].posterPath
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().fitCenter())
                .submit()
                .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val extras = bundleOf(
            FavouriteWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv

    }

    override fun getCount() : Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor.close()
    }
}