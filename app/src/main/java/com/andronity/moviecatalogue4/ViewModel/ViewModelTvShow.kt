package com.andronity.moviecatalogue4.ViewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.andronity.moviecatalogue4.Api.ApiCall
import com.andronity.moviecatalogue4.Model.TvShow.ResponseTvShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ViewModelTvShow(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val data = MutableLiveData<ResponseTvShow>()
    val isLoading = MutableLiveData<Boolean>()
    val REGULAR_KEY = "regular_key"
    val LIVE_DATE_KEY = "live_data_key"


    fun getDataTv() {
        isLoading.value = true
        ApiCall().run {
            instance().getDataTv()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    isLoading.value = false
                    if (it.isSuccessful) {
                        data.postValue(it.body())
                    }
                }, {
                    isLoading.value = false

                })


        }


    }

    val regularText: String
        get() = savedStateHandle.get<String>(REGULAR_KEY).orEmpty()

    val dataBundle = MutableLiveData<Bundle>()
    val getDataBundle: LiveData<Bundle>? = savedStateHandle.getLiveData(LIVE_DATE_KEY)

    fun saveRegularText(text: String) {
        savedStateHandle.set(REGULAR_KEY, text)
    }

    fun saveBundle(b: Bundle) {
        savedStateHandle.set(LIVE_DATE_KEY, b)
    }


}

