package com.ourteam.hoohflix.api

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ourteam.hoohflix.BuildConfig
import com.ourteam.hoohflix.utils.SessionInterceptor
import com.ourteam.hoohflix.utils.SessionManager

object DjangoRetrofitClient {
    private const val BASE_URL = "http://192.168.0.118:8000/"

    lateinit var sessionManager: SessionManager

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(SessionInterceptor(sessionManager))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val djangoService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}