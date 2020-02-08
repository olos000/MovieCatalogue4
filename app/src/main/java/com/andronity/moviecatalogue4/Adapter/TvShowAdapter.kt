package com.andronity.moviecatalogue4.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andronity.moviecatalogue4.Activity.TvShowDetailActivity
import com.andronity.moviecatalogue4.Model.TvShow.ResultsItem
import com.andronity.moviecatalogue4.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie_favorit.view.*
import kotlinx.android.synthetic.main.tvshowcardview.view.*

class TvShowAdapter(val activity: Activity?) : RecyclerView.Adapter<TvShowAdapter.tvShow>() {


    private var data: List<ResultsItem> = java.util.ArrayList()





    var listvShow = ArrayList<ResultsItem>()
        set(listMovie) {
            if (listMovie.size > 0) {
                this.listvShow.clear()
            }
            this.listvShow.addAll(listMovie)

            notifyDataSetChanged()
        }
    fun swapData(data: List<ResultsItem>) {

        this.data = data
        notifyDataSetChanged()
    }

    fun addItem(movie:ResultsItem) {
        this.listvShow.add(movie)
        notifyItemInserted(this.listvShow.size - 1)
    }

    fun updateItem(position: Int, movie:ResultsItem) {
        this.listvShow[position] = movie
        notifyItemChanged(position, movie)
    }

    fun removeItem(position: Int) {
        this.listvShow.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listvShow.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):tvShow {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tvshowcardview, parent, false)
        return tvShow(view)
    }

    override fun getItemCount(): Int  = this.data.size

    override fun onBindViewHolder(holder: TvShowAdapter.tvShow, position: Int) {
        holder.bind(data[position])
    }
    inner class tvShow(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ResultsItem) {

            val url: String = "https://image.tmdb.org/t/p/w185/" + item.posterPath
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_photo)

                txt_name.text = item.name
                txt_deskripsi.text = item.overview
                setOnClickListener {
                    val data = Gson().toJson(item)
                    val moveWithObjectIntent = Intent(context, TvShowDetailActivity::class.java)
                    moveWithObjectIntent.putExtra("data", data)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}