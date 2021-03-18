package com.asociateapp.pixabaysearcher.data.paging

import androidx.paging.PageKeyedDataSource
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.api.images.ImagesApiParams
import com.asociateapp.pixabaysearcher.data.api.images.ImagesParamsBuilder
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.data.models.ResponseDto
import com.asociateapp.pixabaysearcher.utils.CoroutineExecutor

internal class ImagesDataSource(
    private val galleryConfiguration: GalleryConfiguration,
    private val repository: ImagesRepository,
    private val coroutineExecutor: CoroutineExecutor
) : PageKeyedDataSource<Int, ImageDto>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageDto>
    ) {
        val apiParams = ImagesApiParams(galleryConfiguration, ImagesParamsBuilder())
        coroutineExecutor.runCoroutine {
            val imagesResponse = repository.searchImagesByTerm(apiParams)
            callback.onResult(imagesResponse.hits, null, apiParams.page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageDto>) {
        loadImagesAndNotify(params, callback, ::getPreviousPageIfAvailable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageDto>) {
        loadImagesAndNotify(params, callback, ::getNextPageIfAvailable)
    }

    private fun loadImagesAndNotify(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ImageDto>,
        page: (params: LoadParams<Int>, imagesResponse: ResponseDto<List<ImageDto>>) -> Int?
    ) {
        val apiParams = ImagesApiParams(galleryConfiguration, ImagesParamsBuilder()).also { it.page = params.key }
        coroutineExecutor.runCoroutine {
            val imagesResponse = repository.searchImagesByTerm(apiParams)
            callback.onResult(imagesResponse.hits, page(params, imagesResponse))
        }
    }

    private fun getPreviousPageIfAvailable(
        params: LoadParams<Int>,
        response: ResponseDto<List<ImageDto>>
    ): Int? {
        return if (params.key == 1) {
            return null
        } else {
            params.key - 1
        }
    }

    private fun getNextPageIfAvailable(
        params: LoadParams<Int>,
        response: ResponseDto<List<ImageDto>>
    ): Int? {
        return if (response.total == pageCount(response.total)) {
            return null
        } else {
            params.key + 1
        }
    }

    private fun pageCount(totalImages: Int): Int {
        return totalImages / galleryConfiguration.imagesPerPage
    }
}