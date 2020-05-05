package com.asociateapp.pixabaysearcher.data.models

data class Response<T>(
    val total: Int,
    val totalHits: Int,
    val hits: T
)