package com.vinade.starwars.repository

import com.vinade.starwars.network.RetrofitClient


class StarWarsRepository {

    suspend fun getPeoplePage() = RetrofitClient.apiStarWars.getPeoplePage()
}