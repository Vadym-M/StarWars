package com.vinade.starwars.model

data class FilmResult(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Film>
)