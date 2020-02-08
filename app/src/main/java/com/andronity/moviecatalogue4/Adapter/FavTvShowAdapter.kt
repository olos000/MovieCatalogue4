package com.andronity.moviecatalogue4.Adapter

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
import kotlinx.android.synthetic.main.tvshowcardview.view.*
import java.util.*

class FavTvShowAdapter : RecyclerView.Adapter<FavTvShowAdapter.FavTv>() {

    private var data: List<ResultsItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTv {
        return FavTv(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tvshowcardview, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavTv, position: Int) = holder.bind(data[position])

    fun swapData(data: List<ResultsItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class FavTv(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ResultsItem) = with(itemView) {
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