package com.vinade.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.util.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailViewModel(private val repository: DetailRepository): ViewModel() {

    private var _films = MutableLiveData<APIResult<List<Film>>>()
    val films: LiveData<APIResult<List<Film>>>
    get() = _films

    //private var newList = emptyList<Film>()


    fun getFilms(person: Result) = viewModelScope.launch {
        _films.value = APIResult.Loading()
        try {
            val response = repository.getFilms()
            if(response.isSuccessful){
                val separatedList =  response.body()!!.results.filter {
                    person.films?.contains(it.url) == true
                }
                _films.value = APIResult.Success(separatedList)
            }else{
                _films.value = APIResult.Error(response.message())
            }
        }catch (e: Exception){
            _films.value = APIResult.Error(e.message.toString())
        }
    }


//    fun getFilm(person: Result) = viewModelScope.launch{
//        _films.value = APIResult.Loading()
//        try {
//            val newList = mutableListOf<Film>()
//                person.films?.forEach { film ->
//                    withContext(Dispatchers.Default) {
//                       val res = repository.getFilms(film)
//                        if(res.isSuccessful){
//                            newList.add(res.body()!!)
//                        }else{
//                            throw java.lang.Exception(res.message())
//                        }
//
//                    }
//                }
//            _films.value = APIResult.Success(newList as List<Film>)
//        }catch (e: Exception){
//            _films.value = APIResult.Error(e.message.toString())
//        }
//    }


}