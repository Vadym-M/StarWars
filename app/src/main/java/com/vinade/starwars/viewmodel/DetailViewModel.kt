package com.vinade.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.util.APIResult
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(private val repository: DetailRepository) : ViewModel() {

    private var _characterFilms = MutableLiveData<APIResult<List<Film>>>()
    val characterFilms: LiveData<APIResult<List<Film>>>
        get() = _characterFilms

    private var _films: List<Film>? = null


    fun getCharacterFilms(character: Result) {
        if (_films != null) {
            val separatedList = _films!!.filter {
                character.films.contains(it.url)
            }
            _characterFilms.value = APIResult.Success(separatedList)
        } else {
            getFilmsNetwork(character)
        }
    }

    private fun getFilmsNetwork(character: Result) = viewModelScope.launch {
        _characterFilms.value = APIResult.Loading()
        try {
            val response = repository.getFilms()
            if (response.isSuccessful) {
                _films = response.body()!!.results
                val separatedList = response.body()!!.results.filter {
                    character.films.contains(it.url)
                }
                _characterFilms.value = APIResult.Success(separatedList)
            } else {
                _characterFilms.value = APIResult.Error(response.message())
            }
        } catch (e: Exception) {
            _characterFilms.value = APIResult.Error(e.message.toString())
        }
    }

    fun getFilms() = viewModelScope.launch {
        try {
            val response = repository.getFilms()
            if (response.isSuccessful)
                _films = response.body()!!.results
        } catch (e: Exception) {
        }

    }

}