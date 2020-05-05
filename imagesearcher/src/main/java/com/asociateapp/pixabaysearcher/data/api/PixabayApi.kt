package com.asociateapp.pixabaysearcher.data.api

import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName

internal interface PixabayApi {

    companion object {
        const val API = "https://pixabay.com/"
    }

    @GET("api/")
    fun search(@QueryMap params: Map<String, String>): Single<Response<List<Image>>>

}