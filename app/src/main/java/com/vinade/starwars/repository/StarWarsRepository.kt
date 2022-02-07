package com.vinade.starwars.repository

import androidx.annotation.WorkerThread
import com.vinade.starwars.model.Result
import com.vinade.starwars.network.RetrofitClient
import com.vinade.starwars.room.StarWarsDao


class StarWarsRepository(private val dao: StarWarsDao) {

    suspend fun getPeoplePage() = RetrofitClient.apiStarWars.getPeoplePage()

    suspend fun getFavorites() = dao.getFavorites()

    suspend fun deleteFavoriteByName(name: String) = dao.deleteFavoriteByName(name)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(favorite: Result) {
        dao.insert(favorite)
    }
}