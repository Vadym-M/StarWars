package com.vinade.starwars.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.model.StarWarsResult
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.util.APIResult
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class DetailViewModel(private val repository: DetailRepository): ViewModel() {

    private var _films = MutableLiveData<APIResult<List<Film>>>()
    val films: LiveData<APIResult<List<Film>>>
    get() = _films

    //private var newList = emptyList<Film>()




    fun getFilm(person: Result) = viewModelScope.launch{
        _films.value = APIResult.Loading()
        try {
            val newList = person.films.map { film ->
                async { repository.getFilms(film) }
            }.map { it.await() }
            _films.value = APIResult.Success(newList as List<Film>)
        }catch (e: Exception){
            _films.value = APIResult.Error(e.message.toString())
        }
    }


}