package com.vinade.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.StarWarsRepository
import com.vinade.starwars.util.APIResult
import com.vinade.starwars.util.AdapterDataType
import kotlinx.coroutines.launch
import java.lang.Exception

class StarWarsViewModel(private val repository: StarWarsRepository) : ViewModel() {
    private var _result = MutableLiveData<APIResult<List<AdapterDataType>>>()
    val result: LiveData<APIResult<List<AdapterDataType>>>
    get() = _result

    private var _favorites = MutableLiveData<List<Result>>()
    val favorites: LiveData<List<Result>>
        get() = _favorites

    private var pageCounter = 1

    init {
        getPeoplePage()
    }
    fun getPeoplePage(){
        viewModelScope.launch {
                _result.value = APIResult.Loading()
            try {
                val response = repository.getPeoplePage(pageCounter)
                if(response.isSuccessful){
                    val list = mutableListOf<AdapterDataType>()
                    list.add(AdapterDataType.Header("Page number: $pageCounter"))
                    response.body()!!.results.forEach { list.add(AdapterDataType.Item(it)) }
                    _result.value = APIResult.Success(list)
                    pageCounter++
                }else{
                    _result.value = APIResult.Error(response.message())
                }

            }catch (e :Exception){
                _result.value = APIResult.Error(e.message.toString())
            }
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

    fun deleteFavorite(favorite: Result) = viewModelScope.launch {
        val list = _favorites.value as MutableList
        val index = list.indexOf(favorite)
        list.remove(favorite)
        _favorites.postValue(list as List<Result>)

        repository.deleteFavoriteByName(favorite.name)
    }
}