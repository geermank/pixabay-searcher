package com.asociateapp.pixabaysearcher.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal class RetrofitWrapper {

    fun getClient(): PixabayApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(PixabayApi.API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(PixabayApi::class.java)
    }

}