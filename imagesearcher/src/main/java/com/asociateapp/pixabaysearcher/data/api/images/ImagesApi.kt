package com.asociateapp.pixabaysearcher.data.api.images

import com.asociateapp.pixabaysearcher.data.api.ApiParams
import com.asociateapp.pixabaysearcher.data.api.BaseApi
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.data.models.ResponseDto
import javax.inject.Inject

internal class ImagesApi @Inject constructor() : BaseApi() {

    private val service: PixabayServiceInterface = buildService()

    suspend fun searchByTerm(params: ApiParams): ResponseDto<List<ImageDto>> {
        return service.search(params.get())
    }

    override fun getBaseUrl(): String {
        return "https://pixabay.com/"
    }
}
