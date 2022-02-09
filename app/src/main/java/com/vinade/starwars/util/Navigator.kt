package com.vinade.starwars.util

import androidx.fragment.app.Fragment
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result

interface Navigator {
    fun showDetailFragment(result: Result, films: List<Film>?)
    fun backPress()
}
fun Fragment.navigator() :Navigator{
    return requireActivity() as Navigator
}
