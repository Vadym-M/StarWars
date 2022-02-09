package com.vinade.starwars.room

import androidx.room.*
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import retrofit2.http.DELETE


@Dao
interface StarWarsDao {
    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Result)
    @Query("DELETE FROM favorite WHERE name = :deleteName")
    suspend fun deleteFavoriteByName(deleteName : String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertFilms(film: List<Film>)
    @Query("SELECT * FROM films WHERE url = :url")
    suspend fun getFilmByUrl(url: String): Film
    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE url = :url)")
    suspend fun isExist(url: String): Boolean
    @Query("DELETE FROM favorite WHERE url = :url")
    suspend fun deleteCharacter(url: String)
    @Delete
    suspend fun deleteFilms(films: List<Film>)

}