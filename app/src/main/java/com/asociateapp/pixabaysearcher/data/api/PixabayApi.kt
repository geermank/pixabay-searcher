package com.asociateapp.pixabaysearcher.data.api

import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    companion object {
        const val API_KEY = "16364083-5d65dcbe76548cde9ed7b94c0"
        const val API = "https://pixabay.com/"
    }

    @GET("api/")
    fun search(
        @Query("key") key: String,
        @Query("q") term: String,
        @Query("per_page") perPage: Int
    ): Single<Response<List<Image>>>

}