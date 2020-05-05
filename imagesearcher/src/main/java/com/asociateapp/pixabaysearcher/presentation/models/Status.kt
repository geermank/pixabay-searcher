package com.asociateapp.pixabaysearcher.presentation.models

internal sealed class State<T>(
    val data: T,
    val page: Int
)

internal class Default<T>(
    data: T,
    page: Int
): State<T>(data, page)

internal class Loading<T>(
    data: T,
    page: Int
): State<T>(data, page)

internal class Paginating<T>(
    data: T,
    page: Int
): State<T>(data, page)

internal class Error<T>(
    data: T,
    page: Int,
    val errorMessage: String
): State<T>(data, page)