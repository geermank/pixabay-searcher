package com.asociateapp.pixabaysearcher.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal abstract class BaseApi {

    protected inline fun <reified T> buildService(): T {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(getClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    protected fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
            addInterceptors(this)
        }.build()
    }

    /**
     * Let each subclass override this method to add their own interceptors if applies
     */
    protected open fun addInterceptors(clientBuilder: OkHttpClient.Builder) {
        // do nothing by default
    }

    abstract fun getBaseUrl(): String
}
