package com.example.jetpackcomp.View.Fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.Model.entities.RandomObject
import com.example.jetpackcomp.R
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.databinding.FragmentRandomDishBinding
import com.example.jetpackcomp.ViewModel.NotificationsViewModel
import com.example.jetpackcomp.ViewModel.RandomDishViewModel
import com.example.jetpackcomp.application.JetPackCompApplication
import com.example.jetpackcomp.databinding.DialogCustomListBinding
import com.example.jetpackcomp.utils.Constants

class RandomDishFragment : Fragment() {
  private lateinit var notificationsViewModel: NotificationsViewModel
  private var mBinding : FragmentRandomDishBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.

    private lateinit var mRandomDishViewModel : RandomDishViewModel
    private var mProgressDialog : Dialog? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    mBinding = FragmentRandomDishBinding.inflate(inflater, container, false)
    return mBinding!!.root
  }

    private fun showCustomDialog(){
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }

    private fun hideCustomDialog(){
        mProgressDialog?.let {
            it.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)

        mRandomDishViewModel.getRandomRecipeFromAPI()
        randomDishViewModelObserver()

        mBinding!!.swipeToRefresh.setOnRefreshListener {
            mRandomDishViewModel.getRandomRecipeFromAPI()
        }
    }

    private fun randomDishViewModelObserver(){
        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            {
                randomDishResponse -> randomDishResponse?.let {
                if(mBinding!!.swipeToRefresh.isRefreshing){
                    mBinding!!.swipeToRefresh.isRefreshing = false
                }
                    setRandomDishInUI(randomDishResponse.recipes[0])
            }
            }
        )
        mRandomDishViewModel.randomDishLoadError.observe(viewLifecycleOwner,
            {
                dataError -> dataError?.let {
                    Log.i("Random Dish API Error", "${dataError}")
            }
            })
        mRandomDishViewModel.loadRandomDish.observe(viewLifecycleOwner,
            {
                loadingError -> loadingError?.let {
                    Log.i("Dish Load Error", "${loadingError}")

                if(loadingError && !mBinding!!.swipeToRefresh.isRefreshing){
                    showCustomDialog()
                }else{
                    hideCustomDialog()
                }
            }
            })
    }

    private fun setRandomDishInUI(recipe : RandomObject.Recipe){
        Glide.with(requireActivity()).load(recipe.image).into(mBinding!!.IvImageView)
        mBinding!!.tvDishTitle.text = recipe.title

        var dishType : String  = "Other"
        if(recipe.dishTypes.isNotEmpty()){
            dishType = recipe.dishTypes[0]
            mBinding!!.tvTypeDesc.text = dishType
        }

        mBinding!!.tvIngredientsContent.text = "Other"
        var ingredients = ""
            for (value in recipe.extendedIngredients){
                if(ingredients.isEmpty()){
                    ingredients = value.original
                }else{
                    ingredients = ingredients + ", \n" + value.original
                }
            }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mBinding!!.tvDirectionsContent.text = Html.fromHtml(
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT
            )
        }else{
            mBinding!!.tvDirectionsContent.text = Html.fromHtml(recipe.instructions)
        }

        var addedToFavorite = false

        mBinding!!.TimeRequired.text = recipe.readyInMinutes.toString()

        mBinding!!.ivAddImage.setOnClickListener{
            if(addedToFavorite){
                Toast.makeText(requireActivity(),"Already added to the favorite" ,Toast.LENGTH_LONG).show()
            }else{
                val randomDishDetails = JetpackComp(
                    recipe.image,
                    Constants.ONLINE_IMAGE_SOURCE,
                    recipe.title,
                    dishType,
                    "Other",
                    ingredients,
                    recipe.readyInMinutes.toString(),
                    recipe.instructions,
                    true
                )
                val mFavDishViewModel : JetPackCompViewModel by viewModels {
                    JetPackCompViewModelFactory((requireActivity().application as JetPackCompApplication).repository)
                }
                mFavDishViewModel.insert(randomDishDetails)
            }
        }
        addedToFavorite = true
    }


override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}