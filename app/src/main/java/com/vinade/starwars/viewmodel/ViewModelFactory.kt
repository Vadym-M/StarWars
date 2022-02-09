package com.vinade.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.repository.FavoriteRepository
import com.vinade.starwars.repository.Repository
import com.vinade.starwars.repository.StarWarsRepository

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StarWarsViewModel::class.java) -> {
                StarWarsViewModel(repository as StarWarsRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository as DetailRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository as FavoriteRepository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel not Found")
            }
        }
    }
}