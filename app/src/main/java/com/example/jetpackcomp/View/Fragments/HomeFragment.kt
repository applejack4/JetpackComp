package com.example.jetpackcomp.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jetpackcomp.R
import com.example.jetpackcomp.View.Activities.AddUpdate
import com.example.jetpackcomp.View.adapter.favDishAdapter
import com.example.jetpackcomp.databinding.FragmentHomeBinding
import com.example.jetpackcomp.ViewModel.HomeViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication

class HomeFragment : Fragment() {

  private lateinit var mbinding: FragmentHomeBinding

  private val mJetPackCompViewModel : JetPackCompViewModel by viewModels{
    JetPackCompViewModelFactory((requireActivity().application as JetPackCompApplication).repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mbinding.addDishesRecView.layoutManager = GridLayoutManager(requireActivity(), 2)
    val favDishAdapter = favDishAdapter(this@HomeFragment)

    mbinding.addDishesRecView.adapter = favDishAdapter

    mJetPackCompViewModel.allDishesList.observe(viewLifecycleOwner){
      dishes ->
      run {
        dishes.let {
          for (item in it) {
            if(it.isNotEmpty()){
              mbinding.addDishesRecView.visibility = View.VISIBLE
              mbinding.tvAddDishes.visibility = View.GONE

              favDishAdapter.dishesList(it)
            }else{
              mbinding.addDishesRecView.visibility = View.GONE
              mbinding.tvAddDishes.visibility = View.VISIBLE
            }
          }
        }
      }
    }
  }

  private lateinit var homeViewModel: HomeViewModel
private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mbinding = FragmentHomeBinding.inflate(inflater, container, false)
    return mbinding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_add, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId){
      R.id.Add_button -> {
        startActivity(Intent(requireActivity(), AddUpdate::class.java))
        return true
      }
    }
    return super.onOptionsItemSelected(item)

  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//  fun dishDetails(){
//    findNavController().navigate()
//  }
}