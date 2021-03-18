package com.asociateapp.pixabaysearcher.data.api.images

import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.data.models.ResponseDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface PixabayServiceInterface {

    @GET("api")
    suspend fun search(@QueryMap params: HashMap<String, Any>): ResponseDto<List<ImageDto>>

}