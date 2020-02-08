package com.andronity.moviecatalogue4.Model.Movie

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class ResponseMovie(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("results"),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(page)
        parcel.writeValue(totalPages)
        parcel.writeValue(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseMovie> {
        override fun createFromParcel(parcel: Parcel): ResponseMovie {
            return ResponseMovie(parcel)
        }

        override fun newArray(size: Int): Array<ResponseMovie?> {
            return arrayOfNulls(size)
        }
    }
}