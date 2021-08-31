package com.example.jetpackcomp.View.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.jetpackcomp.R
import com.karumi.dexter.Dexter
import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.View.Fragments.HomeFragment
import com.example.jetpackcomp.View.adapter.CustomItemListAdapter
import com.example.jetpackcomp.ViewModel.JetPackCompViewModel
import com.example.jetpackcomp.ViewModel.JetPackCompViewModelFactory
import com.example.jetpackcomp.application.JetPackCompApplication
import com.example.jetpackcomp.databinding.ActivityAddUpdateBinding
import com.example.jetpackcomp.databinding.CustomImageSelectionBinding
import com.example.jetpackcomp.databinding.DialogCustomListBinding
import com.example.jetpackcomp.utils.Constants
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdate : AppCompatActivity(), View.OnClickListener {

    lateinit var addUpdate : ActivityAddUpdateBinding
    private var imagePath : String = ""
    private lateinit var customListDialog: Dialog
    private var mFavDishDetails : JetpackComp? = null

    private val jetPackCompViewModel : JetPackCompViewModel by viewModels {
        JetPackCompViewModelFactory((application as JetPackCompApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         addUpdate  = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(addUpdate.root)

        setupActionbar()

        addUpdate.addupdateCamera.setOnClickListener(this)
        addUpdate.etTitle.setOnClickListener(this)
        addUpdate.etCategory.setOnClickListener(this)
        addUpdate.etType.setOnClickListener(this)
        addUpdate.addUpdateSubmit.setOnClickListener(this)

        if(intent.hasExtra(Constants.EXTRA_DISH_DETAILS)){
            mFavDishDetails = intent.getParcelableExtra(Constants.EXTRA_DISH_DETAILS)
        }

        mFavDishDetails?.let {
            if(it.id != 0){
                imagePath = it.image
                Glide.with(this@AddUpdate).load(imagePath).centerCrop().into(addUpdate.imageViewSsey)
                addUpdate.etTitle.setText(it.title)
                addUpdate.etCategory.setText(it.category)
                addUpdate.etType.setText(it.type)
                addUpdate.etIngredients.setText(it.ingredients)
                addUpdate.etCooking.setText(it.cookingTime)
                addUpdate.etCookingDirections.setText(it.DirectionsToCook)

                addUpdate.addUpdateSubmit.text = resources.getString(R.string.Submit)
            }
        }
    }

    private fun setupActionbar(){
        if(mFavDishDetails != null && mFavDishDetails!!.id != 0){
            supportActionBar?.let {
                it.title = resources.getString(R.string.edit_dish)
            }
        }else{
            supportActionBar.let {
                if (it != null) {
                    it.title = resources.getString(R.string.add_dish)
                }
            }
        }

        setSupportActionBar(addUpdate.toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addUpdate.toolbarAdd.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.addupdate_camera -> {
                    customimageselection()
                    return
                }
                R.id.et_category ->{
                    customItemListDialog(resources.getString(R.string.title_select_dish_type), Constants.dishTypes(), Constants.Dish_type)
                }
                R.id.et_type ->{
                    customItemListDialog(resources.getString(R.string.title_select_type), Constants.dishCategory(), Constants.Dish_Category)
                }
                R.id.add_update_submit ->{
                    val title = addUpdate.etTitle.text.toString().trim(){it <= ' '}
                    val category = addUpdate.etCategory.text.toString().trim(){it <= ' '}
                    val type = addUpdate.etType.text.toString().trim(){it <= ' '}
                    val ingredients = addUpdate.etIngredients.toString().trim(){it <= ' '}
                    val cookingTimeMinutes = addUpdate.etCooking.toString().trim(){it <= ' '}
                    val cookingDirections = addUpdate.etCookingDirections.toString().trim(){it <= ' '}

                    when{
                        TextUtils.isEmpty(imagePath) ->{
                            println(imagePath + "Image path")
                            Toast.makeText(this@AddUpdate, "Select a dish image", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(title) ->{
                            println(title + "These are Titles")
                            Toast.makeText(this@AddUpdate, "Select a title", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(category) ->{
                            println(category + "These are Category")
                            Toast.makeText(this@AddUpdate, "Select a category", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(type) ->{
                            Toast.makeText(this@AddUpdate, "Select a type", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(ingredients) ->{
                            Toast.makeText(this@AddUpdate, "Select the ingredients", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(cookingTimeMinutes) ->{
                            println(cookingTimeMinutes + "These are Minutes")
                            Toast.makeText(this@AddUpdate, "Select cooking time", Toast.LENGTH_LONG).show()
                        }
                        TextUtils.isEmpty(cookingDirections) ->{
                            println(cookingDirections + "These are Directions")
                            Toast.makeText(this@AddUpdate, "Be sure to include cooking directions", Toast.LENGTH_LONG).show()
                        }
                        else ->{

                            var dishId = 0
                            var imageSource = Constants.LOCAL_IMAGE_SOURCE
                            var favoriteDish = false

                            mFavDishDetails?.let {
                                if(it.id != 0){
                                    dishId = it.id
                                    imageSource = it.imageSource
                                    favoriteDish = it.FavoriteDish
                                }
                            }

                            val jetPackDetails : JetpackComp = JetpackComp(
                                imagePath,
                                imageSource,
                                title,
                                type,
                                category,
                                ingredients,
                                cookingTimeMinutes,
                                cookingDirections,
                                favoriteDish,
                                dishId
                            )
                            jetPackCompViewModel.insert(jetPackDetails)
                            Toast.makeText(this@AddUpdate, "Successfully added the data", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@AddUpdate, HomeFragment::class.java))
                            finish()

                            if(dishId == 0){
                               jetPackCompViewModel.insert(jetPackDetails)
                                Toast.makeText(this@AddUpdate, "Successfully added the data", Toast.LENGTH_LONG).show()
                            }else{
                                jetPackCompViewModel.update(jetPackDetails)
                                Toast.makeText(this@AddUpdate, "Successfully updated the data", Toast.LENGTH_LONG).show()
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }


    val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val bitmap = it.data?.extras?.get("data") as Bitmap?

        addUpdate.imageViewSsey.setImageBitmap(bitmap)
            addUpdate.imageViewSsey.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_create_24))
            Glide.with(this).load(bitmap).centerCrop().into(addUpdate.imageViewSsey)
            imagePath = bitmap?.let { it1 -> storeImageToInternalStorage(it1) }.toString()
    }

    val getGalleryAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val bitmap = it?.data?.extras?.get("data") as Bitmap?

            addUpdate.imageViewSsey.setImageBitmap(bitmap)
            addUpdate.imageViewSsey.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_create_24))
            println("This is bitmap data" + bitmap )


        Glide.with(this).load(bitmap).centerCrop().
        diskCacheStrategy(DiskCacheStrategy.ALL).listener(object: RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
//                TODO("Not yet implemented")
                Log.e(TAG, "onLoadFailed: Failed to load data", e)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
//                TODO("Not yet implemented")
                resource?.let {
                    val  bitmap : Bitmap = resource.toBitmap()
                    imagePath = storeImageToInternalStorage(bitmap)
                }
                return false
            }

        })
            .into(addUpdate.imageViewSsey)

    }

    fun selectedListItem(item : String, selection : String){
        when(selection){
            Constants.Dish_type ->{
                customListDialog.dismiss()
                addUpdate.etCategory.setText(item)
            }
            Constants.Dish_Category ->{
                customListDialog.dismiss()
                addUpdate.etType.setText(item)
            }
            else ->{
                customListDialog.dismiss()
            }
        }
    }

    fun customimageselection(){
        val customListDialog = Dialog(this)
        val  binding : CustomImageSelectionBinding = CustomImageSelectionBinding.inflate(layoutInflater)
        customListDialog.setContentView(binding.root)
        customListDialog.show()

        binding.tvCamera.setOnClickListener{
            Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).withListener(object: MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if(report.areAllPermissionsGranted()){
                           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            getAction.launch(intent)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showRationalPermissionDialog()
                }
            }).onSameThread().check()
            customListDialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val galleryintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    getGalleryAction.launch(galleryintent)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@AddUpdate, "YOu have denied permission", Toast.LENGTH_LONG).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRationalPermissionDialog()
                }
                }).onSameThread().check()
        }
    }

    private fun showRationalPermissionDialog(){
        AlertDialog.Builder(this)
            .setMessage("Permissions have been turned off required by this application, turn them on in application settings")
            .setPositiveButton("GO TO SETTINGS"){_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("Package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch
                    (E :ActivityNotFoundException){
                    E.printStackTrace()
                }
        }.setNegativeButton("Cancel"){dialog,_ ->
                Toast.makeText(this, "Works posh", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }.show()
    }

    private fun storeImageToInternalStorage(bitmap: Bitmap):String{
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e :IOException){
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun customItemListDialog(title : String, itemList : List<String>, selection : String){
         customListDialog = Dialog(this)
        val binding : DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        customListDialog.setContentView(binding.root)
        binding.tvDialogList.text = title

        binding.rvListDialog.layoutManager = LinearLayoutManager(this)
        val adapter = CustomItemListAdapter(this, null ,itemList, selection)
        binding.rvListDialog.adapter = adapter
        customListDialog.show()
    }

    companion object{
        private const val CAMERA = 1
        private const val GALLERY = 2

        private const val IMAGE_DIRECTORY = "11"
    }
}