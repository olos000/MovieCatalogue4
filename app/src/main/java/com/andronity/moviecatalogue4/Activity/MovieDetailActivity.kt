package com.andronity.moviecatalogue4.Activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.andronity.moviecatalogue4.DbSql.MoviesHelper
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import com.andronity.moviecatalogue4.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = " extra_movie"
    }
    private var menu:Menu?=null
    private var isfavorite = false
    val data = MutableLiveData<List<ResultsItem?>>()
    private lateinit var movieHelper: MoviesHelper
    private var dataItem: ResultsItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieHelper = MoviesHelper.getInstance(this)
        movieHelper.open()
        progressDetailMovie.visibility = View.GONE

        dataItem = Gson().fromJson(intent.getStringExtra("data"), ResultsItem::class.java)
        dataItem?.let {
            var url = "https://image.tmdb.org/t/p/w185/" + it.posterPath
            Glide.with(this)
                .load(url)
                .apply(RequestOptions().override(350, 550))
                .into(img_photo)

            val txtJudul = txt_name
            txtJudul.setText(it.title)

            val txtDeskripsi = txt_deskripsi
            txtDeskripsi.setText(it.overview)
        }

        checkFavorite()

    }

    private fun checkFavorite() {
        val moviesInDatabase = movieHelper.getAllNotes(context = this)
        for (movie in moviesInDatabase) {
            if (this.dataItem?.title == movie.title) {
                isfavorite = true
            }
            if (isfavorite) {
                break
            }
        }
        Toast.makeText(this, isfavorite.toString(), Toast.LENGTH_SHORT).show()
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
                movieHelper.deleteMovie("${it.id}")
                menu?.let {mn->
                    isfavorite=false
                    setIconFavorite(mn)
                }
            }
            Toast.makeText(this, "menghapus data", Toast.LENGTH_LONG).show()
        } else {
            dataItem?.let { movieHelper.insertMovie(it)
                menu?.let {mn->
                    isfavorite=true
                    setIconFavorite(mn)
                }
            }
            Toast.makeText(this, "tambahkan data", Toast.LENGTH_LONG).show()
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
        movieHelper.close()
    }


}

