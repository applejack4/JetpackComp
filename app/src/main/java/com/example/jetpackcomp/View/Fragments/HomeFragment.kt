package com.example.jetpackcomp.View.Fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.R
import com.example.jetpackcomp.View.Activities.AddUpdate
import com.example.jetpackcomp.databinding.FragmentHomeBinding
import com.example.jetpackcomp.ViewModel.HomeViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication

class HomeFragment : Fragment() {

  private val mJetPackCompViewModel : JetPackCompViewModel by viewModels{
    JetPackCompViewModelFactory((requireActivity().application as JetPackCompApplication).repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mJetPackCompViewModel.allDishesList.observe(viewLifecycleOwner){
      dishes ->
      run {
        dishes.let {
          for (item in it) {
            Log.i(TAG, "onViewCreated: ${item.id} :: ${item.title}")
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
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
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
}