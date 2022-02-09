package com.vinade.starwars.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "films")
data class Film(
    @PrimaryKey
    val url: String,
    val characters: List<String>?,
    val created: String?,
    val director: String?,
    val edited: String?,
    val episode_id: Int?,
    val opening_crawl: String?,
    val planets: List<String>?,
    val producer: String?,
    val release_date: String?,
    val species: List<String>?,
    val starships: List<String>?,
    val title: String?,
    val vehicles: List<String>?
)