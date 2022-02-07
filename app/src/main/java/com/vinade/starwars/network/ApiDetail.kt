package com.vinade.starwars.network

import retrofit2.http.GET

interface ApiDetail {
    @GET
    suspend fun getFilms(): Any
}