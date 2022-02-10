package com.vinade.starwars.repository

import com.vinade.starwars.network.RetrofitClient

class StarWarsRepository : Repository{

    suspend fun getPeoplePage(page: Int) = RetrofitClient.apiStarWars.getPeoplePage(page)

    suspend fun getPeopleByQuery(query: String) = RetrofitClient.apiStarWars.getPeopleByQuery(query)

}