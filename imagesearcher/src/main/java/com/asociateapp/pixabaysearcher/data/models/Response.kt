package com.asociateapp.pixabaysearcher.data.models

internal data class Response<T>(
    val total: Int,
    val totalHits: Int,
    val hits: T
)