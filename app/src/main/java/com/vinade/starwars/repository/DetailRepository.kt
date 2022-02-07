package com.vinade.starwars.repository

import com.vinade.starwars.network.ApiDetail

class DetailRepository(private val api: ApiDetail) {
    suspend fun getFilms() = api.getFilms()
}