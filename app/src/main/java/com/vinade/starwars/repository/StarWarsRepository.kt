package com.vinade.starwars.repository

import com.vinade.starwars.model.Result
import com.vinade.starwars.network.RetrofitClient
import com.vinade.starwars.room.StarWarsDao


class StarWarsRepository(private val dao: StarWarsDao) : Repository{

    suspend fun getPeoplePage(page: Int) = RetrofitClient.apiStarWars.getPeoplePage(page)

    suspend fun getFavorites() = dao.getFavorites()

    suspend fun deleteFavoriteByName(name: String) = dao.deleteFavoriteByName(name)

    suspend fun insert(favorite: Result) {
        dao.insert(favorite)
    }
}