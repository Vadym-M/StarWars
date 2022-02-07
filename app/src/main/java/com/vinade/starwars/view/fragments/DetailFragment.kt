package com.vinade.starwars.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vinade.starwars.R
import com.vinade.starwars.databinding.FragmentDetailBinding
import com.vinade.starwars.model.Result


class DetailFragment : Fragment() {
    lateinit var result: Result
    lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = arguments?.getParcelable(KEY)!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun initData(){
        binding.apply {
            detailEyeColor.text = getString(R.string.eye_color_s, result.eye_color)
            detailGender.text = getString(R.string.gender_s, result.gender)
            detailHairColor.text = getString(R.string.hair_color_s, result.hair_color)
            detailHeight.text = getString(R.string.height_d, result.height)
            detailMass.text = getString(R.string.mass_d, result.mass)
            detailSkinColor.text = getString(R.string.skin_color_s, result.skin_color)
            detailName.text = result.name
        }
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