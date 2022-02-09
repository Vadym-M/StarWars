package com.vinade.starwars.network

import com.vinade.starwars.model.Film
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiDetail {
    @GET
    suspend fun getFilms(@Url path: String): Film
}