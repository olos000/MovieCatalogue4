@file:Suppress("DEPRECATION")

package com.andronity.moviecatalogue4.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andronity.moviecatalogue4.Adapter.MovieAdapter
import com.andronity.moviecatalogue4.Model.Movie.ResponseMovie
import com.andronity.moviecatalogue4.Model.Movie.ResultsItem
import com.andronity.moviecatalogue4.R
import com.andronity.moviecatalogue4.ViewModel.ViewModelMovie
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie.view.*


@Suppress("UNCHECKED_CAST", "DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var mAdapter: MovieAdapter
    private lateinit var moviesViewModel: ViewModelMovie
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
        mAdapter = MovieAdapter(activity)
        root.rv_category.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(root.context,RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
        val factory = SavedStateVMFactory(this)
        moviesViewModel = ViewModelProviders.of(this, factory).get(ViewModelMovie::class.java)

        moviesViewModel.getDataBundle?.observe(
            this,
            Observer { b ->
                Log.d("umar sebelum masuk", Gson().toJson(b))
                val data: ResponseMovie? = b.getParcelable("dataUmar")
                if (data != null) {
                    data.let { dataa -> dataa.results?.let { it1 -> mAdapter.swapData(it1 as List<ResultsItem>) } }
                } else {
                    moviesViewModel.getData()
                    val pd = ProgressDialog(activity)
                    pd.setMessage("Loading")
                    moviesViewModel.isLoading.observe(this, Observer {
                        if (it) pd.show()
                        else pd.dismiss()
                    })
                }
            }
        )
        moviesViewModel.getData()

        moviesViewModel.data.observe(this, Observer {
            val bundle = Bundle()
            bundle.putParcelable("dataUmar", it)
            moviesViewModel.getDataBundle?.observe(this, Observer {

            })
            moviesViewModel.saveBundle(bundle)
            moviesViewModel.getDataBundle?.observe(this, Observer { b ->
                if (b != null) {
                    val data: ResponseMovie? = b.getParcelable("dataUmar")
                    data?.let { dataa -> dataa.results?.let { it1 -> mAdapter.swapData(it1 as List<ResultsItem>) } }!!
                } else {

                }
            })
            it.results?.let { it1 ->
              mAdapter.swapData(it1 as List<ResultsItem>)
            }

        })
        mAdapter.notifyDataSetChanged()



        return root
    }
}
