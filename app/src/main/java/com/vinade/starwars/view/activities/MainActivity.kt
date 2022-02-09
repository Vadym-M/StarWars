package com.vinade.starwars.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.vinade.starwars.R
import com.vinade.starwars.databinding.ActivityMainBinding
import com.vinade.starwars.model.Result
import com.vinade.starwars.util.Constants.Companion.DETAIL_FRAGMENT
import com.vinade.starwars.util.Constants.Companion.FAVORITE_FRAGMENT
import com.vinade.starwars.util.Constants.Companion.HOME_FRAGMENT
import com.vinade.starwars.util.Navigator
import com.vinade.starwars.view.fragments.DetailFragment
import com.vinade.starwars.view.fragments.FavoriteFragment
import com.vinade.starwars.view.fragments.HomeFragment

class MainActivity : AppCompatActivity(), Navigator {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showFragment(HomeFragment(), null, HOME_FRAGMENT)
        }


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (binding.bottomNavigation.selectedItemId != R.id.home) {
                        showFragment(HomeFragment(), null, HOME_FRAGMENT)
                    }
                    true
                }
                R.id.favorite -> {
                    if (binding.bottomNavigation.selectedItemId != R.id.favorite) {
                        showFragment(FavoriteFragment(), null, FAVORITE_FRAGMENT)
                    }
                    true
                }
                else -> false
            }
        }


    }

    private fun hideBottomBar() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showBottomBar() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun showDetailFragment(result: Result) {
        val fragment = DetailFragment.newInstance(result)
        showFragment(fragment, "bs", DETAIL_FRAGMENT)
        hideBottomBar()
    }

    private fun showFragment(fragment: Fragment, backStack: String?, tag: String?) {
        supportFragmentManager.commit {
            backStack?.let { addToBackStack(backStack) }
            replace(R.id.fragmentContainer, fragment, tag)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(DETAIL_FRAGMENT)
        fragment?.let {
            if (fragment.isVisible)
                showBottomBar()
        }

        super.onBackPressed()
    }

    override fun backPress() {
        onBackPressed()
    }
}