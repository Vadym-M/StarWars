package com.vinade.starwars.viewmodel

import androidx.lifecycle.ViewModel
import com.vinade.starwars.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

}