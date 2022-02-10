package com.vinade.starwars.repository

import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.room.StarWarsDao

class FavoriteRepository(private val dao: StarWarsDao) : Repository {

    suspend fun insertFavorite(data: Result) {dao.insert(data)}

    suspend fun insertFilm(data: List<Film>) = dao.insertFilms(data)

    suspend fun getAllFavorites() = dao.getFavorites()

    suspend fun getFilmByUrl(url: String) = dao.getFilmByUrl(url)

    suspend fun isExist(url:String) = dao.isExist(url)

    suspend fun deleteCharacterByUrl(url: String) = dao.deleteCharacter(url)

    suspend fun deleteFilms(films: List<Film>) = dao.deleteFilms(films)

}