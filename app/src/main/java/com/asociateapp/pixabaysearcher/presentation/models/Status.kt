package com.asociateapp.pixabaysearcher.presentation.models

sealed class State<T>(
    val data: T,
    val page: Int
)

class Default<T>(
    data: T,
    page: Int
): State<T>(data, page)

class Loading<T>(
    data: T,
    page: Int
): State<T>(data, page)

class Paginating<T>(
    data: T,
    page: Int
): State<T>(data, page)

class Error<T>(
    data: T,
    page: Int,
    val errorMessage: String
): State<T>(data, page)