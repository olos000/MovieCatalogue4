package com.andronity.moviecatalogue4.Activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.andronity.moviecatalogue4.DbSql.TvShowHelper
import com.andronity.moviecatalogue4.Model.TvShow.ResultsItem
import com.andronity.moviecatalogue4.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = " extra_tvshow"
    }

    private var menu:Menu?=null
    private var isfavorite = false
    val data = MutableLiveData<List<ResultsItem?>>()
    private lateinit var tvShowHelper: TvShowHelper
    private var dataItem: ResultsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        tvShowHelper = TvShowHelper.getInstance(this)
        tvShowHelper.open()
        progressDetailTv.visibility = View.GONE

        dataItem = Gson().fromJson(intent.getStringExtra("data"), ResultsItem::class.java)
        dataItem?.let {
            var url = "https://image.tmdb.org/t/p/w185/" + it.posterPath
            Glide.with(this)
                .load(url)
                .apply(RequestOptions().override(350, 550))
                .into(img_photo)

            val txtJudul = txt_name
            txtJudul.setText(it.name)

            val txtDeskripsi = txt_deskripsi
            txtDeskripsi.setText(it.overview)
        }

        checkFavorite()

    }

    private fun checkFavorite() {
        val tvShowDatabase: ArrayList<ResultsItem> = tvShowHelper.getAllNotes()
        for (tvshow in tvShowDatabase) {
            if (this.dataItem?.name == tvshow.name) {
                isfavorite = true
            }
            if (isfavorite) {
                break
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorit, menu)
        setIconFavorite(menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.menu_item_add_favorite_menu_detail) {
            favoriteButtonPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun favoriteButtonPressed() {
        if (isfavorite) {
            dataItem?.let {
                tvShowHelper.deleteTvShow("${it.id}")
                menu?.let {mn->
                    isfavorite=false
                    setIconFavorite(mn)
                }
            }
            Toast.makeText(this, "delete", Toast.LENGTH_LONG).show()
        } else {
            dataItem?.let { tvShowHelper.insertTvShow(it)
                menu?.let {mn->
                    isfavorite=true
                    setIconFavorite(mn)
                }
            }
            Toast.makeText(this, "addeds", Toast.LENGTH_LONG).show()
        }
        isfavorite = !!isfavorite

    }

    private fun setIconFavorite(menu: Menu) {
        if (isfavorite) {
            menu.getItem(0).icon = resources.getDrawable(R.drawable.ic_favorite_black_24dp)
        } else {
            menu.getItem(0).icon = resources.getDrawable(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowHelper.close()
    }


}
