package com.example.jetpackcomp.View.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.R
import com.example.jetpackcomp.ViewModel.FaviorateDishViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication
import com.example.jetpackcomp.databinding.FragmentDishDetailBinding
import java.io.IOException
import java.lang.Exception

class DishDetailFragment : Fragment() {

    private var mbinding : FragmentDishDetailBinding? = null

    private var mFavDishDetails : JetpackComp? = null

    private val mFaviorateDishViewModel : JetPackCompViewModel by viewModels {
        JetPackCompViewModelFactory(((requireActivity().application) as JetPackCompApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Share_menu ->{
                Toast.makeText(requireActivity(), "Share", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentDishDetailBinding.inflate(inflater, container, false)
        return mbinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DishDetailFragmentArgs by navArgs()

        mFavDishDetails = args.dishDetails
        args.let {
            try {
                Glide.with(requireActivity()).load(it.dishDetails.image).centerCrop().into(mbinding!!.IvImageView)
            }catch (e : IOException){
                e.printStackTrace()
            }
            mbinding!!.tvDishTitle.text = it.dishDetails.title
            mbinding!!.tvTypeDesc.text = it.dishDetails.type
            mbinding!!.tvIngredientsContent.text = it.dishDetails.ingredients
            mbinding!!.tvDirectionsContent.text = it.dishDetails.DirectionsToCook
            mbinding!!.TimeRequired.text = it.dishDetails.cookingTime
        }

        mbinding!!.ivAddImage.setOnClickListener {
            args.dishDetails.FavoriteDish = !args.dishDetails.FavoriteDish
            mFaviorateDishViewModel.update(args.dishDetails)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }
}