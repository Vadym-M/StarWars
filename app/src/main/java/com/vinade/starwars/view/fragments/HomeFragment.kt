package com.vinade.starwars.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinade.starwars.databinding.FragmentHomeBinding
import com.vinade.starwars.repository.StarWarsRepository
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

        val repo = StarWarsRepository()
        viewModel = ViewModelProvider(this, ViewModelFactory(repo)).get(StarWarsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner) {
            adapterSW.setAdapter(it)
        }

        adapterSW.onItemClick = {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
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