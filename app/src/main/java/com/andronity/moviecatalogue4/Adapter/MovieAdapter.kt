package com.andronity.moviecatalogue4.Adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andronity.moviecatalogue4.Activity.MovieDetailActivity
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import com.andronity.moviecatalogue4.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cardviewmovie.view.*
import kotlinx.android.synthetic.main.cardviewmovie.view.img_photo
import kotlinx.android.synthetic.main.cardviewmovie.view.txt_name


class MovieAdapter(val activity: Activity?) : RecyclerView.Adapter<MovieAdapter.VhMovie>() {
    private var data: List<ResultsItem> = java.util.ArrayList()


    var listMovie = ArrayList<ResultsItem>()
        set(listMovie) {
            if (listMovie.size > 0) {
                this.listMovie.clear()
            }
            this.listMovie.addAll(listMovie)

            notifyDataSetChanged()
        }

    fun swapData(data: List<ResultsItem>) {
        Log.d("umar", "data adapter : ${data.size}" )
        this.data = data
        notifyDataSetChanged()
    }

    fun addItem(movie:ResultsItem) {
        this.listMovie.add(movie)
        notifyItemInserted(this.listMovie.size - 1)
    }

    fun updateItem(position: Int, movie:ResultsItem) {
        this.listMovie[position] = movie
        notifyItemChanged(position, movie)
    }

    fun removeItem(position: Int) {
        this.listMovie.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovie.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):VhMovie {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardviewmovie, parent, false)
        return VhMovie(view)
    }

    override fun getItemCount(): Int = this.data.size


    override fun onBindViewHolder(holder: VhMovie, position: Int) {
        Log.d("umar", "data pos adapter : ${position}" )
        holder.bind(data[position])


    }

    inner class VhMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ResultsItem) {
            Log.d("umar", "data title adapter : ${item.title}" )
            val url: String = "https://image.tmdb.org/t/p/w185/" + item.posterPath
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_photo)

                txt_name.text = item.title
                txt_deskripsi.text = item.overview
                setOnClickListener {
                    val data = Gson().toJson(item)
                    val moveWithObjectIntent = Intent(context, MovieDetailActivity::class.java)
                    moveWithObjectIntent.putExtra("data", data)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}







