package com.vinade.starwars.network

import com.vinade.starwars.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private fun getClient():Retrofit{
           return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    val apiStarWars: ApiStarWars = getClient().create(ApiStarWars::class.java)
    val apiDetail: ApiDetail = getClient().create(ApiDetail::class.java)
    }
