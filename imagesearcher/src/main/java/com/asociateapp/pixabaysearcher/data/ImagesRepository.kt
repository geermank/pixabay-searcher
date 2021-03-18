package com.asociateapp.pixabaysearcher.data

import com.asociateapp.pixabaysearcher.data.api.ApiParams
import com.asociateapp.pixabaysearcher.data.api.images.ImagesApi
import javax.inject.Inject

internal class ImagesRepository @Inject constructor(private val imagesApi: ImagesApi) {

    suspend fun searchImagesByTerm(apiParams: ApiParams) = imagesApi.searchByTerm(apiParams)
}