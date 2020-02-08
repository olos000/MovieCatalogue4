package com.andronity.moviecatalogue4.ui.dashboard

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
import com.andronity.moviecatalogue4.Adapter.TvShowAdapter
import com.andronity.moviecatalogue4.Model.TvShow.ResponseTvShow
import com.andronity.moviecatalogue4.Model.TvShow.ResultsItem
import com.andronity.moviecatalogue4.R
import com.andronity.moviecatalogue4.ViewModel.ViewModelTvShow
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_tvshow.view.*

class DashboardFragment : Fragment() {

    private lateinit var tvAdapter : TvShowAdapter
    private lateinit var tvShowViewModel: ViewModelTvShow
    private var dashboardViewModel: DashboardViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tvshow, container, false)
        tvAdapter = TvShowAdapter(activity)
        root.rv_category.apply {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(root.context,RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
        val factory = SavedStateVMFactory(this)
        tvShowViewModel = ViewModelProviders.of(this, factory).get(ViewModelTvShow::class.java)

       tvShowViewModel.getDataBundle?.observe(
            this,
            Observer { b ->
                Log.d("umar sebelum masuk", Gson().toJson(b))
                val data: ResponseTvShow? = b.getParcelable("dataUmar")
                if (data != null) {
                    data.let { dataa -> dataa.results?.let { it1 -> tvAdapter.swapData(it1 as List<ResultsItem>) } }
                } else {
                    tvShowViewModel.getDataTv()
                    val pd = ProgressDialog(activity)
                    pd.setMessage("Loading")
                    tvShowViewModel.isLoading.observe(this, Observer {
                        if (it) pd.show()
                        else pd.dismiss()
                    })
                }
            }
        )
        tvShowViewModel.getDataTv()

        tvShowViewModel.data.observe(this, Observer {
            val bundle = Bundle()
            bundle.putParcelable("dataUmar", it)
            tvShowViewModel.getDataBundle?.observe(this, Observer {

            })
            tvShowViewModel.saveBundle(bundle)
            tvShowViewModel.getDataBundle?.observe(this, Observer { b ->
                if (b != null) {
                    val data: ResponseTvShow? = b.getParcelable("dataUmar")
                   data?.let { dataa -> dataa.results?.let { it1 -> tvAdapter.swapData(it1 as List<ResultsItem>) } }!!
                } else {

                }
            })
            it.results?.let { it1 ->
                         tvAdapter.swapData(it1 as List<ResultsItem>)
            }

        })
       tvAdapter.notifyDataSetChanged()



        return root
    }
}