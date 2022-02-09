package com.vinade.starwars.network

import com.vinade.starwars.model.Film
import com.vinade.starwars.model.FilmResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiDetail {
    @GET("films/")
    suspend fun getFilms(): Response<FilmResult>
}