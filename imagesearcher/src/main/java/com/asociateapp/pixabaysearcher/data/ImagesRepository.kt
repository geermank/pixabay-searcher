package com.asociateapp.pixabaysearcher.data

import com.asociateapp.pixabaysearcher.data.api.ImagesApi

internal class ImagesRepository(private val imagesApi: ImagesApi) {

    fun searchImagesByTerm(apiKey: String, itemsPerPage: Int, value: String) =
        imagesApi.searchByTerm(apiKey, itemsPerPage, value)

}