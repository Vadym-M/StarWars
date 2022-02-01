package com.vinade.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinade.starwars.repository.StarWarsRepository

class ViewModelFactory(private val repository: StarWarsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StarWarsViewModel::class.java)){
            StarWarsViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel not Found")
        }
    }
}