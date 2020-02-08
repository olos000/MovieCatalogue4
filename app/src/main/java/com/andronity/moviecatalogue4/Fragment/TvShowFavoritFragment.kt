package com.andronity.moviecatalogue4.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andronity.moviecatalogue4.Adapter.FavTvShowAdapter
import com.andronity.moviecatalogue4.DbSql.TvShowHelper
import com.andronity.moviecatalogue4.Model.TvShow.ResultsItem
import com.andronity.moviecatalogue4.R


@Suppress("UNREACHABLE_CODE")
class TvShowFavoritFragment : Fragment() {

    private lateinit var mAdapter: FavTvShowAdapter
    private var dataItem: ResultsItem? = null
    private lateinit var hhalper: TvShowHelper
    var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tv_show_favorit, container, false)

        mAdapter = FavTvShowAdapter()
        rv = root.findViewById(R.id.crcFv)
        getData()
        return root
    }

    private fun getData() {
        hhalper = TvShowHelper.getInstance(context)
        hhalper.open()
        mAdapter = FavTvShowAdapter()
//        dataItem?.let {
//            hhalper.getAllNotes()
//        }
        val data = hhalper.getAllNotes()
        Log.d("data tvsho", data.toString())
        mAdapter.swapData(data)
//        this.dataItem?.let { mAdapter.swapData(data = listOf(it)) }
        rv?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
//        dataItem?.let {
//            hhalper.getAllNotes()
//
//        }
//        this.dataItem?.let { mAdapter.swapData(data = listOf(it)) }
    }

    override fun onDestroy() {
        super.onDestroy()
        hhalper.close()
    }

}


