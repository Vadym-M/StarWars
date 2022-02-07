package com.vinade.starwars.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class Result(
    //@PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "birth_year")
    val birth_year: String,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "edited")
    val edited: String,
    @ColumnInfo(name = "eye_color")
    val eye_color: String,
    @ColumnInfo(name = "films")
    val films: List<String>,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "hair_color")
    val hair_color: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "homeworld")
    val homeworld: String,
    @ColumnInfo(name = "mass")
    val mass: String,
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "skin_color")
    val skin_color: String,
    @ColumnInfo(name = "species")
    val species: List<String>,
    @ColumnInfo(name = "starships")
    val starships: List<String>,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "vehicles")
    val vehicles: List<String>
): Parcelable