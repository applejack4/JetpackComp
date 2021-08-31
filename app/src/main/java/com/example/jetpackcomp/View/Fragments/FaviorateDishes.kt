package com.example.jetpackcomp.View.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.View.Activities.MainActivity
import com.example.jetpackcomp.View.adapter.favDishAdapter
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication
import com.example.jetpackcomp.databinding.FragmentFavoriteDishBinding


class FaviorateDishes : Fragment() {
private var mbinding:  FragmentFavoriteDishBinding?= null

  private val mFavDishViewModel : JetPackCompViewModel by viewModels {
    JetPackCompViewModelFactory((requireActivity().application as JetPackCompApplication).repository)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mbinding = FragmentFavoriteDishBinding.inflate(layoutInflater, container, false)
    return mbinding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mFavDishViewModel.favoriteDishes.observe(viewLifecycleOwner){
      dishes ->
      dishes.let {
        mbinding?.rvFavoriteDishes?.layoutManager = GridLayoutManager(requireActivity(), 2)
        val adapter = favDishAdapter(this)
        mbinding?.rvFavoriteDishes?.adapter = adapter
        if(it.isNotEmpty()){
          for (dish in it){
            mbinding!!.rvFavoriteDishes.visibility = View.VISIBLE
            mbinding!!.textDashboard.visibility = View.GONE
            adapter.dishesList(it)
          }
        }else{
          mbinding!!.rvFavoriteDishes.visibility = View.GONE
          mbinding!!.textDashboard.visibility = View.VISIBLE
        }
      }
    }
  }

  fun dishDetails(jetpackComp: JetpackComp){
    findNavController().navigate(FaviorateDishesDirections.actionNavigationFavoriteDishToDishDetailFragment(jetpackComp))
    if(requireActivity() is MainActivity){
      (activity as MainActivity?)!!.hideBottomNavigation()
    }
  }

  override fun onResume() {
    super.onResume()
    if(requireActivity() is MainActivity){
      (activity as MainActivity?)!!.showBottomNavigation()
    }
  }

override fun onDestroyView() {
        super.onDestroyView()
        mbinding = null
    }
}