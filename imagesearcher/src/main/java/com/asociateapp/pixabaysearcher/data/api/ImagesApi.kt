package com.asociateapp.pixabaysearcher.data.api

import com.asociateapp.pixabaysearcher.data.models.Image
import com.asociateapp.pixabaysearcher.data.models.Response
import io.reactivex.Single

class ImagesApi(private val api: PixabayApi) : BaseApi() {

    fun searchByTerm(apiKey: String, itemsPerPage: Int, searchTerm: String): Single<Response<List<Image>>> {
        return api.search(buildBaseParams(apiKey, itemsPerPage, searchTerm))
    }

}