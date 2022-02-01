package com.vinade.starwars.network

import com.vinade.starwars.model.StarWarsResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiStarWars {
    @GET("people/?page=1")
    suspend fun getPeoplePage():Response<StarWarsResult>
}