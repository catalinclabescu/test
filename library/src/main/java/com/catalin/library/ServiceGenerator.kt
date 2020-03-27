package com.catalin.library

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private var baseUrl = "https://www.weightwatchers.com"
    private lateinit var builder: Retrofit.Builder
    private lateinit var retrofit: Retrofit

    fun <S> createService(objectClass: Class<S>): S {
        if (baseUrl.isEmpty()) {
            throw IllegalStateException("The base url cannot be empty")
        }
        builder = Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        return retrofit.create(objectClass)
    }
}