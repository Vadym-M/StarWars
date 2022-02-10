package com.vinade.starwars.repository

import com.vinade.starwars.network.RetrofitClient

class DetailRepository : Repository{
    suspend fun getFilms() = RetrofitClient.apiDetail.getFilms()
}