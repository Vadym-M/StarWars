package com.vinade.starwars.room

import android.content.Context
import androidx.room.*
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result


@Database(entities = [Result::class, Film::class], version = 3 ,exportSchema = true)
@TypeConverters(RoomConverter::class)
abstract class StarWarsRoomDatabase : RoomDatabase() {

    abstract fun favoriteDao(): StarWarsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StarWarsRoomDatabase? = null

        fun getDatabase(context: Context): StarWarsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarWarsRoomDatabase::class.java,
                    "favorite_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}