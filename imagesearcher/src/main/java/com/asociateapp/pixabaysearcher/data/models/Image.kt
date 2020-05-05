package com.asociateapp.pixabaysearcher.data.models

data class Image(
    val id: Int,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageURL: String,
    val fullHDURL: String?,
    val imageURL: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Long,
    val pageURL: String,
    val user_id: Int,
    val user: String,
    val userImageURL: String
)