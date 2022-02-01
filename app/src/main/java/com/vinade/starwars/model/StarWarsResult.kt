package com.vinade.starwars.model

data class StarWarsResult(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)