package com.vinade.starwars.util

import com.vinade.starwars.network.ApiStarWars

sealed class APIResult<T>(val data: T? = null, val msg: String? = null){
    class Loading<T>: APIResult<T>()
    class Success<T>(data: T) : APIResult<T>(data)
    class Error<T>(msg: String): APIResult<T>(null ,msg)
}
