package com.vinade.starwars.util

import com.vinade.starwars.model.Result

sealed class AdapterDataType(){
    data class Item (val data: Result): AdapterDataType()
    data class Header(val header: String?): AdapterDataType()
}
fun AdapterDataType.getResult(): Result?{
    return when (this){
        is AdapterDataType.Item -> this.data
        else -> null
    }
}
