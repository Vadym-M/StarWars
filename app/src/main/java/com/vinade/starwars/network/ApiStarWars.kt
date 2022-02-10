package com.vinade.starwars.network

import com.vinade.starwars.model.StarWarsResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStarWars {
    @GET("people/")
    suspend fun getPeoplePage(@Query("page") page: Int):Response<StarWarsResult>
    @GET("people/")
    suspend fun getPeopleByQuery(@Query("search") search: String): Response<StarWarsResult>
}