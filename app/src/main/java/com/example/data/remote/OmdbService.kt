package com.example.data.remote

import com.example.data.model.OmdbResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.MoshiKotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {
    @GET("/")
    suspend fun getShowDetails(
        @Query("t") title: String,
        @Query("apikey") apiKey: String = "bbb54f5d"
    ): OmdbResponse

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com/"

        fun create(): OmdbService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val moshi = Moshi.Builder()
                .addLast(MoshiKotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(OmdbService::class.java)
        }
    }
}
