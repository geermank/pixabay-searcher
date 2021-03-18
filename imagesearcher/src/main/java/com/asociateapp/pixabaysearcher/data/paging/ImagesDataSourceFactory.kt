package com.asociateapp.pixabaysearcher.data.paging

import androidx.paging.DataSource
import com.asociateapp.pixabaysearcher.config.imagegallery.GalleryConfiguration
import com.asociateapp.pixabaysearcher.data.ImagesRepository
import com.asociateapp.pixabaysearcher.data.models.ImageDto
import com.asociateapp.pixabaysearcher.utils.CoroutineExecutor

internal class ImagesDataSourceFactory(
    private val galleryConfiguration: GalleryConfiguration,
    private val repository: ImagesRepository,
    private val coroutineExecutor: CoroutineExecutor
) : DataSource.Factory<Int, ImageDto>() {

    override fun create(): DataSource<Int, ImageDto> {
        return ImagesDataSource(galleryConfiguration, repository, coroutineExecutor)
    }
}
