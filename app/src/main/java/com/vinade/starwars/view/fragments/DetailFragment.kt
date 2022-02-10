package com.vinade.starwars.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
import java.util.ArrayList


class DetailFragment : Fragment() {

    lateinit var character: Result
    var films: List<Film>? = null
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFavorite: FavoriteViewModel
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapterD: DetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        character = arguments?.getParcelable(KEY_F)!!

        if (films == null) {
            viewModel.getCharacterFilms(character)
        }else{
            viewModelFavorite.getFilms(character)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        isFavorite()
        initData()
        initTopBar()

        viewModel.films.observe(viewLifecycleOwner){
            viewModelFavorite.insertFilms(it)
        }

        viewModelFavorite.isExist.observe(viewLifecycleOwner){
            character.isFavorite = it
            setIsFavorite()
        }

        viewModelFavorite.callBack.observe(requireActivity()) {
            showSnackBar(it)
        }

        viewModel.characterFilms.observe(requireActivity()) { result ->
            when (result) {
                is APIResult.Loading -> {
                    showProgressBar()
                }
                is APIResult.Success -> {
                    result.data?.let {
                        films = it
                        adapterD.setAdapter(films!!)
                        hideProgressBar()
                    }
                }
                is APIResult.Error -> {
                    Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (films != null) {
            adapterD.setAdapter(films!!)
            hideProgressBar()
        } else {
            viewModel.getCharacterFilms(character)
        }


    }

    private fun initData() {
        binding.apply {
            detailEyeColor.text = getString(R.string.eye_color_s, character.eye_color)
            detailGender.text = getString(R.string.gender_s, character.gender)
            detailHairColor.text = getString(R.string.hair_color_s, character.hair_color)
            detailHeight.text = getString(R.string.height_d, character.height)
            detailMass.text = getString(R.string.mass_d, character.mass)
            detailSkinColor.text = getString(R.string.skin_color_s, character.skin_color)
            detailName.text = character.name

            if (character.isFavorite == true)
            binding.topAppBar.menu[0].icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart)

            adapterD = DetailAdapter()
            filmRecycler.adapter = adapterD
            filmRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }


    }

    private fun showProgressBar() {
        binding.filmProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.filmProgressBar.visibility = View.GONE
    }

    private fun initTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            navigator().backPress()
        }
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorite -> {
                    character.isFavorite = !character.isFavorite!!
                    if(character.isFavorite!!) {
                        setIsFavorite()
                        viewModelFavorite.insertFavoritePerson(character)
                    }else {
                        setIsFavorite()
                        viewModelFavorite.deleteCharacter(character)
                    }
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun setIsFavorite(){
        binding.topAppBar.menu[0].icon = if(character.isFavorite == true) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart)
        }else{
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_outline )
        }
    }

    private fun isFavorite(){
        viewModelFavorite.isExist(character)
    }

    private fun initViewModels() {
        val repo = DetailRepository()
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory(repo))[DetailViewModel::class.java]

        val dao: StarWarsRoomDatabase =
            StarWarsRoomDatabase.getDatabase(requireContext().applicationContext)
        val repos = FavoriteRepository(dao.favoriteDao())
        viewModelFavorite =
            ViewModelProvider(this, ViewModelFactory(repos))[FavoriteViewModel::class.java]
    }

    companion object {

        @JvmStatic
        val KEY_F = "characterKey"

        @JvmStatic
        fun newInstance(result: Result) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_F, result)

                }
            }
    }
}