package com.andronity.moviecatalogue4.Api

import com.andronity.moviecatalogue4.Model.Movie.ResponseMovie
import com.andronity.moviecatalogue4.Model.TvShow.ResponseTvShow
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("movie?api_key=585a997fd75bcea1f74fbae918812643&language=en-US/k69.json")
    fun getData(): Flowable<Response<ResponseMovie>>

    @GET("tv?api_key=585a997fd75bcea1f74fbae918812643&language=en-US/k69.json")
    fun getDataTv(): Flowable<Response<ResponseTvShow>>

    @GET("/search/movie?api_key=585a997fd75bcea1f74fbae918812643&language=en-US&query=Avenger")
    fun getDataSCmv() : Flowable<Response<ResponseMovie>>

    @GET("/search/tv?api_key=585a997fd75bcea1f74fbae918812643&language=en-US&query=Avenger")
    fun getDataTvSc(): Flowable<Response<ResponseTvShow>>

    @GET("/discover/movie?api_key=585a997fd75bcea1f74fbae918812643&primary_release_date.gte=2020-01-10&primary_release_date.lte=2020-01-10")
    fun getDataRimbrmv() : Flowable<Response<ResponseMovie>>

    @GET("/discover/tv?api_key=585a997fd75bcea1f74fbae918812643&primary_release_date.gte=2020-01-10&primary_release_date.lte=2020-01-10")
    fun getDataRimbrTv() : Flowable<Response<ResponseTvShow>>
}