package com.vinade.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.FavoriteRepository
import com.vinade.starwars.util.AdapterDataType
import com.vinade.starwars.util.Constants.Companion.ADDED_TO_FAVORITE
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {


    private var _favorites = MutableLiveData<List<AdapterDataType>>()
    val favorites: LiveData<List<AdapterDataType>>
    get() = _favorites

    private var _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>>
        get() = _films

    val isExist = MutableLiveData<Boolean>()

    val callBack = MutableLiveData<String>()

    fun insertFavoritePerson(data : Result)= viewModelScope.launch {
        try {
            repository.insertFavorite(data)
            callBack.value = ADDED_TO_FAVORITE
        }catch (e: Exception){
            callBack.value = e.toString()
        }

    }
    fun insertFilms(films: List<Film>) = viewModelScope.launch{
        repository.insertFilm(films)
    }
    fun getAllFavorites() = viewModelScope.launch {
        val response = repository.getAllFavorites()
        val currentList = mutableListOf<AdapterDataType>()
        response.forEach {
            currentList.add(AdapterDataType.Item(it))
        }
        _favorites.value = currentList
    }
    fun getFilms(person: Result) {

        viewModelScope.launch {
            val list = person.films.map { repository.getFilmByUrl(it) }
            _films.value = list
        }

    }
    fun isExist(character: Result) = viewModelScope.launch {
        val res = repository.isExist(character.url)
        isExist.value = res
    }
    fun deleteCharacter(character: Result) = viewModelScope.launch {
        repository.deleteCharacterByUrl(character.url)
    }
    fun deleteFilms(films: List<Film>) = viewModelScope.launch {
        repository.deleteFilms(films)
    }
}