package com.vinade.starwars.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.StarWarsResult
import com.vinade.starwars.repository.StarWarsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StarWarsViewModel(private val repository: StarWarsRepository) : ViewModel() {
    private var _result = MutableLiveData<Response<StarWarsResult>>()

    init {
        getPeoplePage()
    }
    private fun getPeoplePage(){
        viewModelScope.launch {
                val response = repository.getPeoplePage()
                _result.postValue(response)


        }
    }
}