package com.asociateapp.pixabaysearcher.presentation.imagegallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagedList
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.cache.ApiKeyCache
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.data.paging.ImagesDataSourceFactory
import com.asociateapp.pixabaysearcher.data.paging.asLiveData
import com.asociateapp.pixabaysearcher.presentation.BaseViewModel

const val IMAGES_PREFETCH_SIZE = 20

internal class GalleryViewModel @ViewModelInject constructor(
    private val repository: ImagesRepository,
    private val apiCache: ApiKeyCache,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val images: LiveData<PagedList<ImageDto>>

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _toolbarConfiguration = MutableLiveData<GalleryToolbarConfig>()
    val toolbarConfiguration: LiveData<GalleryToolbarConfig> = _toolbarConfiguration

    private val galleryConfig: GalleryConfiguration = savedStateHandle[GalleryConfiguration.IMAGE_GALLERY_CONFIGURATION_EXTRA]!!

    init {
        saveApiKey()

        val toolbarConfig = GalleryToolbarConfig(galleryConfig.title, galleryConfig.showUpButton)
        _toolbarConfiguration.postValue(toolbarConfig)

        val imagesDataSourceFactory = ImagesDataSourceFactory(galleryConfig, repository, coroutineExecutor)
        images = imagesDataSourceFactory.asLiveData(galleryConfig.imagesPerPage, IMAGES_PREFETCH_SIZE)
    }

    fun expectingUriResult() = galleryConfig.returnResult

    override fun onCoroutineError(throwable: Throwable) {
        _error.value = throwable.message
    }

    private fun saveApiKey() {
        apiCache.save(galleryConfig.apiKey)
    }
}
