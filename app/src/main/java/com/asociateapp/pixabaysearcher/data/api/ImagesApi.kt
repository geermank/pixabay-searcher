package com.asociateapp.pixabaysearcher.data.api

class ImagesApi(private val api: PixabayApi) {

    fun searchByTerm(value: String) = api.search(PixabayApi.API_KEY, value, 24)

}