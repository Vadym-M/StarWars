package com.vinade.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.StarWarsRepository
import kotlinx.coroutines.launch

class StarWarsViewModel(private val repository: StarWarsRepository) : ViewModel() {
    private var _result = MutableLiveData<List<Result>>()
    val result: LiveData<List<Result>>
    get() = _result

    private var _favorites = MutableLiveData<List<Result>>()
    val favorites: LiveData<List<Result>>
        get() = _favorites

    init {
        getPeoplePage()
    }
    private fun getPeoplePage(){
        viewModelScope.launch {
                val response = repository.getPeoplePage()
                _result.postValue(response.body()!!.results)
        }
    }
    fun getFavorites(){
        viewModelScope.launch {
            val response = repository.getFavorites()
            _favorites.postValue(response)
        }
    }
    fun insertItem(it: Result){
        viewModelScope.launch {
            repository.insert(it)
        }
    }
    fun delete() = viewModelScope.launch {
        repository.deleteAll()
    }
}