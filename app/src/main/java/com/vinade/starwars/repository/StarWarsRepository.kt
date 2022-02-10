package com.vinade.starwars.repository

import com.vinade.starwars.model.Result
import com.vinade.starwars.network.RetrofitClient
import com.vinade.starwars.room.StarWarsDao


class StarWarsRepository : Repository{

    suspend fun getPeoplePage(page: Int) = RetrofitClient.apiStarWars.getPeoplePage(page)

}