package com.vinade.starwars.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinade.starwars.R
import com.vinade.starwars.databinding.FragmentDetailBinding
import com.vinade.starwars.model.Film
import com.vinade.starwars.model.Result
import com.vinade.starwars.repository.DetailRepository
import com.vinade.starwars.repository.FavoriteRepository
import com.vinade.starwars.room.StarWarsRoomDatabase
import com.vinade.starwars.util.APIResult
import com.vinade.starwars.util.navigator
import com.vinade.starwars.view.adapters.DetailAdapter
import com.vinade.starwars.viewmodel.DetailViewModel
import com.vinade.starwars.viewmodel.FavoriteViewModel
import com.vinade.starwars.viewmodel.ViewModelFactory


class DetailFragment : Fragment() {

    lateinit var result: Result
    lateinit var films: List<Film>
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFavorite: FavoriteViewModel
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapterD: DetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = arguments?.getParcelable(KEY)!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val repo = DetailRepository()
        viewModel = ViewModelProvider(this, ViewModelFactory(repo))[DetailViewModel::class.java]
        initFavoriteViewModel()
        initData()
        initTopBar()
        viewModel.films.observe(requireActivity()) { result ->
            when (result) {
                is APIResult.Loading -> {showProgressBar()}
                is APIResult.Success -> {
                    result.data?.let {
                        films = it
                        adapterD.setAdapter(films)
                        hideProgressBar()
                    }
                }
                is APIResult.Error -> { Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT).show()}
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilm(result)
    }

    private fun initData(){
        binding.apply {
            detailEyeColor.text = getString(R.string.eye_color_s, result.eye_color)
            detailGender.text = getString(R.string.gender_s, result.gender)
            detailHairColor.text = getString(R.string.hair_color_s, result.hair_color)
            detailHeight.text = getString(R.string.height_d, result.height)
            detailMass.text = getString(R.string.mass_d, result.mass)
            detailSkinColor.text = getString(R.string.skin_color_s, result.skin_color)
            detailName.text = result.name

            adapterD = DetailAdapter()
            filmRecycler.adapter = adapterD
            filmRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun showProgressBar(){
        binding.filmProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.filmProgressBar.visibility = View.GONE
    }
    private fun initTopBar(){
        binding.topAppBar.setNavigationOnClickListener {
            navigator().backPress()
        }
        binding.topAppBar.setOnClickListener { item ->
            when(item.id){
                R.id.favorite -> {
                    //favorite view model
                }
            }
        }
    }

    private fun initFavoriteViewModel(){
        val dao: StarWarsRoomDatabase = StarWarsRoomDatabase.getDatabase(requireContext().applicationContext)
        val repo = FavoriteRepository(dao.favoriteDao())
        viewModelFavorite = ViewModelProvider(this, ViewModelFactory(repo))[FavoriteViewModel::class.java]
    }

    companion object {

        @JvmStatic val KEY = "datakey"
        @JvmStatic
        fun newInstance(result: Result) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                putParcelable(KEY, result)
                }
            }
    }
}