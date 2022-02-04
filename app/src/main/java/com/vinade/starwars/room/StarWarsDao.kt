package com.vinade.starwars.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinade.starwars.model.Result


@Dao
interface StarWarsDao {
    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<Result>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Result)
    @Query("DELETE FROM favorite")
    suspend fun deleteAll()
}