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
import androidx.recyclerview.widget.RecyclerView
import com.vinade.starwars.databinding.FragmentHomeBinding
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.repository.StarWarsRepository
import com.vinade.starwars.room.StarWarsRoomDatabase
import com.vinade.starwars.util.APIResult
import com.vinade.starwars.util.navigator
import com.vinade.starwars.view.adapters.StarWarsAdapter
import com.vinade.starwars.viewmodel.DetailViewModel
import com.vinade.starwars.viewmodel.StarWarsViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: StarWarsViewModel
    lateinit var viewModelDetail: DetailViewModel
    lateinit var adapterSW: StarWarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerListener()
        resultListener()


        adapterSW.onItemClick = {
            navigator().showDetailFragment(it, null)
        }

    }
    private fun initViewModels(){
        val repo = DetailRepository()
        viewModelDetail = ViewModelProvider(requireActivity(), ViewModelFactory(repo))[DetailViewModel::class.java]
        viewModelDetail.getFilms()
        val dao: StarWarsRoomDatabase =
            StarWarsRoomDatabase.getDatabase(requireContext().applicationContext)
        val repos = StarWarsRepository(dao.favoriteDao())
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(repos))[StarWarsViewModel::class.java]
    }

    private fun recyclerListener(){
        adapterSW = StarWarsAdapter()
        binding.homeRecycler.apply {
            adapter = adapterSW
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.homeRecycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!binding.homeRecycler.canScrollVertically(1)) {
                        viewModel.getPeoplePage()
                    }
                }
            }
        )
    }
    private fun resultListener(){
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is APIResult.Loading -> binding.homeProgressBar.visibility = View.VISIBLE
                is APIResult.Success -> {
                    result.data?.let {
                        adapterSW.setAdapter(it)
                        binding.homeProgressBar.visibility = View.GONE
                    }
                }
                is APIResult.Error -> Toast.makeText(
                    requireContext(),
                    result.msg,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

}