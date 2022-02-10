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

    private val currentList = mutableListOf<AdapterDataType>()

    private var pageCounter = 1
    private var isProcess = false
    private var isLastPage = false

    init {
        getPeoplePage()
    }

    fun getPeoplePage() {
        if (!isProcess && !isLastPage) {
            viewModelScope.launch {
                _result.value = APIResult.Loading()
                isProcess = true
                try {
                    val response = repository.getPeoplePage(pageCounter)
                    if (response.isSuccessful) {
                        currentList.add(AdapterDataType.Header("Page number: $pageCounter"))
                        response.body()!!.results.forEach { currentList.add(AdapterDataType.Item(it)) }
                        _result.value = APIResult.Success(currentList)
                        pageCounter++
                        if (response.body()!!.next == null) {
                            isLastPage = true
                        }
                    } else {
                        _result.value = APIResult.Error(response.message())
                    }

                } catch (e: Exception) {
                    _result.value = APIResult.Error(e.message.toString())
                }
                isProcess = false
            }
        }
    }

}