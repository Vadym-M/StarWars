package com.vinade.starwars.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {
    @TypeConverter
    fun fromString(str: String):List<String>{
        val listType = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(str,listType)
    }


    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}