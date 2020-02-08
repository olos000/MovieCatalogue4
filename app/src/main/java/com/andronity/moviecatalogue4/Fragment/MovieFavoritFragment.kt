package com.andronity.moviecatalogue4.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andronity.moviecatalogue4.Adapter.FavMovieAdapter
import com.andronity.moviecatalogue4.DbSql.MoviesHelper
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import com.andronity.moviecatalogue4.R


@Suppress("UNREACHABLE_CODE")
class MovieFavoritFragment : Fragment() {
    private lateinit var mAdapter: FavMovieAdapter
    val data = MutableLiveData<List<ResultsItem?>>()
    private lateinit var movieHelper: MoviesHelper
    private var dataItem: ResultsItem? = null
    var rv: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_movie_favorit, container, false)
        rv = root.findViewById(R.id.crcFv)
        getData()
        return root
    }

    private fun getData() {
        movieHelper = MoviesHelper.getInstance(context)
        movieHelper.open()
        mAdapter = FavMovieAdapter()

        val data = movieHelper.getAllNotes(this.context)
        Log.d("data", data.toString())
        mAdapter.swapData(data)
        rv?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
    }

    override fun onResume() {
        super.onResume()
        getData()

    }
}
