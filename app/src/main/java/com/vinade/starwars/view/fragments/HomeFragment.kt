package com.vinade.starwars.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinade.starwars.databinding.FragmentHomeBinding
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.StarWarsRepository
import com.vinade.starwars.room.StarWarsRoomDatabase
import com.vinade.starwars.util.APIResult
import com.vinade.starwars.view.adapters.StarWarsAdapter
import com.vinade.starwars.viewmodel.StarWarsViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: StarWarsViewModel
    lateinit var adapterSW: StarWarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapterSW = StarWarsAdapter()
        binding.homeRecycler.apply {
            adapter = adapterSW
            layoutManager = LinearLayoutManager(requireContext())
        }
        val dao: StarWarsRoomDatabase = StarWarsRoomDatabase.getDatabase(requireContext().applicationContext)
        val repo = StarWarsRepository(dao.favoriteDao())
        viewModel = ViewModelProvider(this, ViewModelFactory(repo))[StarWarsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debug", "created")
        viewModel.result.observe(viewLifecycleOwner) {result->
            when(result){
                is APIResult.Loading -> binding.homeProgressBar.visibility = View.VISIBLE
                is APIResult.Success -> {
                    result.data?.let { adapterSW.setAdapter(it)
                        binding.homeProgressBar.visibility = View.GONE
                    }
                }
                is APIResult.Error -> Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT).show()
            }

        }

        adapterSW.onItemClick = {
            viewModel.insertItem(it)
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}