package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())

        .baseUrl(Constants.BASE_URL).build()

interface NasaService{
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") start_date:String,
                             @Query(value = "api_key") api_key:String) : String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query(value = "api_key") api_key: String) : PictureOfDay
}

object NasaApi{
    val retrofitService:NasaService by lazy {
        retrofit.create(NasaService::class.java)
    }
}
