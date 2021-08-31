package com.example.jetpackcomp.View.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.R
import com.example.jetpackcomp.View.Activities.AddUpdate
import com.example.jetpackcomp.View.Activities.MainActivity
import com.example.jetpackcomp.View.adapter.CustomItemListAdapter
import com.example.jetpackcomp.View.adapter.favDishAdapter
import com.example.jetpackcomp.databinding.FragmentHomeBinding
import com.example.jetpackcomp.ViewModel.HomeViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication
import com.example.jetpackcomp.databinding.DialogCustomListBinding
import com.example.jetpackcomp.utils.Constants

class HomeFragment : Fragment() {

  private lateinit var mbinding: FragmentHomeBinding

  private lateinit var mFavDishAdapter: favDishAdapter

  private lateinit var mCustomListDialog :Dialog

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
    mFavDishAdapter = favDishAdapter(this@HomeFragment)

    mbinding.addDishesRecView.adapter = mFavDishAdapter

    mJetPackCompViewModel.allDishesList.observe(viewLifecycleOwner){
      dishes ->
      run {
        dishes.let {
          for (item in it) {
            if(it.isNotEmpty()){
              mbinding.addDishesRecView.visibility = View.VISIBLE
              mbinding.tvAddDishes.visibility = View.GONE

              mFavDishAdapter.dishesList(it)
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
      R.id.Filter_Dishes ->{
        filteredDishesListDialog()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

  fun dishDetails(jetPackComp : JetpackComp){
    findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToDishDetailFragment(jetPackComp))
    if(requireActivity() is MainActivity){
      (activity as MainActivity).hideBottomNavigation()
    }
  }

  fun deleteDish(dish : JetpackComp){
    val builder = AlertDialog.Builder(requireActivity())
    builder.setTitle(resources.getString(R.string.popup_title))
    builder.setMessage(resources.getString(R.string.popup_message))
    builder.setIcon(android.R.drawable.ic_dialog_alert)
    builder.setPositiveButton(resources.getString(R.string.popup_opt_Yes)){ dialogInterface,  _ ->
      mJetPackCompViewModel.delete(dish)
      dialogInterface.dismiss()
    }

    builder.setNegativeButton(resources.getString(R.string.popup_opt_No)){ dialogInterface, _ ->
      dialogInterface.dismiss()
    }

    val alertDialog : AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()
  }

  override fun onResume() {
    super.onResume()
    if(requireActivity() is MainActivity){
      (activity as MainActivity).showBottomNavigation()
    }
  }

  private fun filteredDishesListDialog(){
    mCustomListDialog = Dialog(requireActivity())
    val binding : DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
    mCustomListDialog.setContentView(binding.root)

    binding.tvDialogList.text = resources.getString(R.string.select_filter)
    val dishTypes  = Constants.dishTypes()
    dishTypes.add(0, Constants.ALL_ITEMS)
    binding.rvListDialog.layoutManager = LinearLayoutManager(requireActivity())

    val adapter = CustomItemListAdapter(requireActivity(), this@HomeFragment ,dishTypes, Constants.FILTER_SELECTION)

    binding.rvListDialog.adapter = adapter
    mCustomListDialog.show()
  }

  fun filterSelection(filterItemSelection : String){
    mCustomListDialog.dismiss()
    if(filterItemSelection == Constants.ALL_ITEMS){
      mJetPackCompViewModel.allDishesList.observe(viewLifecycleOwner){
          dishes ->
        run {
          dishes.let {
            for (item in it) {
              if(it.isNotEmpty()){
                mbinding.addDishesRecView.visibility = View.VISIBLE
                mbinding.tvAddDishes.visibility = View.GONE

                mFavDishAdapter.dishesList(it)
              }else{
                mbinding.addDishesRecView.visibility = View.GONE
                mbinding.tvAddDishes.visibility = View.VISIBLE
              }
            }
          }
        }
      }
    }else{
      mJetPackCompViewModel.getFilteredList(filterItemSelection).observe(viewLifecycleOwner){
        dishes ->
        dishes.let {
          if(it.isNotEmpty()){
            binding.addDishesRecView.visibility = View.VISIBLE
            binding.tvAddDishes.visibility = View.GONE

            mFavDishAdapter.dishesList(it)
          }else{
            binding.addDishesRecView.visibility = View.GONE
            binding.tvAddDishes.visibility = View.VISIBLE
          }
        }
      }
    }
  }
}