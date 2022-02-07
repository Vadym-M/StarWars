package com.vinade.starwars.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vinade.starwars.R
import com.vinade.starwars.databinding.ActivityMainBinding
import com.vinade.starwars.repository.StarWarsRepository
import com.vinade.starwars.view.fragments.DetailFragment
import com.vinade.starwars.view.fragments.FavoriteFragment
import com.vinade.starwars.view.fragments.HomeFragment
import com.vinade.starwars.viewmodel.StarWarsViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: StarWarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            //binding.bottomNavigation.selectedItemId = R.id.home
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer, HomeFragment())
            }
        }


        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    if(binding.bottomNavigation.selectedItemId != R.id.home){
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainer, HomeFragment())
                        }
                    }
                    true
                }
                R.id.favorite -> {
                    if (binding.bottomNavigation.selectedItemId != R.id.favorite){
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainer, FavoriteFragment())}
                    }
                    true
                }
                else -> false
            }
        }
        fun hideBottomBar(){
            binding.bottomNavigation.visibility = View.GONE
        }



    }
}