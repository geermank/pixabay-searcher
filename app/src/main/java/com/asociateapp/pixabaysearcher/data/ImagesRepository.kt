package com.asociateapp.pixabaysearcher.data

import com.asociateapp.pixabaysearcher.data.api.ImagesApi

class ImagesRepository(private val imagesApi: ImagesApi) {

    fun searchImagesByTerm(value: String) = imagesApi.searchByTerm(value)

}