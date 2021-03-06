package com.vinade.starwars.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinade.starwars.databinding.FragmentFavoriteBinding
import com.vinade.starwars.repository.FavoriteRepository
import com.vinade.starwars.room.StarWarsRoomDatabase
import com.vinade.starwars.util.navigator
import com.vinade.starwars.view.adapters.StarWarsAdapter
import com.vinade.starwars.viewmodel.FavoriteViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: FavoriteViewModel
    lateinit var adapterSW: StarWarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        adapterSW = StarWarsAdapter()
        binding.favoriteRecycler.apply {
            adapter = adapterSW
            layoutManager = LinearLayoutManager(requireContext())
        }
        val dao: StarWarsRoomDatabase = StarWarsRoomDatabase.getDatabase(requireContext().applicationContext)
        val repo = FavoriteRepository(dao.favoriteDao())
        viewModel = ViewModelProvider(this, ViewModelFactory(repo))[FavoriteViewModel::class.java]
        viewModel.getAllFavorites()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favorites.observe(viewLifecycleOwner) {
            adapterSW.setAdapter(it)
        }

        adapterSW.onItemClick = {res->
            navigator().showDetailFragment(res)

        }
    }

}