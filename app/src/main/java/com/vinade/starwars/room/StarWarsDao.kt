package com.vinade.starwars.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result


@Dao
interface StarWarsDao {
    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<Result>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Result)
    @Query("DELETE FROM favorite WHERE name = :deleteName")
    suspend fun deleteFavoriteByName(deleteName : String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertFilm(film: Film)
    @Query("SELECT * FROM films WHERE url = :url")
    suspend fun getFilmByUrl(url: String): Film

}