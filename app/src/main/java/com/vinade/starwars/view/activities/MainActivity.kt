package com.vinade.starwars.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vinade.starwars.R
import com.vinade.starwars.repository.StarWarsRepository
import com.vinade.starwars.viewmodel.StarWarsViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: StarWarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val repo = StarWarsRepository()
        viewModel = ViewModelProvider(this, ViewModelFactory(repo)).get(StarWarsViewModel::class.java)
    }
}